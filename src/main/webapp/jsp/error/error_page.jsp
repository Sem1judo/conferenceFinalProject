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
        <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
        <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

        <%-- this way we get the exception --%>
        <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

        <c:if test="${not empty code}">
        <h3>Error code: ${code}</h3>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <h3>Message: ${errorMessage}</h3>
        </c:if>
        <c:if test="${not empty message}">
            <h3>Message: ${message}</h3>
        </c:if>


        <%-- this way we print exception stack trace --%>
        <%--        <c:if test="${not empty exception}">--%>
        <%--            <h3>Stack trace:</h3>--%>
        <%--            <c:forEach var="stackTraceElement" items="${exception.stackTrace}">--%>
        <%--                ${stackTraceElement}--%>
        <%--            </c:forEach>--%>
        <%--        </c:if></div>--%>
    </div>


    <%@ include file="../jspf/footer.jspf" %>
</body>
</html>