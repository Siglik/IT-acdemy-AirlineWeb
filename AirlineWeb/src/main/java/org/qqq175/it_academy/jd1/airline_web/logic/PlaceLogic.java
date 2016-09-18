/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic;

import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Country;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.Settings;
import org.qqq175.it_academy.jd1.airline_web.util.exception.GoogleApiException;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.PlaceDetails;

/**
 * @author qqq175
 *
 */
public class PlaceLogic {
	GeoApiContext apiContext;

	/**
	 * @param apiContext
	 */
	public PlaceLogic() {
		this.apiContext = new GeoApiContext();
		apiContext.setApiKey(Settings.getInstance().getGoogleAPIKey());
	}

	/**
		 * @author qqq175
		 *
		 */
	public class Place {
		City city;
		Country country;
	}

	public Place getPlaceByPlaceId(String placeId, String language) throws GoogleApiException{
		Place place = new Place();
		
		PlaceDetailsRequest placeRequest = new PlaceDetailsRequest(apiContext);
		placeRequest.placeId(placeId);
		placeRequest.language(language);
		
		PlaceDetails placeDetail;
		try {
			placeDetail = placeRequest.await();
		} catch (Exception e) {
			Logger.getInstance().log(e);
			throw new GoogleApiException();
		}
		
		City city = new City();
		Country country = new Country();
		AddressComponent[] addressComponents = placeDetail.addressComponents;
		city.setPlaceId(placeId);
		city.setName(placeDetail.name);
		System.out.println(placeDetail.name);
		System.out.println(addressComponents[addressComponents.length-1].longName);
		System.out.println(addressComponents[addressComponents.length-1].shortName);
		System.out.println(placeDetail.url);
		System.out.println(placeDetail.photos[0]);
		System.out.println(placeDetail.geometry.location.lat);
		city.setLatitude(placeDetail.geometry.location.lat);
		city.setLongitude(placeDetail.geometry.location.lng);
		city.setCountryId(0);
		
		country.setLongName(addressComponents[addressComponents.length-1].longName);
		country.setShortName(addressComponents[addressComponents.length-1].shortName);
		
		place.city = city;
		place.country = country;
		
		return place;
	}
	
	public String getCityURL(City city){
		PlaceDetailsRequest placeRequest = new PlaceDetailsRequest(apiContext);
		placeRequest.placeId(city.getPlaceId());
		PlaceDetails placeDetail = null;
		try {
			placeDetail = placeRequest.await();
			return placeDetail.url.toString();
		} catch (Exception e) {
			Logger.getInstance().log("URL request: ",e);
		}
		
		return "";
	}
	
	public static void main(String [] arrgs) throws GoogleApiException{
		PlaceLogic cl = new PlaceLogic();
		Place place = cl.getPlaceByPlaceId("ChIJOwg_06VPwokRYv534QaPC8g", "ru");
		System.out.println(cl.getCityURL(place.city));
	}
}
