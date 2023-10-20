
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

package com.bytechef.component.webhook.trigger;

import com.bytechef.hermes.component.definition.TriggerDefinition;
import com.bytechef.hermes.component.definition.TriggerDefinition.StaticWebhookRequestContext;
import com.bytechef.hermes.component.definition.TriggerDefinition.TriggerType;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookBody;
import com.bytechef.hermes.component.definition.TriggerDefinition.WebhookOutput;

import java.util.Map;

import static com.bytechef.component.webhook.constant.WebhookConstants.BODY;
import static com.bytechef.component.webhook.constant.WebhookConstants.HEADERS;
import static com.bytechef.component.webhook.constant.WebhookConstants.METHOD;
import static com.bytechef.component.webhook.constant.WebhookConstants.PATH;
import static com.bytechef.component.webhook.constant.WebhookConstants.PARAMETERS;
import static com.bytechef.hermes.component.definition.ComponentDSL.trigger;
import static com.bytechef.hermes.definition.DefinitionDSL.array;
import static com.bytechef.hermes.definition.DefinitionDSL.display;
import static com.bytechef.hermes.definition.DefinitionDSL.object;
import static com.bytechef.hermes.definition.DefinitionDSL.oneOf;

/**
 * @author Ivica Cardic
 */
public class AutoRespondWithHTTP200Trigger {

    public static final TriggerDefinition TRIGGER_DEFINITION = trigger("autoRespondWithHTTP200")
        .display(display("Auto Respond with HTTP 200 status").description(
            "The webhook trigger always replies immediately with an HTTP 200 status code in response to any incoming webhook request. This guarantees execution of the webhook trigger, but does not involve any validation of the received request."))
        .type(TriggerType.STATIC_WEBHOOK)
        .outputSchema(
            object()
                .properties(
                    object(HEADERS),
                    object(PARAMETERS),
                    oneOf(BODY).types(array(), object())))
        .staticWebhookRequest(AutoRespondWithHTTP200Trigger::staticWebhookRequest);

    protected static WebhookOutput staticWebhookRequest(StaticWebhookRequestContext context) {
        WebhookBody webhookBody = context.body();

        return WebhookOutput.map(
            Map.of(
                BODY, webhookBody.getContent(),
                METHOD, context.method(),
                HEADERS, context.headers(),
                PARAMETERS, context.parameters(),
                PATH, context.path()));
    }
}