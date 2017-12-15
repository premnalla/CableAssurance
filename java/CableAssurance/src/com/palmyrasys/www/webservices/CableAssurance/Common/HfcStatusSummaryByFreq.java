/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

import java.math.BigInteger;

import com.palmyrasys.www.webservices.CableAssurance.HfcStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT;
import com.palmyrasyscorp.db.tables.HfcStatusSummary;

/**
 * @author Prem
 *
 */
public class HfcStatusSummaryByFreq extends HfcStatusSummaryT {

	/**
	 * 
	 *
	 */
    private HfcStatusSummaryByFreq() {
    	super();
    }

    /**
     * 
     * @param m
     */
    public HfcStatusSummaryByFreq(HfcStatusSummaryT m) {
    	super(m.getStatusSummary(), m.getCmtsResId(), m.getCmtsName());
    }
    
    /**
     * 
     * @param statusSummary
     * @param cmtsResId
     * @param cmtsName
     * @param hfcResId
     * @param hfcName
     */
    public HfcStatusSummaryByFreq(StatusSummaryT statusSummary,
           BigInteger cmtsResId, String cmtsName) {
    	super(statusSummary,cmtsResId,cmtsName);
    }


    /**
     * @author Prem
     * @param dbSumm
     */
    public HfcStatusSummaryByFreq(HfcStatusSummary dbSumm) {
    	super(dbSumm);
    }
    
    
    /**
     * @author Prem
     */
    public int compareTo(Object m) {
    	HfcStatusSummaryT inO = (HfcStatusSummaryT) m;
   		short thisVal = getStatusSummary().getSumStateChanges();
   		short inVal = inO.getStatusSummary().getSumStateChanges();
   		
    	if (thisVal < inVal) {
    		return (-1);
    	}
    	
    	if (thisVal > inVal) {
    		return (1);
    	}
    	
    	String thisMac = getStatusSummary().getName();
    	String inMac = inO.getStatusSummary().getName();
    	
    	return (thisMac.compareTo(inMac));
    }

}
