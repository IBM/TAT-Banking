/*##############################################################################
# Copyright 2021 IBM Corp. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
##############################################################################*/
package tat.microservice.bank.customer.cmd.es.configuration;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tat.microservice.bank.customer.cmd.es.aggregates.Customer;
import tat.microservice.bank.customer.cmd.es.handlers.CustomerManagerCommandHandler;

@Configuration
public class CustomerAxonConfig {

	@Autowired
	private AxonConfiguration axonConfiguration;

	@Autowired
	private EventBus eventBus;

	@Bean
	public CustomerManagerCommandHandler CustomerCommandHandler() {
		System.out.println(
				" <============  inside tat.microservice.bank.customer.cmd.es.configuration.AxonConfig.CustomerCommandHandler()  =============>");
		return new CustomerManagerCommandHandler(axonConfiguration.repository(Customer.class), eventBus);
	}

	@Autowired
	public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
		simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
	}

}
