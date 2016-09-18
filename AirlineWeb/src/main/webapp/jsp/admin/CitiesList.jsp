<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form method="post" action="">
    <table id="flights" class="list">
        <thead>
            <tr class="tableheader">
                <th class="oddcolumn">&nbsp;</th>
                <th class="evencolumn">ID</th>
                <th class="oddcolumn">Город</th>
                <th class="evencolumn">Страна</th>
                <th class="oddcolumn">Широта</th>
                <th class="evencolumn">Долгота</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="city" items="${cities}">
                <tr>
                    <td class="oddcolumn"><input type="radio" name="flightId"
                        value="${city.id}" checked></td>
                    <td class="evencolumn">${city.id}</td>
                    <td class="oddcolumn">${city.name}</td>
                    <td class="evencolumn">${city.country}</td>
                    <td class="oddcolumn">${city.latitude}</td>
                    <td class="evencolumn">${city.longitude}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <button class="listAction" name="action" type="submit"
        value="DELETE_FLIGHT">Удалить</button>
    <button class="listAction" name="action" type="submit"
        value="NEW_FLIGHT">Создать новый</button>
</form>