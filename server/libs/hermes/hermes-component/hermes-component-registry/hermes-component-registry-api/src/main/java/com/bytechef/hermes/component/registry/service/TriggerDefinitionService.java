
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

package com.bytechef.hermes.component.registry.service;

import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookEnableOutput;
import com.bytechef.hermes.component.registry.trigger.WebhookRequest;
import com.bytechef.hermes.registry.domain.Option;
import com.bytechef.hermes.component.registry.domain.TriggerDefinition;
import com.bytechef.hermes.registry.domain.ValueProperty;
import com.bytechef.hermes.component.registry.trigger.TriggerOutput;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public interface TriggerDefinitionService {

    List<? extends ValueProperty<?>> executeDynamicProperties(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String propertyName, Long connectionId, Map<String, ?> connectionParameters, String authorizationName);

    void executeDynamicWebhookDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId,
        DynamicWebhookEnableOutput output);

    DynamicWebhookEnableOutput executeDynamicWebhookEnable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String webhookUrl, String workflowExecutionId);

    DynamicWebhookEnableOutput executeDynamicWebhookRefresh(
        String componentName, int componentVersion, String triggerName, DynamicWebhookEnableOutput output);

    String executeEditorDescription(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName);

    void executeListenerDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId);

    void executeOnEnableListener(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId);

    List<Option> executeOptions(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String propertyName, Long connectionId, Map<String, ?> connectionParameters, String authorizationName,
        String searchText);

    List<? extends ValueProperty<?>> executeOutputSchema(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName);

    Object executeSampleOutput(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName);

    TriggerOutput executeTrigger(
        String componentName, int componentVersion, String triggerName, Map<String, ?> inputParameters,
        Object triggerState, WebhookRequest webhookRequest, Map<String, Long> connectionIdMap);

    boolean executeWebhookValidate(
        String componentName, int componentVersion, String triggerName, Map<String, ?> inputParameters,
        WebhookRequest webhookRequest, Map<String, Long> connectionIdMap);

    TriggerDefinition getTriggerDefinition(String componentName, int componentVersion, String triggerName);

    List<TriggerDefinition> getTriggerDefinitions(String componentName, int componentVersion);
}