package com.palmyrasyscorp.db.populate.stats;

import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.LinkLoopException;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

public class CmOfflineWorker extends AbstractWorker {

	public static final float PERCENT_CM_OFFLINE = 2;
	public static final float PERCENT_CM_EXCEPTION = 3;
	public static final float PERCENT_MTA_UNAVAILABLE = 5;
	public static final float PERCENT_MTA_ALARM = 10;
	
	private DbConnection m_conn = null;

	public CmOfflineWorker() {
		super();
	}

	public void run() {
		// obtain db connection
		getConnection();
		
		// turn CM's offline
		LinkedList cmList = getCmList();
		turnCmsOffline(cmList);
		
		LinkedList mtaList = getMtaList();
		makeMTAsUnavailable(mtaList);
		
		// close
		releaseConnection();
		
		// finally: inform that we are done
		setDoneWork();
	}
	
	protected boolean getConnection()
	{
		boolean ret = true;
		
		m_conn = createDbConnection();
		
		return (ret);
	}
	
	protected boolean releaseConnection()
	{
		boolean ret = true;
		
		closeConnection(m_conn);
		m_conn = null;
		
		return (ret);
	}

	protected void turnCmsOffline(LinkedList cmList) {
		float numCms = cmList.size();
		float percentOffline = PERCENT_CM_OFFLINE;
		int numCmsToTurnOffline = (int) ((numCms*percentOffline)/100);
		int interval = (int) (numCms/numCmsToTurnOffline);
		for (int i=0; i<numCms; i++) {
			if (i!=0 && (i%interval==0)) {
				CableModem cm = (CableModem) cmList.get(i);
				turnCmOffline(cm);
			}
		}
	}
	
	protected void turnCmOffline(CableModem cm) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = m_conn.getConnection().createStatement();
			String qryStr = "update cable_modem set docsis_state=3 where cm_res_id=" + cm.getCmResId();
			stmt.executeUpdate(qryStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected LinkedList getCmList() {
		LinkedList ret = new LinkedList();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = m_conn.getConnection().createStatement();
			String qryStr = "SELECT cm.* FROM cable_modem cm left outer join emta mta using (cm_res_id) where mta.cm_res_id IS NULL";
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);

			if (rs.next()) {
				CableModem cm = new CableModem(mrs);
				ret.add(cm);
			}
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}
	
	protected void makeMTAsUnavailable(LinkedList mtaList) {
		float numMtas = mtaList.size();
		float percentOffline = PERCENT_MTA_UNAVAILABLE;
		int numCmsToMakeUnavailable = (int) ((numMtas*percentOffline)/100);
		int interval = (int) (numMtas/numCmsToMakeUnavailable);
		for (int i=0; i<numMtas; i++) {
			if (i!=0 && (i%interval==0)) {
				Emta mta = (Emta) mtaList.get(i);
				makeMtaUnavailable(mta);
			}
		}
	}

	protected void makeMtaUnavailable(Emta mta) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = m_conn.getConnection().createStatement();
			String qryStr = "update emta set available=0 where emta_res_id=" + mta.getEmtaResId();
			stmt.executeUpdate(qryStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}

	protected LinkedList getMtaList() {
		LinkedList ret = new LinkedList();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = m_conn.getConnection().createStatement();
			String qryStr = "SELECT * FROM emta";
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);

			if (rs.next()) {
				Emta cm = new Emta(mrs);
				ret.add(cm);
			}
		} catch (Exception e) {
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}

}
