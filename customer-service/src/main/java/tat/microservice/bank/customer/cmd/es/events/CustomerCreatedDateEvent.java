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
package tat.microservice.bank.customer.cmd.es.events;

import tat.microservice.bank.customer.cmd.es.aggregates.RecordCreationDate;

public class CustomerCreatedDateEvent extends BaseEvent<String> {
	
	private  RecordCreationDate recordCreationDate;
	
	public CustomerCreatedDateEvent(String customer_ssn, RecordCreationDate recordCreationDate) {
		super(customer_ssn);
		this.setRecordCreationDate(recordCreationDate);
	}

	public RecordCreationDate getRecordCreationDate() {
		return recordCreationDate;
	}

	public void setRecordCreationDate(RecordCreationDate recordCreationDate) {
		this.recordCreationDate = recordCreationDate;
	}


}
