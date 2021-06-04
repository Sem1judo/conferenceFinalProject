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

<%@ include file="../jspf/header.jspf" %>


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
                <ul class="nav-pills nav-stacked">
                    <li class="active"><a href="#"> <i class="fa fa-user"></i><fmt:message
                            key="header_jspf.anchor.profile"/></a></li>
                    <li><a href="#"> <i class="fa fa-calendar"></i><fmt:message key="profile.quantityEvents"/><span
                            class="label label-warning pull-right r-activity">${events.size()}</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/settings.jsp"> <i class="fa fa-edit"></i><fmt:message key="btn.change"/></a></li>
                </ul>
            </div>
        </div>
        <div class="profile-info col-md-9">
            <div class="panel">
                <footer class="panel-footer">
                    <h5><fmt:message key="profile.quote"/></h5>
                </footer>
            </div>
            <div class="panel">
                <div class="bio-graph-heading">
                    <fmt:message key="profile.quote2"/>
                </div>
                <div class="panel-body bio-graph-info">
                    <h1><fmt:message key="profile.info"/></h1>
                    <div class="row">
                        <div class="bio-row">
                            <p><span><fmt:message key="login_jsp.label.first_name"/></span>: ${user.firstName}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span><fmt:message key="login_jsp.label.last_name"/></span>: ${user.lastName}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span><fmt:message key="login_jsp.label.login"/></span>: ${user.login}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p>
                                <span><fmt:message
                                        key="profile.registrationDate"/></span>: ${p:formatLocalDateTime(user.registrationDate, 'dd-MM-yyyy HH:mm')}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span><fmt:message key="profile.role"/></span>: <c:if test="${not empty userRole}">
                                <c:out value="${userRole.name}"/>
                            </c:if>
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span><fmt:message key="login_jsp.label.email"/></span>: ${user.email}
                            </p>
                        </div>
                        <div class="bio-row">
                            <p><span><fmt:message key="login_jsp.label.phone"/></span>: ${user.phone}
                            </p>
                        </div>

                    </div>
                </div>
            </div>
            <div>
                <c:choose>
                    <c:when test="${fn:length(events) == 0}"><fmt:message
                            key="list_jsp.table.header.empty"/>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="event" items="${p:sortByTime(events)}">
                            <div class="col-md-6">
                                <div class="panel">
                                    <div class="panel-body">
                                        <div class="bio-chart">
                                            <div style="display:inline;width:100px;height:100px;">
                                                <canvas width="100" height="100px"></canvas>
                                                <input class="knob" data-width="100" readonly data-height="100"
                                                       data-displayprevious="true" data-thickness=".2"
                                                       value="${event.id}"
                                                       data-fgcolor="#96be4b" data-bgcolor="#e8e8e8"
                                                       style="width: 54px; height: 33px; position: absolute; vertical-align: middle; margin-top: 33px; margin-left: -77px; border: 0px; font-weight: bold; font-style: normal; font-variant: normal; font-stretch: normal; font-size: 20px; line-height: normal; font-family: Arial; text-align: center; color: rgb(251 192 45); padding: 0px; -webkit-appearance: none; background: none;">
                                            </div>
                                        </div>
                                        <div class="bio-desk">
                                            <h3>${event.title}</h3>
                                            <h4>${event.description}</h4>
                                            <h4>${event.location}</h4>
                                            <h4 class="green">${p:formatLocalDateTime(event.startTime, 'dd-MM-yyyy HH:mm')}</h4>
                                            <h4 class="red">${p:formatLocalDateTime(event.endTime, 'dd-MM-yyyy HH:mm')}</h4>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>


<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
