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
import org.springframework.stereotype.Component;

import tat.microservice.bank.account.cmd.es.events.TransferCompletedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferCreatedEvent;
import tat.microservice.bank.account.cmd.es.events.TransferFailedEvent;
import tat.microservice.bank.account.query.es.entities.AccountTransferQueryEntity;
import tat.microservice.bank.account.query.es.entities.repositories.AccountTransferRepository;

@Component
public class AccountTransferEventListener {

    private AccountTransferRepository repository;

    @Autowired
    public AccountTransferEventListener(AccountTransferRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(TransferCreatedEvent event) {
        repository.save(new AccountTransferQueryEntity(event.getTransferId(),
                                              event.getFromAccountId(),
                                              event.getToAccountId(),
                                              event.getAmount()));
    }

    @EventHandler
    public void on(TransferFailedEvent event) {
    	AccountTransferQueryEntity accountTransferQueryEntity = repository.findOneByTransferId(event.getTransferId());
    	accountTransferQueryEntity.setStatus(String.valueOf(AccountTransferQueryEntity.Status.FAILED));

        repository.save(accountTransferQueryEntity);
    }

    @EventHandler
    public void on(TransferCompletedEvent event) {
    	AccountTransferQueryEntity accountTransferQueryEntity = repository.findOneByTransferId(event.getTransferId());
    	accountTransferQueryEntity.setStatus(String.valueOf(AccountTransferQueryEntity.Status.COMPLETED));

        repository.save(accountTransferQueryEntity);
    }

   

}
