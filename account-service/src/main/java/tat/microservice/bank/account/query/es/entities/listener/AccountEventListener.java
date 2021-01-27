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
package tat.microservice.bank.account.query.es.entities.listener;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import tat.microservice.bank.account.cmd.es.events.AccountCreatedEvent;
import tat.microservice.bank.account.cmd.es.events.AccountDeletedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneyAddedEvent;
import tat.microservice.bank.account.cmd.es.events.MoneySubtractedEvent;
import tat.microservice.bank.account.query.es.entities.AccountQueryEntity;
import tat.microservice.bank.account.query.es.entities.repositories.AccountRepository;

@Component
public class AccountEventListener {

    private AccountRepository repository;
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public AccountEventListener(AccountRepository repository, SimpMessageSendingOperations messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        repository.save(new AccountQueryEntity(event.getAccountId(), event.getCustomerSsn(),event.getBalance(), event.getOverdraftLimit()));

        broadcastUpdates();
    }

    @EventHandler
    public void on(MoneyAddedEvent event) {
        AccountQueryEntity accountQueryEntity = repository.findOneByAccountId(event.getAccountId());
        accountQueryEntity.setBalance(accountQueryEntity.getBalance() + event.getAmount());

        repository.save(accountQueryEntity);

        broadcastUpdates();
    }

    @EventHandler
    public void on(MoneySubtractedEvent event) {
    	AccountQueryEntity accountQueryEntity = repository.findOneByAccountId(event.getAccountId());
    	accountQueryEntity.setBalance(accountQueryEntity.getBalance() - event.getAmount());

        repository.save(accountQueryEntity);

        broadcastUpdates();
    }
    
    @EventHandler
    public void on(AccountDeletedEvent event) {
    	AccountQueryEntity accountQueryEntity = repository.findOneByAccountId(event.getAccountId());
        repository.delete(accountQueryEntity);

        broadcastUpdates();
    }


    private void broadcastUpdates() {
        Iterable<AccountQueryEntity> accountQueryEntities = repository.findAll();
        messagingTemplate.convertAndSend("/topic/bank-accounts.updates", accountQueryEntities);
    }

}
