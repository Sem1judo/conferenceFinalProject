<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="p" uri="/WEB-INF/function/functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Profile</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../style/profileUser.css">

</head>
<%@ include file="../admin/header.jspf" %>

<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container bootstrap snippets bootdey">
    <div class="row">
        <div class="profile-nav col-md-3">
            <div class="panel">
                <div class="user-heading round">
                    <a href="#">
                        <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="">
                    </a>
                    <h1>${user.firstName} ${user.lastName}
                    </h1>
                    <p>${user.email}
                    </p>
                </div>
                <ul class="nav nav-pills nav-stacked">
                    <li class="active"><a href="#"> <i class="fa fa-user"></i> Profile</a></li>
                    <li><a href="#"> <i class="fa fa-calendar"></i> Recent Activity <span
                            class="label label-warning pull-right r-activity">9</span></a></li>
                    <li><a href="#"> <i class="fa fa-edit"></i> Edit profile</a></li>
                </ul>
            </div>
        </div>
        <div class="profile-info col-md-9">
            <div class="panel">
                <form>
                    <textarea placeholder="Whats in your mind today?" rows="2"
                              class="form-control input-lg p-text-area"></textarea>
                </form>
                <footer class="panel-footer">
                    <button class="btn btn-warning pull-right">Post</button>
                    <ul class="nav nav-pills">
                        <li>
                            <a href="#"><i class="fa fa-map-marker"></i></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-camera"></i></a>
                        </li>
                        <li>
                            <a href="#"><i class=" fa fa-film"></i></a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-microphone"></i></a>
                        </li>
                    </ul>
                </footer>
            </div>
            <div class="panel">
                <div class="bio-graph-heading">
                    Aliquam ac magna metus. Nam sed arcu non tellus fringilla fringilla ut vel ispum. Aliquam ac magna
                    metus.
                </div>
                <div class="panel-body bio-graph-info">
                    <h1>Bio Graph</h1>
                    <div class="row">
                        <div class="bio-row">
                            <p><span>First Name </span>: ${user.firstName}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Last Name </span>: ${user.lastName}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Username </span>: ${user.login}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Creation data</span>: ${user.registrationDate}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Role </span>: ${user.roleId}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Email </span>: ${user.email}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span>Phone </span>: ${user.phone}
                            </p>
                        </div>

                    </div>
                </div>
            </div>
            <div>
                <c:choose>
                <c:when test="${fn:length(events) == 0}"><fmt:message
                        key="list_orders_jsp.table.header.empty"/>
                </c:when>
                <c:otherwise>
                <table class="table table-striped table-hover">
                    <tr>
                        <th><fmt:message key="list_orders_jsp.table.header.title"/></th>
                        <th><fmt:message key="list_orders_jsp.table.header.description"/></th>
                        <th><fmt:message key="list_orders_jsp.table.header.location"/></th>
                        <th><fmt:message key="list_orders_jsp.table.header.startTime"/></th>
                        <th><fmt:message key="list_orders_jsp.table.header.endTime"/></th>
                    </tr>

                    <c:forEach var="event" items="${p:sortByTime(events)}">
                    <tr>
                        <td>${event.title}</td>
                        <td>${event.description}</td>
                        <td>${event.location}</td>
                        <td>${p:formatLocalDateTime(event.startTime, 'dd-MM-yyyy HH:mm')}</td>
                        <td>${p:formatLocalDateTime(event.endTime, 'dd-MM-yyyy HH:mm')}</td>
                        </c:forEach>
                        </c:otherwise>

                        </c:choose>

                        <%--                            <div class="col-md-6">--%>
                        <%--                                <div class="panel">--%>
                        <%--                                    <div class="panel-body">--%>
                        <%--                                        <div class="bio-chart">--%>
                        <%--                                            <div style="display:inline;width:100px;height:100px;">--%>
                        <%--                                                <canvas width="100" height="100px"></canvas>--%>
                        <%--                                                <input class="knob" data-width="100" data-height="100"--%>
                        <%--                                                       data-displayprevious="true" data-thickness=".2"--%>
                        <%--                                                       value="${event.startTime}"--%>
                        <%--                                                       data-fgcolor="#e06b7d" data-bgcolor="#e8e8e8"--%>
                        <%--                                                       style="width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(224, 107, 125); padding: 0px; -webkit-appearance: none; background: none;">--%>
                        <%--                                            </div>--%>
                        <%--                                        </div>--%>
                        <%--                                        <div class="bio-desk">--%>
                        <%--                                            <h4 class="red">${event.title}</h4>--%>
                        <%--                                            <p>${event.description}</p>--%>
                        <%--                                            <p>${event.location}</p>--%>
                        <%--                                            <p>${event.endTime}</p>--%>
                        <%--                                        </div>--%>
                        <%--                                    </div>--%>
                        <%--                                </div>--%>
                        <%--                            </div>--%>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="panel">
                                    <div class="panel-body">
                                        <div class="bio-chart">
                                            <div style="display:inline;width:100px;height:100px;">
                                                <canvas width="100" height="100px"></canvas>
                                                <input class="knob" data-width="100" data-height="100"
                                                       data-displayprevious="true" data-thickness=".2" value="63"
                                                       data-fgcolor="#4CC5CD" data-bgcolor="#e8e8e8"
                                                       style="width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(76, 197, 205); padding: 0px; -webkit-appearance: none; background: none;">
                                            </div>
                                        </div>
                                        <div class="bio-desk">
                                            <h4 class="terques">ThemeForest CMS </h4>
                                            <p>Started : 15 July</p>
                                            <p>Deadline : 15 August</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel">
                                    <div class="panel-body">
                                        <div class="bio-chart">
                                            <div style="display:inline;width:100px;height:100px;">
                                                <canvas width="100" height="100px"></canvas>
                                                <input class="knob" data-width="100" data-height="100"
                                                       data-displayprevious="true" data-thickness=".2" value="75"
                                                       data-fgcolor="#96be4b" data-bgcolor="#e8e8e8"
                                                       style="width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(150, 190, 75); padding: 0px; -webkit-appearance: none; background: none;">
                                            </div>
                                        </div>
                                        <div class="bio-desk">
                                            <h4 class="green">VectorLab Portfolio</h4>
                                            <p>Started : 15 July</p>
                                            <p>Deadline : 15 August</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel">
                                    <div class="panel-body">
                                        <div class="bio-chart">
                                            <div style="display:inline;width:100px;height:100px;">
                                                <canvas width="100" height="100px"></canvas>
                                                <input class="knob" data-width="100" data-height="100"
                                                       data-displayprevious="true" data-thickness=".2" value="50"
                                                       data-fgcolor="#cba4db" data-bgcolor="#e8e8e8"
                                                       style="width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(203, 164, 219); padding: 0px; -webkit-appearance: none; background: none;">
                                            </div>
                                        </div>
                                        <div class="bio-desk">
                                            <h4 class="purple">Adobe Muse Template</h4>
                                            <p>Started : 15 July</p>
                                            <p>Deadline : 15 August</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


            </div>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
