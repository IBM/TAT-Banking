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
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tat.microservice.bank.account.cmd.es.commands.CreateTransferCommand;
import tat.microservice.bank.account.cmd.es.commands.MarkTransferCompletedCommand;
import tat.microservice.bank.account.cmd.es.commands.MarkTransferFailedCommand;
import tat.microservice.bank.account.cmd.es.events.TransferCompletedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferCreatedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferFailedEvent;

@Aggregate
public class AccountTransfer {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@AggregateIdentifier
	private String transferId;
	private String fromAccountId;
	private String toAccountId;
	private double amount;
	private Status status;

	@SuppressWarnings("unused")
	protected AccountTransfer() {
	}

	@CommandHandler
	public AccountTransfer(CreateTransferCommand command) {
		logger.info(" <============  AccountTransfer(CreateTransferCommand command)  =============>" +command);
		AggregateLifecycle.apply(new TransferCreatedEvent(command.getFromAccountId(),
				command.getToAccountId(), command.getTransferId(), command.getAmount(),String.valueOf(Status.COMPLETED)));
		
		logger.info(" <============  AccountTransfer(CreateTransferCommand command)  =============>" +command.toString());
	}

	@CommandHandler
	public void handle(MarkTransferCompletedCommand command) {
		logger.info(" <============  handle(MarkTransferCompletedCommand command)  =============>" +command);
		AggregateLifecycle.apply(new TransferCompletedEvent(command.getTransferId()));
		logger.info(" <============  handle(MarkTransferCompletedCommand command)  =============> completed!! -->" +command.toString());
	}

	@CommandHandler
	public void handle(MarkTransferFailedCommand command) {
		logger.info(" <============  handle(MarkTransferFailedCommand command)  =============>" +command);
		AggregateLifecycle.apply(new TransferFailedEvent(command.getTransferId(),String.valueOf(Status.FAILED)));
		logger.info(" <============  handle(MarkTransferFailedCommand command)  =============> failed -->" +command.toString());
	}

	@Override
	public String toString() {
		return "AccountTransfer [transferId=" + transferId + ", fromAccountId=" + fromAccountId + ", toAccountId="
				+ toAccountId + ", amount=" + amount + ", status=" + status + "]";
	}

	@EventHandler
	public void on(TransferCreatedEvent event) throws Exception {
		logger.info(" <============  on(TransferCreatedEvent event)  =============>" +event);
		this.transferId = event.getTransferId();
		this.fromAccountId = event.getFromAccountId();
		this.toAccountId = event.getToAccountId();
		this.amount = event.getAmount();
		this.status = Status.STARTED;
		logger.info(" <============  on(TransferCreatedEvent event)  =============>" +event.toString());
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@EventHandler
	public void on(TransferCompletedEvent event) {
		this.status = Status.COMPLETED;
	}

	@EventHandler
	public void on(TransferFailedEvent event) {
		this.status = Status.FAILED;
	}

	private enum Status {
		STARTED, FAILED, COMPLETED
	}
}