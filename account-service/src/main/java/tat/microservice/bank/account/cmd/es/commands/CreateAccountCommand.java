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

import javax.validation.constraints.Min;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


public class CreateAccountCommand{
	
	 @TargetAggregateIdentifier
	 private String accountId;
	

	@Min(value = 0, message = "Overdraft limit must not be less than zero")
	 private long overdraftLimit;
	 
	 private double balance;
    private String customerSsn;
	   

    public CreateAccountCommand(String accountId, double balance, long overdraftLimit, String customerSsn) {
        super();
        this.accountId = accountId;
        this.balance = balance;
        this.customerSsn = customerSsn;
        this.overdraftLimit = overdraftLimit;
    }

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "CreateAccountCommand [accountId=" + accountId + ", overdraftLimit=" + overdraftLimit + ", balance="
				+ balance + ", customerSsn=" + customerSsn + "]";
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	 public String getAccountId() {
			return accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public long getOverdraftLimit() {
			return overdraftLimit;
		}

		public void setOverdraftLimit(long overdraftLimit) {
			this.overdraftLimit = overdraftLimit;
		}

	   	public String getCustomerSsn() {
			return customerSsn;
		}

		public void setCustomerSsn(String customerSsn) {
			this.customerSsn = customerSsn;
		}

	
}
