<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="p" uri="/WEB-INF/function/functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <title>Settings</title>
</head>

<%@ include file="jspf/header.jspf" %>
<body class=" bg-warning">

<div class="container w-25 my-3">
    <div class=" card d-block border-0 ">
        <div class="card-body">
            <form action="controller" method="get">
                <input type="hidden" name="command" value="updateSettings"/>
                <p>
                    <fmt:message key="settings_jsp.label.localization"/>
                </p>
                <select name="localeToSet">
                    <c:choose>
                        <c:when test="${not empty defaultLocale}">
                            <option value="">${defaultLocale}[Default]</option>
                        </c:when>
                        <c:otherwise>
                            <option value=""/>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach var="localeName" items="${locales}">
                        <option value="${localeName}">${localeName}</option>
                    </c:forEach>
                </select>
                <div class="form-group">
                    <p>
                        <fmt:message key="settings_jsp.label.first_name"/>
                    </p>
                    <input name="firstName">
                </div>

                <div class="form-group">
                    <p>
                        <fmt:message key="settings_jsp.label.last_name"/>
                    </p>
                    <input name="lastName">
                </div>
                <button type="submit"
                        class="btn btn-dark btn-lg"><fmt:message key="settings_jsp.button.update"/></button>
            </form>
        </div>
    </div>
</div>


<%@ include file="jspf/footer.jspf" %>
</body>
</html>