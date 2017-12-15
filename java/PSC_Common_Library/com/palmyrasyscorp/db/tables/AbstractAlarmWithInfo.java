/**
 * 
 */
package com.palmyrasyscorp.db.tables;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.db.common.CADbConnectionPool;
import com.palmyrasyscorp.db.common.ProxyDbConnection;
import com.palmyrasyscorp.db.common.ProxyDbResultSet;
import com.palmyrasyscorp.db.common.ProxyDbStatement;

/**
 * @author Prem
 * 
 */
public abstract class AbstractAlarmWithInfo extends AbstractAlarm {

	private static Log log = LogFactory.getLog(AbstractAlarmWithInfo.class);

	protected BigInteger m_id;

	public BigInteger getId() {
		return m_id;
	}

	protected BigInteger m_root_alarm_id;
	public BigInteger getRootAlarmId() {
		return m_root_alarm_id;
	}

	public BigInteger getAlarmId() {
		return m_root_alarm_id;
	}

	protected Long m_resource_res_id;

	public Long getResourceId() {
		return m_resource_res_id;
	}

	protected Long m_alarm_detection_time;

	public Long getAlarmDetectionTime() {
		return m_alarm_detection_time;
	}

	protected Long m_alarm_detection_time_usec;

	public Long getAlarmDetectionTimeUSec() {
		return m_alarm_detection_time_usec;
	}

	protected Integer m_alarm_type;

	public Integer getAlarmType() {
		return m_alarm_type;
	}

	protected Integer m_alarm_sub_type;

	public Integer getAlarmSubType() {
		return m_alarm_sub_type;
	};

	protected Integer m_soak_duration;

	public Integer getSoakDuration() {
		return m_soak_duration;
	}

	protected Integer m_alarm_state;

	public Integer getAlarmState() {
		return m_alarm_state;
	}
	public void setAlarmState(Integer s) {
		m_alarm_state = s;
	}

	protected String m_alarm_type_str;

	public String getAlarmTypeStr() {
		return m_alarm_type_str;
	}

	protected String m_alarm_sub_type_str;

	public String getAlarmSubTypeStr() {
		return m_alarm_sub_type_str;
	}

	protected String m_alarm_state_str;

	public String getAlarmStateStr() {
		return m_alarm_state_str;
	}

	protected String m_alarm_description;

	protected String m_alarm_details;

	public String getAlarmDetails() {
		if (m_alarm_details == null) {
			queryAlarmDetails();
		}
		return (m_alarm_details);
	}

	protected String m_resourceName = GlobalConstants.HTML_UKNOWN_STR;
	public String getResourceName() {
		return m_resourceName;
	}
	
	/*
	 * The following are secondary fields that are obtained on an as-needed basis.
	 */
	
//	protected long m_hfcResId;
//	protected String m_hfcName = GlobalConstants.HTML_UKNOWN_STR;
//	protected long m_upstreamResId;
//	protected String m_upstreamName = GlobalConstants.HTML_UKNOWN_STR;
//	protected long m_cmtsResId;
//	protected String m_cmtsName = GlobalConstants.HTML_UKNOWN_STR;
//	protected String m_cmsResId;
//	protected String m_cmsName = GlobalConstants.HTML_UKNOWN_STR;
//	
//	public long getHfcResId() {
//		return m_hfcResId;
//	}
//	public String getHfcName() {
//		return m_hfcName;
//	}
//	public long getUpstreamResId() {
//		return m_upstreamResId;
//	}
//	public String getUpstreamName() {
//		return m_upstreamName;
//	}
//	public long getCmtsResId() {
//		return m_cmtsResId;
//	}
//	public String getCmtsName() {
//		return m_cmtsName;
//	}
//	public long getCmsResId() {
//		return m_cmtsResId;
//	}
//	public String getCmsName() {
//		return m_cmsName;
//	}
	
	/**
	 * 
	 * 
	 */
	protected AbstractAlarmWithInfo(BigInteger rootAI) {
		m_root_alarm_id = rootAI;
	}

	/**
	 * 
	 *
	 */
	protected AbstractAlarmWithInfo() {
		
	}
	
	private void queryAlarmDetails() {
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
			String qryStr = GlobalQueries.QRY_ALARM_DETAILS + m_root_alarm_id;

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				i++;
				i++; // skip id, root_alarm_id fields
				m_alarm_details = (String) rs.getObject(i++);
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

	/**
	 * 
	 * @param alarmType
	 * @param alarmSubType
	 * @return
	 */
	protected int[] getAlarmType(String alarmType, String alarmSubType) {
		int[] ret = null;
		
		ArrayList l = new ArrayList();
		
//		int[] ret = new int[2];
//		ret[0] = 0;
//		ret[1] = 0;

		if (alarmType == null) {
			return (ret);
		}
		
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
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_ALARM_TYPES);
			qryStr.append("where type_desc='").append(alarmType).append("'");
			if (alarmSubType != null) {
				qryStr.append(" and subtyp_desc='").append(alarmSubType).append("'");
			}

			/*
			 * Create statement
			 */
			stmt = c.createStatement();

			/*
			 * execute query
			 */
			rs = stmt.executeQuery(qryStr.toString());

			if (rs.next()) {
				int i = 1;
				// ret[0] = rs.getInt(i++);
				// ret[1] = rs.getInt(i++);
				Integer I = (Integer) rs.getObject(i++);
				l.add(I);
				if (alarmSubType != null) {
					I = (Integer) rs.getObject(i++);
					l.add(I);
				}
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
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

		if (l.size() > 0) {
			ret = new int[l.size()];
			for (int i=0; i<l.size(); i++) {
				ret[i] = ((Integer)l.get(i)).intValue();
			}
			l.clear();
		}

		return (ret);
	}
	
	/**
	 * 
	 * @param rs
	 * @param startId
	 */
	protected void seekToBeginningOfBatch(ProxyDbResultSet rs, long startId) {
		try {
			if (startId != 0) {
				while(rs.next()) {
					if (rs.getLong(1) <= startId) {
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean setAdditionalInfo() {
		boolean ret = true;

		switch (m_alarm_type) {
		case AlarmTypes.ALARM_TYPE_HFC:
			HfcPlant h = new HfcPlant(m_resource_res_id);
			m_resourceName = h.getName();
			break;
		case AlarmTypes.ALARM_TYPE_MTA:
			Emta e = new Emta(m_resource_res_id);
			m_resourceName = e.getMacAddress();
			break;
		case AlarmTypes.ALARM_TYPE_CMTS:
			Cmts c = new Cmts(m_resource_res_id);
			m_resourceName = c.getName();
			break;
		case AlarmTypes.ALARM_TYPE_CMS:
			break;
		default:
			break;
		}

		return (ret);
	}

}
