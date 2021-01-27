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
package tat.microservice.bank.account.cmd.es.api.gateway;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tat.microservice.bank.account.cmd.es.exceptions.CustomerNotFoundException;

@Service
public class CustomerProxyImpl implements CustomerProxy{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	//private final String api-context-customer= "/api/v1/customers";
	//String api-context-account="/api/v1/accounts";
	 // private final RestTemplate restTemplate;
      @Value("${customer.api.url:http://customer:8080/api/v1/customers}")
      //@Value("${customer.api.url:http://custoner:9080}")
	   private String remoteURL;
	
	  
	    public CustomerDto getCustomer(String ssn) throws CustomerNotFoundException{
	    	
	    	logger.info(" <============ remoteURL =============> " +remoteURL);
	       RestTemplate restTemplate = new RestTemplate();
	    	
	    	CustomerDto customerDto = restTemplate.getForObject(remoteURL+"/"+ssn+"", CustomerDto.class);
	    	
	    	logger.info(" <============ customerDto.toString() =============>");
	    	logger.info( customerDto.toString());
	    	return customerDto;
	    	
	    }
	    
	    

	    @SuppressWarnings("unchecked")
		public  List<String> getCustomerByName(String name) throws CustomerNotFoundException{
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	    	 URI uri = URI.create(remoteURL+"/"+name+"");
	    	
	    	
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
	    	 URI uri = URI.create(remoteURL+"/events/"+ssn+"");
	    	
	    	
	    	return restTemplate.getForObject(uri, List.class);
	    	
	    }
}
