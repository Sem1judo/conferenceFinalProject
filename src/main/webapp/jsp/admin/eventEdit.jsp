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

            <h1><fmt:message key="list_events_jsp.table_title.event"/></h1>
            <table class="table table-striped table-hover">
                <tr>
                    <th><fmt:message key="list_orders_jsp.table.header.title"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.description"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.location"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.startTime"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.endTime"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.update"/></th>
                </tr>
                <tr>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="id" value="${event.id}">
                        <input type="hidden" name="command" value="updateEvent"/>
                        <input type="hidden" name="isUpdated" value="${true}">

                        <td><input name="title" value="${event.title}" type="text"></td>
                        <td><input name="description" value="${event.description}" type="text"></td>
                        <td><input name="location" value="${event.location}" type="text"></td>
                        <td><input name="start_time" value="${event.startTime}" type="datetime-local"></td>
                        <td><input name="end_time" value="${event.endTime}" type="datetime-local"></td>
                        <td><input name="organizer_id" value="${event.organizerId}" type="text"></td>
                        <td>
                            <button type="submit"
                                    class="btn btn-dark btn-lg"><fmt:message key="list_orders_jsp.table.header.update"/>
                            </button>
                        </td>
                    </form>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <td>

                            <input type="hidden" name="command" value="deleteEvent"/>
                            <input type="hidden" name="id" value="${event.id}">
                            <button type="submit"
                                    class="btn btn-dark btn-lg">
                                <fmt:message key="list_orders_jsp.table.header.delete"/>
                            </button>
                        </td>
                    </form>
                </tr>
            </table>

            <h1><fmt:message key="header_jspf.anchor.all_topics"/></h1>
            <table class="table table-striped table-hover">
                <tr>
                    <th><fmt:message key="list_orders_jsp.table.header.title"/></th>
                    <th><fmt:message key="list_orders_jsp.table.header.description"/></th>
                    <th><fmt:message key="list_topics.speaker"/>
                    <th><fmt:message key="login_jsp.label.email"/>
                </tr>
                <c:choose>
                    <c:when test="${fn:length(eventTopics) == 0}">
                        <fmt:message
                                key="list_orders_jsp.table.header.empty"/>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="topic" items="${eventTopics}">
                            <tr>

                                <td>${topic.name}</td>
                                <td>${topic.description}</td>
                                <td>${topic.speaker.firstName} ${topic.speaker.lastName}</td>
                                <td>${topic.speaker.email}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/controller" method="post">
                                        <input type="hidden" name="command" value="updateTopic"/>
                                        <input type="hidden" name="id" value="${topic.id}">
                                        <button type="submit"
                                                class="btn btn-dark btn-lg">
                                            <fmt:message key="btn.change"/>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
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