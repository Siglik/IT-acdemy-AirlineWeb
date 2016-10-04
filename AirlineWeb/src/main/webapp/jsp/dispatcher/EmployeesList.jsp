<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<br>
<nav class="add-bar">
	<p class="menu-name">Фильтровать по професии:</p>
	<form method="post" action="">
		<input type="radio" name="speciality" value="" checked /> Все <input
			type="radio" name="speciality" value="PILOT" /> Пилот <input
			type="radio" name="speciality" value="NAVIGATOR" /> Навигатор <input
			type="radio" name="speciality" value="RADIO_OPERATOR" /> Радист <input
			type="radio" name="speciality" value="AIR_STEWARD" /> Бортпроводник
		<button class="listAction" name="action" type="submit"
			value=SHOW_EMPLOYEES>Применить фильтр</button>
	</form>
</nav>
<form method="post" action="">
	<table id="employees" class="list">
		<thead>
			<tr class="tableheader">
				<th class="oddcolumn">&nbsp;</th>
				<th class="evencolumn">ID</th>
				<th class="oddcolumn">Имя</th>
				<th class="evencolumn">Фамилия</th>
				<th class="oddcolumn">Профессия</th>
				<th class="evencolumn">Аэропорт</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td class="oddcolumn"><input type="radio" name="employeeID"
						value="${employee.id}" /></td>
					<td class="evencolumn">${employee.id}</td>
					<td class="oddcolumn">${employee.firstName}</td>
					<td class="evencolumn">${employee.lastName}</td>
					<td class="oddcolumn">${employee.speciality}</td>
					<td class="evencolumn">${employee.hub}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<button class="listAction" name="action" type="submit"
		value="DELETE_EMPLOYEE">Удалить</button>
	<button class="listAction" name="action" type="submit"
		value="NEW_EMPLOYEE">Добавить</button>
	<!-- <button class="listAction" name="action" type="submit"
		value="SHOW_EMPLOYEE_FLIGHTS">Показать рейсы работника</button>-->
</form>