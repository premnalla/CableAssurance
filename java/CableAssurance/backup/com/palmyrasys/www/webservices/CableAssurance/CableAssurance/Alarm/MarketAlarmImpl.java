/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

import com.palmyrasys.www.webservices.CableAssurance.*;
import com.palmyrasyscorp.common.dsandalgo.AvlTree;
import com.palmyrasyscorp.db.tables.Blade;
import com.palmyrasyscorp.db.tables.CurrentAlarm;
import com.palmyrasyscorp.db.tables.HistoricalAlarm;
import com.palmyrasyscorp.db.tables.Market;
import com.palmyrasyscorp.www.webservices.helper.ConversionHelper;

/**
 * @author Prem
 * 
 */
public class MarketAlarmImpl extends AbstractAlarmImpl implements AlarmEP {

	/**
	 * 
	 */
	public CurrentAlarmT[] getCurrentAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch)
			throws java.rmi.RemoteException {
		CurrentAlarmT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		/*
		 * Tree of sorted CMTS object
		 */
		AvlTree cmtsTree = new AvlTree();

		/*
		 * Spawn a thread each for each of the blades in the market. Each thread
		 * will get the list of CMTS's from each blade and subsequently add it
		 * to 'a' AVL tree. Once getting is done or, timeout has occured, the
		 * AVL tree will be processed and result returned to the caller.
		 */

		Market mkt = new Market();
		List blades = mkt.getBlades();
		for (int i = 0; i < blades.size(); i++) {
			Blade bld = (Blade) blades.get(i);

			/*
			 * 
			 */
		}
		blades.clear();

		/*
		 * Get Alarms from myself (which is Market)
		 */

		long fromSec = ConversionHelper.inputTimeToSec(fromTime);
		long toSec = ConversionHelper.inputTimeToSec(toTime);
		CurrentAlarm ca = new CurrentAlarm();
		List l = ca.getAlarms(fromSec, toSec, batch.getFromIndex(), batch
				.getToIndex());
		for (int i = 0; i < l.size(); i++) {
			CurrentAlarm dbAlm = (CurrentAlarm) l.get(i);
			CurrentAlarmT svcAlm = new CurrentAlarmT(dbAlm);
			al.add(svcAlm);
		}
		l.clear();

		if (al.size() > 0) {
			CurrentAlarmT[] tmp = new CurrentAlarmT[al.size()];
			ret = (CurrentAlarmT[]) al.toArray(tmp);
		}

		return (ret);
	}

	public CurrentAlarmT getCurrentAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		CurrentAlarmT ret = null;

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CurrentAlarm ca = new CurrentAlarm(alarmId);
			ret = new CurrentAlarmT(ca);

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		return (ret);
	}

	public CurrentAlarmDetailsT getCurrentAlarmDetails(
			TopoHierarchyKeyT topologyKey, BigInteger alarmId)
			throws java.rmi.RemoteException {
		CurrentAlarmDetailsT ret = null;

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			CurrentAlarm ca = new CurrentAlarm(alarmId);
			ret = new CurrentAlarmDetailsT(ca);

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		return (ret);
	}

	/**
	 * 
	 */
	public CurrentAlarmT[] getCurrentAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime,
			ResultBatchT batch) {
		CurrentAlarmT[] ret = null;

		ArrayList al = new ArrayList();

		/*
		 * Tree of sorted CMTS object
		 */
		AvlTree cmtsTree = new AvlTree();

		/*
		 * Spawn a thread each for each of the blades in the market. Each thread
		 * will get the list of CMTS's from each blade and subsequently add it
		 * to 'a' AVL tree. Once getting is done or, timeout has occured, the
		 * AVL tree will be processed and result returned to the caller.
		 */

		Market mkt = new Market();
		List blades = mkt.getBlades();
		for (int i = 0; i < blades.size(); i++) {
			Blade bld = (Blade) blades.get(i);

			/*
			 * 
			 */
		}
		blades.clear();

		/*
		 * Get Alarms from myself (which is Market)
		 */

		long fromSec = ConversionHelper.inputTimeToSec(fromTime);
		long toSec = ConversionHelper.inputTimeToSec(toTime);
		CurrentAlarm ca = new CurrentAlarm();
		List l = ca.getAlarmsByType(alarmType, alarmSubType, fromSec, toSec,
				batch.getFromIndex(), batch.getToIndex());
		for (int i = 0; i < l.size(); i++) {
			CurrentAlarm dbAlm = (CurrentAlarm) l.get(i);
			CurrentAlarmT svcAlm = new CurrentAlarmT(dbAlm);
			al.add(svcAlm);
		}
		l.clear();

		if (al.size() > 0) {
			CurrentAlarmT[] tmp = new CurrentAlarmT[al.size()];
			ret = (CurrentAlarmT[]) al.toArray(tmp);
		}

		return (ret);
	}

	/**
	 * 
	 */
	public HistoricalAlarmT[] getHistoricalAlarms(InputTimeT fromTime,
			InputTimeT toTime, ResultBatchT batch)
			throws java.rmi.RemoteException {
		HistoricalAlarmT[] ret = null;

		ArrayList al = new ArrayList();

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		/*
		 * Tree of sorted CMTS object
		 */
		AvlTree cmtsTree = new AvlTree();

		/*
		 * Spawn a thread each for each of the blades in the market. Each thread
		 * will get the list of CMTS's from each blade and subsequently add it
		 * to 'a' AVL tree. Once getting is done or, timeout has occured, the
		 * AVL tree will be processed and result returned to the caller.
		 */

		Market mkt = new Market();
		List blades = mkt.getBlades();
		for (int i = 0; i < blades.size(); i++) {
			Blade bld = (Blade) blades.get(i);

			/*
			 * 
			 */
		}
		blades.clear();

		/*
		 * Get Alarms from myself (which is Market)
		 */

		long fromSec = ConversionHelper.inputTimeToSec(fromTime);
		long toSec = ConversionHelper.inputTimeToSec(toTime);

		HistoricalAlarm ha = new HistoricalAlarm();
		List l = ha.getAlarms(fromSec, toSec, batch.getFromIndex(), batch
				.getToIndex());
		for (int i = 0; i < l.size(); i++) {
			HistoricalAlarm dbAlm = (HistoricalAlarm) l.get(i);
			HistoricalAlarmT svcAlm = new HistoricalAlarmT(dbAlm);
			al.add(svcAlm);
		}
		l.clear();

		if (al.size() > 0) {
			HistoricalAlarmT[] tmp = new HistoricalAlarmT[al.size()];
			ret = (HistoricalAlarmT[]) al.toArray(tmp);
		}

		return (ret);
	}

	public HistoricalAlarmT getHistoricalAlarm(TopoHierarchyKeyT topologyKey,
			BigInteger alarmId) throws java.rmi.RemoteException {
		HistoricalAlarmT ret = null;

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			HistoricalAlarm ca = new HistoricalAlarm(alarmId);
			ret = new HistoricalAlarmT(ca);

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		return (ret);
	}

	public HistoricalAlarmDetailsT getHistoricalAlarmDetails(
			TopoHierarchyKeyT topologyKey, BigInteger alarmId)
			throws java.rmi.RemoteException {
		HistoricalAlarmDetailsT ret = null;

		// System.out.println("getChannels(): cmts resid=" + cmtsResId);

		if (topologyKey.getBladeId().longValue() == 0) {
			/*
			 * Get Alarms from myself (which is Market)
			 */
			HistoricalAlarm ca = new HistoricalAlarm(alarmId);
			ret = new HistoricalAlarmDetailsT(ca);

		} else {
			/*
			 * Get Alarms from Blade
			 */
		}

		return (ret);
	}

	public HistoricalAlarmT[] getHistoricalAlarmsByType(String alarmType,
			String alarmSubType, InputTimeT fromTime, InputTimeT toTime, ResultBatchT batch) {
		HistoricalAlarmT[] ret = null;

		ArrayList al = new ArrayList();

		/*
		 * Tree of sorted CMTS object
		 */
		AvlTree cmtsTree = new AvlTree();

		/*
		 * Spawn a thread each for each of the blades in the market. Each thread
		 * will get the list of CMTS's from each blade and subsequently add it
		 * to 'a' AVL tree. Once getting is done or, timeout has occured, the
		 * AVL tree will be processed and result returned to the caller.
		 */

		Market mkt = new Market();
		List blades = mkt.getBlades();
		for (int i = 0; i < blades.size(); i++) {
			Blade bld = (Blade) blades.get(i);

			/*
			 * 
			 */
		}
		blades.clear();

		/*
		 * Get Alarms from myself (which is Market)
		 */
		long fromSec = ConversionHelper.inputTimeToSec(fromTime);
		long toSec = ConversionHelper.inputTimeToSec(toTime);
		HistoricalAlarm ca = new HistoricalAlarm();
		List l = ca.getAlarmsByType(alarmType, alarmSubType, fromSec, fromSec,
				batch.getFromIndex(), batch.getToIndex());
		for (int i = 0; i < l.size(); i++) {
			HistoricalAlarm dbAlm = (HistoricalAlarm) l.get(i);
			HistoricalAlarmT svcAlm = new HistoricalAlarmT(dbAlm);
			al.add(svcAlm);
		}
		l.clear();

		if (al.size() > 0) {
			HistoricalAlarmT[] tmp = new HistoricalAlarmT[al.size()];
			ret = (HistoricalAlarmT[]) al.toArray(tmp);
		}

		return (ret);
	}

}
