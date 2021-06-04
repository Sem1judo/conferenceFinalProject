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
    <title>Topic list</title>

</head>

<body>
<%@ include file="../jspf/header.jspf" %>
<hr>
<c:if test="${userRole.name == 'moderator'}">
<c:choose>
    <c:when test="${fn:length(topics) == 0}"><fmt:message key="list_jsp.table.header.empty"/></c:when>

    <c:otherwise>
        <table class="table table-striped table-hover">
            <tr>
                <th>№</th>
                <th><fmt:message key="list_menu_jsp.table.header.name"/></th>
                <th><fmt:message key="list_jsp.table.header.description"/></th>
                <th><fmt:message key="list_topics.speaker"/></th>
                <th><fmt:message key="list_event"/></th>
                <th><fmt:message key="btn.confirm"/></th>


            </tr>

            <c:forEach var="topic" items="${topics}">
                <c:if test="${not topic.confirm}">
                <tr>
                    <td>${topic.id}</td>
                    <td>${topic.name}</td>
                    <td>${topic.description}</td>
                    <td>${topic.speaker.firstName} ${topic.speaker.lastName}</td>
                    <td>${topic.event.title}</td>

                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="adminConfirm"/>
                            <input type="hidden" name="id" value="${topic.id}">
                            <button type="submit"
                                    class="btn btn-dark btn-lg">
                                <fmt:message key="btn.confirm"/>
                            </button>
                        </form>
                    </td>
                </tr>
                </c:if>
            </c:forEach>

        </table>
    </c:otherwise>
</c:choose>


<%@ include file="../jspf/footer.jspf" %>
</c:if>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>