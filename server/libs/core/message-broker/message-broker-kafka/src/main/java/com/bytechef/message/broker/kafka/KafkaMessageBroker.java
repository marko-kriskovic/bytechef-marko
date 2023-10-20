
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

package com.bytechef.message.broker.kafka;

import com.bytechef.message.broker.MessageBroker;
import com.bytechef.message.Retryable;
import java.util.concurrent.TimeUnit;

import com.bytechef.message.broker.MessageRoute;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;

/**
 * @author Arik Cohen
 */
public class KafkaMessageBroker implements MessageBroker {

    private KafkaTemplate<Integer, Object> kafkaTemplate;

    @Override
    public void send(MessageRoute messageRoute, Object message) {
        Assert.notNull(messageRoute, "'queueName' key must not be null");

        if (message instanceof Retryable retryable) {
            delay(retryable.getRetryDelayMillis());
        }

        Class<?> messageClass = message.getClass();

        kafkaTemplate.send(MessageBuilder.withPayload(message)
            .setHeader(KafkaHeaders.TOPIC, messageRoute.toString())
            .setHeader("_type", messageClass.getName())
            .build());
    }

    private void delay(long aValue) {
        try {
            TimeUnit.MILLISECONDS.sleep(aValue);
        } catch (InterruptedException e) {
        }
    }

    @SuppressFBWarnings("EI")
    public void setKafkaTemplate(KafkaTemplate<Integer, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}