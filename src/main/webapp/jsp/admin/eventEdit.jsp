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
    <title>Edit event</title>

</head>
<body>
<%@ include file="../jspf/header.jspf" %>

<h1><fmt:message key="list_event"/></h1>
<table class="table table-striped table-hover">
    <tr>
        <th><fmt:message key="list_jsp.table.header.title"/></th>
        <th><fmt:message key="list_jsp.table.header.description"/></th>
        <th><fmt:message key="list_jsp.table.header.location"/></th>
        <th><fmt:message key="list_jsp.table.header.startTime"/></th>
        <th><fmt:message key="list_jsp.table.header.endTime"/></th>
        <th><fmt:message key="list_orders_jsp.table.header.organizerId"/></th>
        <th><fmt:message key="list_jsp.table.header.status"/></th>
        <c:if test="${userRole.name == 'moderator' }">
            <th><fmt:message key="list_jsp.table.header.update"/></th>
            <th><fmt:message key="list_jsp.table.header.delete"/></th>
        </c:if>
    </tr>
    <tr>

        <c:choose>
            <c:when test="${userRole.name == 'moderator' }">
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
                    <td><input name="status_id" value="${eventStatus.name}" readonly type="text"></td>
                    <td>
                        <button type="submit"
                                class="btn btn-dark btn-lg"><fmt:message key="list_jsp.table.header.update"/>
                        </button>
                    </td>


                </form>

            </c:when>
            <c:when test="${userRole.name == 'speaker' }">
                <td>${event.title}</td>
                <td>${event.description}</td>
                <td>${event.location}</td>
                <td>${event.startTime}</td>
                <td>${event.endTime}</td>
                <td>${event.organizerId}</td>
            </c:when>
        </c:choose>

        <c:if test="${userRole.name == 'moderator' }">
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <td>
                    <input type="hidden" name="command" value="deleteEvent"/>
                    <input type="hidden" name="id" value="${event.id}">
                    <button type="submit"
                            class="btn btn-dark btn-lg">
                        <fmt:message key="list_jsp.table.header.delete"/>
                    </button>
                </td>
            </form>
        </c:if>
    </tr>
</table>

<h1><fmt:message key="header_jspf.anchor.all_topics"/></h1>
<c:if test="${userRole.name == 'moderator' or userRole.name == 'speaker'}">
    <div class="text-center">
        <a href="" class="btn btn-default btn-rounded mb-4" data-toggle="modal"
           data-target="#createTopic"><fmt:message key="topic.createTopic"/></a>
    </div>
    <div class="modal fade" id="createTopic" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold"><fmt:message key="topic.topic"/></h4>
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="proposeTopicSpeaker"/>
                        <div class="md-form mb-5">
                            <i class="fas fa-user prefix grey-text"></i>
                            <input name="name" type="text" id="name"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right"
                                   for="name"><fmt:message key="list_menu_jsp.table.header.name"/></label>
                        </div>
                        <div class="md-form mb-5">
                            <i class="fas fa-envelope prefix grey-text"></i>
                            <input name="description" type="text" id="description"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right"
                                   for="description"><fmt:message
                                    key="list_jsp.table.header.description"/></label>
                        </div>
                        <c:if test="${userRole.name == 'moderator' }">
                            <div class="md-form">
                                <i class="fas fa-pencil prefix grey-text"></i>
                                <input name="user_id" type="text" id="userId" value="${user.id}"
                                       class="md-textarea form-control">
                                <label data-error="wrong" data-success="right" for="userId"><fmt:message
                                        key="list_topics.speaker"/>
                                    ID </label>
                            </div>
                        </c:if>
                        <c:if test="${userRole.name == 'speaker' }">
                            <div class="md-form">
                                <i class="fas fa-pencil prefix grey-text"></i>
                                <input name="user_id" type="text" id="userId" readonly value="${user.id}"
                                       class="md-textarea form-control">
                                <label data-error="wrong" data-success="right" for="userId"><fmt:message
                                        key="list_topics.speaker"/>
                                    ID </label>
                            </div>
                        </c:if>
                        <div class="md-form mb-4">
                            <i class="fas fa-lock prefix grey-text"></i>
                            <input name="event_id" type="text" id="eventId" readonly value="${event.id}"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right" for="eventId"><fmt:message
                                    key="list_event"/>
                                ID</label>
                        </div>
                        <div class="modal-footer d-flex justify-content-center">
                            <button class="btn btn-deep-orange"><fmt:message
                                    key="list_btn.create"/></button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</c:if>

<table class="table table-striped table-hover">
    <tr>
        <th><fmt:message key="list_jsp.table.header.title"/></th>
        <th><fmt:message key="list_jsp.table.header.description"/></th>
        <th><fmt:message key="list_topics.speaker"/>
        <th>
            <fmt:message key="login_jsp.label.email"/>
            <c:if test="${userRole.name == 'moderator' }">
        <th><fmt:message key="btn.change"/></th>
        </c:if>
    </tr>
    <c:choose>
        <c:when test="${fn:length(eventTopics) == 0}">
            <fmt:message
                    key="list_jsp.table.header.empty"/>
        </c:when>
        <c:otherwise>
            <c:forEach var="topic" items="${eventTopics}">
                <tr>
                    <td>${topic.name}</td>
                    <td>${topic.description}</td>
                    <td>${topic.speaker.firstName} ${topic.speaker.lastName}</td>
                    <td>${topic.speaker.email}</td>

                    <c:if test="${userRole.name == 'moderator' }">
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
                    </c:if>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>


<%@ include file="../jspf/footer.jspf" %>


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