/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.palmyrasys.www.webservices.CableAssurance.CurrentAlarmT;
import com.palmyrasys.www.webservices.CableAssurance.HistoricalAlarmT;
import com.palmyrasys.www.webservices.CableAssurance.MtaStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.ScrollPageT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;
import com.palmyrasyscorp.db.tables.CmPerfThreshSummary;
import com.palmyrasyscorp.db.tables.CmStatusSummary;
import com.palmyrasyscorp.db.tables.CurrentAlarm;
import com.palmyrasyscorp.db.tables.HfcStatusSummary;
import com.palmyrasyscorp.db.tables.HistoricalAlarm;
import com.palmyrasyscorp.db.tables.MtaStatusSummary;

/**
 * This class serves the various servers namely, Market, Blade, etc. It Contains
 * functions that can be used by these servers.
 * 
 * @author Prem
 * 
 */
public final class CommonServiceUtils {

	/**
	 * 
	 * 
	 */
	private CommonServiceUtils() {
		super();
	}

	/**
	 * 
	 * @param tree
	 * @param al
	 */
	public static void TreeToListDescending(ArrayList al, TreeMap tree,
			int batchSize) {

		Collection coln = tree.values();
		// Iterator iter = c.iterator();
		AbstractCAServiceObject[] tmp = new AbstractCAServiceObject[coln.size()];
		AbstractCAServiceObject[] array = (AbstractCAServiceObject[]) coln
				.toArray(tmp);
		int i = array.length - 1;
		int c = 0;
		for (; c < batchSize && i >= 0; i--, c++) {
			al.add(array[i]);
		}
	}

