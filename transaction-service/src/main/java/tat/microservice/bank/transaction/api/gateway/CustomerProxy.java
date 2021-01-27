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

import java.util.List;

import tat.microservice.bank.transaction.dto.CustomerDto;
import tat.microservice.bank.transaction.dto.UpdateCustomerDto;
import tat.microservice.bank.transaction.exceptions.CustomerNotFoundException;

public interface CustomerProxy {
	  public CustomerDto getCustomer(String ssn) throws CustomerNotFoundException;
	  public  List<String> getCustomerByName(String name) throws CustomerNotFoundException;
	  public  List<String> getAllCustomers();
	  public  List<String> getListEventForCustomer(String ssn) throws CustomerNotFoundException;
	  public String createCustomer(CustomerDto customerDto);
	  public void updateCustomer(String customer_ssn, UpdateCustomerDto updateCustomer);
	  public void deleteCustomer(String customer_ssn)throws CustomerNotFoundException;

}
