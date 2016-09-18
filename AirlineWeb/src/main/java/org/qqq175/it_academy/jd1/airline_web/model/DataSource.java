/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.model;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.Settings;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author qqq175
 *
 */
public class DataSource {
	private static volatile DataSource instance;
	private ComboPooledDataSource cpds;

	private DataSource() {
		cpds = new ComboPooledDataSource();
		Settings settings = Settings.getInstance();
		try {
			cpds.setDriverClass(settings.database.getDriver()); // loads the
			                                                    // jdbc driver
		} catch (PropertyVetoException e) {
			Logger.getInstance().log(e);
		}

		cpds.setJdbcUrl(settings.database.getUrl());
		cpds.setUser(settings.database.getUser());
		cpds.setPassword(settings.database.getPassword());

		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(settings.database.pool.getMinPoolSize());
		cpds.setAcquireIncrement(settings.database.pool.getAcquireIncrement());
		cpds.setMaxPoolSize(settings.database.pool.getMaxPoolSize());
		cpds.setMaxStatements(settings.database.pool.getMaxStatements());
	}

	public static DataSource getInstance() {
		DataSource localInstance = instance;

		if (localInstance == null) {
			synchronized (DataSource.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new DataSource();
				}
			}
		}
		
		return localInstance;
	}

	public Connection getConnection() throws SQLException {
		return this.cpds.getConnection();
	}
}
