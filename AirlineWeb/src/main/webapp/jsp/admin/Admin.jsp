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
		<button class="submitLink" name="action" type="submit" value="LOG_OUT">Выход</button>
	</form>
</nav>
<br>
<jsp:include page="${userpage}" />