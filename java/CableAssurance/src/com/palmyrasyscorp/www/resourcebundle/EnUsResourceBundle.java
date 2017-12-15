/**
 * 
 */
package com.palmyrasyscorp.www.resourcebundle;

import java.util.ResourceBundle;
import java.util.Locale;

/**
 * @author Prem
 *
 */
public class EnUsResourceBundle extends AbstractResourceBundle {

	/**
	 * 
	 */
	private ResourceBundle m_rb;
	
	/**
	 * 
	 *
	 */
	public EnUsResourceBundle() {
		super(Locale.US);
		// setLocale(Locale.US);
		try {
			
			// the followind did not work
			// m_rb = ResourceBundle.getBundle("/properties/UiStrings", getLocale());
			// m_rb = ResourceBundle.getBundle("properties/UiStrings", getLocale());
			
			// Following works when UiStrings_en_US.properties is moved to the dir below
			m_rb = ResourceBundle.getBundle(
					"com/palmyrasyscorp/www/resourcebundle/UiStrings", getLocale());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public String getString(String key) {
		String ret;
		
		if (m_rb != null) {
			ret = m_rb.getString(key);
		} else {
			ret = null;
		}
		
		return (ret);
	}

}
