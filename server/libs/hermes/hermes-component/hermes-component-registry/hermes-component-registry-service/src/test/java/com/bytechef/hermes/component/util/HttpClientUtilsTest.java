
/*
 * Copyright 2021 <your company/name>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.hermes.component.util;

import com.bytechef.hermes.component.definition.Context;
import com.bytechef.hermes.component.definition.AuthorizationContextConnection;
import com.bytechef.hermes.component.definition.Authorization;
import com.bytechef.hermes.component.definition.Authorization.ApplyFunction;
import com.bytechef.hermes.component.definition.Authorization.ApplyResponse;
import com.bytechef.hermes.component.definition.Authorization.AuthorizationType;
import com.bytechef.hermes.component.definition.ComponentDSL;
import com.bytechef.hermes.component.util.HttpClientUtils.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.mizosoft.methanol.FormBodyPublisher;
import com.github.mizosoft.methanol.MediaType;
import com.github.mizosoft.methanol.MultipartBodyPublisher;
import com.github.mizosoft.methanol.internal.extensions.MimeBodyPublisherAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.net.ssl.SSLSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Ivica Cardic
 */
public class HttpClientUtilsTest {

    private static final Context CONTEXT = Mockito.mock(Context.class);
    private static final Configuration EMPTY_CONFIGURATION = Configuration.newConfiguration()
        .build();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final HttpClientExecutorImpl HTTP_CLIENT_EXECUTOR = new HttpClientExecutorImpl();

    @BeforeAll
    public static void beforeAll() {
        HTTP_CLIENT_EXECUTOR.objectMapper = new ObjectMapper();
        HTTP_CLIENT_EXECUTOR.xmlMapper = new XmlMapper();
    }

    @Test
    public void testCreateBodyHandler() {
        HttpResponse.BodyHandler<?> bodyHandler = HTTP_CLIENT_EXECUTOR.createBodyHandler(
            EMPTY_CONFIGURATION);

        Assertions.assertEquals(bodyHandler, HttpResponse.BodyHandlers.discarding());

        //

        bodyHandler = HTTP_CLIENT_EXECUTOR.createBodyHandler(
            HttpClientUtils.responseType(HttpClientUtils.ResponseType.BINARY)
                .build());

        Assertions.assertEquals(bodyHandler, HttpResponse.BodyHandlers.ofInputStream());

        //

        bodyHandler = HTTP_CLIENT_EXECUTOR.createBodyHandler(
            HttpClientUtils.responseType(HttpClientUtils.ResponseType.XML)
                .build());

        Assertions.assertEquals(bodyHandler, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void testCreateBodyPublisher() {
        Context.FileEntry fileEntry = Mockito.mock(Context.FileEntry.class);

        Mockito.when(fileEntry.getName())
            .thenReturn("fileName");
        Mockito.when(fileEntry.getExtension())
            .thenReturn("txt");
        Mockito.when(fileEntry.getMimeType())
            .thenReturn("text/plain");

        MultipartBodyPublisher multipartBodyPublisher =
            (MultipartBodyPublisher) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
                CONTEXT,
                HttpClientUtils.Body.of(
                    Map.of("key1", "value1", "key2", fileEntry), HttpClientUtils.BodyContentType.FORM_DATA));

        Assertions.assertTrue(multipartBodyPublisher.mediaType()
            .toString()
            .startsWith("multipart/form-data"));

        MimeBodyPublisherAdapter mimeBodyPublisherAdapter = (MimeBodyPublisherAdapter) multipartBodyPublisher.parts()
            .stream()
            .map(MultipartBodyPublisher.Part::bodyPublisher)
            .filter(bodyPublisher -> bodyPublisher instanceof MimeBodyPublisherAdapter)
            .findFirst()
            .orElseThrow();

        Assertions.assertEquals(MediaType.TEXT_PLAIN, mimeBodyPublisherAdapter.mediaType());

        //

        FormBodyPublisher formBodyPublisher = (FormBodyPublisher) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
            CONTEXT,
            HttpClientUtils.Body.of(
                Map.of("key1", "value1", "key2", "value2"), HttpClientUtils.BodyContentType.FORM_URL_ENCODED));

        Assertions.assertEquals(MediaType.APPLICATION_FORM_URLENCODED, formBodyPublisher.mediaType());

        Assertions.assertTrue(formBodyPublisher.encodedString()
            .contains("key1=value1"));
        Assertions.assertTrue(formBodyPublisher.encodedString()
            .contains("key2=value2"));

        //

        mimeBodyPublisherAdapter = (MimeBodyPublisherAdapter) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
            CONTEXT,
            HttpClientUtils.Body.of(Map.of("key1", "value1"), HttpClientUtils.BodyContentType.JSON));

