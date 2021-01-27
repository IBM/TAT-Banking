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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import tat.microservice.bank.account.query.es.entities.AccountQueryEntity;
import tat.microservice.bank.account.query.es.entities.AccountTransferQueryEntity;
import tat.microservice.bank.account.query.es.services.AccountQueryService;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@Api(value = "Account Query", description = "Account Query Related Endpoints", tags = "Account Queries")
public class AccountQueryController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AccountQueryService accountQueryService;

	public AccountQueryController(AccountQueryService accountQueryService) {
		super();
		this.accountQueryService = accountQueryService;
	}

	@GetMapping("/GetAllAccounts")
	public Iterable<AccountQueryEntity> findAllAccount() {
		return accountQueryService.findAllAccount();
	}

	@GetMapping("/{accountNumber}")
	public AccountQueryEntity findAccountById(@PathVariable(value = "accountNumber") String accountNumber) {
		return accountQueryService.findAccountById(accountNumber);
	}

	
	@GetMapping("/findAccountByCustomer/{customerNumber}")
	public List<AccountQueryEntity> findAccountByCustomer(@PathVariable(value = "customerNumber") String customerNumber) {
		
		
		return accountQueryService.findOneByCustomerSsn(customerNumber);
	}
	

	@GetMapping("/GetAllTransfers")
	public Iterable<AccountTransferQueryEntity> findAllTransfer() {
		return accountQueryService.findAllTransfer();
	}

	@GetMapping("/transfer/{transferId}")
	public AccountTransferQueryEntity findTransferById(@PathVariable(value = "transferId") String transferId) {
		return accountQueryService.findTransferById(transferId);
	}

	@GetMapping("/events/{accountNumber}")
	public List<Object> listEventsForAccount(@PathVariable(value = "accountNumber") String accountNumber) {
		return accountQueryService.listEventsForAccount(accountNumber);
	}

}
