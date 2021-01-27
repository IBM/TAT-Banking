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
package tat.microservice.bank.account.cmd.es.commands.dto;

public class CreateAccountDto {

	public CreateAccountDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	private long overdraftLimit;

	private double balance;
	private String customerSsn;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCustomerSsn() {
		return customerSsn;
	}

	@Override
	public String toString() {
		return "CreateAccountDto [overdraftLimit=" + overdraftLimit + ", balance=" + balance + ", customerSsn="
				+ customerSsn + "]";
	}

	public CreateAccountDto(long overdraftLimit, double balance, String customerSsn) {
		super();
		this.overdraftLimit = overdraftLimit;
		this.balance = balance;
		this.customerSsn = customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}

	public long getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
}
