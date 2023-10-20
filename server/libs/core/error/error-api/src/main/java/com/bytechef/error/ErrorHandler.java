
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

package com.bytechef.error;

/**
 * A strategy for handling errors. This is especially useful for handling errors that occur during asynchronous
 * execution of tasks that have been submitted to a worker. In such cases, it may not be possible to throw the error to
 * the original caller.
 *
 * @author Arik Cohen
 * @since Apt 10, 2017
 */
public interface ErrorHandler<E extends Errorable> {

    /** Handle the given error. */
    void handle(E errorable);
}