<!-- /*##############################################################################
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
##############################################################################*/ -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- tpl:insert page="/theme/itso_jsp_template.jtpl" --%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/gray.css" type="text/css">
<%-- tpl:put name="headarea" --%>
<title>Account Detail</title>
<%-- /tpl:put --%></head>
<body>
<table width="600" cellspacing="0" cellpadding="0" border="0">
   <tbody>
      <tr>
         <td valign="top">
         <table class="header" cellspacing="0" cellpadding="0" border="0" width="100%">
            <tbody>
               <tr>
                 <td width="150"><img border="0" width="110" height="50" alt="Company's LOGO" src="${pageContext.request.contextPath}/resources/images/bank-logo.gif"></td>
                  <td><H1>TAT <Font color="blue">Banking Demo on LinuxONE</Font></H1></td>
               </tr>
            </tbody>
         </table>
         </td>
      </tr>
      <tr>
         <td valign="top"><siteedit:navbar spec="/employeebanking/theme/nav_head.jsp" target="topchildren"></siteedit:navbar></td>
      </tr>
      <tr class="content-area">
         <td valign="top"><%-- tpl:put name="bodyarea" --%>
					<table width="700" height="356" cellspacing="0" cellpadding="0"
						border="0">
						<!-- flm:table -->
						<tbody>
							<tr>
								<td height="16" width="63"></td>
								<td width="646"></td>
								<td width="50"></td>
							</tr>
							<tr>
								<td height="294"></td>
								<!-- flm:cell -->
								<td valign="top" align="center">
								<table border="0">
									<tbody>
										<tr>
											<td><table width="636" height="73">
												<tbody>

													<tr>
														<td align="left">Account Number:</td>
														<td><c:out
															value="${requestScope.accountId}" /></td>
													</tr>

													<tr>
														<td align="left">Balance:</td>
														<td><fmt:formatNumber maxFractionDigits="2"
															minFractionDigits="2"
															value="${requestScope.account.balance}"></fmt:formatNumber>
														</td>
													</tr>

												</tbody>
											</table>
											</td>
										</tr>
									</tbody>
								</table>
								<hr>
								<form method="post" action="performTransaction"><input
									type="hidden" name="accountId"
									value='<c:out value="${requestScope.accountId}" />'>
								<center>
								<table border="1">
									<tbody>
										<tr>
											<td><input type="radio" name="transacType" value="list"
												checked></td>
											<td colspan="3" width="380">List Transactions</td>
										</tr>
										<tr>
											<td><input type="radio" name="transacType"
												value="withdraw"></td>
											<td>Withdraw</td>
											<td rowspan="2">Amount:</td>
											<td rowspan="2"><input type="text"
												name="amount" size="20"></td>
										</tr>
										<tr>
											<td><input type="radio" name="transacType"
												value="deposit"></td>
											<td>Deposit</td>
										</tr>
										<tr>
											<td><input type="radio" name="transacType"
												value="transfer"></td>
											<td>Transfer</td>
											<td>To Account:</td>
											<td><input type="text"
												name="targetAccountId" size="20"></td>
										</tr>
										<tr>
											<td colspan="4" align="center"><input type="submit"
												value="Submit"></td>
										</tr>
									</tbody>
								</table>
								</center>
								</form>
								<hr>
								<table border="0" cellpadding="10">
									<tbody>
									<tr>
									<td>
										<form action="ListAccounts" method="post">
										<input type="hidden" name="customerNumber"
												value="<c:out value='${requestScope.account.customerSsn}' />" />
											<input type="submit" value="Customer Details">
										</form>
									</td><td>
										<form action="deleteAccount" method="post">
											<input type="hidden" name="accountId"
												value='<c:out value="${requestScope.accountId}" />'>
											<input type="submit" value="Delete Account">
										</form>
									</td>
									</tr>
									</tbody>
								</table>
								<td></td>
							</tr>
							<tr>
								<td height="46"></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				<%-- /tpl:put --%></td>
      </tr>
      <tr>
         <td valign="top"><siteedit:navbar spec="/employeebanking/theme/footer.jsp" target="ancestor"></siteedit:navbar></td>
      </tr>
   </tbody>
</table>
</body>
</html><%-- /tpl:insert --%>
