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
package tat.microservice.bank.customer.cmd.es.exceptions;

import tat.microservice.bank.customer.cmd.es.errors.DefaultError;
import tat.microservice.bank.customer.cmd.es.errors.IError;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6293662498600553602L;

    private IError error;
    private String extMessage;

    public BusinessException() {
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
    }

    public BusinessException(String message) {
        super(message);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.extMessage = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.extMessage = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
    }

    public BusinessException(IError error) {
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.error = error;
    }

    public BusinessException(String message, IError error) {
        super(message);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = message;
        this.error = error;
    }

    public BusinessException(String message, Throwable cause, IError error) {
        super(message, cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = message;
        this.error = error;
    }

    public BusinessException(Throwable cause, IError error) {
        super(cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.error = error;
    }

    public IError getError() {
        return this.error;
    }

    public String getExtMessage() {
        return this.extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    @Override
    public String toString() {
        return super.toString() + ",ErrorCode : " + this.error.getErrorCode() + ", ErrorMessage : " + this.error.getErrorMessage() + ", ExtMessage : " + this.extMessage;
    }
}
