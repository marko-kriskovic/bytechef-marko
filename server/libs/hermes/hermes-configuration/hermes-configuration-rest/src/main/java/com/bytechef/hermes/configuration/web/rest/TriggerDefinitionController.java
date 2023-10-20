
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

package com.bytechef.hermes.configuration.web.rest;

import com.bytechef.autoconfigure.annotation.ConditionalOnEnabled;
import com.bytechef.commons.util.CollectionUtils;
import com.bytechef.hermes.component.registry.ComponentOperation;
import com.bytechef.hermes.component.registry.facade.RemoteTriggerDefinitionFacade;
import com.bytechef.hermes.component.registry.service.RemoteTriggerDefinitionService;
import com.bytechef.hermes.configuration.web.rest.model.ComponentOperationRequestModel;
import com.bytechef.hermes.configuration.web.rest.model.OptionModel;
import com.bytechef.hermes.configuration.web.rest.model.PropertyModel;
import com.bytechef.hermes.configuration.web.rest.model.TriggerDefinitionBasicModel;
import com.bytechef.hermes.configuration.web.rest.model.TriggerDefinitionModel;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ivica Cardic
 */
@RestController
@RequestMapping("${openapi.openAPIDefinition.base-path.core:}")
@ConditionalOnEnabled("coordinator")
public class TriggerDefinitionController implements TriggerDefinitionApi {

    private final ConversionService conversionService;
    private final RemoteTriggerDefinitionFacade triggerDefinitionFacade;
    private final RemoteTriggerDefinitionService triggerDefinitionService;

    public TriggerDefinitionController(
        ConversionService conversionService, RemoteTriggerDefinitionFacade triggerDefinitionFacade,
        RemoteTriggerDefinitionService triggerDefinitionService) {

        this.conversionService = conversionService;
        this.triggerDefinitionFacade = triggerDefinitionFacade;
        this.triggerDefinitionService = triggerDefinitionService;
    }

    @Override
    @SuppressFBWarnings("NP")
    public ResponseEntity<TriggerDefinitionModel> getComponentTriggerDefinition(
        String componentName, Integer componentVersion, String triggerName) {

        return ResponseEntity.ok(
            conversionService.convert(
                triggerDefinitionService.getTriggerDefinition(componentName, componentVersion, triggerName),
                TriggerDefinitionModel.class));
    }

    @Override
    public ResponseEntity<List<TriggerDefinitionBasicModel>> getComponentTriggerDefinitions(
        String componentName, Integer componentVersion) {

        return ResponseEntity.ok(
            triggerDefinitionService.getTriggerDefinitions(componentName, componentVersion)
                .stream()
                .map(triggerDefinitionDTO -> conversionService.convert(
                    triggerDefinitionDTO, TriggerDefinitionBasicModel.class))
                .toList());
    }

    @Override
    public ResponseEntity<String> getComponentTriggerEditorDescription(
        String componentName, Integer componentVersion, String triggerName,
        ComponentOperationRequestModel componentOperationRequestModel) {

        return ResponseEntity.ok(
            triggerDefinitionFacade.executeEditorDescription(
                componentName, componentVersion, triggerName, componentOperationRequestModel.getParameters(),
                componentOperationRequestModel.getConnectionId()));
    }

    @Override
    public ResponseEntity<List<PropertyModel>> getComponentTriggerOutputSchema(
        String componentName, Integer componentVersion, String triggerName,
        ComponentOperationRequestModel componentOperationRequestModel) {

        return ResponseEntity.ok(
            CollectionUtils.map(
                triggerDefinitionFacade.executeOutputSchema(
                    componentName, componentVersion, triggerName, componentOperationRequestModel.getParameters(),
                    componentOperationRequestModel.getConnectionId()),
                property -> conversionService.convert(property, PropertyModel.class)));
    }

    @Override
    public ResponseEntity<Object> getComponentTriggerSampleOutput(
        String componentName, Integer componentVersion, String triggerName,
        ComponentOperationRequestModel componentOperationRequestModel) {

        return ResponseEntity.ok(
            triggerDefinitionFacade.executeSampleOutput(
                componentName, componentVersion, triggerName, componentOperationRequestModel.getParameters(),
                componentOperationRequestModel.getConnectionId()));
    }

    @Override
    public ResponseEntity<List<PropertyModel>> getComponentTriggerPropertyDynamicProperties(
        String componentName, Integer componentVersion, String triggerName, String propertyName,
        ComponentOperationRequestModel componentOperationRequestModel) {

        return ResponseEntity.ok(CollectionUtils.map(
            triggerDefinitionFacade.executeDynamicProperties(
                componentName, componentVersion, triggerName, propertyName,
                componentOperationRequestModel.getParameters(),
                componentOperationRequestModel.getConnectionId()),
            option -> conversionService.convert(option, PropertyModel.class)));
    }

    @Override
    public ResponseEntity<List<OptionModel>> getComponentTriggerPropertyOptions(
        String componentName, Integer componentVersion, String triggerName, String propertyName, String searchText,
        ComponentOperationRequestModel componentOperationRequestModel) {

        return ResponseEntity.ok(
            CollectionUtils.map(
                triggerDefinitionFacade.executeOptions(
                    componentName, componentVersion, triggerName, propertyName,
                    componentOperationRequestModel.getParameters(), componentOperationRequestModel.getConnectionId(),
                    searchText),
                option -> conversionService.convert(option, OptionModel.class)));
    }

    @Override
    public ResponseEntity<List<TriggerDefinitionModel>> getTriggerDefinitions(List<String> triggerTypes) {
        return ResponseEntity.ok(
            CollectionUtils.map(
                triggerDefinitionService.getTriggerDefinitions(
                    triggerTypes == null ? List.of() : CollectionUtils.map(triggerTypes, ComponentOperation::ofType)),
                triggerDefinition -> conversionService.convert(triggerDefinition, TriggerDefinitionModel.class)));
    }
}