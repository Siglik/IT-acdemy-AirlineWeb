/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic;

import org.qqq175.it_academy.jd1.airline_web.model.dto.City;

/**
 * @author qqq175
 *
 */
class Calculations {
	public static double EARTH_RADIUS = 6378137;
	
	public static double calcDistance(City from, City to){
		double latFrom = from.getLatitude() / 180 * Math.PI;
		double latTo = to.getLatitude() / 180 * Math.PI;
		double lngFrom = from.getLongitude() / 180 * Math.PI;
		double lngTo = to.getLongitude() / 180 * Math.PI;
		double distance;
		distance = EARTH_RADIUS * Math.acos(Math.cos( latFrom ) * Math.cos( latTo ) * Math.cos( lngTo - lngFrom) + Math.sin( latFrom ) * Math.sin( latTo ) );
		
		return distance/1000;
	}
	
//	public static void main(String [] arrgs){
//		City from = new City();
//		City to = new City();
//		from.setLatitude(53.9045397);
//		from.setLongitude(27.5615244);
//		to.setLatitude(40.7127837);
//		to.setLongitude(-74.0059413);
//		System.out.println(calcDistance(from, to));
//	}
}
