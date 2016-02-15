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

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;

import io.hawt.testing.env.builtin.NotFound;
import io.hawt.testing.env.builtin.WrappingEnvironment;

/**
 * <p>Mapping of {@link HttpServletRequest#getPathInfo() path info} for {@link Environment}. Environments are selected
 * using longest possible part of path info.</p>
 * <p>If one env is registered under <code>org/example/env1</code> and other under
 * <code>org/example/env1/version/search</code>, servlet requests with path info
 * <code>org/example/env1/version/search/for/particular/jmx</code> will be handler by second environment</p>
 * <p>This may seem to be opposite to what javax.servlets specification says, but this allows us to override
 * some request in one environment with other (sub)environments.</p>
 */
public class Repository {

    // number of path elements (ASC) -> path info (ASC) -> Environment
    public static Map<Integer, Map<String, WrappingEnvironment>> ENVIRONMENTS = new TreeMap<>();

    private static WrappingEnvironment NOT_FOUND = new WrappingEnvironment("", new NotFound());

    /**
     * Select {@link Environment} by {@link HttpServletRequest#getPathInfo() path info}
     * @param pathInfo
     * @return
     */
    public static WrappingEnvironment select(String pathInfo) {
        pathInfo = trimSlashes(pathInfo);

        ListIterator<Map<String, WrappingEnvironment>> iterator = new LinkedList<>(ENVIRONMENTS.values()).listIterator(ENVIRONMENTS.values().size());
        while (iterator.hasPrevious()) {
            Map<String, WrappingEnvironment> envs = iterator.previous();
            for (String path : envs.keySet()) {
                if (pathInfo.indexOf(path) == 0) {
                    return envs.get(path);
                }
            }
            if (envs.containsKey(pathInfo)) {
                return envs.get(pathInfo);
            }
        }
        return NOT_FOUND;
    }

    /**
     *
     * @param path
     * @param environment
     */
    public static void register(String path, Environment environment) {
        if (path == null) {
            throw new IllegalArgumentException("Can't register environemnt under null path");
        }

        path = trimSlashes(path);

        String[] parts = path.split("/");
        if (!ENVIRONMENTS.containsKey(parts.length)) {
            ENVIRONMENTS.put(parts.length, new TreeMap<>());
        }

        Map<String, WrappingEnvironment> map = ENVIRONMENTS.get(parts.length);
        map.put(path, new WrappingEnvironment(path, environment));
    }

    private static String trimSlashes(String path) {
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

}
