<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/list.css" />
<nav class="nav-bar">
	<form method="post" action="">
		<button class="submitLink" name="action" type="submit"
			value=SHOW_FLIGHTS>Рейсы</button>
		<button class="submitLink" name="action" type="submit"
			value="SHOW_ROUTES">Маршруты</button>
		<button class="submitLink" name="action" type="submit"
			value="SHOW_CITIES">Города</button>
		<button class="submitLink" name="action" type="submit"
			value="SHOW_HUBS">Аэропорты базирования</button>
	</form>
</nav>
<jsp:include page="${userpage}" />


<br>
<br>
<p>flights size is ${flights.size()}</p>
<p>login is ${param.login}</p>
<p>user type is ${sessionScope.airlineUserType}</p>
<p>password hash is ${sessionScope.airlineUser.getPasswordSha()}</p>
<p>user first name is ${sessionScope.airlineUser.getFirstName()}</p>
<p>user last name is ${sessionScope.airlineUser.getLastName()}</p>
<p>user ID is ${sessionScope.airlineUser.getId()}</p>