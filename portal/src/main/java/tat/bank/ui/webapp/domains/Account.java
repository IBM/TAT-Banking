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
package tat.bank.ui.webapp.domains;

public class Account {

	@Override
	public String toString() {
		return "Account [overdraftLimit=" + overdraftLimit + ", balance=" + balance + ", customerSsn=" + customerSsn
				+ ", accountId=" + accountId + "]";
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	private long overdraftLimit;

	private double balance;
	private String customerSsn;
	private String accountId;
	private long id;

	public String getCustomerSsn() {
		return customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
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

	public Account(long overdraftLimit, double balance, String customerSsn) {
		super();
		this.overdraftLimit = overdraftLimit;
		this.balance = balance;
		this.customerSsn = customerSsn;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
