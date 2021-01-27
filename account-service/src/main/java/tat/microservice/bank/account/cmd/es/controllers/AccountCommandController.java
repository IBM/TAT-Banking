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
package tat.microservice.bank.account.cmd.es.controllers;

import io.swagger.annotations.Api;
import tat.microservice.bank.account.cmd.es.aggregates.Account;
import tat.microservice.bank.account.cmd.es.api.gateway.CustomerDto;
import tat.microservice.bank.account.cmd.es.api.gateway.CustomerProxy;
import tat.microservice.bank.account.cmd.es.commands.dto.AccountTransferDto;
import tat.microservice.bank.account.cmd.es.commands.dto.CreateAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.DeleteAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeDepositDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeWithdrawalDto;
import tat.microservice.bank.account.cmd.es.exceptions.CustomerNotFoundException;
import tat.microservice.bank.account.cmd.es.services.AccountCommandService;
import tat.microservice.bank.account.cmdquery.es.response.BaseResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@Api(value = "Account Commands", description = "Account Commands Related Endpoints", tags = "Account Commands")
public class AccountCommandController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AccountCommandService accountCommandService;
	private final CustomerProxy customerProxy;

	public AccountCommandController(AccountCommandService accountCommandService, CustomerProxy customerProxy) {
		this.accountCommandService = accountCommandService;
		this.customerProxy = customerProxy;
	}

	@PostMapping
	public CompletableFuture<Account> createAccount(@RequestBody CreateAccountDto account)
			throws CustomerNotFoundException {
		logger.info("@@@@@@@@@@ inside AccountCommandController createAccount(@RequestBody CreateAccountDto account)"  +account);
		logger.info("@@@@@@@@@@ inside AccountCommandController createAccount(@RequestBody CreateAccountDto account)"  +account.getCustomerSsn());
		//CustomerDto customer = new CustomerProxyImpl().getCustomer(account.getCustomerSsn()); 

       CustomerDto customer = customerProxy.getCustomer(account.getCustomerSsn());

       logger.info("@@@@@@@@@@ inside AccountCommandController createAccount(@RequestBody CreateAccountDto account) CustomerDto customer"  +customer);

		if (customer.getCustomerSsn() != null || !customer.getCustomerSsn().equals("")) {

			account.setCustomerSsn(customer.getCustomerSsn());

			CompletableFuture<Account> accountObj = accountCommandService.createAccount(account);
			logger.info(" <============  accountObj =============>" +accountObj);

			return accountObj;
		}
		throw new CustomerNotFoundException(account.getCustomerSsn(), "Person not found during creation of account.");

	}

	@PutMapping(value = "/deposit/{accountNumber}")
	public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountNumber") String accountNumber,
			@RequestBody MakeDepositDto makeDepositDto) {
		//makeDepositDto.setAccountId(accountNumber);
		return accountCommandService.creditMoneyToAccount(accountNumber,makeDepositDto);
	}

	@PutMapping(value = "/withdraw/{accountNumber}")
	public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountNumber") String accountNumber,
			@RequestBody MakeWithdrawalDto makeWithdrawalDto) {
		//makeWithdrawalDto.setAccountId(accountNumber);
		return accountCommandService.debitMoneyFromAccount(accountNumber,makeWithdrawalDto);
	}

	@PostMapping(value = "/transfer")
	public CompletableFuture<String> transfer(@RequestBody AccountTransferDto transfer) {
		return accountCommandService.transfer(generateTransferId(), transfer);

	}
	
	 //@DeleteMapping(value = "/deleteAccount/{accountNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
	 @DeleteMapping(value = "/deleteAccount/{accountNumber}")
	 @ResponseStatus(value = HttpStatus.ACCEPTED)
	public BaseResponse deleteAccount(@PathVariable(value = "accountNumber") String accountNumber){
				 
		 logger.info("<----  delete request received for customer ----->" + accountNumber );
			
		
		return accountCommandService.deleteAccount(accountNumber);
	}

	private String generateTransferId() {

		return UUID.randomUUID().toString();
	}

	
}
