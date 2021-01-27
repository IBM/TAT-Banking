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
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tat.microservice.bank.account.query.es.entities.AccountQueryEntity;
import tat.microservice.bank.account.query.es.entities.AccountTransferQueryEntity;
import tat.microservice.bank.account.query.es.entities.repositories.AccountRepository;
import tat.microservice.bank.account.query.es.entities.repositories.AccountTransferRepository;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final AccountRepository accountRepository;
	private final AccountTransferRepository accountTransferRepository;
	private final EventStore eventStore;


	public AccountQueryServiceImpl(AccountRepository accountRepository,
			AccountTransferRepository accountTransferRepository, EventStore eventStore) {
		this.accountRepository = accountRepository;
		this.accountTransferRepository = accountTransferRepository;
		this.eventStore = eventStore;
	}

	public Iterable<AccountQueryEntity> findAllAccount() {

		return accountRepository.findAllByOrderByIdAsc();
	}

	public AccountQueryEntity findAccountById(String id) {

		//return accountRepository.findOne(id);
		logger.info("<--- accountRepository.findOneByAccountId(id)" + id);
		return accountRepository.findOneByAccountId(id);
		
		
	}
 	
	public Iterable<AccountTransferQueryEntity> findAllTransfer() {
		return accountTransferRepository.findAll();

	}

	public AccountTransferQueryEntity findTransferById(String id) {

		return accountTransferRepository.findOne(id);
	}
	 @Override
	    public List<Object> listEventsForAccount(String accountNumber) {
	        return eventStore.readEvents(accountNumber).asStream().map( s -> s.getPayload()).collect(Collectors.toList());
	    }


	public Iterable<AccountTransferQueryEntity> findByFromAccountIdOrToAccountId(String fromAccountId,
			String toAccountId) {

		return accountTransferRepository.findByFromAccountIdOrToAccountId(fromAccountId,toAccountId);
	}
	
	public List<AccountQueryEntity> findOneByCustomerSsn(String customerSsn){
		
		return accountRepository.findByCustomerSsn(customerSsn);
		
	}
}
