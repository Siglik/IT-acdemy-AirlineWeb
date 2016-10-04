<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form method="post" action="">
    <table id="hubs" class="list">
        <thead>
            <tr class="tableheader">
                <th class="oddcolumn">&nbsp;</th>
                <th class="evencolumn">ID</th>
                <th class="oddcolumn">Город</th>
                <th class="evencolumn">Страна</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="hub" items="${hubs}">
                <tr>
                    <td class="oddcolumn"><input type="radio" name="hubId"
                        value="${hub.id}" checked></td>
                    <td class="evencolumn">${hub.id}</td>
                    <td class="oddcolumn">${hub.name}</td>
                    <td class="evencolumn">${hub.country}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <button class="listAction" name="action" type="submit"
        value="DELETE_HUB">Удалить</button>
    <button class="listAction" name="action" type="submit"
        value="NEW_HUB">Добавить</button>
</form>