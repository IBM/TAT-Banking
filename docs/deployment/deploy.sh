#! /bin/sh
##############################################################################
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
##############################################################################
fail(){
   echo $1
   exit 1
}
oc whoami || fail "need to be logged into an OpenShift server"
oc new-project tat-banking-demo

declare -A Parm
Parm["RABBIT"]=rabbit.yaml  

Parm+=( ["CUSTOMER"]=customer.yaml \
        ["ACCOUNT"]=account.yaml \
        ["TRANSACTION"]=transaction.yaml \
        ["PORTAL"]=portal.yaml )


for DEPLOY in RABBIT CUSTOMER ACCOUNT TRANSACTION PORTAL
do
    echo `date` >> deploy.log  2>&1
    echo ${DEPLOY} ${Parm[${DEPLOY}]} >> deploy.log  2>&1
    oc create -f ${Parm[${DEPLOY}]} >> deploy.log 2>&1
    echo `date` >> deploy.log 2>&1
    echo "---" >> deploy.log 2>&1
done
oc expose service customer
oc expose service portal
