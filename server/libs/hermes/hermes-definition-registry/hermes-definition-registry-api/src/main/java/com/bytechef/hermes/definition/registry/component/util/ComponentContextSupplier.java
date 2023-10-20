
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

package com.bytechef.hermes.definition.registry.component.util;

import com.bytechef.hermes.component.Context;

import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public final class ComponentContextSupplier {

    public static <T, E extends Exception> T get(Context context, Supplier<T, E> supplier) throws E {
        Objects.requireNonNull(context, "'context' must not be null");
        Objects.requireNonNull(supplier, "'supplier' must not be null");

        Context curContext = ComponentContextThreadLocal.get();

        ComponentContextThreadLocal.set(context);

        try {
            return supplier.get();
        } finally {
            ComponentContextThreadLocal.set(curContext);
        }
    }

    @FunctionalInterface
    public interface Supplier<T, E extends Exception> {

        T get() throws E;
    }
}