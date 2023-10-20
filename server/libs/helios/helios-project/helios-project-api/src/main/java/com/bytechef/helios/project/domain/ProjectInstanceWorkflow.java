
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

package com.bytechef.helios.project.domain;

import com.bytechef.commons.data.jdbc.wrapper.MapWrapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ivica Cardic
 */
@Table("project_instance_workflow")
public class ProjectInstanceWorkflow implements Persistable<Long> {

    @CreatedBy
    @Column("created_by")
    private String createdBy;

    @Column("created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column("input_parameters")
    private MapWrapper inputParameters = new MapWrapper();

    @Column
    private boolean enabled;

    @Id
    private Long id;

    @Column("last_execution_date")
    private LocalDateTime lastExecutionDate;

    @Column("last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column("last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column("project_instance_id")
    private AggregateReference<Project, Long> projectInstanceId;

    @MappedCollection(idColumn = "project_instance_workflow_id")
    private Set<ProjectInstanceWorkflowConnection> projectInstanceWorkflowConnections = Collections.emptySet();

    @Version
    private int version;

    @Column("workflow_id")
    private String workflowId;

    public ProjectInstanceWorkflow() {
    }

    @SuppressFBWarnings("NP")
    public ProjectInstanceWorkflow(Map<String, Object> inputParameters, String workflowId) {
        this.inputParameters = new MapWrapper(inputParameters);
        this.workflowId = workflowId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectInstanceWorkflow that = (ProjectInstanceWorkflow) o;

        return Objects.equals(id, that.id) && Objects.equals(workflowId, that.workflowId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<Long> getConnectionIds() {
        return projectInstanceWorkflowConnections.stream()
            .map(ProjectInstanceWorkflowConnection::getConnectionId)
            .toList();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Map<String, Object> getInputParameters() {
        return Collections.unmodifiableMap(inputParameters.getMap());
    }

    @Override
    public Long getId() {
        return id;
    }

    public LocalDateTime getLastExecutionDate() {
        return lastExecutionDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Long getProjectInstanceId() {
        return projectInstanceId.getId();
    }

    public int getVersion() {
        return version;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public void setConnectionIds(List<Long> connectionIds) {
        projectInstanceWorkflowConnections = new HashSet<>();

        if (connectionIds != null) {
            for (Long connectionId : connectionIds) {
                projectInstanceWorkflowConnections.add(new ProjectInstanceWorkflowConnection(connectionId));
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInputParameters(Map<String, Object> inputParameters) {
        if (!CollectionUtils.isEmpty(inputParameters)) {
            this.inputParameters = new MapWrapper(inputParameters);
        }
    }

    public void setLastExecutionDate(LocalDateTime lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
    }

    public void setProjectInstanceId(Long projectInstanceId) {
        this.projectInstanceId = AggregateReference.to(projectInstanceId);
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String toString() {
        return "ProjectTag{" + ", id='"
            + id + '\'' + ", workflowId='"
            + workflowId + '\'' + ", inputParameters="
            + inputParameters + ", projectInstanceWorkflowConnections="
            + projectInstanceWorkflowConnections + ", lastExecutionDate="
            + lastExecutionDate + ", enabled="
            + enabled + '}';
    }
}