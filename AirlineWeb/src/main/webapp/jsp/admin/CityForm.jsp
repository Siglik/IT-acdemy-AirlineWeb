<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/form.css" />
<script src="js/cityform.js"></script>s
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAJd9P5Y4yQ8hd9wx0iXhgUXD1R-uWc_Bw"></script>
<div id="place-form">
	<form method="post" autocomplete="off" target="">
		<div class="form-items">
			<div class="form-item">
				<div class="inputname">Страна:</div>
				<input id="country" type="text" value="" />
				<div id="countries" class="list"></div>
			</div>

			<div class="form-item">
				<div class="inputname">Населенный пункт:</div>
				<input id="city" type="text" disabled="disabled" value="" />
				<div id="cities" class="list"></div>
			</div>
		</div>
		<input id="place_id" type="hidden" name="place_id" value=""> <input
			id="action" type="hidden" name="action" value="NEW_CITY"> <input
			id="place_id" type="hidden" name="mode" value="ADD">
		<div class="submit">
			<input id="addbutton" type="submit" disabled="disabled"
				value="Добавить" />
		</div>
		<div id="googleMap"></div>
	</form>

	<script type="text/javascript">
		initMapForm();
	</script>
</div>