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
package tat.microservice.bank.customer.query.es.entities.listener;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import tat.microservice.bank.customer.cmd.es.events.CustomerCreatedDateEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerCreatedEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerDeletedEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerUpdatedEvent;
import tat.microservice.bank.customer.query.es.entities.CustomerQueryEntity;
import tat.microservice.bank.customer.query.es.entities.repositories.CustomerRepository;

@Component
public class CustomerEventListener {

    private CustomerRepository repository;
    private SimpMessageSendingOperations messagingTemplate;
    String createDate;

    @Autowired
    public CustomerEventListener(CustomerRepository repository, SimpMessageSendingOperations messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        repository.save(new CustomerQueryEntity(event.getCustomerSsn(),event.getTitle(), event.getFirstname(), event.getLastname(), createDate));

        broadcastUpdates();
    }

    @EventHandler
    public void on(CustomerCreatedDateEvent event) {
         createDate = event.getRecordCreationDate().getCreatedDate();

    }
    
    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        repository.save(new CustomerQueryEntity(event.getCustomerSsn(),event.getTitle(), event.getFirstname(), event.getLastname(), createDate));

        broadcastUpdates();
    }
    
    @EventHandler
    public void on(CustomerDeletedEvent event) {
    	
    	CustomerQueryEntity customerQueryEntity = repository.findOne(event.getCustomerSsn());
        repository.delete(customerQueryEntity);
        broadcastUpdates();
    }



    private void broadcastUpdates() {
        Iterable<CustomerQueryEntity> customerQueryEntities = repository.findAll();
        messagingTemplate.convertAndSend("/topic/bank-customers.updates", customerQueryEntities);
    }

}
