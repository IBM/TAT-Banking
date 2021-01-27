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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tat.bank.ui.webapp.domains.Account;
import tat.bank.ui.webapp.exceptions.AccountNotFoundException;
import tat.bank.ui.webapp.exceptions.CustomerNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	//String api-context-account="/api/v1/accounts";

	// private final RestTemplate restTemplate;
   // @Value("${account.api.url:http://192.168.1.232:8081}")
	@Value("${account.api.url:http://account:8080/api/v1/accounts}")
	//@Value("${account.api.url:http://account:9080}")
	private String remoteURL;

	public Account findAccountById(String accountId) throws AccountNotFoundException {

		logger.info(" <============ remoteURL =============> " + remoteURL);
		logger.info(" <============ accountId =============> " + accountId);
		RestTemplate restTemplate = new RestTemplate();

		Account account = restTemplate.getForObject(remoteURL+"/"+accountId, Account.class);
		
		
		logger.info(" <============ account) =============> + account");
		logger.info(account.toString());
		return account;

	}

	@SuppressWarnings("unchecked")
	public List<Account>  findAccountByCustomer(String ssn) throws AccountNotFoundException {
		
	
		  List<Account> accounts =  new ArrayList<Account>();;
		
		logger.info(" <============ inside findAccountByCustomerSsn(String ssn) =============> " + ssn);

		RestTemplate restTemplate = new RestTemplate();
				
		URI uri = URI.create(remoteURL+"/findAccountByCustomer/"+ ssn);
		
		   accounts  = restTemplate.getForObject(uri, List.class);
		
		
		   logger.info(" <============ accounts =============>  " +accounts);
		return accounts;

	}

	@SuppressWarnings("unchecked")
	public Iterable<Account> findAllAccount() {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL);

		return restTemplate.getForObject(uri, List.class);

	}

	@SuppressWarnings("unchecked")
	public List<String> getListEventForAccount(String accountId) throws AccountNotFoundException {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL +"/events/" + accountId);

		return restTemplate.getForObject(uri, List.class);

	}

	public HttpHeaders retrieveHeaders() {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL;

		// send HEAD request
		return restTemplate.headForHeaders(url);
	}

	/*public void createAccount(Account account) {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL + "/account";
		
		logger.info(" <============ url =============> " + url);
		logger.info(" <============ account =============> " + account);


		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		logger.info(" <============  build the request =============> ");
		HttpEntity<Account> entity = new HttpEntity<>(account, headers);

		// send POST request
		logger.info(" <============  send POST request =============> ");
		 restTemplate.postForObject(url, entity, Account.class);
		 
		 logger.info(" <============ restTemplate.postForEntity(url, entity, Account.class)  =============> Done");
	
		
	}
	*/
	public String addOneAccount(String customerNumber) {
		
		RestTemplate restTemplate = new RestTemplate();
				
		logger.info(" <----------- addOneAccount ------> " + customerNumber);
	
		String REQUEST_URI = remoteURL;
		
		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		 MultiValueMap<String, String> headers = new HttpHeaders();
				  
		  headers.add("Content-Type", "application/json");
		  headers.add("Accept", "*/*");
		
		
		HttpEntity<Account> entity = new HttpEntity<>(accountDefaultValue(customerNumber), headers);
		  
		  logger.info(" <============ entity =============> " + entity);
		     
		  
		  
		//  ResponseEntity<Account> response= restTemplate.postForEntity(REQUEST_URI, entity, Account.class);
		  
		  ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);
		  
		  logger.info(" <============ response =============> " + response);
		 
		/*  Gson gs = new Gson();
		  
		  Account accountFromJson = gs.fromJson(response.getBody(), Account.class); 
		  
		  logger.info(" <============ accountFromJson =============> " + accountFromJson);*/
		  
		  return response.getBody();
		}
	
	public void deleteAccount(String accountId) throws AccountNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL +"/deleteAccount/{accountNumber}";
		
		logger.info(" <----------- deleteAccount ------> " + accountId);
		
		logger.info(" <============ url =============> " + url);
		
	
		// send DELETE request to delete the bank account with `account id`
		restTemplate.delete(url, accountId);
	}
	
	
	
	
	private Account accountDefaultValue(String customerNumber) {
		Account account = new Account();

		account.setOverdraftLimit(20000);
		account.setBalance(0.00);
		;
		account.setCustomerSsn(customerNumber);

		return account;
	}

	
	

	}
