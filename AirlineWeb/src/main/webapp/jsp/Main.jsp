<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/page-structure.css" />
<title>Могилевские Авиалинии</title>
</head>
<body>
	<div id="header">
		<img src="pic/startpage/airbus.png" alt="Smiley face" height="56"
			width="200">
		<h2 class="headTitle">Могилевские авиалинии</h2>
	</div>
	<div id="content">
		<p class="state">${mainState}</p>
		<script type="text/javascript">
			var message = "${mainState}";
			if (message.length > 0) {
				alert(message);
			}
		</script>
		<jsp:include page="${mainform}" />
	</div>
	<div id="footer">
		<div id="footer-content">
			<a href="mailto:7078009@gmail.com">Contact us: 7078009@gmail.com</a>
		</div>
	</div>
</body>
</html>