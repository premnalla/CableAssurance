/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.sql.*;
import java.util.*;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public abstract class AbstractCmCountLog extends AbstractWorker {
	
	private LinkedList m_objectList = null;
	private DbConnection m_conn = null;
	private String m_tblName = null;
	private String m_resIdColName = null;
	private String m_totalColName = null;
	private String m_onlineColName = null;
	private int m_totalCount = 0;
	private int m_onlineCount = 0;
	
	protected AbstractCmCountLog() {}
	
	abstract protected String getTableName();
	abstract protected LinkedList getObjectList();
	abstract protected long getResId(Object o);
	abstract protected String getResIdColumnName();
	abstract protected String getTotalColumnName();
	abstract protected String getOnlineColumnName();
	abstract protected int getTotalCount();
	abstract protected int getOnlineCount();

	public void run()
	{
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
		
		m_tblName = getTableName();
		m_objectList = getObjectList();
		m_resIdColName = getResIdColumnName();
		m_totalColName = getTotalColumnName();
		m_onlineColName = getOnlineColumnName();
		m_totalCount = getTotalCount();
		m_onlineCount = getOnlineCount();
		
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
	
	protected void createRow(long resId)
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			
			String qryStr = "insert into " + m_tblName 
			+ "(" + m_resIdColName + "," + m_totalColName + "," + m_onlineColName + ") values ("
			+ resId + "," + m_totalCount + "," + m_onlineCount + ");"	;
			
			System.out.println(qryStr);
			
			stmt.execute(qryStr);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
	}
	
	protected LinkedList getCmtsList()
	{
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = GlobalQueries.QRY_CMTS_IN_ENTERPRISE;
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Cmts c = new Cmts(mrs);
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

	protected LinkedList getHfcList()
	{
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = GlobalQueries.QRY_HFC_IN_ENTERPRISE;
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				HfcPlant c = new HfcPlant(mrs);
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

	protected LinkedList getChannelList()
	{
		LinkedList ret = new LinkedList();
		
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = GlobalQueries.QRY_CHANNEL_IN_ENTERPRISE;
			rs = stmt.executeQuery(qryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Channel c = new Channel(mrs);
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
