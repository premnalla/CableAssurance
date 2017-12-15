/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Prem
 * 
 */
public class Test {

	private static Log log = LogFactory.getLog(Test.class);

	public static void main(String[] args) {
		// int i=0;
		// while (true) {
		// i++;
		// ProxyDbConnection_Intfc p = new ProxyDbConnection();
		// p = null;
		// try {
		// Thread.sleep(500);
		// // System.out.println("woke up...");
		// } catch (Exception e) {
		//				
		// }
		// if (i==10) {
		// i = 0;
		// System.gc();
		// }
		// }

		// try {
		// // The newInstance() call is a work around for some
		// // broken Java implementations
		//
		// Class.forName("com.mysql.jdbc.Driver").newInstance();
		// } catch (Exception ex) {
		// // handle the error
		// }
		//
		// try {
		// Connection conn = DriverManager
		// .getConnection("jdbc:mysql://localhost/canet?user=root&password=manager");
		// System.out.println("Got connection");
		// } catch (SQLException ex) {
		// // handle any errors
		// // System.out.println("SQLException: " + ex.getMessage());
		// System.out.println("SQLState: " + ex.getSQLState());
		// System.out.println("VendorError: " + ex.getErrorCode());
		// // ex.printStackTrace();
		// }

		Integer tmpI = null;
		try {
			tmpI.intValue();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
			// log.error(e);	
		}
		
		TestTable tbl = new TestTable(new Integer(1));
		tbl.insert();
		List<TestTable> l = TestTable.Get(null);
		for (int i=0; i<l.size(); i++) {
			TestTable entry = l.get(i);
			System.out.println(entry.getName());
		}
		
	}
	
}
