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

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ServiceRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /*
         * Definition of the external services: aloha
         */

        from("direct:customer")
                .id("customer")
                .removeHeaders("accept*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("text/plain"))
                .hystrix()
                    .hystrixConfiguration().executionTimeoutInMilliseconds(1000).circuitBreakerRequestVolumeThreshold(5).end()
                    .id("customer")
                    .groupKey("http://localhost:9091/")
                    .to("http4:localhost:9091/api/customer?bridgeEndpoint=true&connectionClose=true")
                    .convertBodyTo(String.class)
                .onFallback()
                    .transform().constant("customer-service response (fallback)")
                .end();

        /*
         * Definition of the external services: account
         */

        from("direct:account")
                .id("account")
                .removeHeaders("accept*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("text/plain"))
                .hystrix()
                    .hystrixConfiguration().executionTimeoutInMilliseconds(1000).circuitBreakerRequestVolumeThreshold(5).end()
                    .id("account")
                    .groupKey("http://localhost:9091/")
                    .to("http4:localhost:9091/api/account?bridgeEndpoint=true&connectionClose=true")
                    .convertBodyTo(String.class)
                .onFallback()
                    .transform().constant("account-service response (fallback)")
                .end();

        /*
         * Definition of the external services: transaction
         */

        from("direct:transaction")
                .id("transaction")
                .removeHeaders("accept*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("text/plain"))
                .hystrix()
                    .hystrixConfiguration().executionTimeoutInMilliseconds(1000).circuitBreakerRequestVolumeThreshold(5).end()
                    .id("transaction")
                    .groupKey("http://transaction:9091/")
                    .to("http4:transaction:9091/api/transaction?bridgeEndpoint=true&connectionClose=true")
                    .convertBodyTo(String.class)
                .onFallback()
                    .transform().constant("transaction-service response (fallback)")
                .end();

        /*
         * Definition of the external services: web
         */

		/*
		 * from("direct:bonjour") .id("bonjour") .removeHeaders("accept*")
		 * .setHeader(Exchange.HTTP_METHOD, constant("GET"))
		 * .setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("text/plain")) .hystrix()
		 * .hystrixConfiguration().executionTimeoutInMilliseconds(1000).
		 * circuitBreakerRequestVolumeThreshold(5).end() .id("bonjour")
		 * .groupKey("http://bonjour:8080/") .to(
		 * "http4:bonjour:8080/api/bonjour?bridgeEndpoint=true&connectionClose=true")
		 * .convertBodyTo(String.class) .onFallback()
		 * .transform().constant("Bonjour response (fallback)") .end();
		 */

    }

}
