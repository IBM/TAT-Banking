<!--
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
-->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/gray.css" type="text/css">


<title>List Transactions</title>
</head>
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
         <td valign="top"></td>
      </tr>
      <tr class="content-area">
         <td valign="top">
					<table width="700" height="329" cellspacing="0" cellpadding="0"
						border="0">
						<!-- flm:table -->
						<tbody>
							<tr>
								<td height="50" width="3"></td>
								<td width="694"></td>
							<td width="3"></td></tr>
							<tr>
								<td height="260"></td>
								<!-- flm:cell -->
								<td valign="top" align="center">
								<table border="0">
									<tbody>
										<tr>
											<td><table width="653">
												<tbody>

													<tr>
														<td align="left">AccountNumber:</td>
														<td><c:out
															value="${requestScope.accountId}" /></td>
													</tr>

													<tr>
														<td align="left">Balance:</td>
														<td>
															<fmt:formatNumber maxFractionDigits="2"
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
								<table border="1" cellpadding="5">
									<thead>
										<tr>
											<th>Time</th>
											<th>Transaction Type</th>
											<th width="80">Amount</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="varTransactions"
											items="${requestScope.transactions}">
											<tr>
												<td><%-- <c:set var = "now" value = "<%= new java.util.Date()%>" />
												<%System.out.println("##################### new Date()" + new java.util.Date());%>
      
                                             <fmt:formatDate type = "both"  dateStyle = "long" timeStyle = "long" value = "${now}" />		
                                             <fmt:formatDate value="${varTransactions.transTime}" type="both" dateStyle="short" timeStyle="short"/>			
												 --%>
																								
													<c:out value = "${varTransactions.transactionDate}" />
												
												</td>
												<td><c:out value="${varTransactions.transType}" /></td>
												<td align="right">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${varTransactions.amount}"/>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<hr>
								<form method="post" action="accountDetails"><input type="hidden" name="accountId"
									value="${requestScope.accountId}"><input
									type="submit" value="Account Details"></form>
								</td>
							<td></td></tr>
							<tr>
								<td height="19"></td>
								<td></td>
							<td></td></tr>
						</tbody>
					</table>
				<%-- /tpl:put --%></td>
      </tr>
      <tr>
        
      </tr>
   </tbody>
</table>
</body>
</html><%-- /tpl:insert --%>
