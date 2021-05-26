<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="p" uri="/WEB-INF/function/functions.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">


</head>




<body class=" bg-warning">

<div class="container w-25 my-3">
    <div class=" card d-block border-0 ">
        <form id="login_form" action="controller" method="post">
            <input type="hidden" name="command" value="login"/>
            <div class="card-header bg-dark text-light">
                <h2 class="text-uppercase font-weight-bold text-center"><fmt:message key="login_jsp.label.login"/></h2>
            </div>
            <div class="card-body">
                <div class="form-header mx-0">
                </div>
                <div class="form-group">
                    <label for="login"><fmt:message key="login_jsp.label.login"/></label>
                    <input type="text" class="form-control" id="login"
                           name="login">
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="login_jsp.label.password"/></label>
                    <input type="password" class="form-control" id="password"
                           name="password">
                </div>
            </div>
            <div class="text-center form-group"><fmt:message key="login_jsp.question"/>
                <h4 class="text-uppercase font-weight-bold text-center">
                    <a href="jsp/speaker_client/registration.jsp" class="link-dark"><fmt:message key="login_jsp.registerNow"/></a>
                </h4>
            </div>
            <div class="card-footer text-center ">
                <button type="submit"
                        class="btn btn-dark btn-lg"><fmt:message key="login_jsp.button.login"/></button>
            </div>
        </form>
    </div>
</div>
<%@ include file="/jsp/jspf/footer.jspf" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</body>
</html>