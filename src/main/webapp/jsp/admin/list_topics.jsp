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
<c:choose>
    <c:when test="${fn:length(topics) == 0}"><fmt:message key="list_orders_jsp.table.header.empty"/></c:when>

    <c:otherwise>
        <table class="table table-striped table-hover">
            <tr>
                <th>№</th>
                <th><fmt:message key="list_menu_jsp.table.header.name"/></th>
                <th><fmt:message key="list_orders_jsp.table.header.description"/></th>
                <th><fmt:message key="list_topics.speaker"/></th>
                <th><fmt:message key="list_events_jsp.table_title.event"/></th>
                <c:if test="${userRole.name == 'moderator'}">
                    <th><fmt:message key="btn.change"/></th>
                </c:if>
                <c:if test="${userRole.name == 'speaker'}">
                    <th><fmt:message key="list_orders_jsp.table.header.details"/></th>
                </c:if>


            </tr>

            <c:forEach var="topic" items="${topics}">
                <tr>
                    <td>${topic.id}</td>
                    <td>${topic.name}</td>
                    <td>${topic.description}</td>
                    <td>${topic.speaker.firstName} ${topic.speaker.lastName}</td>
                    <td>${topic.event.title}</td>
                    <c:if test="${userRole.name == 'moderator'}">
                        <td>
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="updateTopic"/>
                                <input type="hidden" name="id" value="${topic.id}">

                                <button type="submit"
                                        class="btn btn-dark btn-lg">
                                    <fmt:message key="btn.change"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${userRole.name == 'speaker'}">
                        <td>
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="updateTopic"/>
                                <input type="hidden" name="id" value="${topic.id}">

                                <button type="submit"
                                        class="btn btn-dark btn-lg">
                                    <fmt:message key="list_orders_jsp.table.header.details"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

        </table>
        <%--For displaying Page numbers.
               The when condition does not display a link for the current page--%>
        <table class="table">
            <tr>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="controller?command=listTopics&page=${i}">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tr>
        </table>

        <%--For displaying Previous link except for the 1st page --%>
        <c:if test="${currentPage != 1}">
            <td><a href="controller?command=listTopics&page=${currentPage - 1}"><fmt:message
                    key="list.pagination.previous"/></a></td>
        </c:if>

        <%--For displaying Next link --%>
        <c:if test="${currentPage lt noOfPages}">
            <td><a href="controller?command=listTopics&page=${currentPage + 1}"><fmt:message
                    key="list.pagination.next"/></a></td>
        </c:if>

    </c:otherwise>
</c:choose>

<c:if test="${userRole.name == 'moderator'}">
    <div class="text-center">
        <a href="" class="btn btn-default btn-rounded mb-4" data-toggle="modal"
           data-target="#createEvent"><fmt:message key="topic.createTopic"/></a>
    </div>
    <div class="modal fade" id="createEvent" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold"><fmt:message key="topic.createTopic"/></h4>
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="createTopic"/>
                        <div class="md-form mb-5">
                            <i class="fas fa-user prefix grey-text"></i>
                            <input name="name" type="text" id="name"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right"
                                   for="name"><fmt:message key="list_menu_jsp.table.header.name"/></label>
                        </div>

                        <div class="md-form">
                            <i class="fas fa-pencil prefix grey-text"></i>
                            <input name="description" type="text" id="desc"
                                   class="md-textarea form-control">
                            <label data-error="wrong" data-success="right" for="desc"><fmt:message
                                    key="list_orders_jsp.table.header.description"/></label>
                        </div>
                        <div class="md-form mb-4">
                            <i class="fas fa-lock prefix grey-text"></i>
                            <input name="user_id" type="text" id="user_id"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right"
                                   for="user_id"><fmt:message
                                    key="list_orders_jsp.table.header.organizerId"/></label>
                        </div>
                        <div class="md-form mb-4">
                            <i class="fas fa-lock prefix grey-text"></i>
                            <input name="event_id" type="text" id="event_id"
                                   class="form-control validate">
                            <label data-error="wrong" data-success="right"
                                   for="event_id">ID <fmt:message
                                    key="list_events_jsp.table_title.event"/></label>
                        </div>

                        <div class="modal-footer d-flex justify-content-center">
                            <button class="btn btn-deep-orange"><fmt:message
                                    key="list_orders_jsp.table.header.create"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>


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