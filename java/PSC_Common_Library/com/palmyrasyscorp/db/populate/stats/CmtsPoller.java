/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.math.BigInteger;

import com.palmyrasyscorp.db.common.*;
import com.palmyrasyscorp.db.populate.netobj.ProgramProperties;
import com.palmyrasyscorp.db.tables.*;

/**
 * @author Prem
 *
 */
public class CmtsPoller extends AbstractWorker {
	
	public static final float PERCENT_CM_OFFLINE = 2;
	public static final float PERCENT_CM_EXCEPTION = 3;
	public static final float PERCENT_MTA_UNAVAILABLE = 10;
	public static final float PERCENT_MTA_ALARM = 10;
	
	public static final float PERCENT_CM_OFFLINE_HFC_OUTAGE = 10;
	public static final float PERCENT_MTA_UNAVAILABLE_HFC_OUTAGE = 20;
	
	private Random m_rand = new Random();
	private DbConnection m_conn = null;
	private Cmts m_cmts = null;
	private LinkedList m_onlineCmList = new LinkedList();
	private LinkedList m_offlineCmList = new LinkedList();
	private LinkedList m_availableMtaList = new LinkedList();
	private LinkedList m_unavailableMtaList = new LinkedList();
	private LinkedList m_mtaCmList = new LinkedList();
	private LinkedList m_hfcList = new LinkedList();
	private LinkedList m_channelList = new LinkedList();
	
	private LinkedList m_mtaInFirstHfc = new LinkedList();
	private LinkedList m_cmInSecondHfc = new LinkedList();
	
	/**
	 * 
	 */
	protected CmtsPoller() {
		super();
	}
	
	public CmtsPoller(Cmts cmts){
		super();
		m_cmts = cmts;
	}
	
