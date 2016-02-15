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

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import io.hawt.testing.env.Environment;
import io.hawt.testing.env.Repository;
import io.hawt.testing.env.builtin.WrappingEnvironment;

/**
 * Main servlet that selects one of the configured environments by request path and forwards further processing
 */
public class EnvSelectorServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String request = null;
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        if ("".equals(pathInfo)) {
            list(req, resp);
            return;
        }

        WrappingEnvironment env = Repository.select(pathInfo);
        env.handle(new HttpServletRequestWrapper(req) {
            @Override
            public String getPathInfo() {
                return super.getPathInfo().substring(env.context().length() + 1);
            }
        }, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Available environments</h3>\n";
        ListIterator<Map<String, WrappingEnvironment>> it = new LinkedList<>(Repository.ENVIRONMENTS.values()).listIterator(Repository.ENVIRONMENTS.values().size());
        while (it.hasPrevious()) {
            Map<String, WrappingEnvironment> mapping = it.previous();
            for (String k : mapping.keySet()) {
                html += String.format("%s &rarr; %s<br />\n", k, mapping.get(k).getClass().getName());
            }
        }
        html += "</body>\n" +
                "</html>\n";
             resp.getWriter().print(html);
        resp.getWriter().close();
    }

}
