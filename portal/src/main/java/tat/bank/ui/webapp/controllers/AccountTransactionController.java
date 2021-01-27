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
package tat.bank.ui.webapp.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.bank.ui.webapp.domains.Account;
import tat.bank.ui.webapp.domains.Transaction;
import tat.bank.ui.webapp.domains.Transfer;
import tat.bank.ui.webapp.exceptions.AccountNotFoundException;
import tat.bank.ui.webapp.services.AccountService;
import tat.bank.ui.webapp.services.AccountTxnService;

@Controller
public class AccountTransactionController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AccountService accountService;
	private final AccountTxnService accountTxnService;

	public AccountTransactionController(AccountService accountService, AccountTxnService accountTxnService) {
		super();
		this.accountService = accountService;
		this.accountTxnService = accountTxnService;
	}

	@PostMapping("/performTransaction")
	public String performTransaction(@RequestParam("accountId") String accountId,
			@RequestParam("transacType") String transacType,
			@RequestParam(required = false, name = "amount") String amount,
			@RequestParam(required = false, name = "targetAccountId") String targetAccountId, Model model)
			throws AccountNotFoundException {

		Account account = new Account();

		Transaction transaction = new Transaction();

		if (transacType != null) {

			if (transacType.equals("deposit")) {

				logger.info(" <----------- transacType ------> " + transacType);

				account = accountService.findAccountById(accountId);

				logger.info(" <----------- account ------> " + account);

				transaction.setAccountId(accountId);
				transaction.setAmount(new Double(amount).doubleValue());
				transaction.setCustomerSsn(account.getCustomerSsn());

				try {

					account = depositCommand(transaction);
					model.addAttribute("accountId", account.getAccountId());
					model.addAttribute("account", account);
					model.addAttribute("customerNumber", account.getCustomerSsn());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "showException";
				}

			} else if (transacType.equals("withdraw")) {

				logger.info(" <----------- transacType ------> " + transacType);

				account = accountService.findAccountById(accountId);

				logger.info(" <----------- account ------> " + account);

				transaction.setAccountId(accountId);
				transaction.setAmount(new Double(amount).doubleValue());
				transaction.setCustomerSsn(account.getCustomerSsn());

				try {

					account = withdrawCommand(transaction);

					model.addAttribute("accountId", account.getAccountId());
					model.addAttribute("account", account);
					model.addAttribute("customerNumber", account.getCustomerSsn());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "showException";
				}

			} else if (transacType.equals("transfer")) {

				logger.info(" <----------- transacType ------>  " + transacType);

				if (targetAccountId != null) {

					// check the target account to see if it exists

					if (accountService.findAccountById(targetAccountId) != null) {

						account = accountService.findAccountById(accountId);

						Transfer transfer = new Transfer();
						transfer.setFromAccountId(accountId);
						transfer.setToAccountId(targetAccountId);
						transfer.setAmount(new Double(amount).doubleValue());

						try {
							account = transferCommand(transfer);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						logger.info(" <----------- account ------> " + account);

						model.addAttribute("accountId", account.getAccountId());
						model.addAttribute("account", account);
						model.addAttribute("customerNumber", account.getCustomerSsn());

					}

				} else {

					return "showException";

				}

			} else {

				logger.info(" <----------- list ------> ");

				// Control logic
				account = accountService.findAccountById(accountId);

				List<Transaction> transactionEntities = listTransactionQuery(accountId);

				logger.info(" <----------- transactions ------> " + transactionEntities);

				/*
				 * Transaction transactionEntity=(Transaction)transactionEntities.get(0);
				 * 
				 * logger.
				 * info(" <----------- TransactionEntity transactionEntity=(TransactionEntity)transactionEntities.get(0) ------> "
				 * +transactionEntity);
				 * 
				 * logger.info(" <----------- transactions.get(0).getTransTime() ------> "
				 * +transactionEntities.get(0).getTransTime());
				 */

				// Response
				model.addAttribute("transactions", transactionEntities);
				model.addAttribute("account", account);
				model.addAttribute("accountId", account.getAccountId());
				model.addAttribute("customerNumber", account.getCustomerSsn());

				return "listTransactions";
				// return "formatDate";
			}
		} else {

			return "showException";
		}

		return "accountDetails";
	}

	private Account depositCommand(Transaction transaction) throws Exception {

		// Control logic

		accountTxnService.doDeposit(transaction);

		Account account = accountService.findAccountById(transaction.getAccountId());

		// return view
		return account;

	}

	private Account withdrawCommand(Transaction transaction) throws Exception {

		// Control logic
		accountTxnService.doWithdraw(transaction);

		Account account = accountService.findAccountById(transaction.getAccountId());

		return account;

	}

	private Account transferCommand(Transfer transfer) throws Exception {

		// Control logic
		// accountTxnService.debitMoneyFromAccount(transaction);

		accountTxnService.doTransfer(transfer);

		Account account = accountService.findAccountById(transfer.getFromAccountId());

		return account;

	}

	private List<Transaction> listTransactionQuery(String accountId) throws AccountNotFoundException {

		// Control logic

		List<Transaction> transactionEntities = accountTxnService.findTransactionByAccoutID(accountId);

		return transactionEntities;

	}

}
