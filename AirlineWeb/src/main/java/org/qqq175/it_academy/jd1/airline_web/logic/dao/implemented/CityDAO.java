/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.CityDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;

/**
 * @author qqq175
 *
 */
public class CityDAO extends BasicDAO<City> implements CityDAOInterface{
	private static String TABLE_NAME = "city";
	private static int COLUMN_COUNT = 6;
	
	public CityDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, City entity) throws SQLException {
		prepStatment.setString(1, entity.getName());
		prepStatment.setInt(2, entity.getCountryId());
		prepStatment.setString(3, entity.getPlaceId());
		prepStatment.setDouble(4, entity.getLatitude());
		prepStatment.setDouble(5, entity.getLongitude());
	}

	@Override
	protected City fillEntity(ResultSet resultSet) throws SQLException {
		City city = new City();
		city.setId(resultSet.getInt(1));
		city.setName(resultSet.getString(2));
		city.setCountryId(resultSet.getInt(3));
		city.setPlaceId(resultSet.getString(4));
		city.setLatitude(resultSet.getDouble(5));
		city.setLongitude(resultSet.getDouble(6));
		return city;
	}

	@Override
	public int getCityIdByPlaceId(String placeId) throws SQLException {
		try (PreparedStatement prepStatment = this.getConnection().prepareStatement(this.getSqlQuery().getQuery("sql.city.idByPlaceId"))) {
			prepStatment.setString(1, placeId);
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				if (resultSet.next()){
					return resultSet.getInt(1);
				}
			}
		}
		//if nothing found return -1
		return -1;
	}
	
	
//	public static void main(String [] args){
//		try(Connection conn = DataSource.getInstance().getConnection()){
//			CountryDAO contryDAO = new CountryDAO(conn);
//			CityDAO cityDAO = new CityDAO(conn);
//			Country country = new Country();
////			country.setLongName("Беларусь");
////			country.setShortName("BY");
////			City city = new City();
////			city.setCountryId(1);
////			city.setName("Минск");
////			city.setPlaceId("Dhufusdfhfhwefwe7");
////			city.setLatitude(27.222);
////			city.setLongitude(62.22123);
//////			contryDAO.create(country);
////			cityDAO.create(city);
////			HubDAO hubDAO = new HubDAO(conn);
////			RouteDAO routeDAO = new RouteDAO(conn);
//			FlightDAO flightDAO = new FlightDAO(conn);
////			
////			Hub hub = new Hub();
////			hub.setCityId(1);
////			hubDAO.create(hub);
////			
////			Route route = new Route();
////			route.setCityId(2);
////			route.setHubId(1);
////			routeDAO.create(route);
//			
//			Flight flight = new Flight();
//			flight.setRouteId(1);
//			flight.setAirplaneModelId(2);
//			Calendar cal = new GregorianCalendar();
//			cal.setTime(new Date());
//			cal.add(Calendar.DAY_OF_YEAR, 3);
//			flight.setDeptTime(cal.getTime());
//			cal.add(Calendar.DAY_OF_YEAR, 1);
//			flight.setDeptTimeBack(cal.getTime());
//			System.out.print(cal.getTime());
//			flightDAO.create(flight);
//			
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedOperation e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
