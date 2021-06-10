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
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="localeSetting"/>
                <h3>
                    <fmt:message key="settings_jsp.label.localization"/>
                </h3>
                <select name="localeToSet">
                    <c:choose>
                        <c:when test="${not empty defaultLocale}">
                            <option value="">${defaultLocale}<fmt:message key="default"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value=""/>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach var="localeName" items="${locales}">
                        <option value="${localeName}">${localeName}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="<fmt:message key="list_jsp.table.header.update"/>">
            </form>
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="updateSettings"/>
                <input type="hidden" name="id" value="${user.id}">
                <input type="hidden" name="isUpdated" value="${true}">
                <h3>
                    <fmt:message key="header_jspf.anchor.profile"/>
                </h3>
                <div class="form-group">
                    <p>
                        <fmt:message key="settings_jsp.label.first_name"/>
                    </p>
                    <input type="text" name="firstName" value="${user.firstName}">
                </div>

                <div class="form-group">
                    <p>
                        <fmt:message key="settings_jsp.label.last_name"/>
                    </p>
                    <input type="text" name="lastName" value="${user.lastName}">
                </div>

                <div class="form-group">
                    <p>
                        <fmt:message key="login_jsp.label.email"/>
                    </p>
                    <input name="email" value="${user.email}"
                    <c:if test="${userRole.name == 'moderator'}"> readonly</c:if>>
                </div>

                <div class="form-group">
                    <p>
                        <fmt:message key="login_jsp.label.login"/>
                    </p>
                    <input type="text" name="login" value="${user.login}"
                    <c:if test="${userRole.name == 'moderator'}"> readonly</c:if>>
                </div>

                <div class="form-group">
                    <p>
                        <fmt:message key="login_jsp.label.password"/>
                    </p>
                    <input type="password" name="password" placeholder="*****"
                    <c:if test="${userRole.name == 'moderator'}"> readonly</c:if>>
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