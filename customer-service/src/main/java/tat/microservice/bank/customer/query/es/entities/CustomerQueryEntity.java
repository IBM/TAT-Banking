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
package tat.microservice.bank.customer.query.es.entities;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class CustomerQueryEntity {

	@Id
	private String customerSsn;
	public CustomerQueryEntity(String customerSsn, String title, String firstname, String lastname,
			String createdDate) {
		super();
		this.customerSsn = customerSsn;
		this.title = title;
		this.firstname = firstname;
		this.lastname = lastname;
		CreatedDate = createdDate;
	}




	@Override
	public String toString() {
		return "CustomerQueryEntity [customerSsn=" + customerSsn + ", title=" + title + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", CreatedDate=" + CreatedDate + "]";
	}




	private String title;
	public String getCustomerSsn() {
		return customerSsn;
	}




	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}




	public String getFirstname() {
		return firstname;
	}




	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}




	public String getLastname() {
		return lastname;
	}




	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




	private String firstname;
	private String lastname;
	private String CreatedDate;

	//recordCreationDate =new RecordCreationDate().getTodayDate();
	public CustomerQueryEntity() {
	    }
	
	
	
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	/*
	 * @Override public String toString() { return "CustomerQueryEntity [ssn=" + id
	 * + ", title=" + title + ", first_name=" + first_name + ", last_name=" +
	 * last_name + "]"; }
	 */
	
	




	public String getCreatedDate() {
		return CreatedDate;
	}




	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

}
