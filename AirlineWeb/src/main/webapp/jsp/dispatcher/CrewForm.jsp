<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/form.css" />
<br>
<div id="place-form">
	<form method="post" target="">
		<input type="hidden" name="flightId" value="${flightId}" />
		<div class="form-items">
			<div class="form-item" class="hub">
				<div class="inputname2">Выберите пилотов:</div>
				<c:forEach var="i" begin="1" end="${airplane.pilots}">
					<select name="pilotIDs" value="">
						<c:forEach var="pilot" items="${pilots}">
							<option value="${pilot.id}">${pilot.id}-
								${pilot.firstName}&nbsp; ${pilot.lastName}</option>
						</c:forEach>
					</select>
					<p class="current-crew">Current: ${crewPilots[i-1].id} -
						${crewPilots[i-1].firstName} ${crewPilots[i-1].lastName}</p>
				</c:forEach>
				<br>

				<div class="inputname2">Выберите навигаторов:</div>
				<c:forEach var="i" begin="1" end="${airplane.navigators}">
					<select name="navigatorIDs" value="">
						<c:forEach var="navigator" items="${navigators}">
							<option value="${navigator.id}">${navigator.id}-
								${navigator.firstName}&nbsp; ${navigator.lastName}</option>
						</c:forEach>
					</select>
					<p class="current-crew">Current: ${crewNavigators[i-1].id} -
                        ${crewNavigators[i-1].firstName} ${crewNavigators[i-1].lastName}</p>
				</c:forEach>

				<br>
				<div class="inputname2">Выберите радистов:</div>
				<c:forEach var="i" begin="1" end="${airplane.radioOperators}">
					<select name="radioOperatorIDs" value="">
						<c:forEach var="radioOperator" items="${radioOperators}">
							<option value="${radioOperator.id}">${radioOperator.id}
								- ${radioOperator.firstName}&nbsp; ${radioOperator.lastName}</option>
						</c:forEach>
					</select>
					<p class="current-crew">Current: ${crewRadioOperators[i-1].id} -
                        ${crewRadioOperators[i-1].firstName} ${crewRadioOperators[i-1].lastName}</p>
				</c:forEach>

				<br>
				<div class="inputname2">Выберите бортпроводников:</div>
				<c:forEach var="i" begin="1" end="${airplane.airStewards}">
					<select name="airStewardIDs" value="">
						<c:forEach var="airSteward" items="${airStewards}">
							<option value="${airSteward.id}">${airSteward.id}-
								${airSteward.firstName}&nbsp; ${airSteward.lastName}</option>
						</c:forEach>
					</select>
					<p class="current-crew">Current: ${crewAirStewards[i-1].id} -
                        ${crewAirStewards[i-1].firstName} ${crewAirStewards[i-1].lastName}</p>
				</c:forEach>

			</div>
		</div>

		<div class="submit">
			<button id="addbutton" class="listAction" name="action"
				value="SAVE_CREW">Сохранить</button>
		</div>
	</form>
</div>