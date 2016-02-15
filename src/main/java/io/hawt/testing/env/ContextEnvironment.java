/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.hawt.testing.env;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ContextEnvironment extends Environment {

    /**
     * Returns the context under which the environment is registered. HTTP requests targeted to hawio-testing env
     * are forwarded to Environment by longest registration path. The remaining URL path after trimming the
     * registration path will be new {@link HttpServletRequest#getPathInfo()} visible by
     * {@link #handle(HttpServletRequest, HttpServletResponse)} method
     * @return
     */
    String context();

}
