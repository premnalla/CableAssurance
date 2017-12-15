/**
 * 
 */
package com.palmyrasyscorp.www.resourcebundle;

import java.util.Locale;

/**
 * @author Prem
 *
 */
public abstract class AbstractResourceBundle implements Get {

	/**
	 * 
	 */
	private Locale m_loc;
	protected void setLocale(Locale loc) { m_loc = loc; }
	public Locale getLocale() { return m_loc; }
	
	/**
	 * 
	 *
	 */
	protected AbstractResourceBundle() {
		super();
	}
	
	/**
	 * 
	 *
	 */
	protected AbstractResourceBundle(Locale loc) {
		super();
		m_loc = loc;
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public boolean equals(Object o) {
		return (m_loc.equals(o));
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return (m_loc.hashCode());
	}

}
