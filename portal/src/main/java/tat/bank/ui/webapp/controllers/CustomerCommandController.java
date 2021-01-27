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

import java.util.ArrayList;
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
public class CustomerCommandController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AccountService accountService;
	private final CustomerService customerService;

	public CustomerCommandController(AccountService accountService, CustomerService customerService) {
		super();
		this.accountService = accountService;
		this.customerService = customerService;
	}

	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestParam("customerNumber") String customerNumber,
			@RequestParam(required = false, name = "update") String update,
			@RequestParam(required = false, name = "create") String create,
			@RequestParam(required = false, name = "delete") String delete,
			@RequestParam(required = false, name = "firstname") String firstname,
			@RequestParam(required = false, name = "lastname") String lastname,
			@RequestParam(required = false, name = "title") String title, Model model) {

		if (customerNumber != null && update != null) {

			logger.info("############################ update Calling... " + update);

			Customer customer = new Customer();

			logger.info("############################ title= " + title.trim());
			customer.setTitle(title.trim());

			logger.info("############################ firstName= " + firstname.trim());
			customer.setFirstname(firstname.trim());
			logger.info("############################  lastName = " + lastname.trim());

			customer.setLastname(lastname.trim());

			logger.info("############################  customerNumber = " + customerNumber.trim());
			customer.setCustomerSsn(customerNumber.trim());

			customerService.updateCustomer(customer);

			try {
				customer = customerService.getCustomer(customerNumber.trim());
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "showException";
			}

			if (customer != null) {

				List<Account> accounts;
				try {
					accounts = accountService.findAccountByCustomer(customer.getCustomerSsn());

					model.addAttribute("customer", customer);
					model.addAttribute("accounts", accounts);

					return "listAccounts";

				} catch (AccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "showException";
				}

			} else {
				logger.info("############################ else showException");
				return "showException";
			}

		} else if (create != null) {

			logger.info("############################ new Calling... newCustomer " + create);

			// New Customer
			Customer customer = new Customer();
			customer.setFirstname(firstname);
			customer.setLastname(lastname);
			customer.setTitle(title);
					
			
			String customerSsn =customerService.createCustomer(customer);

			if ( customerSsn!= null)

			{
				customer.setCustomerSsn(customerSsn);
				model.addAttribute("customer", customer);
			} else {
				return "showException";
			}

		} else if (customerNumber != null && delete != null) {

			logger.info("############################ delete Calling... deleteCustomer " + delete);
			
			List<tat.bank.ui.webapp.domains.Account> accounts =  new ArrayList<tat.bank.ui.webapp.domains.Account>();;
			
			try {
				
				accounts = accountService.findAccountByCustomer(customerNumber);
				
				customerService.deleteCustomer(customerNumber);
				
				int i = 0;
				while (i < accounts.size()) {
					logger.info("############################ accounts.get(" + i + ") " +accounts.get(i));
					tat.bank.ui.webapp.domains.Account account =(tat.bank.ui.webapp.domains.Account) accounts.get(i);
					
					logger.info("############################ account " +account);
					
					accountService.deleteAccount(account.getAccountId());
					i++;
				}
				
				
				return "index";
				
				
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "showException";
				
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "showException";
			}
			
			

		} else {

			logger.info("############################ customerNumber is " + customerNumber);

			return "showException";

		}

		return "listAccounts";

	}

}
