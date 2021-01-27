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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tat.microservice.bank.transaction.dto.AccountDto;
import tat.microservice.bank.transaction.dto.TransactionDto;
import tat.microservice.bank.transaction.dto.TransferDto;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForCreditException;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForDebitException;
import tat.microservice.bank.transaction.exceptions.AccountNotFoundException;

@Service
public class AccountProxyImpl implements AccountProxy {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	//private String api-context-account="/api/v1/accounts";
	// private final RestTemplate restTemplate;
	@Value("${account.api.url:http://account:8080/api/v1/accounts}")
	// @Value("${account.api.url:http://account:9080}")
	private String remoteURL;

	public AccountDto findAccountById(String accountId) throws AccountNotFoundException {

		logger.info(" <============ remoteURL =============> " + remoteURL);
		RestTemplate restTemplate = new RestTemplate();

		AccountDto accountDto = restTemplate.getForObject(remoteURL +"/" + accountId,
				AccountDto.class);

		logger.info(" <============ customerDto.toString() =============>");
		logger.info(accountDto.toString());
		return accountDto;

	}

	@SuppressWarnings("unchecked")
	public List<String> findAccountByCustomer_ssn(String ssn) throws AccountNotFoundException {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(remoteURL +"/" + ssn);

		return restTemplate.getForObject(uri, List.class);

	}

	@SuppressWarnings("unchecked")
	public List<String> findAllAccount() {

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

	public String createAccount(AccountDto accountDto) {
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL;

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);

		// send POST request
		ResponseEntity<AccountDto> response = restTemplate.postForEntity(url, entity, AccountDto.class);

		// check response status code
		if (response.getStatusCode() == HttpStatus.CREATED) {
			return response.toString();
		} else {
			return null;
		}

	}

	/*
	 * public void transfer1(TransferDto transferDto) throws
	 * AccountNotEligibleForCreditException, AccountNotEligibleForCreditException {
	 * RestTemplate restTemplate = new RestTemplate(); String url = remoteURL +
	 * "/account/transfer";
	 * 
	 * // create headers HttpHeaders headers = new HttpHeaders(); // set
	 * `content-type` header headers.setContentType(MediaType.APPLICATION_JSON); //
	 * set `accept` header
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	 * 
	 * // build the request HttpEntity<TransferDto> entity = new
	 * HttpEntity<>(transferDto, headers);
	 * 
	 * // send POST request restTemplate.postForEntity(url, entity,
	 * TransferDto.class); //ResponseEntity<TransferDto> response =
	 * restTemplate.postForEntity(url, entity, TransferDto.class);
	 * 
	 * 
	 * // check response status code if (response.getStatusCode() ==
	 * HttpStatus.CREATED) { return response.toString(); } else { return null; }
	 * 
	 * 
	 * }
	 */

	public String transfer(TransferDto transferDto)
			throws AccountNotEligibleForCreditException, AccountNotEligibleForCreditException {

		RestTemplate restTemplate = new RestTemplate();

		logger.info(" <----------- transfer(TransferDto transferDto) ------> " + transferDto);

		String REQUEST_URI = remoteURL +"/transfer";

		logger.info(" <============ REQUEST_URI =============> " + REQUEST_URI);
		MultiValueMap<String, String> headers = new HttpHeaders();

		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");

		HttpEntity<TransferDto> entity = new HttpEntity<>(transferDto, headers);

		logger.info(" <============ entity =============> " + entity);

		ResponseEntity<String> response = restTemplate.postForEntity(REQUEST_URI, entity, String.class);

		logger.info(" <============ response =============> " + response);

		return response.getBody();
	}

	public void deposit(TransactionDto transaction) throws AccountNotEligibleForCreditException {
		RestTemplate restTemplate = new RestTemplate();
		String accountNumber = transaction.getAccountId();
		String url = remoteURL +"/deposit/{accountNumber}";

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<TransactionDto> entity = new HttpEntity<>(transaction, headers);

		restTemplate.put(url, entity, accountNumber);
	}

	public void withdraw(TransactionDto transaction) throws AccountNotEligibleForDebitException {
		String accountNumber = transaction.getAccountId();
		RestTemplate restTemplate = new RestTemplate();
		String url = remoteURL +"/withdraw/{accountNumber}";

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<TransactionDto> entity = new HttpEntity<>(transaction, headers);

		restTemplate.put(url, entity, accountNumber);
	}
}
