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
package tat.microservice.bank.account.query.es.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AccountTransferQueryEntity {

    @Id
    @GeneratedValue
    private long id;
    private String transferId;
	/*
	 * private String sourceAccountId; private String destinationAccountId;
	 */
    private String fromAccountId; 
    private String toAccountId;
    private double amount;
    private String status;

    @SuppressWarnings("unused")
    public AccountTransferQueryEntity() {
    }

    public AccountTransferQueryEntity(String transferId, String fromAccountId, String toAccountId, double amount) {
        this.transferId = transferId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.status = String.valueOf(Status.STARTED);
    }

    public String getTransferId() {
        return transferId;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String geFromAccountId() {
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

	public void setAxonTransferId(String transferId) {
        this.transferId = transferId;
    }

   

    public double getAmount() {
        return amount;
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

    public enum Status {
        STARTED,
        FAILED,
        COMPLETED
    }
}
