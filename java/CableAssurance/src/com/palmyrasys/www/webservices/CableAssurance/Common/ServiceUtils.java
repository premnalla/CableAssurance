/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

// import java.math.BigInteger;
import java.math.BigInteger;
import java.util.*;

// import com.palmyrasys.www.webservices.CableAssurance.MtaConditionLongestT;
import com.palmyrasys.www.webservices.CableAssurance.QueryStateT;
import com.palmyrasys.www.webservices.CableAssurance.ResultBatchT;
import com.palmyrasys.www.webservices.CableAssurance.ScrollPageT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * This Class should have been named MarketServiceUtils because it serves
 * the Market Server needs. Similarly, the class that serves a Blade should
 * be called BladeServiceUtils.
 * 
 * @author Prem
 * 
 */
public final class ServiceUtils {

	/**
	 * 
	 * 
	 */
	private ServiceUtils() {
		super();
	}

	/**
	 * 
	 * @param queryState
	 * @return
	 */
	public static QueryStateT FindQsForMarket(QueryStateT[] queryState) {
		QueryStateT ret = null;

		if (queryState != null) {
			for (int j = 0; j < queryState.length; j++) {
				if (queryState[j] != null && 
						queryState[j].getTopologyKey().getBladeId().intValue() == 0) {
					ret = queryState[j];
					break;
				}
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @return
	 */
	public static QueryStateT FindQsForBlade(QueryStateT[] queryState) {
		QueryStateT ret = null;

		if (queryState != null && queryState.length > 0 && queryState[0] != null) {
			ret = queryState[0];
		}

		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param al
	 * @return
	 */
	static public QueryStateT SetReturnQsForMarket(QueryStateT[] queryState, 
			ResultBatchT batch,	ArrayList al) {
		QueryStateT ret;

		ret = FindQsForMarket(queryState);
		
		ScrollPageT pg;
		
		if (ret != null) {
			pg = CommonServiceUtils.FindScrollPage(ret, batch);
			if (pg == null) {
				UniqueObject last = FindLastForMarket(al);
				if (last == null) {
					// all set; nothing to do; return the one from the input
				} else {
					ScrollPageT[] old = ret.getPages();
					ScrollPageT[] pages = new ScrollPageT[old.length + 1];
					int i=0;
					for (; i<old.length; i++) {
						pages[i] = old[i];
					}
					pages[i] = new ScrollPageT(batch, 
							(new Long(last.getId())).toString());					
					ret.setPages(pages);
				}
			} else {
				// all set; nothing to do; return the one from the input
			}
		} else {
			UniqueObject last = FindLastForMarket(al);
			if (last == null) {
				// all set; nothing to do; return null
			} else {
				TopoHierarchyKeyT topoKey = TopoKeyHelper.getTopoKey("0", "0", "0");
				ScrollPageT[] pages = new ScrollPageT[1];
				ret = new QueryStateT();
				ret.setTopologyKey(topoKey);				
				pages[0] = new ScrollPageT(batch, 
						(new Long(last.getId())).toString());
				ret.setPages(pages);
			}
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param queryState
	 * @param batch
	 * @param al
	 * @return
	 */
	static public QueryStateT SetReturnQsForBlade(QueryStateT[] queryState, 
			ResultBatchT batch,	ArrayList al) {
		QueryStateT ret;

		ret = FindQsForBlade(queryState);
		
		ScrollPageT pg;
		
		if (ret != null) {
			pg = CommonServiceUtils.FindScrollPage(ret, batch);
			if (pg == null) {
				UniqueObject last = FindLastForBlade(al);
				if (last == null) {
					// all set; nothing to do; return the one from the input
				} else {
					ScrollPageT[] old = ret.getPages();
					ScrollPageT[] pages = new ScrollPageT[old.length + 1];
					int i=0;
					for (; i<old.length; i++) {
						pages[i] = old[i];
					}
					pages[i] = new ScrollPageT(batch, 
							(new Long(last.getId())).toString());					
					ret.setPages(pages);
				}
			} else {
				// all set; nothing to do; return the one from the input
			}
		} else {
			UniqueObject last = FindLastForBlade(al);
			if (last == null) {
				// all set; nothing to do; return null
			} else {
				TopoHierarchyKeyT topoKey = TopoKeyHelper.getTopoKey("0", "0", "0");
				ScrollPageT[] pages = new ScrollPageT[1];
				ret = new QueryStateT();
				ret.setTopologyKey(topoKey);				
				pages[0] = new ScrollPageT(batch, 
						(new Long(last.getId())).toString());
				ret.setPages(pages);
			}
		}
		
		return (ret);
	}
	
	/**
	 * 
	 * @param al
	 * @return
	 */
	public static UniqueObject FindLastForMarket(ArrayList al) {
		UniqueObject ret = null;

		for (int j = al.size() - 1; j >= 0; j--) {
			UniqueObject e = (UniqueObject) al.get(j);
			if (e.getTopologyHierarchyKey().getBladeId().intValue() == 0) {
				ret = e;
				break;
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param al
	 * @return
	 */
	public static UniqueObject FindLastForBlade(ArrayList al) {
		UniqueObject ret = null;

		int j = al.size() - 1;
		if (j >= 0) {
			ret = (UniqueObject) al.get(j);
		}
		
		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param bladeId
	 * @return
	 */
	public static QueryStateT FindQsForBlade(QueryStateT[] queryState, Integer bladeId) {
		QueryStateT ret = null;

		if (queryState != null) {
			for (int j = 0; j < queryState.length; j++) {
				if (queryState[j] != null && 
						queryState[j].getTopologyKey().getBladeId().intValue() == 
							bladeId.intValue()) {
					ret = queryState[j];
					break;
				}
			}
		}

		return (ret);
	}

	/**
	 * Called from Market Server to set the Blade Qs
	 * 
	 * @param queryState
	 * @param topoKey
	 * @param batch
	 * @param bladeId
	 * @param al
	 * @return
	 */
	static public QueryStateT SetReturnQsForBlade(
			QueryStateT[] queryState, TopoHierarchyKeyT topoKey, ResultBatchT batch, 
			Integer bladeId, ArrayList al) {
		QueryStateT ret;

		ret = FindQsForBlade(queryState, bladeId);
		
		if (ret != null) {
			ScrollPageT pg = CommonServiceUtils.FindScrollPage(ret, batch);
			if (pg == null) {
				UniqueObject last = FindLastForBlade(al, bladeId);
				if (last == null) {
					// all set; nothing to do; return the one from the input
				} else {
					ScrollPageT[] old = ret.getPages();
					ScrollPageT[] pages = new ScrollPageT[old.length + 1];
					int i=0;
					for (; i<old.length; i++) {
						pages[i] = old[i];
					}
					pages[i] = new ScrollPageT(batch, 
							(new Long(last.getId())).toString());					
					ret.setPages(pages);
				}
			} else {
				// all set; nothing to do; return the one from the input
			}
		} else {
			UniqueObject last = FindLastForBlade(al, bladeId);
			if (last == null) {
				// all set; nothing to do; return null
			} else {
				TopoHierarchyKeyT key;
				if (topoKey != null) {
					key = new TopoHierarchyKeyT(topoKey.getRegionId(),topoKey.getMarketId(),
							new BigInteger(bladeId.toString()));
				} else {
					key = TopoKeyHelper.getTopoKey("0", "0", bladeId.toString());
				}
				ret = new QueryStateT();
				ret.setTopologyKey(key);				
				ScrollPageT[] pages = new ScrollPageT[1];
				pages[0] = new ScrollPageT(batch, 
						(new Long(last.getId())).toString());
				ret.setPages(pages);
			}
		}
		
		return (ret);
	}

	/**
	 * 
	 * @param queryState
	 * @param bladeId
	 * @return
	 */
	public static QueryStateT[] SetupQueryStateForBlade(
			QueryStateT[] queryState, Integer bladeId) {
		QueryStateT[] ret = null;

		if (queryState != null) {
			for (int j = 0; j < queryState.length; j++) {
				QueryStateT qsJ = queryState[j];
				if (qsJ != null && 
						qsJ.getTopologyKey().getBladeId().intValue() == bladeId
							.intValue()) {
					ret = new QueryStateT[1];
//					QueryStateT qs = new QueryStateT();
//					ret[0] = qs;
//					qs.setTopologyKey(qsJ.getTopologyKey());
//					qs.setPages(qsJ.getPages());
					ret[0] = qsJ;
					break;
				}
			}
		}

		return (ret);
	}

	/**
	 * 
	 * @param al
	 * @param bladeId
	 * @return
	 */
	public static UniqueObject FindLastForBlade(ArrayList al, Integer bladeId) {
		UniqueObject ret = null;

		for (int j = al.size() - 1; j >= 0; j--) {
			UniqueObject e = (UniqueObject) al.get(j);
			if (e.getTopologyHierarchyKey().getBladeId().intValue() == 
					bladeId.intValue()) {
				ret = e;
				break;
			}
		}

		return (ret);
	}

}
