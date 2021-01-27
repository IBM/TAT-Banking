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
package tat.microservice.bank.account.cmd.es.handlers;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tat.microservice.bank.account.cmd.es.aggregates.Account;
import tat.microservice.bank.account.cmd.es.commands.DebitSourceAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.DeleteAccountCommand;
import tat.microservice.bank.account.cmd.es.commands.TransferCreditDestinationAccountCommand;
import tat.microservice.bank.account.cmd.es.events.TransferDestinationAccountNotFoundEvent;
import tat.microservice.bank.account.cmd.es.events.TransferSourceAccountNotFoundEvent;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

public class AccountManagerCommandHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Repository<Account> repository;
	private EventBus eventBus;

	public AccountManagerCommandHandler(Repository<Account> repository, EventBus eventBus) {
		this.repository = repository;
		this.eventBus = eventBus;
	}

	@CommandHandler
	public void handle(DebitSourceAccountCommand command) {
		logger.info("<--- DebitSourceAccountCommand command ---->");
		try {
			Aggregate<Account> account = repository.load(command.getAccountId());
			account.execute(Account -> Account.debit(command.getAmount(), command.getTransferId()));
		} catch (AggregateNotFoundException exception) {
			eventBus.publish(asEventMessage(new TransferSourceAccountNotFoundEvent(command.getTransferId())));
		}
	}

	@CommandHandler
	public void handle(TransferCreditDestinationAccountCommand command) {
		
		logger.info("<--- TransferCreditDestinationAccountCommand command ---->");
		try {
			Aggregate<Account> bankAccountAggregate = repository.load(command.getAccountId());
			bankAccountAggregate
					.execute(bankAccount -> bankAccount.credit(command.getAmount(), command.getTransferId()));

		} catch (AggregateNotFoundException exception) {
			eventBus.publish(asEventMessage(new TransferDestinationAccountNotFoundEvent(command.getTransferId())));
		}
	}
	
	  @CommandHandler
	    public void handle(DeleteAccountCommand command) {
	        Aggregate<Account> account = repository.load(command.getAccountId());
	        account.execute(aggregateRoot -> {
	            aggregateRoot.delete(command);
	        });
	    }
}
