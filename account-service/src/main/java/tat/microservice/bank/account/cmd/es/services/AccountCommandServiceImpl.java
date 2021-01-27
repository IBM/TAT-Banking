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
package tat.microservice.bank.account.cmd.es.services;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tat.microservice.bank.account.cmd.es.aggregates.Account;
import tat.microservice.bank.account.cmd.es.commands.CreateAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.CreateTransferCommand;
import tat.microservice.bank.account.cmd.es.commands.DeleteAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.DepositMoneyCommand;
import tat.microservice.bank.account.cmd.es.commands.WithdrawMoneyCommand;
import tat.microservice.bank.account.cmd.es.commands.dto.AccountTransferDto;
import tat.microservice.bank.account.cmd.es.commands.dto.CreateAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.DeleteAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeDepositDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeWithdrawalDto;
import tat.microservice.bank.account.cmd.es.errors.UCError;
import tat.microservice.bank.account.cmd.es.exceptions.BusinessException;
import tat.microservice.bank.account.cmdquery.es.response.BaseResponse;

//import java.util.UUID;
//import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CommandGateway commandGateway;
	  
	public AccountCommandServiceImpl(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
		
	}

	@Override
	public CompletableFuture<Account> createAccount(CreateAccountDto createAccountDto) {
		logger.info(" <============  createAccount(CreateAccountDto createAccountDto)  =============>" +createAccountDto);

		String acct_Id = generateAccountID(createAccountDto.getCustomerSsn());
		// UUID.randomUUID().toString()   
		logger.info(" <============  createAccount(CreateAccountDto createAccountDto)  =============>");

		return commandGateway.send(new CreateAccountCommand(acct_Id, createAccountDto.getBalance(),
				createAccountDto.getOverdraftLimit(), createAccountDto.getCustomerSsn()));
	}

	@Override
	public CompletableFuture<String> creditMoneyToAccount(String accountId, MakeDepositDto MakeDepositDto) {
		return commandGateway.send(new DepositMoneyCommand(accountId, MakeDepositDto.getAmount()));
	}

	@Override
	public CompletableFuture<String> debitMoneyFromAccount(String accountId, MakeWithdrawalDto makeWithdrawalDto) {
		return commandGateway
				.send(new WithdrawMoneyCommand(accountId, makeWithdrawalDto.getAmount()));
	}

	public CompletableFuture<String> transfer(String transferId, AccountTransferDto transfer) {

		return commandGateway.send(new CreateTransferCommand(transferId, transfer.getFromAccountId(),
				transfer.getToAccountId(), transfer.getAmount()));
	}
	
	
	@Override
	public BaseResponse deleteAccount(String accountId) {
		// TODO Auto-generated method stub
		DeleteAccountCommand accountDeleteCommand = new DeleteAccountCommand(accountId);

		UCError error = commandGateway.sendAndWait(accountDeleteCommand);
		if (error != null) {
			throw new BusinessException(error);
		}

		logger.info(
				"<--- " + DeleteAccountCommand.class.getSimpleName() + " sent to command gateway: account [{}] ",
				accountDeleteCommand.getAccountId() + "    ----->");
		return BaseResponse.create();
	}
	
	

	private String generateAccountID(String customerSsn) {

		int acctNumber = (new java.util.Random()).nextInt(899999) + 100000;
		System.out.println("acctNumber " + acctNumber);
		String accountid = "47" + customerSsn.substring(0, 1) + "-" + acctNumber;
		
		return accountid;
	}
	

}
