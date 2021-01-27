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
package tat.microservice.bank.account.cmd.es.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class TransferDebitSourceAccountCommand {

	@TargetAggregateIdentifier
	private String accountId;
	private String transferId;
	private double amount;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public TransferDebitSourceAccountCommand(String accountId, String transferId, double amount) {
		super();
		this.accountId = accountId;
		this.transferId = transferId;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTransferId() {
		return transferId;
	}

	@Override
	public String toString() {
		return "TransferDebitSourceAccountCommand [accountId=" + accountId + ", transferId=" + transferId + ", amount="
				+ amount + "]";
	}

	public void setBankTransferId(String transferId) {
		this.transferId = transferId;
	}
}
