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

public class TransferCreatedEvent {

	private String transferId;
	private String toAccountId;
	private double amount;
	private String status;

	public TransferCreatedEvent(String fromAccountId, String toAccountId, String transferId, double amount, String status) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transferId = transferId;
		this.amount = amount;
		this.setStatus(status);
	}

	public String getTransferId() {
		return transferId;
	}

	@Override
	public String toString() {
		return "TransferCreatedEvent [transferId=" + transferId + ", toAccountId=" + toAccountId + ", amount=" + amount
				+ ", fromAccountId=" + fromAccountId + "]";
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public double getAmount() {
		return amount;
	}

	private String fromAccountId;

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
