
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

package com.bytechef.dione.configuration.web.rest;

import com.bytechef.category.domain.Category;
import com.bytechef.category.web.rest.model.CategoryModel;
import com.bytechef.dione.configuration.facade.IntegrationFacade;
import com.bytechef.dione.configuration.web.rest.config.IntegrationRestTestConfiguration;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Ivica Cardic
 */
@ContextConfiguration(classes = IntegrationRestTestConfiguration.class)
@WebMvcTest(value = IntegrationController.class)
public class IntegrationCategoryControllerIntTest {

    @MockBean
    private IntegrationFacade integrationFacade;

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindTo(mockMvc)
            .build();
    }

    @Test
    public void testGetIntegrationCategories() {
        try {
            when(integrationFacade.getIntegrationCategories()).thenReturn(List.of(new Category(1, "name")));

            this.webTestClient
                .get()
                .uri("/embedded/integration-categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CategoryModel.class)
                .hasSize(1);
        } catch (Exception exception) {
            Assertions.fail(exception);
        }
    }
}