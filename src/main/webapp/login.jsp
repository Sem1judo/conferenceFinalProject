<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<head>
    <title><fmt:message key="login_jsp.label.login"/></title>
</head>
<%@ include file="/WEB-INF/jspf/head.jspf" %>


<body class=" bg-warning">
<%@ include file="/WEB-INF/jspf/header.jspf" %>

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
            <div class="text-center form-group"> Dont have an account? Not a problem!
                <h4 class="text-uppercase font-weight-bold text-center">

                    <a href="jsp/speaker_client/registration.jsp" class="link-dark">Register Now</a>
                </h4>
            </div>
            <div class="card-footer text-center ">
                <button type="submit"
                        class="btn btn-dark btn-lg"><fmt:message key="login_jsp.button.login"/></button>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>