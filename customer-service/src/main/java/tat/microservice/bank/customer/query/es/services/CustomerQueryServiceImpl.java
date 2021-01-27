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
package tat.microservice.bank.customer.query.es.services;

import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tat.microservice.bank.customer.cmd.es.exceptions.CustomerNotFoundException;
import tat.microservice.bank.customer.query.es.entities.CustomerQueryEntity;
import tat.microservice.bank.customer.query.es.entities.repositories.CustomerRepository;

@Service
public class CustomerQueryServiceImpl implements CustomerQueryService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CustomerRepository repository;
	private final EventStore eventStore;


	public CustomerQueryServiceImpl(CustomerRepository repository, EventStore eventStore) {
		this.repository = repository;
		this.eventStore = eventStore;
	}

	public Iterable<CustomerQueryEntity> findAllCustomer() {

		return repository.findAll();
	}

	public CustomerQueryEntity findCustomerById(String id) throws CustomerNotFoundException {
		
		CustomerQueryEntity customer =repository.findOne(id);
		
				
		if(customer == null) {
			
		logger.info(String.format("The customer not found: %s", customer));
    		
			throw new CustomerNotFoundException(String.format("No person with id %s exists"), customer.getCustomerSsn());
			
		}

		return customer;
	}

	
	 @Override
	    public List<Object> listEventsForCustomer(String customerSsn) {
	        return eventStore.readEvents(customerSsn).asStream().map( s -> s.getPayload()).collect(Collectors.toList());
	    }


}
