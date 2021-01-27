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
package tat.microservice.bank.customer.cmd.es.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import tat.microservice.bank.customer.cmd.es.commands.CreateCustomerCommand;
import tat.microservice.bank.customer.cmd.es.commands.DeleteCustomerProfileCommand;
import tat.microservice.bank.customer.cmd.es.commands.UpdateCustomerProfileCommand;
import tat.microservice.bank.customer.cmd.es.events.CustomerCreatedDateEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerCreatedEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerDeletedEvent;
import tat.microservice.bank.customer.cmd.es.events.CustomerUpdatedEvent;


@Aggregate
public class Customer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@AggregateIdentifier
	private String customerSsn;
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	private String title;
	private String firstname;
	private String lastname;
	private String recordCreationDate;

	

	public Customer() {
	}

	@CommandHandler
	public Customer(CreateCustomerCommand createCustomerCommand) {
		logger.info("@@@@222<@CommandHandle> inside Customer createCustomerCommand" + createCustomerCommand);
		
		Assert.notNull(createCustomerCommand.customerSsn, " Customer SSN should not be null or empty");
		Assert.notNull(createCustomerCommand.title, " Title should not be null or empty");
		Assert.notNull(createCustomerCommand.firstname, " First Name should not be null or empty");
		Assert.notNull(createCustomerCommand.lastname, " Last Name should not be null or empty");

		
		AggregateLifecycle.apply(new CustomerCreatedEvent(createCustomerCommand.customerSsn, createCustomerCommand.title,
				createCustomerCommand.firstname, createCustomerCommand.lastname));
		logger.info("@@@@222<@CommandHandler> inside Customer createCustomerCommand --> successfully ran!");
	}

	@EventSourcingHandler
	protected void on(CustomerCreatedEvent CustomerCreatedEvent) {

		logger.info("@@@@333 <<@EventSourcingHandler>> inside Customer CustomerCreatedEvent" + CustomerCreatedEvent);
	
		this.customerSsn = CustomerCreatedEvent.getCustomerSsn();
		this.title = CustomerCreatedEvent.getTitle();
		this.firstname = CustomerCreatedEvent.getFirstname();
		this.lastname = CustomerCreatedEvent.getLastname();
		this.recordCreationDate =new RecordCreationDate().getCreatedDate();
				
		AggregateLifecycle.apply(new CustomerCreatedDateEvent(this.customerSsn,new RecordCreationDate()));
		
		logger.info("@@@@333<<@EventSourcingHandler>> inside Customer CustomerCreatedEvent --> successfully ran!");
	}

		
	@CommandHandler
	public void handle(UpdateCustomerProfileCommand updateCustomerCommand) {
		
		Assert.notNull(updateCustomerCommand.customerSsn, " Customer SSN should not be null or empty");
		Assert.notNull(updateCustomerCommand.title, " Title should not be null or empty");
		Assert.notNull(updateCustomerCommand.firstname, " First Name should not be null or empty");
		Assert.notNull(updateCustomerCommand.lastname, " Last Name should not be null or empty");

		AggregateLifecycle.apply(new CustomerUpdatedEvent(updateCustomerCommand.customerSsn,  updateCustomerCommand.title,
				updateCustomerCommand.firstname, updateCustomerCommand.lastname));

	}
	
	
	@EventSourcingHandler
	public void on(CustomerUpdatedEvent CustomerUpdatedEvent) {
		
		
		this.title = CustomerUpdatedEvent.getTitle();
		this.firstname = CustomerUpdatedEvent.getFirstname();
		this.lastname = CustomerUpdatedEvent.getLastname();
		this.recordCreationDate =new RecordCreationDate().getCreatedDate();
		
		// AggregateLifecycle.apply(CustomerUpdatedEvent);
	}
	
/*

	@CommandHandler
	public Customer(DeleteCustomerProfileCommand deleteCustomerCommand) {
		
		logger.info("@@@@222<@CommandHandle> inside Customer deleteCustomerCommand" + deleteCustomerCommand);
		
		Assert.notNull(deleteCustomerCommand.customerSsn, " Customer SSN should not be null or empty");
		Assert.notNull(deleteCustomerCommand.title, " Title should not be null or empty");
		Assert.notNull(deleteCustomerCommand.firstname, " First Name should not be null or empty");
		Assert.notNull(deleteCustomerCommand.lastname, " Last Name should not be null or empty");

		
	//	AggregateLifecycle.apply(new CustomerDeletedEvent(deleteCustomerCommand.customerSsn, deleteCustomerCommand.title, deleteCustomerCommand.firstname, deleteCustomerCommand.lastname));
		logger.info("<---- Customer id "+ deleteCustomerCommand.customerSsn + "removed");
	}
	*/
	  public void delete(DeleteCustomerProfileCommand command) {
		  
		  AggregateLifecycle.apply(new CustomerDeletedEvent(command.customerSsn));
	    }

	  
	  @EventSourcingHandler
	    public void on(CustomerDeletedEvent event) {
	        this.customerSsn = event.getCustomerSsn();
	        AggregateLifecycle.markDeleted();
	    }
	  
	/*  
	@EventSourcingHandler
	public void on(DeleteCustomerProfileCommand customerDeletedEvent) {

		logger.info("@@@@222<@CommandHandle> inside Customer CustomerDeletedEvent" + customerDeletedEvent);
		
		if(customerDeletedEvent.getCustomerSsn() !=null) {
			
			AggregateLifecycle.markDeleted();
			
		}
		
		
		// AggregateLifecycle.apply(CustomerUpdatedEvent);
	}
	*/

	public String getCustomerSsn() {
		return customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	public String getRecordCreationDate() {
		return recordCreationDate;
	}

	public void setRecordCreationDate(String recordCreationDate) {
		this.recordCreationDate = recordCreationDate;
	}

	/*
	 * private void applyEvent(CustomerCreatedDateEvent event) {
	 * AggregateLifecycle.apply(event); }
	 */
}
