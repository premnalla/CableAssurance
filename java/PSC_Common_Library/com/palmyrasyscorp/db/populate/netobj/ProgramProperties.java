/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Prem
 *
 */
public class ProgramProperties {

	/**
	 * 
	 */
	private static ProgramProperties m_instance;
	
	/**
	 * 
	 */
	Properties m_props = null;
	
	/**
	 * 
	 *
	 */
	private ProgramProperties() {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("com/palmyrasyscorp/db/populate/netobj/program.properties");
			m_props = new Properties();
			m_props.load(fis);			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exitting...");
			System.exit(-1);
		}		
	}
	
	/**
	 * 
	 * @return
	 */
	public static ProgramProperties getInstance() {
		if (m_instance == null) {
			m_instance = new ProgramProperties();
		}
		return (m_instance);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getValue(String name) {
		String val = null;
		
		if (m_props != null) {
			val = m_props.getProperty(name);
		}
		
		return (val);
	}
}
