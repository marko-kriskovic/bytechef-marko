
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

import com.bytechef.commons.util.MapUtils;
import com.bytechef.commons.util.OptionalUtils;
import com.bytechef.hermes.component.definition.Context;
import com.bytechef.hermes.component.definition.ComponentDefinition;
import com.bytechef.hermes.component.definition.ComponentOptionsFunction;
import com.bytechef.hermes.component.definition.ComponentPropertiesFunction;
import com.bytechef.hermes.component.definition.EditorDescriptionDataSource;
import com.bytechef.hermes.component.definition.EditorDescriptionDataSource.EditorDescriptionFunction;
import com.bytechef.hermes.component.definition.OutputSchemaDataSource;
import com.bytechef.hermes.component.definition.OutputSchemaDataSource.OutputSchemaFunction;
import com.bytechef.hermes.component.definition.SampleOutputDataSource;
import com.bytechef.hermes.component.definition.SampleOutputDataSource.SampleOutputFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookDisableConsumer;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookDisableContext;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookEnableFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookEnableOutput;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookRefreshFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookRequestFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.EnableDynamicWebhookContext;
import com.bytechef.hermes.component.definition.TriggerDefinition.HttpHeaders;
import com.bytechef.hermes.component.definition.TriggerDefinition.HttpParameters;
import com.bytechef.hermes.component.definition.TriggerDefinition.ListenerDisableConsumer;
import com.bytechef.hermes.component.definition.TriggerDefinition.ListenerEnableConsumer;
import com.bytechef.hermes.component.definition.TriggerDefinition.PollContext;
import com.bytechef.hermes.component.definition.TriggerDefinition.PollFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.PollOutput;
import com.bytechef.hermes.component.definition.TriggerDefinition.StaticWebhookRequestFunction;
import com.bytechef.hermes.component.definition.TriggerDefinition.TriggerContext;
import com.bytechef.hermes.component.definition.TriggerDefinition.TriggerType;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookBody;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookMethod;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookOutput;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookValidateContext;
import com.bytechef.hermes.component.definition.factory.ContextConnectionFactory;
import com.bytechef.hermes.component.definition.factory.ContextFactory;
import com.bytechef.hermes.definition.DynamicOptionsProperty;
import com.bytechef.hermes.definition.OptionsDataSource;
import com.bytechef.hermes.definition.PropertiesDataSource;
import com.bytechef.hermes.definition.Property.DynamicPropertiesProperty;
import com.bytechef.hermes.component.registry.ComponentDefinitionRegistry;
import com.bytechef.hermes.component.util.ComponentContextSupplier;
import com.bytechef.hermes.registry.domain.Property;
import com.bytechef.hermes.registry.domain.Option;
import com.bytechef.hermes.component.registry.domain.TriggerDefinition;
import com.bytechef.hermes.registry.domain.ValueProperty;
import com.bytechef.hermes.execution.WorkflowExecutionId;
import com.bytechef.hermes.execution.message.broker.TriggerMessageRoute;
import com.bytechef.hermes.component.registry.trigger.WebhookRequest;
import com.bytechef.hermes.component.registry.trigger.TriggerOutput;
import com.bytechef.message.broker.MessageBroker;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * @author Ivica Cardic
 */
@Service("triggerDefinitionService")
public class TriggerDefinitionServiceImpl implements TriggerDefinitionService {

    private final ComponentDefinitionRegistry componentDefinitionRegistry;
    private final ContextConnectionFactory contextConnectionFactory;
    private final ContextFactory contextFactory;
    private final MessageBroker messageBroker;

    @SuppressFBWarnings("EI2")
    public TriggerDefinitionServiceImpl(
        ComponentDefinitionRegistry componentDefinitionRegistry, ContextConnectionFactory contextConnectionFactory,
        ContextFactory contextFactory, MessageBroker messageBroker) {

        this.componentDefinitionRegistry = componentDefinitionRegistry;
        this.contextConnectionFactory = contextConnectionFactory;
        this.contextFactory = contextFactory;
        this.messageBroker = messageBroker;
    }

