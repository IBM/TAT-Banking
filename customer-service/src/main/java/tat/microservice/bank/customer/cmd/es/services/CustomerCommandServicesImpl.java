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
package tat.microservice.bank.customer.cmd.es.services;


import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tat.microservice.bank.customer.cmd.es.commands.CreateCustomerCommand;
import tat.microservice.bank.customer.cmd.es.commands.DeleteCustomerProfileCommand;
import tat.microservice.bank.customer.cmd.es.commands.UpdateCustomerProfileCommand;
import tat.microservice.bank.customer.cmd.es.commands.dto.CreateCustomerDto;
import tat.microservice.bank.customer.cmd.es.commands.dto.DeleteCustomerProfileDto;
import tat.microservice.bank.customer.cmd.es.commands.dto.UpdateCustomerProfileDto;
import tat.microservice.bank.customer.cmd.es.errors.UCError;
import tat.microservice.bank.customer.cmd.es.exceptions.BusinessException;
import tat.microservice.bank.customer.cmdquery.es.response.BaseResponse;

@Service
public class CustomerCommandServicesImpl implements CustomerCommandServices {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CommandGateway commandGateway;

	public CustomerCommandServicesImpl(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@Override
	public CompletableFuture<String> createCustomer(CreateCustomerDto createCustomerDto) {
		// TODO Auto-generated method stub
		logger.info("@@@@11<@Override> inside CustomerCommandServicesImpl createCustomer" + createCustomerDto);
		CreateCustomerCommand customerCommand = new CreateCustomerCommand(generateSSN(),
				createCustomerDto.getTitle(), createCustomerDto.getFirstname(), createCustomerDto.getLastname());

		logger.info("@@@@11<@Override> inside CustomerCommandServicesImpl commandGateway" + commandGateway);

		logger.info(
				"@@@@11<@Override> inside CustomerCommandServicesImpl createCustomer" + createCustomerDto.toString());
		


		return commandGateway.send(customerCommand);
	}

	@Override
	public BaseResponse updateCustomer(String customerSsn, UpdateCustomerProfileDto updateCustomerProfileDto) {
		// TODO Auto-generated method stub
		UpdateCustomerProfileCommand customerUpdateCommand = new UpdateCustomerProfileCommand(customerSsn,
				updateCustomerProfileDto.getTitle(), updateCustomerProfileDto.getFirstname(),
				updateCustomerProfileDto.getLastname());

		UCError error = commandGateway.sendAndWait(customerUpdateCommand);
		if (error != null) {
			throw new BusinessException(error);
		}

		logger.info(
				"<--- " + UpdateCustomerProfileCommand.class.getSimpleName() + " sent to command gateway: customer [{}] ",
				customerUpdateCommand.customerSsn + "    ----->");

		return BaseResponse.create();

	}

	@Override
	public BaseResponse deleteCustomer(String customerSsn) {
		// TODO Auto-generated method stub
		DeleteCustomerProfileCommand customerDeleteCommand = new DeleteCustomerProfileCommand(customerSsn);

		UCError error = commandGateway.sendAndWait(customerDeleteCommand);
		if (error != null) {
			throw new BusinessException(error);
		}

		logger.info(
				"<--- " + DeleteCustomerProfileCommand.class.getSimpleName() + " sent to command gateway: customer [{}] ",
				customerDeleteCommand.customerSsn + "    ----->");
		return BaseResponse.create();
	}

	private String generateSSN() {

		java.util.Random random = new java.util.Random();
		int n1 = random.nextInt(899) + 100;
		int n2 = random.nextInt(89) + 10;
		int n3 = random.nextInt(8999) + 1000;
		String newssn = "" + n1 + "-" + n2 + "-" + n3;

		return newssn;
	}
}
