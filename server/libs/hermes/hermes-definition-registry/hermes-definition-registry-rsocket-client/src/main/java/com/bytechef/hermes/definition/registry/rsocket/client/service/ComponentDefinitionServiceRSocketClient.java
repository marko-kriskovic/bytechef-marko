
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

package com.bytechef.hermes.definition.registry.rsocket.client.service;

import com.bytechef.commons.discovery.util.DiscoveryUtils;
import com.bytechef.commons.rsocket.util.RSocketUtils;
import com.bytechef.hermes.definition.registry.dto.ComponentDefinitionDTO;
import com.bytechef.hermes.definition.registry.service.ComponentDefinitionService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ComponentDefinitionServiceRSocketClient implements ComponentDefinitionService {

    private static final String WORKER_SERVICE_APP = "worker-service-app";

    private final DiscoveryClient discoveryClient;
    private final RSocketRequester.Builder rSocketRequesterBuilder;

    public ComponentDefinitionServiceRSocketClient(
        DiscoveryClient discoveryClient, RSocketRequester.Builder rSocketRequesterBuilder) {

        this.discoveryClient = discoveryClient;
        this.rSocketRequesterBuilder = rSocketRequesterBuilder;
    }

    @Override
    public Mono<ComponentDefinitionDTO> getComponentDefinitionMono(String name, Integer version) {
        return getRSocketRequester(name)
            .route("ComponentDefinitionService.getComponentDefinition")
            .data(Map.of("name", name, "version", version))
            .retrieveMono(ComponentDefinitionDTO.class);
    }

    @Override
    public Mono<List<ComponentDefinitionDTO>> getComponentDefinitionsMono() {
        return Mono.zip(
            DiscoveryUtils.filterServiceInstances(discoveryClient.getInstances(WORKER_SERVICE_APP))
                .stream()
                .map(serviceInstance -> RSocketUtils.getRSocketRequester(serviceInstance, rSocketRequesterBuilder)
                    .route("ComponentDefinitionService.getComponentDefinitions")
                    .retrieveMono(
                        new ParameterizedTypeReference<List<ComponentDefinitionDTO>>() {}))
                .toList(),
            this::toComponentDefinitions);
    }

    @Override
    public Mono<List<ComponentDefinitionDTO>> getComponentDefinitionsMono(String name) {
        return Mono.zip(
            DiscoveryUtils.filterServiceInstances(discoveryClient.getInstances(WORKER_SERVICE_APP))
                .stream()
                .map(serviceInstance -> RSocketUtils.getRSocketRequester(serviceInstance, rSocketRequesterBuilder)
                    .route("ComponentDefinitionService.getComponentDefinitionsForName")
                    .data(name)
                    .retrieveMono(
                        new ParameterizedTypeReference<List<ComponentDefinitionDTO>>() {}))
                .toList(),
            this::toComponentDefinitions);
    }

    @SuppressWarnings("unchecked")
    private List<ComponentDefinitionDTO> toComponentDefinitions(Object[] objectArray) {
        return Arrays.stream(objectArray)
            .map(object -> (List<ComponentDefinitionDTO>) object)
            .flatMap(Collection::stream)
            .toList();
    }

    private RSocketRequester getRSocketRequester(String componentName) {
        return RSocketUtils.getRSocketRequester(
            discoveryClient.getInstances(WORKER_SERVICE_APP), componentName, rSocketRequesterBuilder);
    }
}