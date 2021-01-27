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
package tat.microservice.bank.customer.cmdquery.es.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import tat.microservice.bank.customer.cmd.es.exceptions.CustomerNotFoundException;
import tat.microservice.bank.customer.query.es.entities.CustomerQueryEntity;
import tat.microservice.bank.customer.query.es.services.CustomerQueryService;

@RestController
@RequestMapping(value = "/api/v1/customers")
@Api(value = "Customer Query", description = "Customer Query Related Endpoints", tags = "Customer Queries")
public class CustomerQueryController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final CustomerQueryService customerQueryService;

	public CustomerQueryController(CustomerQueryService customerQueryService) {
		super();
		this.customerQueryService = customerQueryService;
	}
	
	
	
	 @GetMapping("/GetAllCustomers")
	    public Iterable<CustomerQueryEntity> findAllCustomer(){
	        return customerQueryService.findAllCustomer();
	    }
	 
	 @GetMapping("/{customer_ssn}")
	    public CustomerQueryEntity findCustomerById(@PathVariable(value = "customer_ssn") String customer_ssn) {
	        try {
				return customerQueryService.findCustomerById(customer_ssn);
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }
	 
		  
	  @GetMapping("/events/{customer_ssn}")
	    public List<Object> listEventsForCustomer(@PathVariable(value = "customer_ssn") String customer_ssn){
	        return customerQueryService.listEventsForCustomer(customer_ssn);
	    }
	  
	
	

}