	/**
	 * 
	 * @param al
	 * @param tree
	 * @param batchSize
	 */
	public static void TreeToListAscending(ArrayList al, TreeMap tree,
			int batchSize) {
		Collection coln = tree.values();
		Iterator iter = coln.iterator();
		for (int i = 0; i < batchSize && iter.hasNext(); i++) {
			al.add(iter.next());
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddToTreeDescending(TreeMap tree,
			AbstractCAServiceObject[] rslts, int batchSize) {
		if (rslts != null) {
			for (int j = 0; j < rslts.length; j++) {
				AbstractCAServiceObject curr = rslts[j];
				if (tree.size() > batchSize) {
					AbstractCAServiceObject first = (AbstractCAServiceObject) tree
							.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddToTreeAscending(TreeMap tree,
			AbstractCAServiceObject[] rslts, int batchSize) {
		if (rslts != null) {
			for (int j = 0; j < rslts.length; j++) {
				AbstractCAServiceObject curr = rslts[j];
				if (tree.size() > batchSize) {
					AbstractCAServiceObject last = (AbstractCAServiceObject) tree
							.lastKey();
					if (last.compareTo(curr) >= 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddMtaStatusSummary1ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				MtaStatusSummary tC = (MtaStatusSummary) rslts.get(j);
				MtaStatusSummaryT curr = new MtaStatusSummaryT(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					MtaStatusSummaryT first = (MtaStatusSummaryT) tree
							.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddHfcStatusSummary1ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				HfcStatusSummary tC = (HfcStatusSummary) rslts.get(j);
				HfcStatusSummaryT curr = new HfcStatusSummaryT(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					HfcStatusSummaryT first = (HfcStatusSummaryT) tree
							.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddMtaStatusSummary2ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				MtaStatusSummary tC = (MtaStatusSummary) rslts.get(j);
				MtaStatusSummaryByFreq curr = new MtaStatusSummaryByFreq(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					MtaStatusSummaryByFreq first = (MtaStatusSummaryByFreq) tree
							.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddHfcStatusSummary2ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				HfcStatusSummary tC = (HfcStatusSummary) rslts.get(j);
				HfcStatusSummaryByFreq curr = new HfcStatusSummaryByFreq(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					HfcStatusSummaryByFreq first = (HfcStatusSummaryByFreq) tree
							.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddCmTcaSummary1ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				CmPerfThreshSummary tC = (CmPerfThreshSummary) rslts.get(j);
				CmTcaSummary curr = new CmTcaSummary(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					CmStatusSummaryT first = (CmStatusSummaryT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddCmStatusSummary1ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				CmStatusSummary tC = (CmStatusSummary) rslts.get(j);
				CmStatusSummaryT curr = new CmStatusSummaryT(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					CmStatusSummaryT first = (CmStatusSummaryT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddCmStatusSummary2ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				CmStatusSummary tC = (CmStatusSummary) rslts.get(j);
				CmStatusSummaryByFreq curr = new CmStatusSummaryByFreq(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					CmStatusSummaryT first = (CmStatusSummaryT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddCmTcaSummary2ToTree(TreeMap tree, List rslts,
			int batchSize, TopoHierarchyKeyT topoKey) {
		if (rslts != null) {
			for (int j = 0; j < rslts.size(); j++) {
				CmPerfThreshSummary tC = (CmPerfThreshSummary) rslts.get(j);
				CmTcaSummaryByFreq curr = new CmTcaSummaryByFreq(tC);
				curr.getStatusSummary().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					CmStatusSummaryT first = (CmStatusSummaryT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @return
	 */
	static public ScrollPageT FindBestScrollPageForStart(QueryStateT queryState,
			ResultBatchT batch) {
		ScrollPageT ret = null;

		if (queryState != null) {
			ScrollPageT[] pages = queryState.getPages();
			if (pages != null) {
				for (int j = 0; j < pages.length; j++) {
					ScrollPageT pg = pages[j];
					if (pg.getBatch().equals(batch) ||
							pg.getBatch().greaterThan(batch)) {
						int idx;
						if (j > 0) {
							idx = j - 1;
						} else {
							idx = 0;
						}
						ret = pages[idx];
						break;
					}
				}
				if (ret == null && pages.length > 0) {
					ret = pages[pages.length - 1];
				}
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @return
	 */
	static public ScrollPageT FindScrollPage(QueryStateT queryState,
			ResultBatchT batch) {
		ScrollPageT ret = null;

		if (queryState != null) {
			ScrollPageT[] pages = queryState.getPages();
			if (pages != null) {
				for (int j = 0; j < pages.length; j++) {
					ScrollPageT pg = pages[j];
					if (pg.getBatch().equals(batch)) {
						ret = pg;
						break;
					}
				}
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddCurrentAlarmsToTree(TreeMap tree, List rslts,
			int batchSize) {
		if (rslts != null) {
			byte[] b = { 0 };
			TopoHierarchyKeyT tK = new TopoHierarchyKeyT(new BigInteger(b),
					new BigInteger(b), new BigInteger(b));
			for (int j = 0; j < rslts.size(); j++) {
				CurrentAlarm ca = (CurrentAlarm) rslts.get(j);
				CurrentAlarmT curr = new CurrentAlarmT(ca);
				curr.getAbstractAlarm().setTopologyKey(tK);
				if (tree.size() > batchSize) {
					CurrentAlarmT first = (CurrentAlarmT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

	/**
	 * 
	 * @param tree
	 * @param rslts
	 * @param batchSize
	 */
	public static void AddHistoricalAlarmsToTree(TreeMap tree, List rslts,
			int batchSize) {
		if (rslts != null) {
			byte[] b = { 0 };
			TopoHierarchyKeyT topoKey = new TopoHierarchyKeyT(
					new BigInteger(b), new BigInteger(b), new BigInteger(b));
			for (int j = 0; j < rslts.size(); j++) {
				HistoricalAlarm ca = (HistoricalAlarm) rslts.get(j);
				HistoricalAlarmT curr = new HistoricalAlarmT(ca);
				curr.getAbstractAlarm().setTopologyKey(topoKey);
				if (tree.size() > batchSize) {
					CurrentAlarmT first = (CurrentAlarmT) tree.firstKey();
					if (first.compareTo(curr) < 0) {
						break;
					}
				}
				tree.put(curr, curr);
			}
		}
	}

}
