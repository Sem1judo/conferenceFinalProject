<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="p" uri="/WEB-INF/function/functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>

<c:set var="title" value="Error" scope="page"/>

<head>
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@600;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../style/error_page.css">
    <script src="https://kit.fontawesome.com/4b9ba14b0f.js" crossorigin="anonymous"></script>
    <title>Error</title>
</head>
<body>
<%@ include file="../jspf/header.jspf" %>

<div class="mainbox">
    <div class="err">4</div>
    <i class="far fa-question-circle fa-spin"></i>
    <div class="err2">4</div>
    <div class="msg"><p>


        <h3><fmt:message key="error_404_title"/></h3>

        <c:if test="${not empty errorMessage}">
            <h3><fmt:message key="list_jsp.table.header.details"/>: ${errorMessage}</h3>
        </c:if>
        <a href="<%=request.getHeader("REFERER")%>">
            <p><fmt:message key="error_404_back"/></p>
        </a>
    </div>


    <%@ include file="../jspf/footer.jspf" %>
</body>
</html>