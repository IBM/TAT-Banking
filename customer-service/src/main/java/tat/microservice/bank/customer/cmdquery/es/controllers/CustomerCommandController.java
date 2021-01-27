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

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import tat.microservice.bank.customer.cmd.es.commands.dto.CreateCustomerDto;
import tat.microservice.bank.customer.cmd.es.commands.dto.UpdateCustomerProfileDto;
import tat.microservice.bank.customer.cmd.es.services.CustomerCommandServices;
import tat.microservice.bank.customer.cmdquery.es.response.BaseResponse;

/**
 *
 * ITSO Customer Command Controller
 */
@RestController
@RequestMapping(value = "/api/v1/customers")
@Api(value = "Customer Commands", description = "Customer Command Events Endpoint", tags = "Customer Commands")
public class CustomerCommandController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerCommandServices customerCommandServices;

	public CustomerCommandController(CustomerCommandServices customerCommandServices) {
		this.customerCommandServices = customerCommandServices;
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public CompletableFuture<String> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
		logger.info("<----  inside CustomerCommandController createCustomer ----->" + createCustomerDto );
		
		
		return customerCommandServices.createCustomer(createCustomerDto);
	}

	@PutMapping(value = "/updateCustomer/{customer_ssn}", consumes = MediaType.APPLICATION_JSON_VALUE)
	 @ResponseStatus(value = HttpStatus.ACCEPTED)
	public BaseResponse updateCustomer(@PathVariable(value = "customer_ssn") String customer_ssn, @RequestBody UpdateCustomerProfileDto updateCustomerProfileDto){
		
		return customerCommandServices.updateCustomer(customer_ssn, updateCustomerProfileDto);
	}
	
	
	// @DeleteMapping(value = "/deleteCustomer/{customer_ssn}", consumes = MediaType.APPLICATION_JSON_VALUE)
	
	 @DeleteMapping(value = "/deleteCustomer/{customer_ssn}")
	 @ResponseStatus(value = HttpStatus.ACCEPTED)
	public BaseResponse deleteCustomer(@PathVariable(value = "customer_ssn") String customer_ssn){
				 
		 logger.info("<----  delete request received for customer ----->" + customer_ssn );
			
		
		return customerCommandServices.deleteCustomer(customer_ssn);
	}


}
