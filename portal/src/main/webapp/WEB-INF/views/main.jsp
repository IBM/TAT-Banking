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
<%-- tpl:insert page="/theme/itso_jsp_template.jtpl" --%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/gray.css" type="text/css">
<!-- Static content -->
<link rel="stylesheet" href="/resources/css/style.css">
<c:url value="/resources/images/bank-logo.gif" />
<%-- tpl:put name="headarea" --%>
<title>mainPage</title>
<%-- /tpl:put --%></head>
<body>
<table width="600" cellspacing="0" cellpadding="0" border="0">
   <tbody>
      <tr>
         <td valign="top">
         <table class="header" cellspacing="0" cellpadding="0" border="0" width="100%">
           <tbody>
               <tr>
                  <td width="150"><img border="0" width="110" height="50" alt="Company's LOGO" src="${pageContext.request.contextPath}/resources/images/Picture1.png"></td>
            
                  <td><H2>TAT <Font color="blue">Banking Demo on LinuxONE</Font></H1></td>
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
         <div class="form">
         
         <TABLE width="700" height="178" cellspacing="0" cellpadding="0"
						border="0">
						<!-- flm:table -->
						<TBODY>
							<TR>
								<TD height="31" width="55"></TD>
								<TD width="700"></TD>
							</TR>
							<TR>
								<TD height="117"></TD>
								<!-- flm:cell -->
								<TD valign="top">
									<FORM action="ListAccounts" method ="post">Please enter your customer ID (SSN):<BR>
									<INPUT type="text" name="customerNumber" size="20">
									<BR>
									<BR>
									<INPUT type="submit" name="ListAccounts" value="Submit">
									</FORM>
								</TD>
							</TR>
							<TR>
								<TD height="30"></TD>
								<TD></TD>
							</TR>
						</TBODY>
					</TABLE>
				
					</div>
         <%-- /tpl:put --%></td>
      </tr>
     
   </tbody>
</table>
</body>
</html><%-- /tpl:insert --%>
