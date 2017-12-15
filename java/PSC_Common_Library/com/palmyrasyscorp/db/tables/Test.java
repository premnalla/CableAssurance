/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
//import java.math.BigInteger;

import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.helper.*;

/**
 * @author Prem
 *
 */
public class Test {
	
	public static void main(String args[])
	{
		Enterprise e = new Enterprise();
		// e.queryChildren();
		LinkedList childList = e.getListOfChildren();
		for (int i=0; i<childList.size(); i++) {
			Region r = (Region) childList.get(i);
			System.out.println("Region name=" + r.getName());
		}
		System.out.println("Max value of Long on this CPU architecture is : " + Long.MAX_VALUE);
		System.out.println("Max value of Integer on this CPU architecture is : " + Integer.MAX_VALUE);
		// System.out.println("Max value of BigInteger on this CPU architecture is : " + BigInteger.ONE);
		
		/*
		String str1 = new String("foo");
		String str2 = str1;
		str1 = str1.concat("5");
		*/
		
		Test t = new Test();
		// t.queryAlarms();
		t.getBlob();
		t.stringTest();
		t.stringTest2();
		
		Random m_rand = new Random();
		int cycleToGoDown = m_rand.nextInt(10);
		int mtaCycleToGoDown = m_rand.nextInt(10);
		System.out.println("rand1: " + cycleToGoDown);
		System.out.println("rand1: " + mtaCycleToGoDown);
		
		CmPerfEntry perf = CmPerformanceHelper.GetLatestPerfRecord(new Long(12));
		System.out.println("Upstream SNR: " + perf.getUpstreamSnr());
	}
	
	
	protected void stringTest() {
		StringBuffer str1 = new StringBuffer("foo");
		StringBuffer str2 = str1;
		modString(str1);
		System.out.println("String str1:" + str1);
		System.out.println("String str2:" + str2);
	}
	
	private void modString(StringBuffer s) {
		s.append(5);
	}
	
	protected void stringTest2() {
		String str1 = new String("foo");
		String str2 = str1;
		modString(str1);
		System.out.println("String str1:" + str1);
		System.out.println("String str2:" + str2);
	}
	
	private void modString(String s) {
		s = s + 5;
	}

	protected void queryAlarms() {
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT * FROM alarm_basic";
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				// Alarm alm = new Alarm(rs);
				// System.out.println("Arrival_Time: " + alm.getAlarmDetectionTime());
				
				// alm = null;
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void getBlob() {
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		System.out.println("in getBlob() ...");

		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT alarm_details FROM alarm_details";
			rs = stmt.executeQuery(qryStr);
			
			while (rs.next()) {
				int i = 1;
				String str = new String(rs.getBytes(i++));
				System.out.println(str);
				str = null;
			}
			
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
}
