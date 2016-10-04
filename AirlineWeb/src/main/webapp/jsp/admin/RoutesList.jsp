<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form method="post" action="">
	<table id="routes" class="list">
		<thead>
			<tr class="tableheader">
				<th class="oddcolumn">&nbsp;</th>
				<th class="evencolumn">Номер маршрута</th>
				<th class="oddcolumn">Место отправления</th>
				<th class="evencolumn">Место назначения</th>
				<th class="oddcolumn">Расстояние, км</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="route" items="${routes}">
				<tr>
					<td class="oddcolumn"><input type="radio" name="routeId"
						value="${route.id}" checked></td>
					<td class="evencolumn">${route.id}</td>
					<td class="oddcolumn">${route.hub}</td>
					<td class="evencolumn">${route.destination}</td>
					<td class="oddcolumn">${route.distance}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<button class="listAction" name="action" type="submit"
		value=SHOW_ROUTE>Показать</button>
	<button class="listAction" name="action" type="submit"
		value="DELETE_ROUTE">Удалить</button>
	<button class="listAction" name="action" type="submit"
		value="NEW_ROUTE">Создать новый</button>
</form>