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
package tat.bank.ui.webapp.services;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tat.bank.ui.webapp.domains.Account;
import tat.bank.ui.webapp.domains.Customer;
import tat.bank.ui.webapp.exceptions.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// http://istio-ingressgateway-istio-system.apps-crc.testing/account
	// private final RestTemplate restTemplate;
	//@Value("${customer.api.url:http://192.168.1.232:8080}")
	 @Value("${customer.api.url:http://customer:8080/api/v1/customers}")
	//@Value("${customer.api.url:http://customer:9080}")
	private String remoteURL;
	

	public Customer getCustomer(String ssn) throws CustomerNotFoundException {
		//String api-context-customer="/api/v1/customers";
		logger.info(" <============ remoteURL =============> " + remoteURL +"/" + ssn);
		RestTemplate restTemplate = new RestTemplate();
		Customer customer = null;
		
		try {

			customer = restTemplate.getForObject(remoteURL +"/" + ssn.trim(), Customer.class);

		} catch (Exception ex) {

			logger.info(String.format("The customer not found: %s", customer));

			throw new CustomerNotFoundException(String.format("No person with id %s exists"),
					customer.getCustomerSsn());

		}

		// logger.info(" <============ customerDto.toString() =============>");
		// logger.info( customerDto.toString());

		return customer;

	}

	@SuppressWarnings("unchecked")
	public Iterable<Customer> getCustomerByName(String name) throws CustomerNotFoundException {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL +"/"+ name.trim());

		return restTemplate.getForObject(uri, List.class);

	}

	@SuppressWarnings("unchecked")
	public Iterable<Customer> getAllCustomers() {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL);

		return restTemplate.getForObject(uri, List.class);

	}

	@SuppressWarnings("unchecked")
	public List<String> getListEventForCustomer(String ssn) throws CustomerNotFoundException {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL +"/events/" + ssn);

		return restTemplate.getForObject(uri, List.class);

	}

	public HttpHeaders retrieveHeaders() {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL;

		// send HEAD request
		return restTemplate.headForHeaders(url);
	}

	/*public Customer createCustomer(Customer customer) {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL + "/customer";

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

		// send POST request
		ResponseEntity<Customer> response = restTemplate.postForEntity(url, entity, Customer.class);

		// check response status code
		if (response.getStatusCode() == HttpStatus.CREATED) {
			return response.getBody();
		} else {
			return null;
		}

	}*/
	
public String createCustomer(Customer customer) {
		
		RestTemplate restTemplate = new RestTemplate();
				
		logger.info(" <----------- createCustomer ------> " + customer);
	
		String REQUEST_URI = remoteURL;
		
		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		 MultiValueMap<String, String> headers = new HttpHeaders();
				  
		  headers.add("Content-Type", "application/json");
		  headers.add("Accept", "*/*");
		
		
		HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);
		  
		  logger.info(" <============ entity =============> " + entity);
		     
				  
		  ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);
		  
		  logger.info(" <============ response =============> " + response);
		 
		 
				  
		  return response.getBody();
		}

	public void updateCustomer(Customer updateCustomer) {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL +"/updateCustomer/{customerSsn}";

		String customerSsn = updateCustomer.getCustomerSsn();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<Customer> entity = new HttpEntity<>(updateCustomer, headers);

		restTemplate.put(url, entity, customerSsn);
	}

	public void deleteCustomer(String customerSsn) throws CustomerNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL +"/deleteCustomer/{customer_ssn}";
		//String url = remoteURL +"/deleteCustomer/";
		
		logger.info(" <----------- deleteCustomer ------> " + customerSsn);
		
		logger.info(" <============ url =============> " + url);
		
		
		// MultiValueMap<String, String> headers = new HttpHeaders();
				  
		//  headers.add("Content-Type", "application/json");
		 // headers.add("Accept", "*/*");
		

		// build the request
		//HttpEntity<String> entity = new HttpEntity<>(customerSsn, headers);
		
		//  logger.info(" <============ entity =============> " + entity);
		  
		  //ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);


		// send DELETE request to delete post with `customer social security number`
		restTemplate.delete(url, customerSsn);
	}
}