    @Override
    public List<? extends ValueProperty<?>> executeDynamicProperties(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String propertyName, Long connectionId, Map<String, ?> connectionParameters, String authorizationName) {

        ComponentPropertiesFunction propertiesFunction = getComponentPropertiesFunction(
            componentName, componentVersion, triggerName, propertyName);

        return ComponentContextSupplier.get(
            getTriggerContext(componentName, connectionId),
            () -> {
                List<? extends com.bytechef.hermes.definition.Property.ValueProperty<?>> valueProperties =
                    propertiesFunction.apply(
                        contextConnectionFactory.createConnection(
                            componentName, componentVersion, connectionParameters, authorizationName),
                        triggerParameters);

                return valueProperties.stream()
                    .map(valueProperty -> (ValueProperty<?>) Property.toProperty(valueProperty))
                    .toList();
            });
    }

    @Override
    public void executeDynamicWebhookDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId,
        DynamicWebhookEnableOutput output) {

        DynamicWebhookDisableConsumer dynamicWebhookDisableConsumer = getDynamicWebhookDisableConsumer(
            componentName, componentVersion, triggerName);

        DynamicWebhookDisableContext context = new DynamicWebhookDisableContextImpl(
            triggerParameters, contextConnectionFactory.createConnection(
                componentName, componentVersion, connectionParameters, authorizationName),
            output, workflowExecutionId);

        dynamicWebhookDisableConsumer.accept(context);
    }

    @Override
    public DynamicWebhookEnableOutput executeDynamicWebhookEnable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String webhookUrl, String workflowExecutionId) {

        DynamicWebhookEnableFunction dynamicWebhookEnableFunction = getDynamicWebhookEnableFunction(
            componentName, componentVersion, triggerName);

        EnableDynamicWebhookContext context = new EnableDynamicWebhookContextImpl(
            triggerParameters, contextConnectionFactory.createConnection(
                componentName, componentVersion, connectionParameters, authorizationName),
            webhookUrl, workflowExecutionId);

        return dynamicWebhookEnableFunction.apply(context);
    }

    @Override
    public DynamicWebhookEnableOutput executeDynamicWebhookRefresh(
        String componentName, int componentVersion, String triggerName, DynamicWebhookEnableOutput output) {

        DynamicWebhookRefreshFunction dynamicWebhookRefreshFunction = getDynamicWebhookRefreshFunction(
            componentName, componentVersion, triggerName);

        return dynamicWebhookRefreshFunction.apply(output);
    }

    @Override
    public String executeEditorDescription(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName) {

        EditorDescriptionFunction editorDescriptionFunction = getEditorDescriptionFunction(
            componentName, componentVersion, triggerName);

        return ComponentContextSupplier.get(
            getTriggerContext(componentName, connectionId),
            () -> {
                return editorDescriptionFunction.apply(
                    contextConnectionFactory.createConnection(
                        componentName, componentVersion, connectionParameters, authorizationName),
                    triggerParameters);
            });
    }

    @Override
    public void executeListenerDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId) {

        ListenerDisableConsumer listenerDisableConsumer = getListenerDisableConsumer(
            componentName, componentVersion, triggerName);

        listenerDisableConsumer.accept(
            contextConnectionFactory.createConnection(
                componentName, componentVersion, connectionParameters, authorizationName),
            triggerParameters, workflowExecutionId);
    }

    @Override
    public void executeOnEnableListener(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Map<String, ?> connectionParameters, String authorizationName, String workflowExecutionId) {

        ListenerEnableConsumer listenerEnableConsumer = getListenerEnableConsumer(
            componentName, componentVersion, triggerName);

        listenerEnableConsumer.accept(
            contextConnectionFactory.createConnection(
                componentName, componentVersion, connectionParameters, authorizationName),
            triggerParameters, workflowExecutionId,
            output -> messageBroker.send(
                TriggerMessageRoute.LISTENERS,
                new ListenerParameters(WorkflowExecutionId.parse(workflowExecutionId), output)));
    }

    @Override
    public List<Option> executeOptions(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String propertyName, Long connectionId, Map<String, ?> connectionParameters, String authorizationName,
        String searchText) {

        ComponentOptionsFunction optionsFunction = getComponentOptionsFunction(
            componentName, componentVersion, triggerName, propertyName);

        return ComponentContextSupplier.get(
            getTriggerContext(componentName, connectionId),
            () -> {
                List<com.bytechef.hermes.definition.Option<?>> options = optionsFunction.apply(
                    contextConnectionFactory.createConnection(
                        componentName, componentVersion, connectionParameters, authorizationName),
                    triggerParameters, searchText);

                return options.stream()
                    .map(Option::new)
                    .toList();
            });
    }

    @Override
    public List<? extends ValueProperty<?>> executeOutputSchema(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName) {

        OutputSchemaFunction outputSchemaFunction = getOutputSchemaFunction(
            componentName, componentVersion, triggerName);

        return ComponentContextSupplier.get(
            getTriggerContext(componentName, connectionId),
            () -> Property.toProperty(
                outputSchemaFunction.apply(
                    contextConnectionFactory.createConnection(
                        componentName, componentVersion, connectionParameters, authorizationName),
                    triggerParameters)));
    }

    @Override
    public Object executeSampleOutput(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        Long connectionId, Map<String, ?> connectionParameters, String authorizationName) {

        SampleOutputFunction sampleOutputFunction = getSampleOutputFunction(
            componentName, componentVersion, triggerName);

        return ComponentContextSupplier.get(
            getTriggerContext(componentName, connectionId),
            () -> sampleOutputFunction.apply(
                contextConnectionFactory.createConnection(
                    componentName, componentVersion, connectionParameters, authorizationName),
                triggerParameters));
    }

    @Override
    public TriggerOutput executeTrigger(
        String componentName, int componentVersion, String triggerName, Map<String, ?> inputParameters,
        Object triggerState, WebhookRequest webhookRequest, Map<String, Long> connectionIdMap) {

        TriggerContext triggerContext =
            contextFactory.createTriggerContext(connectionIdMap);
        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return ComponentContextSupplier.get(triggerContext, () -> doExecuteTrigger(
            inputParameters, triggerState, webhookRequest, triggerContext, triggerDefinition));
    }

    @Override
    public boolean executeWebhookValidate(
        String componentName, int componentVersion, String triggerName, Map<String, ?> inputParameters,
        WebhookRequest webhookRequest, Map<String, Long> connectionIdMap) {

        TriggerContext triggerContext =
            contextFactory.createTriggerContext(connectionIdMap);
        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return executeWebhookValidate(triggerDefinition, triggerContext, inputParameters, webhookRequest);
    }

    @Override
    public TriggerDefinition getTriggerDefinition(String componentName, int componentVersion, String triggerName) {
        return new TriggerDefinition(
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName));
    }

    @Override
    public List<TriggerDefinition> getTriggerDefinitions(String componentName, int componentVersion) {
        return componentDefinitionRegistry.getTriggerDefinitions(componentName, componentVersion)
            .stream()
            .map(TriggerDefinition::new)
            .toList();
    }

    @SuppressWarnings("unchecked")
    private TriggerOutput doExecuteTrigger(
        Map<String, ?> inputParameters, Object triggerState, WebhookRequest webhookRequest,
        TriggerContext triggerContext, com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition) {

        TriggerOutput triggerOutput;
        TriggerType triggerType = triggerDefinition.getType();

        if ((TriggerType.DYNAMIC_WEBHOOK == triggerType || TriggerType.STATIC_WEBHOOK == triggerType) &&
            !executeWebhookValidate(
                triggerDefinition, triggerContext, inputParameters, webhookRequest)) {

            throw new IllegalStateException("Invalid trigger signature.");
        }

        if (TriggerType.DYNAMIC_WEBHOOK == triggerType) {
            triggerOutput = triggerDefinition.getDynamicWebhookRequest()
                .map(dynamicWebhookRequestFunction -> executeDynamicWebhookTrigger(
                    triggerContext, inputParameters, (DynamicWebhookEnableOutput) triggerState, webhookRequest,
                    dynamicWebhookRequestFunction))
                .orElseThrow();
        } else if (TriggerType.STATIC_WEBHOOK == triggerType) {
            triggerOutput = triggerDefinition.getStaticWebhookRequest()
                .map(staticWebhookRequestFunction -> executeStaticWebhookTrigger(
                    triggerContext, inputParameters, webhookRequest, staticWebhookRequestFunction))
                .orElseThrow();
        } else if (TriggerType.POLLING == triggerType || TriggerType.HYBRID == triggerType) {
            triggerOutput = triggerDefinition.getPoll()
                .map(pollFunction -> executePollingTrigger(
                    triggerDefinition, triggerContext, inputParameters, (Map<String, Object>) triggerState,
                    pollFunction))
                .orElseThrow();
        } else {
            throw new IllegalArgumentException("Unknown trigger type: " + triggerType);
        }

        return triggerOutput;
    }

    private static TriggerOutput executeDynamicWebhookTrigger(
        TriggerContext triggerContext, Map<String, ?> inputParameters, DynamicWebhookEnableOutput triggerState,
        WebhookRequest webhookRequest, DynamicWebhookRequestFunction dynamicWebhookRequestFunction) {

        WebhookOutput webhookOutput = dynamicWebhookRequestFunction.apply(
            new DynamicWebhookRequestContextImpl(
                inputParameters, new HttpHeadersImpl(webhookRequest.headers()),
                new HttpParametersImpl(webhookRequest.parameters()), webhookRequest.body(), webhookRequest.method(),
                triggerState, triggerContext));

        return new TriggerOutput(webhookOutput.getValue(), null, false);
    }

    private static TriggerOutput executePollingTrigger(
        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition,
        TriggerContext triggerContext, Map<String, ?> inputParameters, Map<String, Object> triggerState,
        PollFunction pollFunction) {

        PollOutput pollOutput = pollFunction.apply(
            new PollContextImpl(triggerState, inputParameters, triggerContext));

        List<Map<?, ?>> records = new ArrayList<>(
            pollOutput.records() == null ? Collections.emptyList() : pollOutput.records());

        while (pollOutput.pollImmediately()) {
            pollOutput = pollFunction.apply(
                new PollContextImpl(pollOutput.closureParameters(), inputParameters, triggerContext));

            records.addAll(pollOutput.records());
        }

        return new TriggerOutput(
            records, pollOutput.closureParameters(),
            OptionalUtils.orElse(triggerDefinition.getBatch(), false));
    }

    private static TriggerOutput executeStaticWebhookTrigger(
        TriggerContext triggerContext,
        Map<String, ?> inputParameters, WebhookRequest webhookRequest,
        StaticWebhookRequestFunction staticWebhookRequestFunction) {

        WebhookOutput webhookOutput = staticWebhookRequestFunction.apply(
            new StaticWebhookRequestContextImpl(
                inputParameters, new HttpHeadersImpl(webhookRequest.headers()),
                new HttpParametersImpl(webhookRequest.parameters()), webhookRequest.body(), webhookRequest.method(),
                triggerContext));

        return new TriggerOutput(webhookOutput.getValue(), null, false);
    }

    private boolean executeWebhookValidate(
        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition,
        TriggerContext triggerContext,
        Map<String, ?> inputParameters, WebhookRequest webhookRequest) {

        WebhookValidateContext context = new WebhookValidateContextImpl(
            inputParameters, new HttpHeadersImpl(webhookRequest.headers()),
            new HttpParametersImpl(webhookRequest.parameters()), webhookRequest.body(), webhookRequest.method(),
            triggerContext);

        return triggerDefinition.getWebhookValidate()
            .map(webhookValidateFunction -> webhookValidateFunction.apply(context))
            .orElse(true);
    }

    private ComponentOptionsFunction getComponentOptionsFunction(
        String componentName, int componentVersion, String triggerName, String propertyName) {

        DynamicOptionsProperty dynamicOptionsProperty = (DynamicOptionsProperty) componentDefinitionRegistry
            .getTriggerProperty(componentName, componentVersion, triggerName, propertyName);

        OptionsDataSource optionsDataSource = OptionalUtils.get(dynamicOptionsProperty.getOptionsDataSource());

        return (ComponentOptionsFunction) optionsDataSource.getOptions();
    }

    private ComponentPropertiesFunction getComponentPropertiesFunction(
        String componentName, int componentVersion, String triggerName, String propertyName) {

        DynamicPropertiesProperty property = (DynamicPropertiesProperty) componentDefinitionRegistry.getTriggerProperty(
            componentName, componentVersion, triggerName, propertyName);

        PropertiesDataSource propertiesDataSource = property.getDynamicPropertiesDataSource();

        return (ComponentPropertiesFunction) propertiesDataSource.getProperties();
    }

    private DynamicWebhookRefreshFunction getDynamicWebhookRefreshFunction(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.get(triggerDefinition.getDynamicWebhookRefresh());
    }

    private DynamicWebhookDisableConsumer getDynamicWebhookDisableConsumer(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.get(triggerDefinition.getDynamicWebhookDisable());
    }

    private DynamicWebhookEnableFunction getDynamicWebhookEnableFunction(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.get(triggerDefinition.getDynamicWebhookEnable());
    }

    private EditorDescriptionFunction getEditorDescriptionFunction(
        String componentName, int componentVersion, String triggerName) {

        ComponentDefinition componentDefinition = componentDefinitionRegistry.getComponentDefinition(
            componentName, componentVersion);

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.mapOrElse(
            triggerDefinition.getEditorDescriptionDataSource(),
            EditorDescriptionDataSource::getEditorDescription,
            (Context.Connection connection, Map<String, ?> inputParameters) -> componentDefinition.getTitle() +
                ": " + triggerDefinition.getTitle());
    }

    private ListenerDisableConsumer getListenerDisableConsumer(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.get(triggerDefinition.getListenerDisable());
    }

    private ListenerEnableConsumer getListenerEnableConsumer(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        return OptionalUtils.get(triggerDefinition.getListenerEnable());
    }

    private OutputSchemaFunction getOutputSchemaFunction(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        OutputSchemaDataSource outputSchemaDataSource = OptionalUtils.get(
            triggerDefinition.getOutputSchemaDataSource());

        return outputSchemaDataSource.getOutputSchema();
    }

    private SampleOutputFunction getSampleOutputFunction(
        String componentName, int componentVersion, String triggerName) {

        com.bytechef.hermes.component.definition.TriggerDefinition triggerDefinition =
            componentDefinitionRegistry.getTriggerDefinition(componentName, componentVersion, triggerName);

        SampleOutputDataSource sampleOutputDataSource = OptionalUtils.get(
            triggerDefinition.getSampleOutputDataSource());

        return sampleOutputDataSource.getSampleOutput();
    }

    private TriggerContext getTriggerContext(String componentName, Long connectionId) {
        return contextFactory.createTriggerContext(
            connectionId == null ? Map.of() : Map.of(componentName, connectionId));
    }

    private record DynamicWebhookDisableContextImpl(
        Map<String, ?> inputParameters, Context.Connection connection,
        DynamicWebhookEnableOutput dynamicWebhookEnableOutput, String workflowExecutionId)
        implements DynamicWebhookDisableContext {
    }

    private record DynamicWebhookRequestContextImpl(
        Map<String, ?> inputParameters, HttpHeaders headers, HttpParameters parameters, WebhookBody body,
        WebhookMethod method, DynamicWebhookEnableOutput dynamicWebhookEnableOutput, TriggerContext triggerContext)
        implements com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookRequestContext {
    }

    private record EnableDynamicWebhookContextImpl(
        Map<String, ?> inputParameters, Context.Connection connection, String webhookUrl,
        String workflowExecutionId) implements EnableDynamicWebhookContext {
    }

    private static class AbstractParameters {

        private final Map<String, String[]> parameters;

        private AbstractParameters(Map<String, String[]> parameters) {
            this.parameters = parameters;
        }

        public List<String> allValues(String name) {
            return parameters.values()
                .stream()
                .flatMap(Arrays::stream)
                .toList();
        }

        public Optional<String> firstValue(String name) {
            Optional<String> optional = Optional.empty();

            if (parameters.containsKey(name)) {
                String[] values = parameters.get(name);

                if (values != null && values.length > 0) {
                    optional = Optional.of(values[0]);
                }
            }

            return optional;
        }

        public OptionalLong firstValueAsLong(String name) {
            return firstValue(name).stream()
                .mapToLong(Long::valueOf)
                .findFirst();
        }

        public Map<String, List<String>> map() {
            return MapUtils.toMap(parameters, Map.Entry::getKey, entry -> Arrays.asList(entry.getValue()));
        }
    }

    private static class HttpHeadersImpl extends AbstractParameters implements HttpHeaders {

        private HttpHeadersImpl(Map<String, String[]> parameters) {
            super(parameters);
        }
    }

    private static class HttpParametersImpl extends AbstractParameters implements HttpParameters {

        private HttpParametersImpl(Map<String, String[]> parameters) {
            super(parameters);
        }
    }

    private record ListenerParameters(WorkflowExecutionId workflowExecutionId, Object output) {
    }

    private record PollContextImpl(
        Map<String, ?> inputParameters, Map<String, ?> closureParameters, TriggerContext triggerContext)
        implements PollContext {
    }

    private record StaticWebhookRequestContextImpl(
        Map<String, ?> inputParameters, HttpHeaders headers, HttpParameters parameters, WebhookBody body,
        WebhookMethod method, TriggerContext triggerContext)
        implements com.bytechef.hermes.component.definition.TriggerDefinition.StaticWebhookRequestContext {
    }

    private record WebhookValidateContextImpl(
        Map<String, ?> inputParameters, HttpHeaders headers,
        HttpParameters parameters, WebhookBody body, WebhookMethod method, TriggerContext triggerContext)
        implements WebhookValidateContext {
    }
}