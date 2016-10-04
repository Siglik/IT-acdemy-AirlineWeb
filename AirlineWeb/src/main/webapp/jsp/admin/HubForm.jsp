<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/form.css" />
<div id="place-form">
	<form method="post" target="">
		<div class="form-items">
			<div class="form-item" class="hub">
				<div class="inputname">Выберите месторасположение:</div>
				<select id="place" name="cityId" value="">
					<c:forEach var="city" items="${cities}">
						<option value="${city.id}">${city.name},&nbsp;
							${city.country}</option>
					</c:forEach>
				</select>
				<div id="countries" class="list"></div>
			</div>
		</div>

		<input id="place_id" type="hidden" name="mode" value="ADD" />
		<div class="submit">
			<button id="addbutton" class = "listAction"  name="action" value="NEW_HUB" >Добавить</button>
		</div>
	</form>
</div>