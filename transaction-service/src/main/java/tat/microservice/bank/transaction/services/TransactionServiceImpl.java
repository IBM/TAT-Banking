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
package tat.microservice.bank.transaction.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import tat.microservice.bank.transaction.api.gateway.AccountProxy;
import tat.microservice.bank.transaction.api.gateway.CustomerProxy;
import tat.microservice.bank.transaction.dto.AccountDto;
import tat.microservice.bank.transaction.dto.CustomerDto;
import tat.microservice.bank.transaction.dto.TransactionDto;
import tat.microservice.bank.transaction.dto.TransactionType;
import tat.microservice.bank.transaction.dto.TransferDto;
import tat.microservice.bank.transaction.entities.Transaction;
import tat.microservice.bank.transaction.entities.repositories.TransactionRepository;
import tat.microservice.bank.transaction.exceptions.AccountBalanceException;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForCreditException;
import tat.microservice.bank.transaction.exceptions.AccountNotFoundException;
import tat.microservice.bank.transaction.exceptions.CustomerNotFoundException;
import tat.microservice.bank.transaction.exceptions.ErrorValidatingCustomerException;

@Service
public class TransactionServiceImpl implements TransactionService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TransactionRepository repository;

	private final AccountProxy accountProxy;
	private final CustomerProxy customerProxy;

	// private final Repository<Transaction> transactionRepository;

	public TransactionServiceImpl(AccountProxy accountProxy, CustomerProxy customerProxy,
			TransactionRepository repository) {
		// this.repository = repository;
		this.customerProxy = customerProxy;
		this.accountProxy = accountProxy;
		this.repository = repository;
	}

	public void withdraw(TransactionDto transactionDto) throws CustomerNotFoundException, AccountNotFoundException {

		logger.info("<-- inside withdraw(TransactionDto transactionDto) --->" + transactionDto);

		try {
			AccountDto account = validateCustomerAccount(transactionDto.getAccountId(),
					transactionDto.getCustomerSsn());

			validateAccountBalance(account, transactionDto.getAmount());

			processTransaction(transactionDto, String.valueOf(TransactionType.DEBIT));

			Transaction transaction = new Transaction(String.valueOf(TransactionType.DEBIT), transactionDto.getAmount(),
					transactionDto.getAccountId());

			repository.save(transaction);

			logger.info("<-- Transaction has successfully completed --->");

		} catch (ErrorValidatingCustomerException | AccountBalanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	public void deposit(TransactionDto transactionDto) throws CustomerNotFoundException, AccountNotFoundException {

		logger.info("<-- inside deposit(TransactionDto transactionDto) --->" + transactionDto);

		try {

			validateCustomerAccount(transactionDto.getAccountId(), transactionDto.getCustomerSsn());

			processTransaction(transactionDto, String.valueOf(TransactionType.CREDIT));

			Transaction transaction = new Transaction(String.valueOf(TransactionType.CREDIT),
					transactionDto.getAmount(), transactionDto.getAccountId());

			repository.save(transaction);

			logger.info("<-- Transaction has successfully completed --->");

		} catch (ErrorValidatingCustomerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	
	
	 public String transfer(TransferDto transferDto)
				throws AccountNotEligibleForCreditException, AccountNotEligibleForCreditException{
		 
		 logger.info("<-- inside  transfer(TransferDto transferDto) --->" + transferDto);

		 String respString = null;
		 
			try {

				respString =accountProxy.transfer(transferDto);
			      	 			      	 
			      	transferTransactionDebit(transferDto);
			    	transferTransactionCredit(transferDto);
			    	
				logger.info("<-- Transaction has successfully completed --->");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return respString;
		 
	 }
	@SuppressWarnings("unused")
	private AccountDto validateCustomerAccount(String accountId, String customerId)
			throws CustomerNotFoundException, AccountNotFoundException, ErrorValidatingCustomerException, HttpServerErrorException {

		logger.info(String.format("Fetching customer: %s", customerId));

		CustomerDto customer = customerProxy.getCustomer(customerId);
		if (customer == null) {
			logger.info(String.format("The customer not found: %s", customer));
			throw new CustomerNotFoundException(String.format("No person with id %s exists"), customerId);
		}

		logger.info(String.format("Fetching account: %s", accountId));
		AccountDto account = accountProxy.findAccountById(accountId);

		if (!account.getCustomerSsn().trim().equals(customer.getCustomerSsn().trim())) {

			String msg = "Unable to validate the customer or the customer does not have a valid account.";
			logger.warn(msg);
			throw new ErrorValidatingCustomerException(msg);

		}

		return account;

	}

	private void validateAccountBalance(AccountDto account, double amount) throws AccountBalanceException {
		if (account.getBalance() < amount) {
			String msg = "Insufficient balance on account.";
			logger.warn(msg);
			throw new AccountBalanceException(msg);
		}
	}

	public void processTransaction(TransactionDto transaction, String transactionType) throws Exception {

		logger.info("<-- inside deposit(TransactionDto transactionDto) --->" + transaction.toString()
				+ " transactionType " + transactionType);

		if (transactionType.equals(String.valueOf(TransactionType.CREDIT))) {
			logger.info(String.format("true credit"));

			accountProxy.deposit(transaction);

		} else if (transactionType.equals(String.valueOf(TransactionType.DEBIT))) {
			logger.info(String.format("false debit"));

			accountProxy.withdraw(transaction);

		} else {
			String msg = "An unknown error occurred during account update.";
			logger.error(msg);
			throw new Exception(msg);
		}

	}

	public Transaction getTransaction(String transactionId) {
		return this.repository.findOne(transactionId);
	}

	public Iterable<Transaction> findAllTransaction() {

		return repository.findAllByOrderByIdAsc();
	}

	public Iterable<Transaction> findOneByAccountId(String accountId) {

		// return accountRepository.findOne(id);
		logger.info("<--- repository.findOneByAccount_Id(account_id) " + accountId);
		return repository.findOneByAccountId(accountId);

	}
	
	  public Iterable<Transaction> findOneByTransType(String trans_type) {
	  
	  // return accountRepository.findOne(id);
	  logger.info("<--- repository.findOneByTransType(transType) " + trans_type);
	  return repository.findOneByTransType(trans_type);
	  
	  }
	 
	  private void transferTransactionDebit(TransferDto transferDto) {
		  

	      	Transaction transaction = new Transaction(String.valueOf(TransactionType.DEBIT), transferDto.getAmount(),
	      			transferDto.getFromAccountId());
			  repository.save(transaction);
	  }
	  
	  private void transferTransactionCredit(TransferDto transferDto) {
		  

	      	Transaction transaction = new Transaction(String.valueOf(TransactionType.CREDIT), transferDto.getAmount(),
	      			transferDto.getToAccountId());
			    repository.save(transaction);
	  }

}
