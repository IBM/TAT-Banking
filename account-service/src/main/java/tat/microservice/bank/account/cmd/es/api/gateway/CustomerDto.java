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
package tat.microservice.bank.account.cmd.es.api.gateway;

public class CustomerDto {
	
	private String customerSsn;
	@Override
	public String toString() {
		return "CustomerDto [customerSsn=" + customerSsn + ", title=" + title + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", createdDate=" + createdDate + "]";
	}
	private String title;
	private String first_name;
	private String last_name;
	private String createdDate;
	
	public String getCustomerSsn() {
		return customerSsn;
	}
	public void setCustomerSsn(String customerSsn) {
		this.customerSsn = customerSsn;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
