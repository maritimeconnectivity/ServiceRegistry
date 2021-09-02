/*
 * Copyright (c) 2021 Maritime Connectivity Platform Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.maritimeconnectivity.serviceregistry.exceptions;

/**
 * The Data Not Found Exception.
 *
 * A generic runtime exception to be thrown whenever some necessary data is not
 * detected.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2171229941490315103L;

    /**
     * Instantiates a new Data Not Found exception.
     *
     * @param message the message
     * @param t       the t
     */
    public DataNotFoundException(String message, Throwable t) {
        super(message, t);
    }

}
