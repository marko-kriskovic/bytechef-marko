
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

package com.bytechef.worker.config;

import com.bytechef.discovery.metadata.ServiceMetadataRegistry;
import com.bytechef.hermes.component.definition.ComponentDefinition;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ivica Cardic
 */
@DependsOn("workerConfiguration")
@Configuration
public class ComponentMetadataRegistryConfiguration implements InitializingBean {

    private final List<ComponentDefinition> componentDefinitions;
    private final ServiceMetadataRegistry serviceMetadataRegistry;

    @SuppressFBWarnings("EI2")
    public ComponentMetadataRegistryConfiguration(
        List<ComponentDefinition> componentDefinitions, ServiceMetadataRegistry serviceMetadataRegistry) {

        this.componentDefinitions = componentDefinitions;
        this.serviceMetadataRegistry = serviceMetadataRegistry;
    }

    @Override
    public void afterPropertiesSet() {
        serviceMetadataRegistry.registerMetadata(
            Map.of(
                "componentNames",
                componentDefinitions.stream()
                    .map(ComponentDefinition::getName)
                    .collect(Collectors.joining(","))));
    }
}