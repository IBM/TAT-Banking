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
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JSTL fmt:dateNumber Tag</title>
</head>
<body>
      <h3>Number Format:</h3>
      <c:set var = "now" value = "<%= new Date()%>" />
      
      <%System.out.println("##################### new Date()" + new Date());%>

      <p>Formatted Date (1): <fmt:formatDate type = "time" 
         value = "${now}" /></p>
      
      <p>Formatted Date (2): <fmt:formatDate type = "date" 
         value = "${now}" /></p>
      
      <p>Formatted Date (3): <fmt:formatDate type = "both" 
         value = "${now}" /></p>
      
      <p>Formatted Date (4): <fmt:formatDate type = "both" 
         dateStyle = "short" timeStyle = "short" value = "${now}" /></p>
      
      <p>Formatted Date (5): <fmt:formatDate type = "both" 
         dateStyle = "medium" timeStyle = "medium" value = "${now}" /></p>
      
      <p>Formatted Date (6): <fmt:formatDate type = "both" 
         dateStyle = "long" timeStyle = "long" value = "${now}" /></p>
      
      <p>Formatted Date (7): <fmt:formatDate pattern = "yyyy-MM-dd" 
         value = "${now}" /></p>

   </body>
</html>