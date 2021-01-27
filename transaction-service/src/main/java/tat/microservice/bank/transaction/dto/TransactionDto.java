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
package tat.microservice.bank.transaction.dto;

public class TransactionDto {

	private String customerSsn;
	private String accountId;
	private Double amount;

	public TransactionDto(String customerSsn, String accountId, Double amount) {
		super();
		this.setCustomerSsn(customerSsn);
		this.setAccountId(accountId);
		this.setAmount(amount);
	}

	public TransactionDto() {
		super();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	

	@Override
	public String toString() {
		return "TransactionDto [customerSsn=" + customerSsn + ", accountId=" + accountId + ", amount=" + amount + "]";
	}

	public String getCustomerSsn() {
		return customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}

}
