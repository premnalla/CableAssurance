/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasys.www.webservices.CableAssurance.AlarmIdT;
import com.palmyrasys.www.webservices.CableAssurance.TopoHierarchyKeyT;

/**
 * @author Prem
 * 
 */
public final class TopoKeyAndId {

	private static Log log = LogFactory.getLog(TopoKeyAndId.class);

	private TopoHierarchyKeyT m_tk;

	public TopoHierarchyKeyT getTopoKey() {
		return m_tk;
	}

	private BigInteger m_id;

	public BigInteger getId() {
		return m_id;
	}

	/**
	 * 
	 * @param rgnId
	 * @param mktId
	 * @param bldId
	 * @param id
	 */
	public TopoKeyAndId(String rgnId, String mktId, String bldId, String id) {
		try {
			m_tk = new TopoHierarchyKeyT(new BigInteger(rgnId), new BigInteger(
					mktId), new BigInteger(bldId));
			m_id = new BigInteger(id);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
	}

	/**
	 * 
	 *
	 */
	private TopoKeyAndId() {
		
	}
	
	/**
	 * 
	 * @param l
	 * @return
	 */
	public static final AlarmIdT[] ConvertTopoAndIdToAlarmId(List l) {
		AlarmIdT[] ret = null;
		
		try {
			AlarmIdT[] tmp = new AlarmIdT[l.size()];
			for (int i=0; i<l.size(); i++) {
				TopoKeyAndId t = (TopoKeyAndId) l.get(i);
				tmp[i] = new AlarmIdT(t.getTopoKey(), t.getId());
			}
			ret = tmp;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);
		}
		
		return (ret);
	}
}
