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
package tat.microservice.bank.transaction.api.gateway;

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
import org.springframework.web.client.RestTemplate;

import tat.microservice.bank.transaction.dto.CustomerDto;
import tat.microservice.bank.transaction.dto.UpdateCustomerDto;
import tat.microservice.bank.transaction.exceptions.CustomerNotFoundException;

@Service
public class CustomerProxyImpl implements CustomerProxy{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	//private String api-context-customer="/api/v1/customers";
	
	 // private final RestTemplate restTemplate;
      @Value("${customer.api.url:http://customer:8080/api/v1/customers}")
      // @Value("${customer.api.url:http://customer:9080}")
	   private String remoteURL;
	
	  
	    public CustomerDto getCustomer(String ssn) throws CustomerNotFoundException{
	    	
	    	logger.info(" <============ remoteURL =============> " +remoteURL);
	       RestTemplate restTemplate = new RestTemplate();
	       CustomerDto customerDto = null;
	       try {
	    	
	    	 customerDto = restTemplate.getForObject(remoteURL+"/"+ssn, CustomerDto.class);
	    	 
	       }catch(Exception ex) {
	    	   
	    	 
		    		logger.info(String.format("The customer not found: %s", customerDto));
		    		
					throw new CustomerNotFoundException(String.format("No person with id %s exists"), customerDto.getCustomerSsn());
				
	       }
	    	
	    	//logger.info(" <============ customerDto.toString() =============>");
	    	//logger.info( customerDto.toString());
	    	
	    	
	    	return customerDto;
	    	
	    }
	    
	    

	    @SuppressWarnings("unchecked")
		public  List<String> getCustomerByName(String name) throws CustomerNotFoundException{
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	    	 URI uri = URI.create(remoteURL+"/"+name);
	    	
	    	
	    	return restTemplate.getForObject(uri, List.class);
	    	
	    }
	    
	    
	    @SuppressWarnings("unchecked")
		public  List<String> getAllCustomers() {
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	    	 URI uri = URI.create(remoteURL);
	    	
	    	
	    	return restTemplate.getForObject(uri, List.class);
	    	
	    }
	    
	    @SuppressWarnings("unchecked")
		public  List<String> getListEventForCustomer(String ssn) throws CustomerNotFoundException{
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	    	 URI uri = URI.create(remoteURL+"/events/"+ssn);
	    	
	    	
	    	return restTemplate.getForObject(uri, List.class);
	    	
	    }
	    
	    public HttpHeaders retrieveHeaders() {
	    	RestTemplate restTemplate = new RestTemplate();
	        String url = remoteURL;

	        // send HEAD request
	        return restTemplate.headForHeaders(url);
	    }
	    

		public String createCustomer(CustomerDto customerDto) {
			RestTemplate restTemplate = new RestTemplate();
			String url = remoteURL;

			// create headers
			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			// set `accept` header
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// build the request
			HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);

			// send POST request
			ResponseEntity<CustomerDto> response = restTemplate.postForEntity(url, entity, CustomerDto.class);

			// check response status code
			if (response.getStatusCode() == HttpStatus.CREATED) {
				return response.toString();
			} else {
				return null;
			}

		}
		
		public void updateCustomer(String customer_ssn, UpdateCustomerDto updateCustomer) {
			RestTemplate restTemplate = new RestTemplate();
			String url = remoteURL +"/updateCustomer/{customer_ssn}";

			// create headers
			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			// set `accept` header
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// build the request
			HttpEntity<UpdateCustomerDto> entity = new HttpEntity<>(updateCustomer, headers);

			restTemplate.put(url, entity, customer_ssn);
		}
		
		public void deleteCustomer(String customer_ssn)throws CustomerNotFoundException {
			RestTemplate restTemplate = new RestTemplate();
			String url = remoteURL +"/deleteCustomer/{customer_ssn}";

		    // send DELETE request to delete post with `customer social security number`
		    restTemplate.delete(url, customer_ssn);
		}
}
