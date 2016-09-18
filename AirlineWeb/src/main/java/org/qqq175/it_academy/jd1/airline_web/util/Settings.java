/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author qqq175
 *
 */
public class Settings {
	public final Database database;
	private final String googleAPIKey;
	private final String logDir;
	private String realPath = null;
	private final String salt;
	private static volatile Settings instance;

	/** Constructor */
	private Settings() {
		database = new Database();
		ResourceBundle resource = ResourceBundle.getBundle("conf/application", Locale.ROOT);
		
		salt = "Unds&4s>dfuPMdqmx84Yfagt=274bfa#fdsa64q1";
		googleAPIKey = resource.getString("googleAPIKey");
		logDir = resource.getString("logDir");
	}

	/**
	 * Double Checked Locking & volatile singleton get instance method
	 * 
	 * @return Settings instance
	 */
	public static Settings getInstance() {
		Settings localInstance = instance;
		if (localInstance == null) {
			synchronized (Settings.class) {
				localInstance = instance;
				if (instance == null) {
					instance = localInstance = new Settings();
				}
			}
		}

		return localInstance;
	}

	/**
	 * contain database connection settings
	 * 
	 * @author qqq175
	 */
	public final class Database {
		public final Pool pool;
		private final String url;
		private final String driver;
		private final String user;
		private final String password;

		private Database() {
			pool = new Pool();
			ResourceBundle resource = ResourceBundle.getBundle("conf/dbconnection", Locale.ROOT);
			
			url = resource.getString("url");
			driver = resource.getString("driver");
			user = resource.getString("user");
			password = resource.getString("password");
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * contain c3p0 pool settings
		 * 
		 * @author qqq175
		 */
		public final class Pool {
			private final int minPoolSize;
			private final int acquireIncrement;
			private final int maxPoolSize;
			private final int maxStatements;

			private Pool() {
				ResourceBundle resource = ResourceBundle.getBundle("conf/dbpool", Locale.ROOT);
				
				minPoolSize = Integer.parseInt(resource.getString("minPoolSize"));
				acquireIncrement = Integer.parseInt(resource.getString("acquireIncrement"));
				maxPoolSize = Integer.parseInt(resource.getString("maxPoolSize"));
				maxStatements = Integer.parseInt(resource.getString("maxStatements"));
			}

			/**
			 * @return the minPoolSize
			 */
			public int getMinPoolSize() {
				return minPoolSize;
			}

			/**
			 * @return the acquireIncrement
			 */
			public int getAcquireIncrement() {
				return acquireIncrement;
			}

			/**
			 * @return the maxPoolSize
			 */
			public int getMaxPoolSize() {
				return maxPoolSize;
			}

			/**
			 * @return the minStatements
			 */
			public int getMaxStatements() {
				return maxStatements;
			}
		}

		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @return the driver
		 */
		public String getDriver() {
			return driver;
		}

	}

	/**
	 * @return the googleAPIKey
	 */
	public String getGoogleAPIKey() {
		return googleAPIKey;
	}

	/**
	 * @return the logDir
	 */
	public String getLogDir() {
		return logDir;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @return the realPath
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * @param realPath the realPath to set
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

}
