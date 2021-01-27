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
package tat.microservice.bank.transaction.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Transaction  implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transType=" + transType + ", transactionDate=" + transactionDate + ", amount=" + amount
				+ ", accountId=" + accountId + "]";
	}

	@Id
	private String id;
	private String transType;
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	//private Timestamp trans_time;
	@Column(name="TRANS_TIME")
	//private Timestamp transTime;
	private String transactionDate;

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	private double amount;
	private String accountId;

		//recordCreationDate =new RecordCreationDate().getTodayDate();
	public Transaction() {
	    }
	/*
	 * public Timestamp getTransTime() { return transTime; }
	 * 
	 * public void setTransTime(Timestamp transTime) { this.transTime = transTime; }
	 */

	public Transaction(String transType, double amount, String accountId) {
		super();
		setId(java.util.UUID.randomUUID().toString());
		this.transType = transType;
		//setTrans_time(new Timestamp(System.currentTimeMillis()));
	  // setTransTime(new Timestamp(System.currentTimeMillis()));
		
	setTransactionDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		//trans_time.getTime()
		this.amount = amount;
		this.accountId = accountId;
	}
	
	/*
	 * public Timestamp getTrans_time() { return trans_time; } public void
	 * setTrans_time(Timestamp trans_time) { this.trans_time = trans_time; }
	 */
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	

}
