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

public enum DefaultError implements IError {
   
    SYSTEM_INTERNAL_ERROR("0000", "System Internal Error"),
   
    INVALID_PARAMETER("0001", "Invalid Parameter"),
   
    SERVICE_NOT_FOUND("0002", "Service Not Found"),
   
    PARAMETER_REQUIRED("0003", "Parameter required"),
   
    PARAMETER_MAX_LENGTH("0004", "Parameter max length limit"),
   
    PARAMETER_MIN_LENGTH("0005", "Parameter min length limit"),
   
    PARAMETER_ANNOTATION_NOT_MATCH("0006", "Parameter annotation not match"),
   
    PARAMETER_NOT_MATCH_RULE("0007", "Parameter not match validation rule"),
    
    METHOD_NOT_SUPPORTED("0008", "method not supported"),
  
    CONTENT_TYPE_NOT_SUPPORT("0009", "content type is not support"),
    
    JSON_FORMAT_ERROR("0010", "json format error"),
   
    CALL_REMOTE_ERROR("0011", "call remote error");;

    String errorCode;
    String errorMessage;
    String zh_errorMessage;
    private static final String ns = "SYS";

    DefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    DefaultError(String errorCode, String errorMessage, String zh_errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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

    public String getZh_errorMessage() {
        return this.zh_errorMessage;
    }
}