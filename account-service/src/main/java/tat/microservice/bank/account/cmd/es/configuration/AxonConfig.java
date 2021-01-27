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
package tat.microservice.bank.account.cmd.es.configuration;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tat.microservice.bank.account.cmd.es.aggregates.Account;
import tat.microservice.bank.account.cmd.es.handlers.AccountManagerCommandHandler;
import tat.microservice.bank.account.cmd.es.sagas.AccountTransferManagementSaga;

@Configuration
public class AxonConfig {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AxonConfiguration axonConfiguration;

	@Autowired
	private EventBus eventBus;

	@Bean
	public AccountManagerCommandHandler AccountCommandHandler() {
		logger.info(" <============  inside AccountCommandHandler()  =============>");
		return new AccountManagerCommandHandler(axonConfiguration.repository(Account.class), eventBus);
	}

	@Bean
	public SagaConfiguration transferManagementSagaConfiguration() {
		logger.info(" <============  inside transferManagementSagaConfiguration()  =============>");
		return SagaConfiguration.trackingSagaManager(AccountTransferManagementSaga.class);
	}

	@Autowired
	public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
		simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
	}

}
