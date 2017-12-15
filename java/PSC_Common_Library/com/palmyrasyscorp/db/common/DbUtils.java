/**
 * 
 */
package com.palmyrasyscorp.db.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Prem
 *
 */
public class DbUtils {

	public static final void ReleaseResultSet(ResultSet rs)
	{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) { 
			}
			rs = null;
		}
	}
	
	public static final void ReleaseStatement(Statement stmt)
	{
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) { // ignore
			}
			stmt = null;
		}
	}
	
	public static final Float ConvertBigDecimalToFloat(BigDecimal bd) {
		Float ret = null;
		
		try {
			ret = new Float(bd.floatValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (ret);
	}
}
