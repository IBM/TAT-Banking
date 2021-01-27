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
package tat.microservice.bank.transaction.entities.listener;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import tat.microservice.bank.transaction.dto.TransactionType;
import tat.microservice.bank.transaction.entities.Transaction;
import tat.microservice.bank.transaction.entities.repositories.TransactionRepository;
import tat.microservice.bank.transaction.events.MoneyAddedEvent;
import tat.microservice.bank.transaction.events.MoneySubtractedEvent;

@Component
public class TransactionEventListener {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private TransactionRepository repository;
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	public TransactionEventListener(TransactionRepository repository, SimpMessageSendingOperations messagingTemplate) {
		this.repository = repository;
		this.messagingTemplate = messagingTemplate;
	}
	/*
	 * @EventHandler public void on(AccountCreatedEvent event) { repository.save(new
	 * AccountQueryEntity(event.getAccountId(),
	 * event.getCustomer_ssn(),event.getBalance(), event.getOverdraftLimit()));
	 * 
	 * broadcastUpdates(); }
	 */

	/*
	 * @EventHandler public void on(MoneyAddedEvent event) {
	 * 
	 * logger.info("<-- inside on(MoneyAddedEvent event) --->" +
	 * event.getAccountId());
	 * 
	 * Transaction transaction = new
	 * Transaction(String.valueOf(TransactionType.CREDIT), event.getAmount(),
	 * event.getAccountId());
	 * 
	 * repository.save(transaction);
	 * 
	 * broadcastUpdates(); }
	 * 
	 * @EventHandler public void on(MoneySubtractedEvent event) {
	 * logger.info("<-- inside on(MoneySubtractedEvent even) --->" +
	 * event.getAccountId());
	 * 
	 * Transaction transaction = new
	 * Transaction(String.valueOf(TransactionType.DEBIT), event.getAmount(),
	 * event.getAccountId());
	 * 
	 * repository.save(transaction);
	 * 
	 * broadcastUpdates(); }
	 */

	private void broadcastUpdates() {
		Iterable<Transaction> transactions = repository.findAll();
		messagingTemplate.convertAndSend("/topic/bank-accounts.updates", transactions);
	}

}
