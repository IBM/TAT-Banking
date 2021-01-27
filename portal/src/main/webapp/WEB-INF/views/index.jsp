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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static content -->
<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/app.js"></script>

<title>Spring Boot</title>
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
             
                  <td><H2>TAT <Font color="blue">Banking Demo on LinuxONE</Font></H1></td>
               </tr>
            </tbody>
         </table>
         </td>
         
      </tr>

	<div class="form">

	<table>
										
				<TR>
								
								<TD>
									<FORM action="/ListAccounts" method ="post" onsubmit="return validate()">Please enter your customer ID (SSN):<BR>
									<INPUT type="text" name="customerNumber" size="20">
									<BR>
									<BR>
									<INPUT type="submit" name="ListAccounts" value="Submit">
									</FORM>
								</TD>
							</TR>
		</table>
			
	</div>

</body>
</html>