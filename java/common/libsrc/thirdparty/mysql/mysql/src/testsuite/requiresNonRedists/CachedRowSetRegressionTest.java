/*
 Copyright (C) 2005 MySQL AB

 This program is free software; you can redistribute it and/or modify
 it under the terms of version 2 of the GNU General Public License as 
 published by the Free Software Foundation.

 There are special exceptions to the terms and conditions of the GPL 
 as it is applied to this software. View the full text of the 
 exception in file EXCEPTIONS-CONNECTOR-J in the directory of this 
 software distribution.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
 */
package testsuite.requiresNonRedists;

import com.sun.rowset.CachedRowSetImpl;

import testsuite.BaseTestCase;
import testsuite.regression.StringRegressionTest;

public class CachedRowSetRegressionTest extends BaseTestCase {

	public CachedRowSetRegressionTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(StringRegressionTest.class);
	}

	/**
	 * Tests fix for BUG#5188, CachedRowSet errors using PreparedStatement.
	 * 
	 * @throws Exception
	 */
	public void testBug5188() throws Exception {
		try {
			this.stmt.executeUpdate("DROP TABLE IF EXISTS testBug5188");
			this.stmt.executeUpdate("CREATE TABLE testBug5188 "
					+ "(ID int NOT NULL AUTO_INCREMENT, "
					+ "datafield VARCHAR(64), " + "PRIMARY KEY(ID))");

			this.stmt.executeUpdate("INSERT INTO testBug5188(datafield) "
					+ "values('test data stuff !')");

			CachedRowSetImpl crs = null;
			String sql = "SELECT * FROM testBug5188 where ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "1");
			rs = pstmt.executeQuery();
			// create a CachedRowSet and populate it
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			// scroll through CachedRowSet & return results to screen ...
			assertTrue(crs.next());
			assertEquals("1", crs.getString("ID"));
			assertEquals("test data stuff !", crs.getString("datafield"));
			assertFalse(crs.next());
		} finally {
			this.stmt.executeUpdate("DROP TABLE IF EXISTS testBug5188");
		}
	}
}
