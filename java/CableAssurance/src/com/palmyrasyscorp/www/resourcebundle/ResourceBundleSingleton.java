/**
 * 
 */
package com.palmyrasyscorp.www.resourcebundle;

import java.util.Locale;
import java.util.LinkedList;

/**
 * @author Prem
 *
 */
public final class ResourceBundleSingleton {

	/**
	 * 
	 */
	private static ResourceBundleSingleton m_instance;
	
	/**
	 * 
	 */
	private LinkedList m_resBundles = new LinkedList();
	
	/**
	 * 
	 *
	 */
	private ResourceBundleSingleton() {
		createAndStoreBundles();
	}
	
	/**
	 * 
	 * @return
	 */
	public static ResourceBundleSingleton getInstance() {
		if (m_instance == null) {
			m_instance = new ResourceBundleSingleton();
		}
		return (m_instance);
	}
	
	/**
	 * 
	 * @param loc
	 * @return
	 */
	public AbstractResourceBundle getBundle(Locale loc) {
		AbstractResourceBundle ret = null;
		
		try {
			for (int i=0; i<m_resBundles.size(); i++) {
				AbstractResourceBundle o = 
					(AbstractResourceBundle) m_resBundles.get(i);
				if (o.equals(loc)) {
					ret = o;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (ret);
	}
	
	/**
	 * 
	 *
	 */
	private void createAndStoreBundles() {
		m_resBundles.add(new EnUsResourceBundle());
		// others to follow ....
	}
}
