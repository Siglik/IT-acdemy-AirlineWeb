<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/form.css" />
<br>
<div id="place-form">
	<form method="post" target="">
		<div class="form-items">
			<div class="form-item" class="hub">
				<div class="input">
					<div class="inputname">Имя:</div>
					<input name="firstName" type="text" value="" />
				</div>
				<br>

				<div class="input">
					<div class="inputname">Фамилия:</div>
					<input name="lastName" type="text" value="" />
				</div>

				<br>
				<div class="inputname">Специальность:</div>
				<select name="speciality" value="">
					<option value="PILOT">пилот</option>
					<option value="NAVIGATOR">навигатор</option>
					<option value="RADIO_OPERATOR">радист</option>
					<option value="AIR_STEWARD">бортпроводник</option>
				</select>
				<div id="countries" class="list"></div>
				<br>

				<div class="inputname">"Родной" аэропорт:</div>
				<select name="hubId" value="">
					<c:forEach var="hub" items="${hubs}">
						<option value="${hub.id}">${hub.name},&nbsp;
							${hub.country}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<input id="place_id" type="hidden" name="mode" value="ADD" />
		<div class="submit">
			<button id="addbutton" class="listAction" name="action"
				value="NEW_EMPLOYEE">Сохранить</button>
		</div>
	</form>
</div>