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
package tat.microservice.bank.account.cmd.es.services;

import java.util.concurrent.CompletableFuture;

import tat.microservice.bank.account.cmd.es.aggregates.Account;
import tat.microservice.bank.account.cmd.es.commands.dto.AccountTransferDto;
import tat.microservice.bank.account.cmd.es.commands.dto.CreateAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.DeleteAccountDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeDepositDto;
import tat.microservice.bank.account.cmd.es.commands.dto.MakeWithdrawalDto;
import tat.microservice.bank.account.cmdquery.es.response.BaseResponse;



public interface AccountCommandService {

    public CompletableFuture<Account> createAccount(CreateAccountDto createAccountDto);
    public CompletableFuture<String> creditMoneyToAccount(String accountId,MakeDepositDto makeDepositDto);
    public CompletableFuture<String> debitMoneyFromAccount(String accountId,MakeWithdrawalDto makeWithdrawalDto);
    public CompletableFuture<String> transfer(String transferId,AccountTransferDto transfer);
	public BaseResponse deleteAccount(String accountId) ;
    
}
