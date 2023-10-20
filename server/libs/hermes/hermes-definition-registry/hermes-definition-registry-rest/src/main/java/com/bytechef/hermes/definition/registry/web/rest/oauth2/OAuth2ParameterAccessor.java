
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

package com.bytechef.hermes.definition.registry.web.rest.oauth2;

import com.bytechef.hermes.connection.config.OAuth2Properties;
import com.bytechef.hermes.definition.registry.dto.OAuth2AuthorizationParametersDTO;
import com.bytechef.hermes.definition.registry.service.ConnectionDefinitionService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
@Component
public class OAuth2ParameterAccessor {

    private final ConnectionDefinitionService connectionDefinitionService;
    private final OAuth2Properties oAuth2Properties;

    @SuppressFBWarnings("EI")
    public OAuth2ParameterAccessor(
        ConnectionDefinitionService connectionDefinitionService, OAuth2Properties oAuth2Properties) {

        this.connectionDefinitionService = connectionDefinitionService;
        this.oAuth2Properties = oAuth2Properties;
    }

    public OAuth2AuthorizationParametersDTO getOAuth2Parameters(
        String componentName, int connectionVersion, Map<String, ?> connectionParameters, String authorizationName) {

        return connectionDefinitionService.getOAuth2Parameters(
            componentName, connectionVersion, oAuth2Properties.checkPredefinedApp(componentName, connectionParameters),
            authorizationName);
    }
}