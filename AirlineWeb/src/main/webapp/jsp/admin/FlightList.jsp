<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form method="post" action="">
    <table id="flights" class="list">
        <thead>
            <tr class="tableheader">
                <th class="oddcolumn">&nbsp;</th>
                <th class="evencolumn">Номер рейса</th>
                <th class="oddcolumn">Место отправления</th>
                <th class="evencolumn">Место назначения</th>
                <th class="oddcolumn">Время полета</th>
                <th class="evencolumn">Вылет туда</th>
                <th class="oddcolumn">Прибытие туда</th>
                <th class="evencolumn">Вылет обратно</th>
                <th class="oddcolumn">Прибытие обратно</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="flight" items="${flights}">
                <tr>
                    <td class="oddcolumn"><input type="radio" name="flightId"
                        value="${flight.id}" checked></td>
                    <td class="evencolumn">${flight.id}</td>
                    <td class="oddcolumn">${flight.hub}</td>
                    <td class="evencolumn">${flight.destination}</td>
                    <td class="oddcolumn">${flight.flightTime}</td>
                    <td class="evencolumn">${flight.deptTime}</td>
                    <td class="oddcolumn">${flight.arrTime}</td>
                    <td class="evencolumn">${flight.deptTimeBack}</td>
                    <td class="oddcolumn">${flight.arrTimeBack}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <button class="listAction" name="action" type="submit"
            value=SHOW_FLIGHT>Показать</button>
        <button class="listAction" name="action" type="submit"
            value="DELETE_FLIGHT">Удалить</button>
        <button class="listAction" name="action" type="submit"
            value="NEW_FLIGHT">Создать новый</button>
</form>