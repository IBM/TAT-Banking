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


public class TransferDestinationAccountCreditedEvent extends MoneyAddedEvent {

    private String transferId;
    private String transferType;
    private double balance;

    public TransferDestinationAccountCreditedEvent(String accountId, double amount, double balance, String transferId, String transferType) {
        super(accountId, amount);

        this.transferId = transferId;
        this.transferType =transferType;
        this.balance = balance;
    }

	public String getTransferId() {
		return transferId;
	}

	public void setBankTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
