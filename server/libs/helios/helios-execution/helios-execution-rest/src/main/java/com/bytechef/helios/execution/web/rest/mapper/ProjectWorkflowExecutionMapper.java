
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

package com.bytechef.helios.execution.web.rest.mapper;

import com.bytechef.helios.configuration.web.rest.mapper.config.ProjectWorkflowConfigurationMapperSpringConfig;
import com.bytechef.helios.configuration.domain.Project;
import com.bytechef.helios.configuration.domain.ProjectInstance;
import com.bytechef.helios.configuration.web.rest.model.ProjectWorkflowExecutionModel;
import com.bytechef.helios.configuration.web.rest.model.ProjectInstanceModel;
import com.bytechef.helios.configuration.web.rest.model.ProjectModel;
import com.bytechef.helios.execution.dto.ProjectWorkflowExecutionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

/**
 * @author Ivica Cardic
 */
@Mapper(config = ProjectWorkflowConfigurationMapperSpringConfig.class)
public interface ProjectWorkflowExecutionMapper
    extends Converter<ProjectWorkflowExecutionDTO, ProjectWorkflowExecutionModel> {

    @Override
    ProjectWorkflowExecutionModel convert(ProjectWorkflowExecutionDTO projectWorkflowExecutionDTO);

    @Mapping(target = "lastExecutionDate", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "projectInstanceWorkflows", ignore = true)
    @Mapping(target = "tags", ignore = true)
    ProjectInstanceModel map(ProjectInstance projectInstance);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    ProjectModel map(Project project);

    default String map(Optional<String> optional) {
        return optional.orElse(null);
    }
}