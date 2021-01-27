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

import tat.bank.ui.webapp.domains.Transaction;
import tat.bank.ui.webapp.domains.Transfer;
import tat.bank.ui.webapp.exceptions.TransactionNotFoundException;

@Service
public class AccountTxnServiceImpl implements AccountTxnService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	//String api-context-transaction="/api/v1/transactions";

	// private final RestTemplate restTemplate;
   // @Value("${account.api.url:http://192.168.1.232:8082}")
    @Value("${transaction.api.url:http://transaction:8080/api/v1/transactions}")
	//@Value("${transaction.api.url:http://transaction:9080}")
	private String remoteURL;

	@Override
	public List<Transaction> findAllTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction findTransactionById(String transactionId) throws TransactionNotFoundException {
				// TODO Auto-generated method stub
		logger.info(" <============ remoteURL =============> " + remoteURL);
		RestTemplate restTemplate = new RestTemplate();

		Transaction transactionEntity = restTemplate.getForObject(remoteURL+"/"+transactionId, Transaction.class);
		
		
		
		logger.info(transactionEntity.toString());
	
		return transactionEntity;
	}

	@Override
	public List<Transaction> findTransactionByAccoutID(String account_Id) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL +"/findTransByAccountID/" + account_Id);
		
		   //ResponseEntity<TransactionEntity>  response = responserestTemplate.getForObject(uri, List.class);
		   
		   ResponseEntity<List>  response = restTemplate.getForEntity(uri, List.class);
		   
		   logger.info(" <----------- response  ------> " +response);
		   	   
		   List<Transaction> list =  response.getBody();
		   
		/*
		 * logger.info(" <----------- response.getBody()  ------> "
		 * +response.getBody());
		 * 
		 * 
		 * Gson gs = new Gson();
		 * 
		 * String transactionEntityToJson = gs.toJson(response.getBody());
		 * 
		 * 
		 * logger.info(" <============ transactionEntityToJson =============> " +
		 * transactionEntityToJson);
		 * 
		 * logger.info(" <============  response.getBody().getClass() =============> " +
		 * response.getBody().getClass());
		 * 
		 * 
		 * String[] listtxn = gs.fromJson(transactionEntityToJson, String[].class);
		 * 
		 * logger.info(" <============ listtxn =============> " + listtxn);
		 */
			  
			  

		return list;
	}

	
	/*
     * Deposit fund to a personal account
     */
	
	public String doDeposit(Transaction transaction) {
		
		RestTemplate restTemplate = new RestTemplate();
				
		logger.info(" <----------- addOneDeposit(TransactionEntity transaction)  transaction ------> " + transaction.toString());
	
		String REQUEST_URI = remoteURL +"/deposit";
		
		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		 MultiValueMap<String, String> headers = new HttpHeaders();
				  
		  headers.add("Content-Type", "application/json");
		  headers.add("Accept", "*/*");
		
		
		HttpEntity<Transaction> entity = new HttpEntity<>(transaction, headers);
		  
		  logger.info(" <============ entity =============> " + entity);
		     
		  
		  
		//  ResponseEntity<Account> response= restTemplate.postForEntity(REQUEST_URI, entity, Account.class);
		  
		  ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);
		  
		  logger.info(" <============ response =============> " + response);
		 
		/*
		 * Gson gs = new Gson();
		 * 
		 * Account accountFromJson = gs.fromJson(response.getBody(), Account.class);
		 * 
		 * logger.info(" <============ accountFromJson =============> " +
		 * accountFromJson);
		 */
		  
		  return response.getBody();
		}

	/*
     * Withdraw fund from a personal account
     */
	public String doWithdraw(Transaction transaction) {
		
		RestTemplate restTemplate = new RestTemplate();
				
		logger.info(" <----------- withdrawOne(TransactionEntity transaction)  transaction ------> " + transaction.toString());
	
		String REQUEST_URI = remoteURL +"/withdraw";
		
		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		 MultiValueMap<String, String> headers = new HttpHeaders();
				  
		  headers.add("Content-Type", "application/json");
		  headers.add("Accept", "*/*");
		
		
		HttpEntity<Transaction> entity = new HttpEntity<>(transaction, headers);
		  
		  logger.info(" <============ entity =============> " + entity);
		     
		  
		  
		//  ResponseEntity<Account> response= restTemplate.postForEntity(REQUEST_URI, entity, Account.class);
		  
		  ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);
		  
		  logger.info(" <============ response =============> " + response);
		 
		/*  Gson gs = new Gson();
		  
		  Account accountFromJson = gs.fromJson(response.getBody(), Account.class); 
		  
		  logger.info(" <============ accountFromJson =============> " + accountFromJson);*/
		  
		  return response.getBody();
		}

	/*
     * Transfer fund from one account to another account
     */
	
	public String doTransfer(Transfer transfer) {
		
		RestTemplate restTemplate = new RestTemplate();
				
		logger.info(" <----------- doTransfer(Transfer transfer)  transfer ------> " + transfer);
	
		String REQUEST_URI = remoteURL +"/transfer";
		
		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		 MultiValueMap<String, String> headers = new HttpHeaders();
				  
		  headers.add("Content-Type", "application/json");
		  headers.add("Accept", "*/*");
		
		
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		  
		  logger.info(" <============ entity =============> " + entity);
		     
		   
		  
		  ResponseEntity<String> response= restTemplate.postForEntity(REQUEST_URI, entity, String.class);
		  
		  logger.info(" <============ response =============> " + response);
		 
		
		  
		  return response.getBody();
		}
}