        Assertions.assertEquals(MediaType.APPLICATION_JSON, mimeBodyPublisherAdapter.mediaType());

        //

        mimeBodyPublisherAdapter = (MimeBodyPublisherAdapter) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
            CONTEXT,
            HttpClientUtils.Body.of(Map.of("key1", "value1"), HttpClientUtils.BodyContentType.XML));

        Assertions.assertEquals(MediaType.APPLICATION_XML, mimeBodyPublisherAdapter.mediaType());

        //

        mimeBodyPublisherAdapter = (MimeBodyPublisherAdapter) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
            CONTEXT, HttpClientUtils.Body.of("text"));

        Assertions.assertEquals(MediaType.TEXT_PLAIN, mimeBodyPublisherAdapter.mediaType());

        HttpRequest.BodyPublisher emptyBodyPublisher = HTTP_CLIENT_EXECUTOR.createBodyPublisher(CONTEXT, null);

        Assertions.assertEquals(0, emptyBodyPublisher.contentLength());

        //

        fileEntry = Mockito.mock(Context.FileEntry.class);

        Mockito.when(fileEntry.getMimeType())
            .thenReturn("text/plain");
        Mockito.when(fileEntry.getName())
            .thenReturn("fileName");
        Mockito.when(fileEntry.getUrl())
            .thenReturn("base64:text");

        mimeBodyPublisherAdapter = (MimeBodyPublisherAdapter) HTTP_CLIENT_EXECUTOR.createBodyPublisher(
            CONTEXT, HttpClientUtils.Body.of(fileEntry));

        Assertions.assertEquals(MediaType.TEXT_PLAIN, mimeBodyPublisherAdapter.mediaType());

        //

        HttpRequest.BodyPublisher bodyPublisher = HTTP_CLIENT_EXECUTOR.createBodyPublisher(CONTEXT, null);

        Assertions.assertEquals(0, bodyPublisher.contentLength());
    }

    @Test
    @SuppressFBWarnings("RV")
    @SuppressWarnings("checkstyle:methodlengthcheck")
    public void testCreateHTTPClient() {
        HttpClient httpClient = HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), new HashMap<>(), HttpClientUtils.allowUnauthorizedCerts(true)
                .build());

        Assertions.assertTrue(httpClient.authenticator()
            .isEmpty());

        Assertions.assertNotNull(httpClient.sslContext());

        //

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(AuthorizationType.API_KEY.name(), AuthorizationType.API_KEY))
                            .parameters(
                                Map.of(Authorization.KEY, Authorization.API_TOKEN, Authorization.VALUE,
                                    "token_value"))));

        Map<String, List<String>> headers = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(CONTEXT, headers, new HashMap<>(), EMPTY_CONFIGURATION);

        Assertions.assertEquals(Map.of(Authorization.API_TOKEN, List.of("token_value")), headers);

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(AuthorizationType.API_KEY.name(), AuthorizationType.API_KEY))
                            .parameters(
                                Map.of(
                                    Authorization.KEY, Authorization.API_TOKEN,
                                    Authorization.VALUE, "token_value",
                                    Authorization.ADD_TO, Authorization.ApiTokenLocation.QUERY_PARAMETERS.name()))));

        Map<String, List<String>> queryParameters = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), queryParameters, EMPTY_CONFIGURATION);

        Assertions.assertEquals(Map.of(Authorization.API_TOKEN, List.of("token_value")), queryParameters);

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(
                            Authorization.AuthorizationType.BASIC_AUTH.name(),
                            Authorization.AuthorizationType.BASIC_AUTH))
                                .parameters(
                                    Map.of(Authorization.USERNAME, "username", Authorization.PASSWORD, "password"))));

        headers = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(CONTEXT, headers, new HashMap<>(), EMPTY_CONFIGURATION);

        Assertions.assertEquals(
            Map.of(
                "Authorization",
                List.of("Basic " + ENCODER
                    .encodeToString("username:password".getBytes(StandardCharsets.UTF_8)))),
            headers);

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(
                            Authorization.AuthorizationType.BEARER_TOKEN.name(),
                            Authorization.AuthorizationType.BEARER_TOKEN))
                                .parameters(Map.of(Authorization.TOKEN, "token"))));

        headers = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(CONTEXT, headers, new HashMap<>(), EMPTY_CONFIGURATION);

        Assertions.assertEquals(Map.of("Authorization", List.of("Bearer token")), headers);

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(
                            Authorization.AuthorizationType.DIGEST_AUTH.name(),
                            Authorization.AuthorizationType.DIGEST_AUTH))
                                .parameters(
                                    Map.of(Authorization.USERNAME, "username", Authorization.PASSWORD, "password"))));

        headers = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(CONTEXT, headers, new HashMap<>(), EMPTY_CONFIGURATION);

        Assertions.assertEquals(
            Map.of(
                "Authorization",
                List.of("Basic " + ENCODER.encodeToString("username:password".getBytes(StandardCharsets.UTF_8)))),
            headers);

        Mockito
            .when(CONTEXT.fetchConnection())
            .thenReturn(
                Optional.of(
                    new MockConnection(
                        ComponentDSL.authorization(
                            Authorization.AuthorizationType.OAUTH2_AUTHORIZATION_CODE.name(),
                            Authorization.AuthorizationType.OAUTH2_AUTHORIZATION_CODE))
                                .parameters(Map.of(Authorization.ACCESS_TOKEN, "access_token"))));

        headers = new HashMap<>();

        HTTP_CLIENT_EXECUTOR.createHttpClient(CONTEXT, headers, new HashMap<>(), EMPTY_CONFIGURATION);

        Assertions.assertEquals(Map.of("Authorization", List.of("Bearer access_token")), headers);

        //

        httpClient = HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), new HashMap<>(), HttpClientUtils.followRedirect(true)
                .build());

        Assertions.assertNotNull(httpClient.followRedirects());

        //

        httpClient = HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), new HashMap<>(), HttpClientUtils.followAllRedirects(true)
                .build());

        Assertions.assertNotNull(httpClient.followRedirects());

        //

        httpClient = HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), new HashMap<>(), HttpClientUtils.proxy("10.11.12.13:30")
                .build());

        Assertions.assertTrue(httpClient.proxy()
            .isPresent());

        //

        httpClient = HTTP_CLIENT_EXECUTOR.createHttpClient(
            CONTEXT, new HashMap<>(), new HashMap<>(), HttpClientUtils.timeout(Duration.ofMillis(2000))
                .build());

        Assertions.assertEquals(
            Duration.ofMillis(2000), httpClient.connectTimeout()
                .orElseThrow());
    }

    @Test
    public void testCreateHTTPRequest() {
        HttpRequest httpRequest = HTTP_CLIENT_EXECUTOR.createHTTPRequest(
            CONTEXT, "http://localhost:8080", HttpClientUtils.RequestMethod.DELETE,
            Map.of("header1", List.of("value1")), Map.of("param1", List.of("value1")), null);

        Assertions.assertEquals(HttpClientUtils.RequestMethod.DELETE.name(), httpRequest.method());
        Assertions.assertEquals(
            Map.of("header1", List.of("value1")), httpRequest.headers()
                .map());
        Assertions.assertEquals(URI.create("http://localhost:8080?param1=value1"), httpRequest.uri());
    }

    @Test
    public void testHandleResponse() throws Exception {
        Assertions.assertNull(
            HTTP_CLIENT_EXECUTOR.handleResponse(CONTEXT, new TestHttpResponse(null), EMPTY_CONFIGURATION)
                .body());

        //

        Context.FileEntry fileEntry = Mockito.mock(Context.FileEntry.class);

        Mockito.when(CONTEXT.storeFileContent(Mockito.anyString(), (InputStream) Mockito.any()))
            .thenReturn(fileEntry);

        Assertions.assertEquals(
            fileEntry,
            HTTP_CLIENT_EXECUTOR.handleResponse(
                CONTEXT, new TestHttpResponse(new ByteArrayInputStream("text".getBytes(StandardCharsets.UTF_8))),
                HttpClientUtils.responseType(HttpClientUtils.ResponseType.BINARY)
                    .build())
                .body());

        //

        Assertions.assertEquals(
            Map.of("key1", "value1"),
            HTTP_CLIENT_EXECUTOR.handleResponse(
                CONTEXT,
                new TestHttpResponse(
                    """
                        {
                            "key1": "value1"
                        }
                        """),
                HttpClientUtils.responseType(HttpClientUtils.ResponseType.JSON)
                    .build())
                .body());

        //

        Assertions.assertEquals(
            "text",
            HTTP_CLIENT_EXECUTOR.handleResponse(
                CONTEXT, new TestHttpResponse("text"),
                HttpClientUtils.responseType(HttpClientUtils.ResponseType.TEXT)
                    .build())
                .body());

        //

        Assertions.assertEquals(
            Map.of("object", Map.of("key1", "value1")),
            HTTP_CLIENT_EXECUTOR.handleResponse(
                CONTEXT,
                new TestHttpResponse(
                    """
                        <root>
                            <object>
                                <key1>value1</key1>
                            </object>
                        </root>

                        """),
                HttpClientUtils.responseType(HttpClientUtils.ResponseType.XML)
                    .build())
                .body());

        //

        Assertions.assertEquals(
            new HttpClientUtils.Response(Map.of(), "text", 200),
            HTTP_CLIENT_EXECUTOR.handleResponse(
                CONTEXT, new TestHttpResponse("text"),
                HttpClientUtils.responseType(HttpClientUtils.ResponseType.TEXT)
                    .build()));
    }

    private static class TestHttpResponse implements HttpResponse<Object> {

        private final Object body;
        private final int statusCode;

        private TestHttpResponse(Object body) {
            this(body, 200);
        }

        private TestHttpResponse(Object body, int statusCode) {
            this.body = body;
            this.statusCode = statusCode;
        }

        @Override
        public int statusCode() {
            return statusCode;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<Object>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return HttpHeaders.of(Map.of(), (n, v) -> true);
        }

        @Override
        public Object body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }

    @SuppressFBWarnings("NP")
    private static class MockConnection implements Context.Connection, AuthorizationContextConnection {

        private final Authorization authorization;
        private final Map<String, Object> parameters = new HashMap<>();

        public MockConnection(Authorization authorization) {
            this.authorization = authorization;
        }

        @Override
        public ApplyResponse applyAuthorization() {
            // TODO mock ConnectionDefinitionService
            ApplyFunction applyFunction = AuthorizationUtils.getDefaultApply(authorization.getType());

            return applyFunction.apply(parameters);
        }

        @Override
        public Optional<String> fetchBaseUri() {
            return Optional.empty();
        }

        @Override
        public Map<String, Object> getParameters() {
            return null;
        }

        public MockConnection parameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);

            return this;
        }

    }
}