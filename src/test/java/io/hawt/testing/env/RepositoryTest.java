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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RepositoryTest {

    @Test
    public void trimming() {
        Repository.register("////a////", new IdEnv("a"));
        assertThat(Repository.select("a").context(), equalTo("a"));
        assertThat(Repository.select("///a///").context(), equalTo("a"));
    }

    @Test
    public void registrations() {
        Repository.register("a", new IdEnv("a"));
        Repository.register("a/b", new IdEnv("a/b"));
        Repository.register("a/b/c", new IdEnv("a/b/c"));

        assertThat(Repository.select("a").context(), equalTo("a"));
        assertThat(Repository.select("a/b").context(), equalTo("a/b"));
        assertThat(Repository.select("a/b/c").context(), equalTo("a/b/c"));
        assertThat(Repository.select("a/d").context(), equalTo("a"));
        assertThat(Repository.select("a/b/d").context(), equalTo("a/b"));
        assertThat(Repository.select("a/b/c/d").context(), equalTo("a/b/c"));
    }

    private static class IdEnv implements Environment {
        public String id;

        public IdEnv(String id) {
            this.id = id;
        }

        @Override
        public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException { }
    }

}
