/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.util.*;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class CmtsPollerMain {

	private LinkedList m_cmtsList = new LinkedList();
	private LinkedList m_cmtsPollerList = new LinkedList();
	
	/**
	 * 
	 */
	public CmtsPollerMain() {
		super();
	}

	public void createCmtsList() {
		ProxyDbConnection c = null;
		ProxyDbStatement stmt = null;
		ProxyDbResultSet rs = null;

		try {
			/*
			 * Get connection
			 */
			c = CADbConnectionPool.getInstance().getConnection();

			/*
			 * Create sql
			 */
			String qryStr = GlobalQueries.QRY_CMTS_IN_LOCAL_SYSTEM;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				Cmts cmts = new Cmts(rs);
				m_cmtsList.add(cmts);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			// log.error(null, e);	
		} finally {
			if (stmt != null) {
				stmt.cleanup();
			}
			if (rs != null) {
				rs.cleanup();
			}
			if (c != null) {
				c.cleanup();
			}
		}

	}

	public void startWorkers() {
		
		for (int i=0; i<m_cmtsList.size(); i++) {
			Cmts cmts = (Cmts) m_cmtsList.get(i);
			CmtsPoller poller = new CmtsPoller(cmts);
			m_cmtsPollerList.add(poller);
			poller.start();
			
			try {
				Thread.sleep(3*1000);
			} catch (Exception e) {
			}
			
		}
		
	}
	
	public void waitForWorkersToComplete() {
		boolean notDone = needToWait();
		while (notDone) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			notDone = needToWait();
		}
	}
	
	private boolean needToWait() {
		boolean ret = false;

		for (int i=0; i<m_cmtsPollerList.size(); i++) {
			CmtsPoller poller = (CmtsPoller) m_cmtsPollerList.get(i);
			if (!poller.isWorkDone()) {
				return (true);
			}
		}

		return (ret);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CmtsPollerMain pm = new CmtsPollerMain();
		pm.createCmtsList();
		System.out.println("Starting....");
		pm.startWorkers();
		pm.waitForWorkersToComplete();
		System.out.println("Exitting....");
	}

}
