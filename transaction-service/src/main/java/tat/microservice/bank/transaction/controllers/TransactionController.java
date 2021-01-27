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
package tat.microservice.bank.transaction.controllers;

import io.swagger.annotations.Api;
import tat.microservice.bank.transaction.dto.TransactionDto;
import tat.microservice.bank.transaction.dto.TransferDto;
import tat.microservice.bank.transaction.entities.Transaction;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForCreditException;
import tat.microservice.bank.transaction.exceptions.AccountNotFoundException;
import tat.microservice.bank.transaction.services.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/transactions")
@Api(value = "Transaction", description = "Transaction Related Endpoints", tags = "Transactions")
public class TransactionController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TransactionService transactionService;
	// private final CustomerProxy customerProxy;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;

	}

	@PostMapping(value = "/deposit")
	public void creditMoneyToAccount(@RequestBody TransactionDto transaction) {

		logger.info("@@@@@@@@@@ inside creditMoneyToAccount(@RequestBody TransactionDto transaction)"
				+ transaction.toString());
		try {
			transactionService.deposit(transaction);
		} catch (tat.microservice.bank.transaction.exceptions.CustomerNotFoundException | AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@PostMapping(value = "/withdraw")
	public void debitMoneyFromAccount(@RequestBody TransactionDto transaction) {

		logger.info("@@@@@@@@@@ inside debitMoneyFromAccount(@RequestBody TransactionDto transaction)"
				+ transaction.toString());
		try {
			transactionService.withdraw(transaction);
			logger.info("@@@@@@@@@@ inside creditMoneyToAccount(@RequestBody TransactionDto transaction)"
					+ transaction.toString());

		} catch (tat.microservice.bank.transaction.exceptions.CustomerNotFoundException | AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	
	@PostMapping(value = "/transfer")
	public String transfer(@RequestBody TransferDto transferDto) {

		logger.info("<----------inside transfer(@RequestBody TransferDto TransferDto) ----------->" + transferDto);
		
		String respString =null;
		try {
			respString = transactionService.transfer(transferDto);
			
			
			logger.info("<------------- Transfer has completed successfully!!! -------------> " + respString);
					

		} catch (AccountNotEligibleForCreditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respString;
	}

	@GetMapping("id/{transactionId}")
	public Transaction findTransactionById(@PathVariable(value = "transactionId") String transactionId) {
		return transactionService.getTransaction(transactionId);
	}

	
	  @GetMapping("transType/{trans_type}") public Iterable<Transaction>
	  findTransactionByType(@PathVariable(value = "trans_type") String trans_type)
	  { return transactionService.findOneByTransType(trans_type); }
	 

	@GetMapping("findTransByAccountID/{account_Id}")
	public Iterable<Transaction> findTransactionByAccoutID(@PathVariable(value = "account_Id") String account_Id) {
		return transactionService.findOneByAccountId(account_Id);
	}

	@GetMapping("/GetAllTransation")
	public Iterable<Transaction> findAllTransaction() {
		return transactionService.findAllTransaction();
	}

}
