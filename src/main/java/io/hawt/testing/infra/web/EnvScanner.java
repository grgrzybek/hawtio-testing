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
package io.hawt.testing.infra.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.hawt.testing.env.Repository;
import io.hawt.testing.env.builtin.EmptyJolokiaEnvironment;
import io.hawt.testing.env.builtin.MinimalJVM;

/**
 * Scans available/configured environments and makes them available for {@link EnvSelectorServlet}
 */
public class EnvScanner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // some built-in environments
        Repository.register("built-in/empty", new EmptyJolokiaEnvironment());
        Repository.register("built-in/minimal-jvm", new MinimalJVM());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }

}
