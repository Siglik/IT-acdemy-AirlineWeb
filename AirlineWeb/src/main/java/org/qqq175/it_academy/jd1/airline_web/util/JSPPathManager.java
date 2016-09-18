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
public class JSPPathManager {
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("conf/pagepath", Locale.ROOT);
	private JSPPathManager() { }
	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}
