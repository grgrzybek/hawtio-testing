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
package io.hawt.testing.env.builtin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.hawt.testing.Version;
import io.hawt.testing.env.Environment;
import org.jolokia.util.RequestType;
import org.json.simple.JSONObject;

/**
 * Empty environment, where Jolokia agent serves as little information as possible
 */
public class EmptyJolokiaEnvironment implements Environment {

    public JSONObject handle(RequestType request) {
        Map<String, String> map = new HashMap<>();
        switch (request) {
            case VERSION:
                map.put("agent", Version.version);
                map.put("protocol", org.jolokia.Version.getProtocolVersion());
                break;
            case READ:
            case LIST:
            case WRITE:
            case EXEC:
            case SEARCH:
                throw new UnsupportedOperationException("Unsupported operation \"" + request + "\"");
        }
        return new JSONObject(map);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (req.getPathInfo().equals("")) {
            resp.getWriter().print(handle(RequestType.VERSION).toJSONString());
        }
        resp.getWriter().close();
    }

}
