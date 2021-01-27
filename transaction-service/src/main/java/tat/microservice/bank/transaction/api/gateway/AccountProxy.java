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

import tat.microservice.bank.transaction.dto.AccountDto;
import tat.microservice.bank.transaction.dto.TransactionDto;
import tat.microservice.bank.transaction.dto.TransferDto;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForCreditException;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForDebitException;
import tat.microservice.bank.transaction.exceptions.AccountNotFoundException;

public interface AccountProxy {

	public List<String> findAllAccount();

	public AccountDto findAccountById(String accountId) throws AccountNotFoundException;

	public List<String> getListEventForAccount(String accountId) throws AccountNotFoundException;

	public List<String> findAccountByCustomer_ssn(String ssn) throws AccountNotFoundException;
	public void withdraw(TransactionDto transaction) throws AccountNotEligibleForDebitException;
	public void deposit(TransactionDto transaction) throws AccountNotEligibleForCreditException;
	public String transfer(TransferDto transferDto) throws AccountNotEligibleForCreditException, AccountNotEligibleForCreditException;

}
