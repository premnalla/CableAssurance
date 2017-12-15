/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public abstract class AbstractStatusLog extends AbstractWorker {

	private LinkedList m_objectList = null;
	private DbConnection m_conn = null;

	/**
	 * 
	 */
	protected AbstractStatusLog() {
	}

	abstract protected LinkedList getObjectList();
	abstract protected long getResId(Object o);

	/**
	 * 
	 */
	public void run() {
		
		// step 1
		getConnection();
		getInput();
		
		// create rows in db
		createRows();
		
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
	
	protected boolean getInput()
	{
		boolean ret = true;
		
		m_objectList = getObjectList();
		
		return (ret);
	}

	protected void createRows()
	{
		Calendar cal = new GregorianCalendar();
		// long currentTimeMillis = Calendar.getInstance().getTimeInMillis();
		
		try {
			for (int numRows=0; numRows<MAX_ROWS; numRows++) {
				long startTime = cal.getTimeInMillis();
				System.out.println("start time: " + startTime);
				long nextStartTime = (long) (startTime + (POLL_INTERVAL * 60 * 1000));
				System.out.println("next time: " + nextStartTime);
				for (int i=0; i<m_objectList.size(); i++) {
					Object o = m_objectList.get(i);
					long resId = getResId(o);
					createRow(resId);
				}
				long endTime = cal.getTimeInMillis();
				System.out.println("end time: " + endTime);
				if (endTime < nextStartTime) {
					long sleepTime = nextStartTime - endTime;
					System.out.println("sleep time: " + sleepTime);
					Thread.sleep(sleepTime);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected abstract String getCreateSql(long resId);
	
	protected void createRow(long resId)
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			
			String qryStr = getCreateSql(resId);
			
			System.out.println(qryStr);
			
			boolean ret = stmt.execute(qryStr);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
	}

	protected LinkedList getEnterpriseCmList(){
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = GlobalQueries.QRY_CM_IN_ENTERPRISE;
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				CableModem c = new CableModem(mrs);
				ret.add(c);
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
	
	protected LinkedList getEnterpriseMtaList(){
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = GlobalQueries.QRY_MTA_IN_ENTERPRISE;
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Emta c = new Emta(mrs);
				ret.add(c);
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
