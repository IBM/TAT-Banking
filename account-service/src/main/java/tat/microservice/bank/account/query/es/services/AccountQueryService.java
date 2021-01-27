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
package tat.microservice.bank.account.query.es.services;

import java.util.List;

import tat.microservice.bank.account.query.es.entities.AccountQueryEntity;
import tat.microservice.bank.account.query.es.entities.AccountTransferQueryEntity;



public interface AccountQueryService {

    public Iterable<AccountQueryEntity> findAllAccount();
    public AccountQueryEntity findAccountById(String id);
    public List<AccountQueryEntity> findOneByCustomerSsn(String customerSsn);
    
    public Iterable<AccountTransferQueryEntity> findAllTransfer();
    public AccountTransferQueryEntity findTransferById(String id);
 // public Iterable <AccountTransferQueryEntity> findBySourceAccountIdOrDestinationAccountId(String sourceAccountId, String destinationAccountId);
    
    public Iterable <AccountTransferQueryEntity> findByFromAccountIdOrToAccountId(String fromAccountId, String toAccountId);
    public List<Object> listEventsForAccount(String accountNumber);
    
}
