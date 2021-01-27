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




import tat.microservice.bank.transaction.dto.TransactionDto;
import tat.microservice.bank.transaction.dto.TransferDto;
import tat.microservice.bank.transaction.entities.Transaction;
import tat.microservice.bank.transaction.exceptions.AccountNotEligibleForCreditException;
import tat.microservice.bank.transaction.exceptions.AccountNotFoundException;
import tat.microservice.bank.transaction.exceptions.CustomerNotFoundException;


public interface TransactionService {

   
    public void withdraw(TransactionDto transactionDto) throws CustomerNotFoundException,AccountNotFoundException;
    public void deposit(TransactionDto transactionDto)throws CustomerNotFoundException, AccountNotFoundException;
    public Transaction getTransaction(String transactionId);
    public Iterable<Transaction> findOneByAccountId(String accountId);
    public Iterable<Transaction> findOneByTransType(String trans_type);
    public Iterable<Transaction> findAllTransaction();
    public String transfer(TransferDto transferDto)
			throws AccountNotEligibleForCreditException, AccountNotEligibleForCreditException;
   
    
} 
