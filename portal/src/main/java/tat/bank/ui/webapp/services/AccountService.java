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

import tat.bank.ui.webapp.domains.Account;
import tat.bank.ui.webapp.exceptions.AccountNotFoundException;



public interface AccountService {

	public Iterable<Account> findAllAccount();
	//public void createAccount(Account account);
	public String addOneAccount(String customerNumber);

	public Account findAccountById(String accountId) throws AccountNotFoundException;

	public List<String> getListEventForAccount(String accountId) throws AccountNotFoundException;

	public List<Account> findAccountByCustomer(String ssn) throws AccountNotFoundException;
	public void deleteAccount(String accountId) throws AccountNotFoundException ;
	

}
