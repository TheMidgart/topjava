<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <div class="row">
            <ul class="list-group list-group-horizontal">
                <li class="col-md-push-1">
                    <a href="meals" class="navbar-brand"><img src="resources/images/icon-meal.png"> <spring:message
                            code="app.title"/></a>
                </li>
                <li class='col-md-push-6 d-flex justify-content-end'>
                    <sec:authorize access="isAuthenticated()">
                        <form:form class="form-inline my-2" action="logout" method="post">
                            <sec:authorize access="hasRole('ADMIN')">
                                <a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>
                            </sec:authorize>
                            <a class="btn btn-info mr-1" href="profile">${userTo.name} <spring:message
                                    code="app.profile"/></a>
                            <button class="btn btn-primary my-1" type="submit">
                                <span class="fa fa-sign-out"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <form:form class="form-inline my-2" id="login_form" action="spring_security_check"
                                   method="post">
                            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                            <button class="btn btn-success" type="submit">
                                <span class="fa fa-sign-in"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                </li>
                <li class='col-md-push-10 d-flex justify-content-end m-1'>
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle m-1" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${pageContext.response.locale}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" onclick="changeLocale('ru')">ru</a>
                            <a class="dropdown-item" onclick="changeLocale('en')">en</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
</nav>

<script>
    function changeLocale(locale) {
        let currLink = $(location).attr("href");
        if (locale === "ru") {
            $.get(currLink + "?lang=ru");
        } else {
            $.get(currLink + "?lang=en");
        }
        location.reload();
    }
</script>

