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
<%-- tpl:insert page="/theme/itso_jsp_template.jtpl" --%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/gray.css" type="text/css">
<%-- tpl:put name="headarea" --%>
<title>Show Exception</title>
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
					<table width="700" height="207" cellspacing="0" cellpadding="0"
						border="0">
						<!-- flm:table -->
						<tbody>
							<tr>
								<td height="33" width="37"></td>
								<td width="632"></td>
								<td width="31"></td>
							</tr>
							<tr>
								<td height="141"></td>
								<!-- flm:cell -->
								<td valign="top">
								<table border="0" width="600">
									<tbody>
										<tr>
											<td>
											<table width="612">
												<tbody>

													<tr>
														<td align="left" width="200">An error has occurred:</td>
														<td><c:out value="${requestScope.message}" /></td>
													</tr>

												</tbody>
											</table>
											</td>
										</tr>
									</tbody>
								</table>
								<table>
									<tbody>

										<tr>
											<td align="left"></td>
											<td><c:url value="${requestScope.forward}"
												var="urlVariable"></c:url>
												<a href="index.jsp"> 
												   <c:out value="Click here to continue" /></a></td>
										</tr>

									</tbody>
								</table>
								</td>
								<td></td>
							</tr>
							<tr>
								<td height="33"></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				<%-- /tpl:put --%></td>
      </tr>
    
   </tbody>
</table>
</body>
</html><%-- /tpl:insert --%>
