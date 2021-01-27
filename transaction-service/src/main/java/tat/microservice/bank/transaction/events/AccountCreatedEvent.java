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
package tat.microservice.bank.transaction.events;

public class AccountCreatedEvent {

	@Override
	public String toString() {
		return "AccountCreatedEvent [accountId=" + accountId + ", overdraftLimit=" + overdraftLimit + ", balance="
				+ balance + ", customer_ssn=" + customer_ssn + "]";
	}

	private String accountId;
	private long overdraftLimit;
	private double balance;
	private String customer_ssn;

	public AccountCreatedEvent(String accountId, double balance, String customer_ssn, long overdraftLimit) {
		super();
		this.setAccountId(accountId);
		this.overdraftLimit = overdraftLimit;
		this.balance = balance;
		this.customer_ssn = customer_ssn;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCustomer_ssn() {
		return customer_ssn;
	}

	public void setCustomer_ssn(String customer_ssn) {
		this.customer_ssn = customer_ssn;
	}

}
