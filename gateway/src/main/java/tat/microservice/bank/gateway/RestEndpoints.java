/**
 * JBoss, Home of Professional Open Source
 * Copyright 2017, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tat.microservice.bank.gateway;

import java.util.LinkedList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestEndpoints extends RouteBuilder {

    @Value("${service.host}")
    private String serviceHost;

    @Override
    public void configure() throws Exception {

        /*
         * Common rest configuration
         */

        restConfiguration()
                .host(serviceHost)
                .bindingMode(RestBindingMode.json)
                .contextPath("/api")
                .apiContextPath("/doc")
                    .apiProperty("api.title", "API-Gateway  REST API")
                    .apiProperty("api.description", "Operations that can be invoked in the api-gateway")
                    .apiProperty("api.license.name", "Apache License Version 2.0")
                    .apiProperty("api.license.url", "http://www.apache.org/licenses/LICENSE-2.0.html")
                    .apiProperty("api.version", "1.0.0");


        /*
         * Gateway service
         */

        // full path: /api/gateway
        rest().get("/gateway")
                .description("Invoke all microservices in parallel")
                .outTypeList(String.class)
                .apiDocs(true)
                .responseMessage().code(200).message("OK").endResponseMessage()
                .route()
                    .multicast(AggregationStrategies.flexible().accumulateInCollection(LinkedList.class))
                    .parallelProcessing()
                        .to("direct:customer")
                        .to("direct:account")
                        .to("direct:transaction")
                    .end()
                    .transform().body(List.class, list -> list)
                    .setHeader("Access-Control-Allow-Credentials", constant("true"))
                    .setHeader("Access-Control-Allow-Origin", header("Origin"));

    }
}
