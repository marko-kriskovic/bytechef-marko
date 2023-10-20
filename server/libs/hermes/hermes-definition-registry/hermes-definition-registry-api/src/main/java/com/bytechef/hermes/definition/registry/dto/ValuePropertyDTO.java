
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

package com.bytechef.hermes.definition.registry.dto;

import com.bytechef.commons.util.OptionalUtils;
import com.bytechef.hermes.definition.Property;
import com.bytechef.hermes.definition.Property.ControlType;

import java.util.Optional;

/**
 * @author Ivica Cardic
 */
public abstract class ValuePropertyDTO<V> extends PropertyDTO {

    private final ControlType controlType;
    private final V defaultValue;
    private final V exampleValue;

    public ValuePropertyDTO(Property.ValueProperty<V, ?> valueProperty) {
        super(valueProperty);

        this.controlType = valueProperty.getControlType();
        this.defaultValue = OptionalUtils.orElse(valueProperty.getDefaultValue(), null);
        this.exampleValue = OptionalUtils.orElse(valueProperty.getExampleValue(), null);
    }

    public ControlType getControlType() {
        return controlType;
    }

    public Optional<V> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    public Optional<V> getExampleValue() {
        return Optional.ofNullable(exampleValue);
    }
}