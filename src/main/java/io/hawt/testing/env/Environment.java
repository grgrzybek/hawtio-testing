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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test environment that represents a server/service that can handle forwarded HTTP requests.
 */
public interface Environment {

    /**
     * Environment's role is to handle incoming HTTP request. It's almost original {@link HttpServletRequest}, but
     * with {@link HttpServletRequest#getPathInfo()} returning <em>remaining</em> part of URL without servlet
     * context of hawtio-testing and path info used for mapping the Environment.
     * @param req
     * @param resp
     * @throws IOException
     */
    void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
