
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

package com.bytechef.hermes.scheduler;

import com.bytechef.hermes.workflow.WorkflowExecutionId;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public interface TaskScheduler {

    void cancelRefreshDynamicWebhookTriggerTask(String workflowExecutionId);

    void cancelPollTriggerTask(String workflowExecutionId);

    void cancelTriggerWorkflowTask(String workflowExecutionId);

    void scheduleRefreshDynamicWebhookTriggerTask(
        WorkflowExecutionId workflowExecutionId, LocalDateTime webhookExpirationDate, String componentName,
        int componentVersion);

    void schedulePollTriggerTask(WorkflowExecutionId workflowExecutionId);

    void scheduleTriggerWorkflowTask(
        String workflowExecutionId, String pattern, String zoneId, Map<String, Object> output);
}