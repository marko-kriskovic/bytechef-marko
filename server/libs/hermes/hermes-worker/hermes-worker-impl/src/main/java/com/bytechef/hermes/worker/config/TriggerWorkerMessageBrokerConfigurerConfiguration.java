
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

package com.bytechef.hermes.worker.config;

import com.bytechef.autoconfigure.annotation.ConditionalOnEnabled;
import com.bytechef.hermes.worker.TriggerWorker;
import com.bytechef.message.broker.SystemMessageRoute;
import com.bytechef.message.broker.config.MessageBrokerConfigurer;
import com.bytechef.hermes.execution.message.broker.TriggerMessageRoute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivica Cardic
 */
@Configuration
@ConditionalOnEnabled("worker")
public class TriggerWorkerMessageBrokerConfigurerConfiguration {

    @Bean
    MessageBrokerConfigurer<?> triggerWorkerMessageBrokerConfigurer(TriggerWorker triggerWorker) {
        return (listenerEndpointRegistrar, messageBrokerListenerRegistrar) -> {
            messageBrokerListenerRegistrar.registerListenerEndpoint(
                listenerEndpointRegistrar, TriggerMessageRoute.TRIGGERS, 1, triggerWorker, "handle");

            messageBrokerListenerRegistrar.registerListenerEndpoint(
                listenerEndpointRegistrar, SystemMessageRoute.CONTROL, 1, triggerWorker, "handle");
        };
    }
}