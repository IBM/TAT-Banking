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
import tat.bank.ui.webapp.domains.Customer;
import tat.bank.ui.webapp.exceptions.AccountNotFoundException;
import tat.bank.ui.webapp.exceptions.CustomerNotFoundException;
import tat.bank.ui.webapp.services.AccountService;
import tat.bank.ui.webapp.services.CustomerService;

@Controller
public class AccountCommandController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AccountService accountService;
	private final CustomerService customerService;

	public AccountCommandController(AccountService accountService, CustomerService customerService) {
		super();
		this.accountService = accountService;
		this.customerService = customerService;
	}

	@PostMapping("/addAccount")
	public String addAccount(@RequestParam("customerNumber") String customerNumber, Model model) {

		logger.info(" <----------- addAccount ------> " + customerNumber);

		if (customerNumber != null) {

			List<Account> accounts;
			try {
				Customer customer = customerService.getCustomer(customerNumber);
				String responseBody = accountService.addOneAccount(customerNumber);
				// createAccount(addOneAccount(customerNumber));
				if (responseBody != null) {
					accounts = accountService.findAccountByCustomer(customerNumber);
					model.addAttribute("customer", customer);
					model.addAttribute("accounts", accounts);

				} else {

					return "showException";
				}

			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return "showException";
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "showException";
			}

		}

		return "listAccounts";
	}

	@PostMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountId") String accountId, Model model) {

		List<Account> accounts;
		String customerNumber;
		
		logger.info(" <----------- deleteAccount ------> " + accountId);

		if (accountId != null) {

			Account account;
			Customer customer;
			try {
				
				logger.info(" <----------- retrieve the account before delete it ------> ");
				account = accountService.findAccountById(accountId);
				logger.info(" <----------- account found and save in memory ------> " +account);
				
				accountService.deleteAccount(accountId);
				logger.info(" <----------- account deleted from database repository ------> ");

				customerNumber = account.getCustomerSsn();
				accounts = accountService.findAccountByCustomer(customerNumber);
				customer = customerService.getCustomer(customerNumber);

				model.addAttribute("customer", customer);
				model.addAttribute("accounts", accounts);

			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return "showException";
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "showException";
			}

		}else {
			
			logger.info(" <----------- accountId is null or empty ------> " + accountId);
			
			return "showException";
		}

		return "listAccounts";
	}
}
