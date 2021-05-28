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
    <title>Create topic</title>

</head>

<body>

<%@ include file="../jspf/header.jspf" %>

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
                    <input type="hidden" name="command" value="createTopic"/>
                    <div class="md-form mb-5">
                        <i class="fas fa-user prefix grey-text"></i>
                        <input name="name" type="text" id="name"
                               class="form-control validate">
                        <label data-error="wrong" data-success="right"
                               for="name">name</label>
                    </div>
                    <div class="md-form mb-5">
                        <i class="fas fa-envelope prefix grey-text"></i>
                        <input name="description" type="text" id="description"
                               class="form-control validate">
                        <label data-error="wrong" data-success="right"
                               for="description">description</label>
                    </div>
                    <div class="md-form">
                        <i class="fas fa-pencil prefix grey-text"></i>
                        <input name="user_id" type="text" id="userId"
                               class="md-textarea form-control">
                        <label data-error="wrong" data-success="right" for="userId">user
                            ID </label>
                    </div>
                    <div class="md-form mb-4">
                        <i class="fas fa-lock prefix grey-text"></i>
                        <input name="event_id" type="text" id="eventId"
                               class="form-control validate">
                        <label data-error="wrong" data-success="right" for="eventId">event
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

</body>
</html>
