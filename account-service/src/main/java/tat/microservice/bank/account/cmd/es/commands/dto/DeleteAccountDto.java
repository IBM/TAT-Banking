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

public class DeleteAccountDto {

	public DeleteAccountDto() {
		super();
		// TODO Auto-generated constructor stub
	}



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


	public DeleteAccountDto( double balance, String customerSsn) {
		super();
		this.balance = balance;
		this.customerSsn = customerSsn;
	}

	@Override
	public String toString() {
		return "DeleteAccountDto [balance=" + balance + ", customerSsn=" + customerSsn + "]";
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}

	
}
