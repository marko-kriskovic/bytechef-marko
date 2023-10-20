
/*
 * Copyright 2016-2018 the original author or authors.
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
 *
 * Modifications copyright (C) 2021 <your company/name>
 */

package com.bytechef.atlas.configuration.task;

import com.bytechef.message.AbstractControllable;

/**
 * @author Arik Cohen
 * @author Ivica Cardic
 * @since Apr 19, 2017
 */
public class CancelControlTask extends AbstractControllable implements ControlTask {

    public static final String TYPE_CANCEL = "task.cancel";

    private long jobId;
    private long taskExecutionId;

    private CancelControlTask() {
    }

    public CancelControlTask(long jobId, long taskExecutionId) {
        super(TYPE_CANCEL);

        this.jobId = jobId;
        this.taskExecutionId = taskExecutionId;
    }

    public long getJobId() {
        return jobId;
    }

    public long getTaskExecutionId() {
        return taskExecutionId;
    }

    @Override
    public String toString() {
        return "CancelControlTask{" + "jobId=" + jobId + ", taskExecutionId=" + taskExecutionId + '}';
    }
}