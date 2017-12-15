/**
 * 
 */
package com.palmyrasyscorp.www.webservices.helper;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.Alarm.*;
import com.palmyrasys.www.webservices.CableAssurance.*;

/**
 * @author Prem
 * 
 */
public class AlarmHelper extends AbstractServicesHelper {

	private static Log log = LogFactory.getLog(AlarmHelper.class);

	/**
	 * 
	 */
	private AlarmSOAPBindingStub m_binding = null;

	/**
	 * 
	 * 
	 */
	public AlarmHelper() {
		try {
			m_binding = (AlarmSOAPBindingStub) new AlarmServiceLocator()
					.getAlarmEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	/**
	 * 
	 * @param host
	 */
	public AlarmHelper(String host) {
		try {
			AlarmServiceLocator loc = new AlarmServiceLocator();
			loc.setHost(host);
			m_binding = (AlarmSOAPBindingStub) loc.getAlarmEP();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			throw new junit.framework.AssertionFailedError(
					"JAX-RPC ServiceException caught: " + jre);
		}
		// assertNotNull("binding is null", m_binding);

		// Time out after a minute
		m_binding.setTimeout(60000);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param alarmId
	 * @return
	 */
	public AlarmHistoryT[] getAlarmHistory(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) {
		AlarmHistoryT[] ret = null;
		
		try {
			ret = m_binding.getAlarmHistory(topologyKey, alarmId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param batch
	 * @return
	 */
	public CurrentAlarmsRespT getCurrentAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState) {
		CurrentAlarmsRespT ret = null;

		try {
			ret = m_binding.getCurrentAlarms(fromTime, toTime, batch,
					queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param tK
	 * @param alarmId
	 * @return
	 */
	public CurrentAlarmT getCurrentAlarm(TopoHierarchyKeyT tK,
			BigInteger alarmId) {
		CurrentAlarmT ret = null;

		try {
			ret = m_binding.getCurrentAlarm(tK, alarmId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

//	/**
//	 * 
//	 * @param tK
//	 * @param alarmId
//	 * @return
//	 */
//	public CurrentAlarmDetailsT getCurrentAlarmDetails(TopoHierarchyKeyT tK,
//			BigInteger alarmId) {
//		CurrentAlarmDetailsT ret = null;
//
//		try {
//			ret = m_binding.getCurrentAlarmDetails(tK, alarmId);
//		} catch (Exception e) {
//			log.error(null, e);
//		}
//
//		return (ret);
//	}

	/**
	 * 
	 * @param topologyKey
	 * @param alarmType
	 * @param alarmSubType
	 * @param batch
	 * @return
	 */
	public CurrentAlarmsRespT getCurrentAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		CurrentAlarmsRespT ret = null;

		try {
			ret = m_binding.getCurrentAlarmsByType(alarmType, alarmSubType,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param fromTime
	 * @param toTime
	 * @param batch
	 * @return
	 */
	public HistoricalAlarmsRespT getHistoricalAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch, QueryStateT[] queryState) {
		HistoricalAlarmsRespT ret = null;

		try {
			ret = m_binding.getHistoricalAlarms(fromTime, toTime, batch,
					queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topologyKey
	 * @param alarmId
	 * @return
	 */
	public HistoricalAlarmT getHistoricalAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) {
		HistoricalAlarmT ret = null;

		try {
			ret = m_binding.getHistoricalAlarm(topologyKey, alarmId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

//	/**
//	 * 
//	 * @param topologyKey
//	 * @param alarmId
//	 * @return
//	 */
//	public HistoricalAlarmDetailsT getHistoricalAlarmDetails(
//			TopoHierarchyKeyT topologyKey, BigInteger alarmId) {
//		HistoricalAlarmDetailsT ret = null;
//
//		try {
//			ret = m_binding.getHistoricalAlarmDetails(topologyKey, alarmId);
//		} catch (Exception e) {
//			log.error(null, e);
//		}
//
//		return (ret);
//	}

	/**
	 * 
	 * @param topologyKey
	 * @param alarmType
	 * @param alarmSubType
	 * @param batch
	 * @return
	 */
	public HistoricalAlarmsRespT getHistoricalAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch, QueryStateT[] queryState) {
		HistoricalAlarmsRespT ret = null;

		try {
			ret = m_binding.getHistoricalAlarmsByType(alarmType, alarmSubType,
					fromTime, toTime, batch, queryState);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param alarms
	 * @return
	 */
	public short clearAlarms(AlarmIdT[] alarms) {
		short ret = -1;

		try {
			ret = m_binding.clearAlarms(alarms);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topoKey
	 * @param resourceId
	 * @return
	 */
	public CurrentAlarmsRespT getCurrentAlarmsForResource(
			TopoHierarchyKeyT topoKey, BigInteger resourceId) {
		CurrentAlarmsRespT ret = null;

		try {
			ret = m_binding.getCurrentAlarmsForResource(topoKey, resourceId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

	/**
	 * 
	 * @param topoKey
	 * @param resourceId
	 * @return
	 */
	public HistoricalAlarmsRespT getHistoricalAlarmsForResource(
			TopoHierarchyKeyT topoKey, BigInteger resourceId) {
		HistoricalAlarmsRespT ret = null;

		try {
			ret = m_binding.getHistoricalAlarmsForResource(topoKey, resourceId);
		} catch (Exception e) {
			log.error(null, e);
		}

		return (ret);
	}

}
