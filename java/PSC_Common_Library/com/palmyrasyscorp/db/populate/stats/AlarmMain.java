/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.palmyrasyscorp.db.common.DbConnectionPool;
import com.palmyrasyscorp.db.common.DbUtils;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.tables.Emta;
import com.palmyrasyscorp.db.tables.GlobalQueries;

/**
 * @author Prem
 *
 */
public class AlarmMain {

	/**
	 * 
	 */
	protected AlarmMain() {
		
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AlarmMain am = new AlarmMain();
		am.createAlarms();
	}
	
	/**
	 * 
	 *
	 */
	public void createAlarms() {
		String qryStr = GlobalQueries.QRY_MTA_IN_ENTERPRISE;
		Connection conn = DbConnectionPool.getInstance().getConnection().getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		java.util.LinkedList mtaList = new LinkedList();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			short i = 0;
			while(rs.next() && i<10) {
				i++;
				mtaList.add(new Emta(mrs));
			}
		} catch (Exception e) {	      
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}  

		for (int i=0; i<mtaList.size(); i++) {
			Emta em = (Emta) mtaList.get(i);
			GregorianCalendar cal = new GregorianCalendar();
			long timeSec = cal.getTimeInMillis() / 1000;
			long timeUSec = (cal.getTimeInMillis() - (timeSec * 1000)) * 1000;
			Alarm alm = new Alarm(em.getEmtaResId(),timeSec, timeUSec, 2, 1, 60*20, 1);
			alm.insertAlarm();
		}
	}
}
