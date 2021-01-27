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
package tat.microservice.bank.customer.cmd.es.commands.dto;

public class CreateCustomerDto {

public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}

private String title;
private String firstname;
private String lastname;

	
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstname() {
		return firstname;
	}
	@Override
	public String toString() {
		return "CreateCustomerDto [title=" + title + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}
	
	
}
