/**
 * 
 */
var map = null;
var infowindow = null;
var marker = null;

function initMapForm() {
	var inputCountry = document.getElementById("country");
	inputCountry.addEventListener("focus", show);
	inputCountry.addEventListener("blur", hide);
	inputCountry.addEventListener("input", updateCountry);

	var inputCity = document.getElementById('city');
	inputCity.addEventListener("focus", show);
	inputCity.addEventListener("blur", hide);
	inputCity.addEventListener("input", updateCity);

	initializeMap();
}

function initializeMap() {
	var mapProp = {
		center : new google.maps.LatLng(53.900715, 30.331359),
		zoom : 6,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	};
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
	infowindow = new google.maps.InfoWindow;

}

// get countries list from google and display it
function updateCountry(event) {
	var list = document.getElementById('countries');
	var city = document.getElementById('city');
	var placeId = document.getElementById('place_id');
	var addButton = document.getElementById('addbutton');
	addButton.disabled = true;
	city.value = "";
	city.disabled = true;
	placeId.value = "";
	list.innerHTML = "";
	list.style.height = '1px';
	getJSON(makeCountryURL(event.target.value), refreshCountriesList);
}

function refreshCountriesList(list) {
	for (i = 0; i < list.results.length; i++) {
		if (list.results[i].types[0] === "country") {
			addCountryToList(list.results[i].address_components[0].long_name,
					list.results[i].place_id);
		}
	}
}

function addCountryToList(country, placeId) {
	var paragraph = "<p class='list-item' onclick=\"chooseItem('country',this)\" id='"
			+ placeId + "'>" + country + "</p>";
	var list = document.getElementById('countries');
	list.innerHTML = list.innerHTML + paragraph;
	list.style.height = (list.clientHeight + 26 * (((country.length / 20) >> 0) + 1))
			+ 'px';
}

function makeCountryURL(country) {
	var URL = "https://maps.googleapis.com/maps/api/geocode/json?address="
			+ country
			+ "&language=ru&key=AIzaSyAJd9P5Y4yQ8hd9wx0iXhgUXD1R-uWc_Bw";
	return URL;
}
// get cities list from google and display it
function updateCity(event) {
	var list = document.getElementById('cities');
	var country = document.getElementById('country');
	list.innerHTML = "";
	list.style.height = '1px';
	getJSON(makeCityURL(country.value, event.target.value), refreshCitiesList);

	var placeId = document.getElementById('place_id');
	var addButton = document.getElementById('addbutton');
	addButton.disabled = true;
	placeId.value = "";
}

function makeCityURL(country, city) {
	var URL = "https://maps.googleapis.com/maps/api/geocode/json?address="
			+ city + ",+" + country
			+ "&language=ru&key=AIzaSyAJd9P5Y4yQ8hd9wx0iXhgUXD1R-uWc_Bw";
	return URL;
}

function refreshCitiesList(list) {
	for (i = 0; i < list.results.length; i++) {
		if (list.results[i].types[0] === "locality") {
			addCityToList(list.results[i].address_components[0].long_name,
					list.results[i].place_id);
		}
	}
}

function addCityToList(city, placeId) {
	var paragraph = "<p class='list-item' onclick=\"chooseItem('city',this)\" id='"
			+ placeId + "'>" + city + "</p>";
	var list = document.getElementById('cities');
	list.innerHTML = list.innerHTML + paragraph;
	list.style.height = (list.clientHeight + 26 * (((country.length / 20) >> 0) + 1))
			+ 'px';
}

// show input elements list on focus
function show(event) {
	var id = '';
	switch (event.target.id) {
	case 'city':
		id = 'cities'
		break
	case 'country':
		var id = 'countries'
	}
	var list = document.getElementById(id);
	var inputPosition = event.target.getBoundingClientRect();
	list.style.left = inputPosition.left + 'px';
	list.style.top = inputPosition.bottom + 'px';
	list.style.display = "block";
}

// hide input elements list on focus
function hide(event) {
	switch (event.target.id) {
	case 'city':
		id = 'cities'
		break
	case 'country':
		var id = 'countries'
	}
	setTimeout(function() {
		var list = document.getElementById(id);
		list.style.display = "none";
	}, 200);
}

// change input value to item content
function chooseItem(inputId, item) {
	var input = document.getElementById(inputId);
	var text = item.innerHTML;
	if (inputId === 'city') {
		var placeId = document.getElementById('place_id');
		placeId.value = item.id;
		var addButton = document.getElementById('addbutton');
		addButton.disabled = false;
	}
	setNewMapCenter(item);
	input.value = text;
	if (inputId === 'country') {
		var city = document.getElementById('city');
		city.disabled = false;
	}
}

function setNewMapCenter(item) {
	var URL = "https://maps.googleapis.com/maps/api/geocode/json?place_id="
			+ item.id
			+ "&language=ru&key=AIzaSyAJd9P5Y4yQ8hd9wx0iXhgUXD1R-uWc_Bw";
	console.log(URL);
	getJSON(URL, applyMapCenter);
}

function applyMapCenter(place) {
	curPlace = place.results[0];
	console.log(curPlace.geometry.location.lat + " "
			+ curPlace.geometry.location.lng);
	switch (curPlace.place_id) {
	case "ChIJ-yRniZpWPEURE_YRZvj9CRQ": // russia
		map.setZoom(2);
		map.setCenter(new google.maps.LatLng(curPlace.geometry.location.lat,
				curPlace.geometry.location.lng));
		break;
	/*
	 * case "ChIJCzYy5IS16lQRQrfeQ5K5Oxw": //usa map.setZoom(3);
	 * map.setCenter(new google.maps.LatLng(curPlace.geometry.location.lat,
	 * curPlace.geometry.location.lng)); break; case
	 * "ChIJ2WrMN9MDDUsRpY9Doiq3aJk": //canada console.log("use");
	 * map.setZoom(3); map.setCenter(new
	 * google.maps.LatLng(curPlace.geometry.location.lat,
	 * curPlace.geometry.location.lng)); break; case
	 * "ChIJqZHHQhE7WgIReiWIMkOg-MQ": //UK console.log("use"); map.setZoom(4);
	 * map.setCenter(new google.maps.LatLng(curPlace.geometry.location.lat,
	 * curPlace.geometry.location.lng)); break;
	 */
	default:
		console.log("another " + curPlace.place_id);
		map.fitBounds(new google.maps.LatLngBounds(
				curPlace.geometry.viewport.southwest,
				curPlace.geometry.viewport.northeast));
	}
	if (marker) {
		marker.setMap(null)
	}
	marker = new google.maps.Marker({
		map : map,
		position : curPlace.geometry.location
	});
	infowindow.close();
	infowindow.setContent(curPlace.formatted_address);
	infowindow.open(map, marker);
}

// get json from url
function getJSON(url, onComplete) {
	var xhr = new XMLHttpRequest();
	xhr.open("get", url, true);
	xhr.responseType = "json";
	xhr.onload = function() {
		onComplete(xhr.response);
	}
	xhr.send();
}
