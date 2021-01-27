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
package tat.microservice.bank.customer.cmdquery.es.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tat.microservice.bank.customer.cmd.es.errors.IError;

@ApiModel(value ="BaseResponse")
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = -5359531292427290394L;

    @ApiModelProperty(value="errorCode")
    private String errorCode;
    @ApiModelProperty(value="errorMessage")
    private String errorMessage;
    @ApiModelProperty(value="extMessage")
    private String extMessage;
    @ApiModelProperty(value="status")
    private BaseResponse.Status status;

    public BaseResponse.Status getStatus() {
        return this.status;
    }

    public void setStatus(BaseResponse.Status status) {
        this.status = status;
    }

    public BaseResponse() {
        this.status = BaseResponse.Status.SUCCEED;
    }

    public BaseResponse(IError error) {
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
        this.status = BaseResponse.Status.FAILED;
    }

    public static BaseResponse create() {
        return new BaseResponse();
    }

    public static BaseResponse create(IError error) {
        BaseResponse response = new BaseResponse();
        response.errorCode = error.getErrorCode();
        response.errorMessage = error.getErrorMessage();
        response.status = Status.FAILED;
        return response;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExtMessage() {
        return this.extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.errorCode != null) {
            sb.append("ErrorCode : ").append(this.errorCode).append("ErrorMessage : ").append(this.errorMessage).append("ExtMessage : " + this.extMessage);
        } else {
            sb.append("Succeed");
        }

        return sb.toString();
    }

    public static enum Status {
        SUCCEED,
        FAILED;

        private Status() {
        }
    }
}