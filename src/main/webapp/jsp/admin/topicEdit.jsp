<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="p" uri="/WEB-INF/function/functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <link rel="stylesheet" type="text/css" media="screen" href="../../style/st4.css"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <title></title>

</head>


<body>
<%@ include file="header.jspf" %>
<hr>
<tr>
    <td class="content">
        <%-- CONTENT --%>

        <h1><fmt:message key="topic.topic"/></h1>
        <table class="table table-striped table-hover">
            <tr>

                <th><fmt:message key="list_orders_jsp.table.header.title"/></th>
                <th><fmt:message key="list_orders_jsp.table.header.description"/></th>
                <th><fmt:message key="list_topics.speaker"/></th>
                <th><fmt:message key="login_jsp.label.email"/></th>
                <th><fmt:message key="list_orders_jsp.table.header.update"/></th>
            </tr>
            <tr>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="id" value="${topic.id}">
                    <input type="hidden" name="command" value="updateTopic"/>
                    <input type="hidden" name="isUpdated" value="${true}">

                    <td><input name="name" value=${topic.name} type="text">
                        <div>${topicDto.name}
                        </div>
                    </td>
                    <td><input name="description" value="${topic.description}" type="text">
                        <div>${topicDto.description}</div>
                    </td>
                    <td>
                        <input name="speakerId" value="${topic.userId}" type="number">
                        <div>${topicDto.speaker.firstName} ${topicDto.speaker.lastName}</div>
                        <div>${topicDto.speaker.email}</div>
                        <div>${topicDto.speaker.id}</div>

                    </td>
                    <td>
                        <input name="eventId" type="number" value="${topic.eventId}">
                        <div>${topicDto.event.title}</div>
                        <div>${topicDto.event.description}</div>
                        <div>${topicDto.event.id}</div>

                    </td>
                    <td>
                        <button type="submit"
                                class="btn btn-dark btn-lg"><fmt:message key="list_orders_jsp.table.header.update"/>
                        </button>
                    </td>
                </form>
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <td>
                        <input type="hidden" name="command" value="deleteTopic"/>
                        <input type="hidden" name="id" value="${topic.id}">
                        <button type="submit"
                                class="btn btn-dark btn-lg"><fmt:message
                                key="list_orders_jsp.table.header.delete"/>
                        </button>
                    </td>
                </form>
            </tr>
        </table>
        <%-- CONTENT --%>
    </td>
</tr>

<%--    <%@ include file="WEB-INF/jsp/admin/createTopic.jsp" %>--%>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>


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