	public void run() {
		// obtain db connection
		getConnection();
		
		// get initial list of CM and MTA
		populateHfcList();
		populateChannelList();
		populateCmList();
		populateMtaList();
		
		populateMtasInFirstHfc();
		populateCmsInSecondHfc();
		
		final int MIN_CYCLE_TO_GO_DOWN = (int) MAX_ROWS/2;
		final int MAX_CYCLE_TO_GO_DOWN = MAX_ROWS-2;
		
		// start polling
		int cycleToGoDown = 0;
		boolean tryAgain = true;
		while (tryAgain) {
			if (cycleToGoDown >= MIN_CYCLE_TO_GO_DOWN && 
					cycleToGoDown <= MAX_CYCLE_TO_GO_DOWN) {
				tryAgain = false;
			} else {
				cycleToGoDown = m_rand.nextInt(MAX_ROWS);
			}
		}
		
		int mtaCycleToGoDown = 0;
		tryAgain = true;
		while (tryAgain) {
			if (mtaCycleToGoDown >= MIN_CYCLE_TO_GO_DOWN && 
					mtaCycleToGoDown <= MAX_CYCLE_TO_GO_DOWN) {
				tryAgain = false;
			} else {
				mtaCycleToGoDown = m_rand.nextInt(MAX_ROWS);
			}
		}
		
		System.out.println("cycleToGoDown: " + cycleToGoDown);
		System.out.println("mtaCycleToGoDown: " + mtaCycleToGoDown);
		
		Calendar cal = null;
		for (int i=0; i<MAX_ROWS; i++) {
			cal = new GregorianCalendar();
			long startTime = cal.getTimeInMillis();
			// System.out.println("start time: " + startTime);
			long nextStartTime = (long) (startTime + (POLL_INTERVAL * 60 * 1000));
			// System.out.println("next time: " + nextStartTime);
			
			doPoll(i==cycleToGoDown);
			doMtaPoll(i==mtaCycleToGoDown);
			updateCounts();
			
			cal = null;
			cal = new GregorianCalendar();
			long endTime = cal.getTimeInMillis();
			// System.out.println("end time: " + endTime);
			if (endTime < nextStartTime) {
				long sleepTime = nextStartTime - endTime;
				System.out.println("sleep time: " + sleepTime);
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			cal = null;
		}
		
		// close
		releaseConnection();
		
		// finally: inform that we are done
		setDoneWork();
		
	}
	
	protected void doPoll(boolean down) {
		if (down) {
			moveCms();
		}
	}
	
	protected void doMtaPoll(boolean down) {
		if (down) {
			moveMtas();
		}
	}
	
	protected void updateCounts() {
		updateCmStatusLog();
		updateMtaStatusLog();
		
		updateCmtsCountLog();
		
		updateHfcCountLog();
		
		updateChannelCountLog();
	}
	
	protected void moveMtas() {
		float numMtas = m_availableMtaList.size();
		float percentUnavailable = PERCENT_MTA_UNAVAILABLE;
		int numMtasToMakeUnavailable = (int) ((numMtas*percentUnavailable)/100);
		int interval = (int) (numMtas/numMtasToMakeUnavailable);
		
		// add mta to unavailable list
		for (int i=0; i<numMtas; i++) {
			if (i!=0 && (i%interval==0)) {
				Emta mta = (Emta) m_availableMtaList.get(i);
				if (!isMtaInFirstHfc(mta)) {
					m_unavailableMtaList.add(mta);
				}
			}
		}
		
		float numMtasHfcOutage = m_mtaInFirstHfc.size();
		float percentUnavailableHfcOutage = PERCENT_MTA_UNAVAILABLE_HFC_OUTAGE;
		int numMtasToMakeUnavailableHfcOutage = (int) ((numMtasHfcOutage*percentUnavailableHfcOutage)/100);
		for (int i=0; i<numMtasToMakeUnavailableHfcOutage; i++) {
			Emta mta = (Emta) m_mtaInFirstHfc.get(i);
			m_unavailableMtaList.add(mta);
		}
		
		// remove mta from available list
		for (int i=0; i<m_unavailableMtaList.size(); i++) {
			Emta mta = (Emta) m_unavailableMtaList.get(i);
			m_availableMtaList.remove(mta);
			updateMtaStatus(mta, true);
		}
		
		createMtaHfcOutage((int)numMtasHfcOutage, numMtasToMakeUnavailableHfcOutage);		
	}
	
	private void createMtaHfcOutage(int total, int numUnavailable) {
		HfcPlant hfc = (HfcPlant) m_hfcList.get(0);
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			Calendar cal = new GregorianCalendar();
			long sec = cal.getTimeInMillis() / 1000;
			long usec = (cal.getTimeInMillis() - (sec * 1000)) * 1000;
			StringBuffer qryStr2 = new StringBuffer(
				"insert into caalarm.root_alarm(res_id,detection_time,det_time_usec,alarm_type,alarm_sub_type) values(");
			qryStr2.append(hfc.getHfcResId()).append(",").append(sec)
			.append(",").append(usec).append(",").append(1).append(",").append(3)
			.append(")");
			
			System.out.println(qryStr2);
			
			stmt.executeUpdate(qryStr2.toString(), Statement.RETURN_GENERATED_KEYS);
			qryStr2 = null;
			rs = stmt.getGeneratedKeys();
			int i = 1;
			Long alarmId = null;
			if (rs.next()) {
				alarmId = (Long) rs.getObject(i++);
			}
			DbUtils.ReleaseResultSet(rs);
			
			if (alarmId != null) {
				CurrentAlarm ca = new CurrentAlarm(new BigInteger(alarmId.toString()));
				ca.addAlarm(60*20, 1);
			}
			
			processIndividualMtaAlarmsForHfcOutage(hfc, alarmId);
			
			HfcCurrentStatus hfcSt = new HfcCurrentStatus(hfc.getHfcResId());
			long sumGoodTm = hfcSt.getSumGoodStateTime();
			long sumBadTm = hfcSt.getSumBadStateTime();
			int sumStChgs = hfcSt.getSumStateChages();
			sumStChgs++;
			sumGoodTm += (sec - hfcSt.getLastStateChangeTime());
			qryStr2 = new StringBuffer("update caperf.hfc_current_alarm set ");
			qryStr2.append("sum_alarmfree_tm=").append(sumGoodTm)
			.append(",sum_alarm_tm=").append(sumBadTm)
			.append(",last_log_tm=").append(sec)
			.append(",alarm_chg_tm=").append(sec)
			.append(",state_changes=").append(sumStChgs)
			.append(",hfc_alarm=1 where hfc_res_id=").append(hfc.getHfcResId());
			stmt.executeUpdate(qryStr2.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	private void processIndividualMtaAlarmsForHfcOutage(
			HfcPlant hfc, Long alarmId) {
		
		LinkedList mtaList = getListOfMtas(hfc);
		
		// delete individual alarm, service outage, etc...
		for (int i=0; i<mtaList.size(); i++) {
			Emta mta = (Emta) mtaList.get(i);
			deleteIndividualAlarmForMta(mta);
		}
		
		mtaList.clear();
		mtaList=null;
	}
	
	private boolean isAlarmForResource(Long resId) {
		boolean ret = false;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CURR_ALARMS);
			qryStr.append(" where ra.res_id=").append(resId);
			rs = stmt.executeQuery(qryStr.toString());
			
			if (rs.next()) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (ret);
	}
	
	private void deleteIndividualAlarmForMta(Emta mta) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			LinkedList l = getAlarmsForResId(mta.getEmtaResId());
			for (int i=0; i<l.size(); i++) {
				CurrentAlarm a = (CurrentAlarm) l.get(i);
				a.removeAlarm();
			}
			l.clear();
			l=null;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	private LinkedList getAlarmsForResId(Long resId) {
		LinkedList l = new LinkedList();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer queryStr = new StringBuffer(GlobalQueries.QRY_CURR_ALARMS);
			queryStr.append(" where ra.res_id=").append(resId);
			rs = stmt.executeQuery(queryStr.toString());
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				CurrentAlarm a = new CurrentAlarm(mrs);
				l.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}

		return (l);
	}

	private LinkedList getListOfMtas(HfcPlant hfc) {
		LinkedList ret = new LinkedList();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			String queryStr = GlobalQueries.QRY_MTA_IN_HFC + hfc.getHfcResId();
			rs = stmt.executeQuery(queryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Emta mta = new Emta(mrs);
				ret.add(mta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}
	
	protected void updateMtaStatus(Emta mta, boolean down) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;
		long usec = (cal.getTimeInMillis() - (sec * 1000)) * 1000;

		try {
			stmt = m_conn.getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			String qryStr = "update canet.emta set pktcbl_prov_state=3, ping_state=2, available=0, alert_level=5 where emta_res_id=" 
				+ mta.getEmtaResId();
			stmt.executeUpdate(qryStr);
			qryStr = null;

			MtaCurrentAvailability mtaA = new MtaCurrentAvailability(mta.getEmtaResId());
			long availTime = mtaA.getSumGoodStateTime();
			long unavailTime = mtaA.getSumBadStateTime();
			int mtaAvailChanges = mtaA.getSumStateChages();
			mtaAvailChanges++;
			if (down) {
				availTime += (sec - mtaA.getLastLogTime());				
			} else {
				unavailTime += (sec - mtaA.getLastLogTime());
			}
			StringBuffer q = new StringBuffer("update caperf.mta_current_avail set sum_avail_tm=");
			q.append(availTime).append(",last_log_tm=").append(sec)
			.append(",sum_unavail_tm=").append(unavailTime)
			.append(",last_avail_chg_tm=").append(sec)
			.append(",state_changes=").append(mtaAvailChanges)
			.append(",available=0 where mta_res_id=").append(mtaA.getResId());
			stmt.executeUpdate(q.toString());
			
			String qryStr5 = "update canet.cable_modem set docsis_state=3 where cm_res_id=" 
				+ mta.getCmResId();
			stmt.executeUpdate(qryStr5);
			qryStr5 = null;

			CmCurrentStatus cmSt = new CmCurrentStatus(mta.getCmResId());
			long sumOnlineTm = cmSt.getSumGoodStateTime();
			long sumOfflineTm = cmSt.getSumBadStateTime();
			int sumStateChanges = cmSt.getSumStateChages();
			sumStateChanges++;
			if (down) {
				sumOnlineTm += (sec - cmSt.getLastLogTime());
			} else {
				sumOfflineTm += (sec - cmSt.getLastLogTime());				
			}
			q = new StringBuffer("update caperf.cm_current_status set sum_online_tm=");
			q.append(sumOnlineTm)
			.append(",sum_offline_tm=").append(sumOfflineTm)
			.append(",last_log_tm=").append(sec)
			.append(",state_changes=").append(sumStateChanges)
			.append(",last_online_chg_tm=").append(sec)
			.append(",online_state=0 where cm_res_id=").append(mta.getCmResId());
			stmt.executeUpdate(q.toString());
			
			int snr = (!down?305:225);
			CmCurrentPerformance cmPerf = new CmCurrentPerformance(mta.getCmResId());
			long sumTcaFreeTm = cmPerf.getSumTcaFreeTime().longValue();
			long sumTcaTm = cmPerf.getSumTcaTime().longValue();
			int stateChanges = cmPerf.getTcaStateChanges();
			stateChanges++;
			if (!down) {
				sumTcaTm += sec - cmPerf.getLastLogTime().longValue();
			} else {
				sumTcaFreeTm += sec - cmPerf.getLastLogTime().longValue();				
			}
			q = new StringBuffer("update caperf.cm_current_perf set last_log_tm=");
			q.append(sec).append(",downstream_snr=").append(snr)
			.append(",downstream_power=-150,upstream_power=-150")
			.append(",sum_tcafree_tm=").append(sumTcaFreeTm)
			.append(",sum_tca_tm=").append(sumTcaTm)
			.append(",state_changes=").append(stateChanges)
			.append(",tca_change_tm=").append(sec)
			.append(" where cm_res_id=")
			.append(mta.getCmResId());
			stmt.executeUpdate(q.toString());

			StringBuffer qryStr2 = new StringBuffer(
				"insert into caalarm.root_alarm(res_id,detection_time,det_time_usec,alarm_type,alarm_sub_type) values(");
			qryStr2.append(mta.getEmtaResId()).append(",").append(sec)
			.append(",").append(usec).append(",").append(2).append(",").append(1)
			.append(")");
			
			System.out.println(qryStr2);
			
			stmt.executeUpdate(qryStr2.toString(), Statement.RETURN_GENERATED_KEYS);
			qryStr2 = null;
			rs = stmt.getGeneratedKeys();
			int i = 1;
			Long alarmId = null;
			if (rs.next()) {
				alarmId = (Long) rs.getObject(i++);
			}
			DbUtils.ReleaseResultSet(rs);
			
			if (alarmId != null) {
				CurrentAlarm ca = new CurrentAlarm(new BigInteger(alarmId.toString()));
				ca.addAlarm(60*20, 1);
			}
			
			alarmId = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void updateMtaStatusLog() {
		for (int i=0; i<m_availableMtaList.size(); i++) {
			Emta mta = (Emta) m_availableMtaList.get(i);
			addMtaStatusLog(mta, true);
		}
		for (int i=0; i<m_unavailableMtaList.size(); i++) {
			Emta mta = (Emta) m_unavailableMtaList.get(i);
			addMtaStatusLog(mta, false);
		}
	}
	
	protected void addMtaStatusLog(Emta mta, boolean up) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			
			Calendar cal = new GregorianCalendar();
			long sec = cal.getTimeInMillis() / 1000;
			
			String qryStr = "insert into caperf.mta_prov_status_log(mta_res_id,tm_sec,prov_state) values(" 
				+ mta.getEmtaResId() + "," + sec + "," + (up?1:3) + ")";

			String qryStr2 = "insert into caperf.mta_ping_status_log(mta_res_id,tm_sec,ping_state) values(" 
				+ mta.getEmtaResId() + "," + sec + "," + (up?1:2) + ")";

			int snr = (up?305:225);
			// performance attributes
			String qryStr3 = "insert into caperf.cm_perf_log(cm_res_id,tm_sec,downstream_snr,downstream_power,upstream_power) values(" 
				+ mta.getCmResId() + "," + sec + "," + snr + ",-150,-150)";
			
			String qryStr4 = "insert into caperf.cm_status_log(cm_res_id,tm_sec,docsis_state) values(" 
				+ mta.getCmResId() + "," + sec + "," + (up?6:3) + ")";

			String qryStr5 = "insert into caperf.mta_availability_log(mta_res_id,tm_sec,availability) values(" 
				+ mta.getEmtaResId() + "," + sec + "," + (up?1:0) + ")";
			
//			CmCurrentPerformance cmPerf = new CmCurrentPerformance(mta.getCmResId());
//			long sumTcaFreeTm = cmPerf.getSumTcaFreeTime().longValue();
//			long sumTcaTm = cmPerf.getSumTcaTime().longValue();
//			int stateChanges = cmPerf.getTcaStateChanges();
//			stateChanges++;
//			if (up) {
//				sumTcaTm += sec - cmPerf.getLastLogTime().longValue();
//			} else {
//				sumTcaFreeTm += sec - cmPerf.getLastLogTime().longValue();				
//			}
//			StringBuffer q = new StringBuffer("update caperf.cm_current_perf set last_log_tm=");
//			q.append(sec).append(",downstream_snr=").append(snr)
//			.append(",downstream_power=-150,upstream_power=-150")
//			.append(",sum_tcafree_tm=").append(sumTcaFreeTm)
//			.append(",sum_tca_tm=").append(sumTcaTm)
//			.append(",state_changes=").append(stateChanges)
//			.append(",tca_change_tm").append(sec)
//			.append(" where cm_res_id=")
//			.append(mta.getCmResId());
			
			stmt.executeUpdate(qryStr);
			qryStr = null;
			stmt.executeUpdate(qryStr2);
			qryStr2 = null;
			stmt.executeUpdate(qryStr5);
			qryStr5 = null;
			stmt.executeUpdate(qryStr3);
			qryStr3 = null;
			stmt.executeUpdate(qryStr4);
			qryStr4 = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void updateCmStatusLog() {
		for (int i=0; i<m_onlineCmList.size(); i++) {
			CableModem cm = (CableModem) m_onlineCmList.get(i);
			addCmStatusLog(cm, true);
		}
		for (int i=0; i<m_offlineCmList.size(); i++) {
			CableModem cm = (CableModem) m_offlineCmList.get(i);
			addCmStatusLog(cm, false);
		}
	}
	
	protected void addCmStatusLog(CableModem cm, boolean up) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();
			String qryStr = 
				"insert into caperf.cm_status_log(cm_res_id,tm_sec,docsis_state) values(" 
				+ cm.getCmResId() + "," + sec + "," + (up?6:3) + ")";
			
			// performance attributes
			int snr = (up?305:225);
			String qryStr2 = 
				"insert into caperf.cm_perf_log(cm_res_id,tm_sec,downstream_snr,downstream_power,upstream_power) values(" 
				+ cm.getCmResId() + "," + sec + "," + snr + ",-15,425)";
			
			stmt.executeUpdate(qryStr);
			stmt.executeUpdate(qryStr2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void updateCmtsCountLog() {
		Statement stmt = null;
		ResultSet rs = null;
		
		int cmTotal = m_onlineCmList.size()+m_offlineCmList.size();
		int mtaTotal = m_availableMtaList.size() + m_unavailableMtaList.size();
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			
			String qryStr = 
				"insert into caperf.cmts_counts_log(cmts_res_id,tm_sec,cm_total,cm_online,mta_total,mta_available) values(" 
				+ m_cmts.getCmtsResId() + "," + sec + "," + cmTotal + "," + m_onlineCmList.size() + "," 
				+ mtaTotal + "," +  m_availableMtaList.size() + ")";
			
			stmt.executeUpdate(qryStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		updateCmtsCurrentCounts();
	}
	
	protected void updateCmtsCurrentCounts() {
		Statement stmt = null;
		ResultSet rs = null;
		
		int cmTotal = m_onlineCmList.size()+m_offlineCmList.size();
		int mtaTotal = m_availableMtaList.size() + m_unavailableMtaList.size();
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();

			boolean found = false;
			
			StringBuffer getStr = new StringBuffer("select * from caperf.cmts_current_counts where cmts_res_id=");
			getStr.append(m_cmts.getCmtsResId());
			rs = stmt.executeQuery(getStr.toString());			
			if (rs.next()) {
				found = true;
			}
			DbUtils.ReleaseResultSet(rs);

			String qryStr;
			
			if (!found) {
				 qryStr = 
					"insert into caperf.cmts_current_counts(cmts_res_id,last_log_tm,cm_total,cm_online,mta_total,mta_available) values(" 
					+ m_cmts.getCmtsResId() + "," + sec + "," + cmTotal + "," + m_onlineCmList.size() + "," 
					+ mtaTotal + "," +  m_availableMtaList.size() + ")";
				
			} else {
				qryStr = 
					"update caperf.cmts_current_counts set cm_total=" + cmTotal
					+ ",cm_online=" + m_onlineCmList.size() + ",mta_total=" + mtaTotal
					+ ",mta_available=" + m_availableMtaList.size() 
					+ ",last_log_tm=" + sec
					+ " where cmts_res_id="	+ m_cmts.getCmtsResId();
			}
			
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
	
	protected void updateHfcCountLog() {
		for (int i=0; i<m_hfcList.size(); i++) {
			HfcPlant hfc = (HfcPlant) m_hfcList.get(i);
			int total = 0;
			int online = 0;
			for (int j=0; j<m_onlineCmList.size(); j++) {
				CableModem cm = (CableModem) m_onlineCmList.get(j);
				if (hfc.getHfcResId().longValue()==cm.getHfcResId().longValue()) {
					total++;
					online++;
					// System.out.println("******* Found CM for HFC *******");
				}
			}
			for (int j=0; j<m_offlineCmList.size(); j++) {
				CableModem cm = (CableModem) m_offlineCmList.get(j);
				if (hfc.getHfcResId().longValue()==cm.getHfcResId().longValue()) {
					total++;
					// System.out.println("******* Found CM for HFC *******");
				}
			}
			
			int mtaTotal = 0;
			int mtaAvailable = 0;
			for (int j=0; j<m_availableMtaList.size(); j++) {
				Emta mta = (Emta) m_availableMtaList.get(j);
				CableModem cm = new CableModem(mta.getCmResId().longValue());
				if (hfc.getHfcResId().longValue()==cm.getHfcResId().longValue()) {
					mtaTotal++;
					mtaAvailable++;
				}
				cm = null;
			}
			for (int j=0; j<m_unavailableMtaList.size(); j++) {
				Emta mta = (Emta) m_unavailableMtaList.get(j);
				CableModem cm = new CableModem(mta.getCmResId().longValue());
				if (hfc.getHfcResId().longValue()==cm.getHfcResId().longValue()) {
					mtaTotal++;
				}
				cm = null;
			}
			
			addHfcCountLog(hfc, total, online, mtaTotal, mtaAvailable);			
		}
	}
	
	protected void addHfcCountLog(HfcPlant hfc, int cmTotal, int cmOnline, 
			int mtaTotal, int mtaAvailable) {
		Statement stmt = null;
		ResultSet rs = null;

		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();
			
			String qryStr = 
				"insert into caperf.hfc_counts_log(hfc_res_id,tm_sec,cm_total,cm_online,mta_total,mta_available) values(" 
				+ hfc.getHfcResId() + "," + sec + "," + cmTotal + "," + cmOnline + "," 
				+ mtaTotal + "," + mtaAvailable + ")";
			
			stmt.executeUpdate(qryStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		updateHfcCurrentCounts(hfc, cmTotal, cmOnline, mtaTotal, mtaAvailable);
	}
	
	protected void updateHfcCurrentCounts(HfcPlant hfc, int cmTotal, int cmOnline, 
			int mtaTotal, int mtaAvailable) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();

			boolean found = false;
			
			StringBuffer getStr = new StringBuffer("select * from caperf.hfc_current_counts where hfc_res_id=");
			getStr.append(hfc.getHfcResId());
			rs = stmt.executeQuery(getStr.toString());			
			if (rs.next()) {
				found = true;
			}
			DbUtils.ReleaseResultSet(rs);

			String qryStr;
			
			if (!found) {
				qryStr = 
					"insert into caperf.hfc_current_counts(hfc_res_id,last_log_tm,cm_total,cm_online,mta_total,mta_available) values(" 
					+ hfc.getHfcResId() + "," + sec + "," + cmTotal + "," + cmOnline + "," 
					+ mtaTotal + "," +  mtaAvailable + ")";
				
			} else {
				qryStr = 
					"update caperf.hfc_current_counts set cm_total=" + cmTotal
					+ ",cm_online=" + cmOnline + ",mta_total=" + mtaTotal
					+ ",mta_available=" + mtaAvailable
					+ ",last_log_tm=" + sec
					+ " where hfc_res_id=" + hfc.getHfcResId();
			}
			
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
	
	protected void updateChannelCountLog() {
		for (int i=0; i<m_channelList.size(); i++) {
			Channel channel = (Channel) m_channelList.get(i);
			int total = 0;
			int online = 0;
			for (int j=0; j<m_onlineCmList.size(); j++) {
				CableModem cm = (CableModem) m_onlineCmList.get(j);
				if (channel.getChannelResId().longValue()==cm.getUpChannelResId().longValue()) {
					total++;
					online++;
					// System.out.println("******* Found CM for Channel *******");
				}
			}
			for (int j=0; j<m_offlineCmList.size(); j++) {
				CableModem cm = (CableModem) m_offlineCmList.get(j);
				if (channel.getChannelResId().longValue()==cm.getUpChannelResId().longValue()) {
					total++;
					// System.out.println("******* Found CM for Channel *******");
				}
			}

			int mtaTotal = 0;
			int mtaAvailable = 0;
			for (int j=0; j<m_availableMtaList.size(); j++) {
				Emta mta = (Emta) m_availableMtaList.get(j);
				CableModem cm = new CableModem(mta.getCmResId().longValue());
				if (channel.getChannelResId().longValue()==cm.getUpChannelResId().longValue()) {
					mtaTotal++;
					mtaAvailable++;
					// System.out.println("******* Found MTA for Channel *******");
				}
				cm = null;
			}
			for (int j=0; j<m_unavailableMtaList.size(); j++) {
				Emta mta = (Emta) m_unavailableMtaList.get(j);
				CableModem cm = new CableModem(mta.getCmResId().longValue());
				if (channel.getChannelResId().longValue()==cm.getUpChannelResId().longValue()) {
					mtaTotal++;
					// System.out.println("******* Found MTA for Channel *******");
				}
				cm = null;
			}

			addChannelCountLog(channel, total, online,mtaTotal, mtaAvailable);			
		}
		
	}
	
	protected void addChannelCountLog(Channel channel, int cmTotal, int cmOnline, 
			int mtaTotal, int mtaAvailable) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();
			
			String qryStr = 
				"insert into caperf.channel_counts_log(channel_res_id,tm_sec,cm_total,cm_online,mta_total,mta_available) values(" 
				+ channel.getChannelResId() + "," + sec + "," + cmTotal + "," + cmOnline + "," 
				+ mtaTotal + "," + mtaAvailable + ")";
			
			stmt.executeUpdate(qryStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}	
		
		updateChannelCurrentCounts(channel, cmTotal, cmOnline, mtaTotal, mtaAvailable);
	}
	
	protected void updateChannelCurrentCounts(Channel channel, int cmTotal, int cmOnline, 
			int mtaTotal, int mtaAvailable) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement();

			boolean found = false;
			
			StringBuffer getStr = new StringBuffer("select * from caperf.channel_current_counts where channel_res_id=");
			getStr.append(channel.getChannelResId());
			rs = stmt.executeQuery(getStr.toString());			
			if (rs.next()) {
				found = true;
			}
			DbUtils.ReleaseResultSet(rs);

			String qryStr;
			
			if (!found) {
				qryStr = 
					"insert into caperf.channel_current_counts(channel_res_id,last_log_tm,cm_total,cm_online,mta_total,mta_available) values(" 
					+ channel.getChannelResId() + "," + sec + "," + cmTotal + "," + cmOnline + "," 
					+ mtaTotal + "," +  mtaAvailable + ")";
				
			} else {
				qryStr = 
					"update caperf.channel_current_counts set cm_total=" + cmTotal
					+ ",cm_online=" + cmOnline + ",mta_total=" + mtaTotal
					+ ",mta_available=" + mtaAvailable
					+ ",last_log_tm=" + sec
					+ " where channel_res_id="	+ channel.getChannelResId();
			}
			
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
	
	protected void moveCms() {
		float numCms = m_onlineCmList.size();
		float percentOffline = PERCENT_CM_OFFLINE;
		int numCmsToTurnOffline = (int) ((numCms*percentOffline)/100);
		int interval = (int) (numCms/numCmsToTurnOffline);
		
		// add cm to offline list
		for (int i=0; i<numCms; i++) {
			if (i!=0 && (i%interval==0)) {
				CableModem cm = (CableModem) m_onlineCmList.get(i);
				if (!isCmInSecondHfc(cm)) {
					m_offlineCmList.add(cm);
				}
			}
		}
		
		float numCmsHfcOutage = m_cmInSecondHfc.size();
		float percentOfflineHfcOutage = PERCENT_CM_OFFLINE_HFC_OUTAGE;
		int numCmsToTurnOfflineHfcOutage = (int) ((numCmsHfcOutage*percentOfflineHfcOutage)/100);
		for (int i=0; i<numCmsToTurnOfflineHfcOutage; i++) {
			CableModem cm = (CableModem) m_cmInSecondHfc.get(i);
			m_offlineCmList.add(cm);
		}
		
		// remove CM from online list
		for (int i=0; i<m_offlineCmList.size(); i++) {
			CableModem cm = (CableModem) m_offlineCmList.get(i);
			m_onlineCmList.remove(cm);
			updateCmStatus(cm, true);
		}
		
		createCmHfcOutage((int) numCmsHfcOutage, numCmsToTurnOfflineHfcOutage);
	}
	
	private void createCmHfcOutage(int total, int numOffline) {
		HfcPlant hfc = (HfcPlant) m_hfcList.get(1);
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			StringBuffer s = new StringBuffer();
			s.append(numOffline).append(" out of ").append(total)
			.append(" CMs became offline <br/>between successive polling cycles");
			
			Calendar cal = new GregorianCalendar();
			long sec = cal.getTimeInMillis() / 1000;
			long usec = (cal.getTimeInMillis() - (sec * 1000)) * 1000;
			StringBuffer qryStr2 = new StringBuffer(
				"insert into caalarm.root_alarm(res_id,detection_time,det_time_usec,alarm_type,alarm_sub_type) values(");
			qryStr2.append(hfc.getHfcResId()).append(",").append(sec)
			.append(",").append(usec).append(",").append(1).append(",").append(2)
			.append(")");
			
			System.out.println(qryStr2);
			
			stmt.executeUpdate(qryStr2.toString(), Statement.RETURN_GENERATED_KEYS);
			qryStr2 = null;
			rs = stmt.getGeneratedKeys();
			int i = 1;
			Long alarmId = null;
			if (rs.next()) {
				alarmId = (Long) rs.getObject(i++);
			}
			DbUtils.ReleaseResultSet(rs);
			
			if (alarmId != null) {
				CurrentAlarm ca = new CurrentAlarm(new BigInteger(alarmId.toString()));
				ca.addAlarm(60*20, 1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
	}
	
	
	private LinkedList getListOfCms(HfcPlant hfc) {
		LinkedList ret = new LinkedList();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			String queryStr = GlobalQueries.QRY_CM_IN_HFC + hfc.getHfcResId();
			rs = stmt.executeQuery(queryStr);
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				CableModem cm = new CableModem(mrs);
				ret.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
		
		return (ret);
	}
	
	
	protected void updateCmStatus(CableModem cm, boolean down) {
		Statement stmt = null;
		ResultSet rs = null;
		
		Calendar cal = new GregorianCalendar();
		long sec = cal.getTimeInMillis() / 1000;

		try {
			stmt = m_conn.getConnection().createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY, 
					java.sql.ResultSet.CONCUR_UPDATABLE);
			
			String qryStr = "update canet.cable_modem set docsis_state=3, alert_level=5 where cm_res_id=" + cm.getCmResId();
			stmt.executeUpdate(qryStr);
			qryStr = null;
			
			CmCurrentStatus cmSt = new CmCurrentStatus(cm.getCmResId());
			long onlineTm = cmSt.getSumGoodStateTime();
			long offlineTm = cmSt.getSumBadStateTime();
			int sumOnlineChanges = cmSt.getSumStateChages();
			if (down) {
				onlineTm += (sec - cmSt.getLastLogTime());				
			} else {
				offlineTm += (sec - cmSt.getLastLogTime());
			}
			StringBuffer q = new StringBuffer("update caperf.cm_current_status set sum_online_tm=");
			q.append(onlineTm).append(", last_log_tm=").append(sec)
			.append(",sum_offline_tm=").append(offlineTm)
			.append(",last_online_chg_tm=").append(sec)
			.append(",state_changes=").append(sumOnlineChanges)
			.append(", online_state=1 where cm_res_id=").append(cm.getCmResId());
			stmt.executeUpdate(q.toString());
			
			int snr = (!down?305:225);

			CmCurrentPerformance cmPerf = new CmCurrentPerformance(cm.getCmResId());
			long sumTcaFreeTm = cmPerf.getSumTcaFreeTime().longValue();
			long sumTcaTm = cmPerf.getSumTcaTime().longValue();
			int stateChanges = cmPerf.getTcaStateChanges();
			stateChanges++;
			if (!down) {
				sumTcaTm += sec - cmPerf.getLastLogTime().longValue();
			} else {
				sumTcaFreeTm += sec - cmPerf.getLastLogTime().longValue();				
			}
			q = new StringBuffer("update caperf.cm_current_perf set last_log_tm=");
			q.append(sec).append(",downstream_snr=").append(snr)
			.append(",downstream_power=-150,upstream_power=-150")
			.append(",sum_tcafree_tm=").append(sumTcaFreeTm)
			.append(",sum_tca_tm=").append(sumTcaTm)
			.append(",state_changes=").append(stateChanges)
			.append(",tca_change_tm=").append(sec)
			.append(" where cm_res_id=")
			.append(cm.getCmResId());
			stmt.executeUpdate(q.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected boolean getConnection()
	{
		boolean ret = true;
		
		// m_conn = createDbConnection();
		
		ProgramProperties props = ProgramProperties.getInstance();
		String connUrl = props.getValue("db.conn");

		System.out.println(connUrl);
		
		m_conn = createDbConnection(connUrl);

		return (ret);
	}
	
	protected boolean releaseConnection()
	{
		boolean ret = true;
		
		closeConnection(m_conn);
		m_conn = null;
		
		return (ret);
	}
	
	protected void populateCmList() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CM_IN_CMTS);
			qryStr.append(m_cmts.getCmtsResId());
			rs = stmt.executeQuery(qryStr.toString());
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				CableModem cm = new CableModem(mrs);
				m_onlineCmList.add(cm);
			}
			
			// System.out.println("CM's: " + m_onlineCmList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void populateMtaList() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_MTA_IN_CMTS);
			qryStr.append(m_cmts.getCmtsResId());
			rs = stmt.executeQuery(qryStr.toString());
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Emta cm = new Emta(mrs);
				m_availableMtaList.add(cm);
			}
			
			// System.out.println("MTA's: " + m_availableMtaList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void populateHfcList() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_HFC);
			qryStr.append(" where h.cmts_res_id=").append(m_cmts.getCmtsResId());
			rs = stmt.executeQuery(qryStr.toString());
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				HfcPlant cm = new HfcPlant(mrs);
				m_hfcList.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	protected void populateChannelList() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = m_conn.getConnection().createStatement();
			StringBuffer qryStr = new StringBuffer(GlobalQueries.QRY_CHANNEL);
			qryStr.append(" where ch.cmts_res_id=").append(m_cmts.getCmtsResId());
			rs = stmt.executeQuery(qryStr.toString());
			ProxyDbResultSet mrs = new ProxyDbResultSet(rs);
			
			while (rs.next()) {
				Channel cm = new Channel(mrs);
				m_channelList.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.ReleaseResultSet(rs);
			DbUtils.ReleaseStatement(stmt);
			rs = null;
			stmt = null;
		}
	}
	
	private void populateMtasInFirstHfc() {
		HfcPlant hfc = (HfcPlant) m_hfcList.get(0);
		
		for (int i=0; i<m_availableMtaList.size(); i++) {
			Emta mta = (Emta) m_availableMtaList.get(i);
			CableModem cm = new CableModem(mta.getCmResId().longValue());
			if (cm.getHfcResId().equals(hfc.getHfcResId())) {
				m_mtaInFirstHfc.add(mta);
			}
		}		
	}
	
	private boolean isMtaInFirstHfc(Emta mta){
		for (int i=0; i<m_mtaInFirstHfc.size(); i++) {
			Emta m = (Emta) m_mtaInFirstHfc.get(i);
			if (m.getEmtaResId().equals(mta.getEmtaResId())) {
				return (true);
			}
		}
		
		return (false);
	}
	
	private void populateCmsInSecondHfc() {
		HfcPlant hfc = (HfcPlant) m_hfcList.get(1);
		
		for (int i=0; i<m_onlineCmList.size(); i++) {
			CableModem cm = (CableModem) m_onlineCmList.get(i);
			if (cm.getHfcResId().equals(hfc.getHfcResId())) {
				m_cmInSecondHfc.add(cm);
			}
		}
	}
	
	private boolean isCmInSecondHfc(CableModem cm){
		for (int i=0; i<m_cmInSecondHfc.size(); i++) {
			CableModem c = (CableModem) m_cmInSecondHfc.get(i);
			if (c.getCmResId().equals(cm.getCmResId())) {
				return (true);
			}
		}
		
		return (false);
	}
	
	
}
