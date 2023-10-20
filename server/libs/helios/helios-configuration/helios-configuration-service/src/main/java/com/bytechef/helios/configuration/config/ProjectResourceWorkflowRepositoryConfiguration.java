
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

package com.bytechef.helios.configuration.config;

import com.bytechef.helios.configuration.constant.ProjectConstants;
import com.bytechef.atlas.configuration.repository.config.contributor.ClasspathResourceWorkflowRepositoryPropertiesContributor;
import com.bytechef.atlas.configuration.repository.config.contributor.FilesystemResourceWorkflowRepositoryPropertiesContributor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectResourceWorkflowRepositoryConfiguration {

    @Bean
    ClasspathResourceWorkflowRepositoryPropertiesContributor
        projectClasspathResourceWorkflowRepositoryPropertiesContributor(
            @Value("${bytechef.workflow.repository.classpath.projects.base-path:}") String basePath) {

        return new ClasspathResourceWorkflowRepositoryPropertiesContributor() {

            @Override
            public String getBasePath() {
                return basePath;
            }

            @Override
            public int getType() {
                return ProjectConstants.PROJECT_TYPE;
            }
        };
    }

    @Bean
    FilesystemResourceWorkflowRepositoryPropertiesContributor
        projectFilesystemResourceWorkflowRepositoryPropertiesContributor(
            @Value("${bytechef.workflow.repository.filesystem.projects.base-path:}") String basePath) {

        return new FilesystemResourceWorkflowRepositoryPropertiesContributor() {

            @Override
            public String getBasePath() {
                return basePath;
            }

            @Override
            public int getType() {
                return ProjectConstants.PROJECT_TYPE;
            }
        };
    }
}