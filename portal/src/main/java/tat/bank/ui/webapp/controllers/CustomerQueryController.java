/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package tat.bank.ui.webapp.controllers;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

import tat.bank.ui.webapp.domains.Account;
import tat.bank.ui.webapp.domains.Customer;
import tat.bank.ui.webapp.exceptions.AccountNotFoundException;
import tat.bank.ui.webapp.exceptions.CustomerNotFoundException;
import tat.bank.ui.webapp.services.AccountService;
import tat.bank.ui.webapp.services.CustomerService;



@Controller
//@RequestMapping("/banks")
public class CustomerQueryController {
	
	//private final Logger logger = LoggerFactory.getLogger(getClass());


	/*
	 * @Value("${welcome}") private String welcome;
	 * 
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * ipaddress() throws Exception { return "Reply: " + welcome; }
	 */
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final AccountService accountService;
	private final CustomerService customerService;

	public CustomerQueryController(AccountService accountService, CustomerService customerService) {
		super();
		this.accountService = accountService;
		this.customerService = customerService;
	}

	 @RequestMapping("/")
	   public String main() {
	      return "main";
	   }
	
	/*
	 * @RequestMapping("/") public String index() { return "index"; }
	 */
	 
	 @PostMapping("/logout")
	   public String logout() {
	      return "main";
	   }

	   @PostMapping("/ListAccounts")
	   public String findCustomer(@RequestParam("customerNumber") String customerNumber, Model model) {
		   
		   logger.info("<----- customerNumber ------------->" +customerNumber);
		   
		   
		   if (customerNumber == null)
				
			   return "showException";
		   
			else
				
				model.addAttribute("customerNumber", customerNumber);
		   
		   
		       Customer customer =null;
			try {
				customer = findCustomeByNumber(customerNumber);
				 logger.info("<----- customer ------------->" +customer);
				 
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       if(customer != null) {
		    	   
		    	  
		    	   		    	   
		    	   List<Account> accounts;
				try {
					accounts = findAccountByCustomer(customer.getCustomerSsn());
					  logger.info("############################ findCustomer(@RequestParam(\"customerNumber\")  " +accounts);
			    	   
			    	     model.addAttribute("customer", customer);
			    		 model.addAttribute("accounts", accounts);
			    		 model.addAttribute("customerNumber", customerNumber);
					
				} catch (AccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					return "showException";
				}
		    	   
		    	 
		       } else {
		    	   logger.info("############################ findCustomer(@RequestParam(\"customerNumber\")  showException");
		    	   return "showException";
		       }
		   
		   
		     
	     
	      return "listAccounts";
	   }
	   
	   public Customer findCustomeByNumber(String customerNumber) throws CustomerNotFoundException {
		   
		   Customer customer =new Customer();
		 
			 customer =  customerService.getCustomer(customerNumber);
			 logger.info("############################ findCustomeByNumber(String customerNumber " +customer);
			 
			 			
			return customer;
		
	   }
	   
	   public List<Account>findAccountByCustomer(String customerNumber) throws AccountNotFoundException {
		   
		   List<Account> accounts =  new ArrayList<Account>();
		   
		 
			   accounts = accountService.findAccountByCustomer(customerNumber);
			   logger.info("############################ findAccountByCustomer(String customerNumber)  " +accounts);
			
		  
		   return accounts;
		   
	   }
}
