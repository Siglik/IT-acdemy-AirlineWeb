<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="css/form.css" />
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="css/jquery-ui-timepicker-addon.css">

<div id="place-form">
	<form method="post" target="">
		<div class="form-items">
			<div class="form-item1">
				<div class="inputname1">Маршрут:</div>
				<select id="place" name="routeId" value="">
					<c:forEach var="route" items="${routes}">
						<option value="${route.id}">${route.hub}->
							${route.destination} (${route.distance} km)</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-item1">
				<div class="inputname1">Время вылета:</div>
					<input type="text" name="deptDate" id="date_picker" value="" />

			</div>


			<div class="form-item1">
				<div class="inputname1">Модель самолета:</div>
				<select id="airplane" name="airplaneId" value="">
					<c:forEach var="airplane" items="${airplanes}">
						<option value="${airplane.id}">${airplane.name},
							&nbsp;max ${airplane.flightRange} km, crew - p:${airplane.pilots}
							n:${airplane.navigators} r:${airplane.radioOperators}
							as:${airplane.airStewards}</option>
					</c:forEach>
				</select>
				<p class="legend">
					<strong>p</strong> is pilots <br> <strong>n</strong> is
					navigators, <br> <strong>r</strong> is radio operators, <br>
					<strong>as</strong> is air stewards
				</p>
			</div>
		</div>


		<input id="place_id" type="hidden" name="mode" value="ADD" />
		<div class="submit">
			<button id="addbutton" class="listAction" name="action"
				value="NEW_FLIGHT">Добавить</button>
		</div>
	</form>
</div>


<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.11.0/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="js/jquery-ui-timepicker-addon-i18n.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-sliderAccess.js"></script>
<script src="js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$("#date_picker").datetimepicker({
		dateFormat : 'dd-mm-yy'
	});
</script>
