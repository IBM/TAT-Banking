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
package tat.microservice.bank.account.cmd.es.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tat.microservice.bank.account.cmd.es.commands.CreateAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.DeleteAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.DepositMoneyCommand;
import tat.microservice.bank.account.cmd.es.commands.ReturnMoneyOfFailedTransferCommand;
import tat.microservice.bank.account.cmd.es.commands.WithdrawMoneyCommand;
import tat.microservice.bank.account.cmd.es.events.AccountCreatedEvent;
import tat.microservice.bank.account.cmd.es.events.AccountDeletedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneyAddedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneyDepositedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneyOfFailedTransferReturnedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneySubtractedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneyWithdrawnEvent;
import tat.microservice.bank.account.cmd.es.events.TransferDestinationAccountCreditedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountDebitRejectedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountDebitedEvent;
import tat.microservice.bank.account.cmd.es.exceptions.AccountBalanceException;



@Aggregate
public class Account {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@AggregateIdentifier
	private String accountId;
	private String customerSsn;
	private long overdraftLimit;
	//private double balanceInCents;
	private double accountBalance;
	private String transferType;
	@SuppressWarnings("unused")
	private Account() {
	}

	@CommandHandler
	public Account(CreateAccountCommand command) {
		logger.info(" <============ inside Account.Account(CreateAccountCommand command) =============>" +command);
		AggregateLifecycle.apply(new AccountCreatedEvent(command.getAccountId(), command.getBalance(),
				command.getCustomerSsn(), command.getOverdraftLimit()));
		logger.info(" <============ inside Account.Account(CreateAccountCommand command) =============> Done!!!");
	}

	@CommandHandler
	public void deposit(DepositMoneyCommand command) {
		AggregateLifecycle.apply(new MoneyDepositedEvent(command.getAccountId(), command.getAmountOfMoney()));
	}

	@CommandHandler
	public void withdraw(WithdrawMoneyCommand command) throws Exception {
		logger.info(" <============ inside withdraw(WithdrawMoneyCommand command) =============> command.getAmountOfMoney() =" +command.getAmountOfMoney());
		if (command.getAmountOfMoney() <=  overdraftLimit & accountBalance >= command.getAmountOfMoney()) {
			logger.info(" <============ inside withdraw(WithdrawMoneyCommand command) condition true =============> command.getAmountOfMoney() =" +command.getAmountOfMoney());
			AggregateLifecycle.apply(new MoneyWithdrawnEvent(accountId, command.getAmountOfMoney()));
		}else {
			throw new AccountBalanceException("Insufficient funds to cover this amount $"+command.getAmountOfMoney() + ", Or you have reached an overdraft limit.");
			
		}
		
	} 

	public void debit(double amount, String transferId) {
		logger.info(" <============ inside debit(double amount, String transferId) =============> amount =" +amount);
		if (amount >  overdraftLimit & accountBalance >= 0 & (accountBalance - amount) < 0) {
			logger.info(" <============ inside debit(double amount, String transferId) condition true =============> amount =" +amount);
			AggregateLifecycle.apply(new TransferSourceAccountDebitRejectedEvent(transferId));
		} else {
			transferType = "Debit";
			AggregateLifecycle.apply(new TransferSourceAccountDebitedEvent(accountId, amount, accountBalance, transferId, transferType));
			
		}
	}

	public void credit(double amount, String transferId) {
		transferType= "Credit";
		AggregateLifecycle.apply(new TransferDestinationAccountCreditedEvent(accountId, amount, accountBalance, transferId,transferType));
	}

	@CommandHandler
	public void returnMoney(ReturnMoneyOfFailedTransferCommand command) {
		AggregateLifecycle.apply(new MoneyOfFailedTransferReturnedEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		logger.info(" <============ inside on(AccountCreatedEvent event) =============>" +event);
		this.accountId = event.getAccountId();
		this.setAccountBalance(event.getBalance());
		this.setCustomerSsn(event.getCustomerSsn());
		this.overdraftLimit = event.getOverdraftLimit();
		logger.info(" <============ inside on(AccountCreatedEvent event) =============> Done!!!");
	}

	@EventSourcingHandler
	public void on(MoneyAddedEvent event) {
		accountBalance += event.getAmount();
	}

	@EventSourcingHandler
	public void on(MoneySubtractedEvent event) {
		accountBalance -= event.getAmount();
	}

	 public void delete(DeleteAccountCommand command) {
		  
		  AggregateLifecycle.apply(new AccountDeletedEvent(command.getAccountId()));
	    }

	  
	  @EventSourcingHandler
	    public void on(AccountDeletedEvent event) {
	        this.accountId = event.getAccountId();
	        AggregateLifecycle.markDeleted();
	    }
	  
	
	
	public String getCustomerSsn() {
		return customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	
}
