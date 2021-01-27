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


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	@Override
	public String toString() {
		return "TransactionEntity [customerSsn=" + customerSsn + ", accountId=" + accountId + ", transType=" + transType
				+ ", transactionDate=" + transactionDate + ", amount=" + amount + "]";
	}

	private String customerSsn;
	private String accountId;
	private String  transType;
//	private Date transTime;
	private String transactionDate;
	private Double amount;
	
	
	//Date date=new Date(tsTimestamp.getTime());
	
	
	
	public Transaction(String customerSsn, String accountId, Double amount) {
		super();
		this.setCustomerSsn(customerSsn);
		this.setAccountId(accountId);
		this.setAmount(amount);
		
		//this.transacDate =new Date(trans_time.getTime());
	}

	
	public Transaction() {
		super();
		

	//	this.transacDate =new Date(trans_time.getTime());
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

	public String getCustomerSsn() {
		return customerSsn;
	}

	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}


	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	/*
	 * public Date getTransTime() {
	 * 
	 * 
	 * 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyHHmm"); Date date
	 * =null; try { date = (Date) dateFormat.parseObject(transTime + ""); } catch
	 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * System.out.println("@@@@@@@ date " +date);
	 * 
	 * return date; }
	 * 
	 * public void setTransTime(Date transTime) {
	 * 
	 * System.out.println("@@@@@@@ transTime " +transTime);
	 * 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyHHmm"); Date date
	 * =null; try { date = (Date) dateFormat.parseObject(transTime + ""); } catch
	 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * System.out.println("@@@@@@@ date " +date);
	 * 
	 * this.transTime = date; }
	 */

	public String getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	

	

	

}
