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

import java.util.List;

import tat.bank.ui.webapp.domains.Customer;
import tat.bank.ui.webapp.exceptions.CustomerNotFoundException;


public interface CustomerService {
	  public Customer getCustomer(String ssn) throws CustomerNotFoundException;
	  public Iterable<Customer> getCustomerByName(String name) throws CustomerNotFoundException;
	  public  Iterable<Customer> getAllCustomers();
	  public  List<String> getListEventForCustomer(String ssn) throws CustomerNotFoundException;
	  public String createCustomer(Customer customer);
	  public void updateCustomer(Customer updateCustomer);
	  public void deleteCustomer(String customerSsn)throws CustomerNotFoundException;

}
