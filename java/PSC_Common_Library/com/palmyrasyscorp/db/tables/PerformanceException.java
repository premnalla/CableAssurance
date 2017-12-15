/**
 * OBSOLETED FOR NOW?
 */
package com.palmyrasyscorp.db.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.palmyrasyscorp.common.base.*;
import com.palmyrasyscorp.db.common.*;

/**
 * @author Prem
 *
 */
public class PerformanceException extends AbstractPerformanceException {

	private static Log log = LogFactory.getLog(PerformanceException.class);
	
	private BigInteger m_id;

	private Long m_resource_res_id;

	// private short m_isCleared;
	private Integer m_exception_type;
	private String m_exception_type_str;

	private Timestamp m_detection_time;

	private String m_exception_description;

	public BigInteger getId() {
		return m_id;
	}

	public Long getResId() {
		return m_resource_res_id;
	}

	public Integer getExceptionType() {
		return m_exception_type;
	}

	public String getExceptionTypeStr() {
		return m_exception_type_str;
	}

	public String getDetectionTime() {
		return m_detection_time.toLocaleString();
	}

	public String getAlarmDescription() {
		return m_exception_description;
	}

	protected PerformanceException() {

	}

	public PerformanceException(ResultSet rs) {
		int i = 1;
		try {
			m_id = (BigInteger) rs.getObject(i++);
			m_resource_res_id = (Long) rs.getObject(i++);
			m_exception_type = (Integer) rs.getObject(i++);
			m_detection_time = (Timestamp) rs.getObject(i++);

			getAndSetAdditionalData();

		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	protected void getAndSetAdditionalData() {
		Connection conn = DbConnectionPool.getInstance().getConnection()
		.getConnection();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String qryStr = "SELECT description FROM exception_types WHERE exception_type=" + m_exception_type;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				int i = 1;
				m_exception_type_str = rs.getString(i++);
			}
			DbUtils.ReleaseResultSet(rs);
			
			qryStr = "SELECT description FROM exception_secondary WHERE exception_id=" + m_id;
			rs = stmt.executeQuery(qryStr);
			
			if (rs.next()) {
				int i = 1;
				m_exception_description = rs.getString(i++);
			}
			// DbUtils.ReleaseResultSet(rs);
		
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}

}
