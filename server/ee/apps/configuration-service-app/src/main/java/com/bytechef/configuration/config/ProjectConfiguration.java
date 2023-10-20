
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

package com.bytechef.configuration.config;

import com.bytechef.atlas.execution.facade.JobFacade;
import com.bytechef.atlas.configuration.service.WorkflowService;
import com.bytechef.category.service.CategoryService;
import com.bytechef.helios.configuration.facade.ProjectFacade;
import com.bytechef.helios.configuration.facade.ProjectFacadeImpl;
import com.bytechef.helios.configuration.facade.ProjectInstanceFacade;
import com.bytechef.helios.configuration.facade.ProjectInstanceFacadeImpl;
import com.bytechef.helios.configuration.service.ProjectInstanceService;
import com.bytechef.helios.configuration.service.ProjectInstanceWorkflowService;
import com.bytechef.helios.configuration.service.ProjectService;
import com.bytechef.hermes.execution.facade.TriggerLifecycleFacade;
import com.bytechef.tag.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivica Cardic
 */
@Configuration
public class ProjectConfiguration {

    @Bean
    ProjectInstanceFacade projectInstanceFacade(
        JobFacade jobFacade, ProjectInstanceService projectInstanceService,
        ProjectInstanceWorkflowService projectInstanceWorkflowService, ProjectService projectService,
        TagService tagService, TriggerLifecycleFacade triggerLifecycleFacade,
        @Value("bytechef.webhookUrl") String webhookUrl, WorkflowService workflowService) {

        return new ProjectInstanceFacadeImpl(
            jobFacade, projectInstanceService, projectInstanceWorkflowService, projectService,
            tagService, triggerLifecycleFacade, webhookUrl, workflowService);
    }

    @Bean
    ProjectFacade projectFacade(
        CategoryService categoryService, ProjectInstanceService projectInstanceService, ProjectService projectService,
        TagService tagService, WorkflowService workflowService) {

        return new ProjectFacadeImpl(
            categoryService, projectInstanceService, projectService, tagService, workflowService);
    }
}