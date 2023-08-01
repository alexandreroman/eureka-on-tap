/*
 * Copyright (c) 2023 VMware, Inc. or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vmware.tanzu.demos.eurekaontap.a;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
class IndexController {
    private final RestTemplate http;
    private final String greetings;
    private final String appName;
    private final String hostName;

    IndexController(RestTemplate http, @Value("${app.greetings}") String greetings,
                    @Value("${spring.application.name}") String appName) throws UnknownHostException {
        this.http = http;
        this.greetings = greetings;
        this.appName = appName;

        hostName = InetAddress.getLocalHost().getHostName();
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    String index() {
        final var msg = http.getForObject("//eot-b", String.class);
        return String.format(greetings, appName, hostName, msg);
    }
}
