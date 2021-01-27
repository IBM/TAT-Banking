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
package tat.microservice.bank.customer.cmd.es.errors;

public enum UCError implements IError {
	SUCCESS(1000, "success"), FAILED(9999, "failed"), CUST_CODE_IS_EXIST(1001,
			"org code is exist"), PARENT_CUST_NOT_EXIST(1002, "customer not exist"), ROLE_IS_EXIST(1003,
					"role is exist"), USERNAME_IS_EXIST(1004, "username is exist"),;
	
	private Integer errorCode;
	private String errorMessage;
	private static final String ns = "UserCenter-Query";

	UCError(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	UCError(Integer code) {
		this.errorCode = code;
	}

	@Override
	public String getNameSpace() {
		return ns;
	}

	@Override
	public String getErrorCode() {
		return ns + "." + this.errorCode;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
}