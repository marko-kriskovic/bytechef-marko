
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

package com.bytechef.hermes.component.registry.facade;

import com.bytechef.hermes.connection.domain.Connection;
import com.bytechef.hermes.connection.service.ConnectionService;
import com.bytechef.hermes.registry.domain.Option;
import com.bytechef.hermes.registry.domain.ValueProperty;
import com.bytechef.hermes.component.registry.service.ActionDefinitionService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
@Service
public class ActionDefinitionFacadeImpl implements ActionDefinitionFacade {

    private final ActionDefinitionService actionDefinitionService;
    private final ConnectionService connectionService;

    @SuppressFBWarnings("EI")
    public ActionDefinitionFacadeImpl(
        ActionDefinitionService actionDefinitionService, ConnectionService connectionService) {

        this.actionDefinitionService = actionDefinitionService;
        this.connectionService = connectionService;
    }

    @Override
    public List<? extends ValueProperty<?>> executeDynamicProperties(
        String componentName, int componentVersion, String actionName, String propertyName,
        Map<String, Object> actionParameters, Long connectionId) {

        Connection connection = connectionId == null ? null : connectionService.getConnection(connectionId);

        return actionDefinitionService.executeDynamicProperties(
            componentName, componentVersion, actionName, propertyName, actionParameters, connectionId,
            connection == null ? null : connection.getParameters(),
            connection == null ? null : connection.getAuthorizationName());
    }

    @Override
    public String executeEditorDescription(
        String componentName, int componentVersion, String actionName, Map<String, Object> actionParameters,
        Long connectionId) {

        Connection connection = connectionId == null ? null : connectionService.getConnection(connectionId);

        return actionDefinitionService.executeEditorDescription(
            componentName, componentVersion, actionName, actionParameters, connectionId,
            connection == null ? null : connection.getParameters(),
            connection == null ? null : connection.getAuthorizationName());
    }

    @Override
    public List<Option> executeOptions(
        String componentName, int componentVersion, String actionName, String propertyName,
        Map<String, Object> actionParameters, Long connectionId, String searchText) {

        Connection connection = connectionId == null ? null : connectionService.getConnection(connectionId);

        return actionDefinitionService.executeOptions(
            componentName, componentVersion, actionName, propertyName, actionParameters,
            searchText, connectionId,
            connection == null ? null : connection.getParameters(),
            connection == null ? null : connection.getAuthorizationName());
    }

    @Override
    public List<? extends ValueProperty<?>> executeOutputSchema(
        String componentName, int componentVersion, String actionName, Map<String, Object> actionParameters,
        Long connectionId) {

        Connection connection = connectionId == null ? null : connectionService.getConnection(connectionId);

        return actionDefinitionService.executeOutputSchema(
            componentName, componentVersion, actionName, actionParameters, connectionId,
            connection == null ? null : connection.getParameters(),
            connection == null ? null : connection.getAuthorizationName());
    }

    @Override
    public Object executeSampleOutput(
        String actionName, String componentName, int componentVersion, Map<String, Object> actionParameters,
        Long connectionId) {

        Connection connection = connectionId == null ? null : connectionService.getConnection(connectionId);

        return actionDefinitionService.executeSampleOutput(
            componentName, componentVersion, actionName, actionParameters, connectionId,
            connection == null ? null : connection.getParameters(),
            connection == null ? null : connection.getAuthorizationName());
    }
}