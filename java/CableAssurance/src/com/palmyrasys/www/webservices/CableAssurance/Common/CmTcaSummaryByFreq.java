/**
 * 
 */
package com.palmyrasys.www.webservices.CableAssurance.Common;

import java.math.BigInteger;

import com.palmyrasys.www.webservices.CableAssurance.CmStatusSummaryT;
import com.palmyrasys.www.webservices.CableAssurance.StatusSummaryT;
import com.palmyrasyscorp.db.tables.CmPerfThreshSummary;

/**
 * @author Prem
 *
 */
public class CmTcaSummaryByFreq extends CmStatusSummaryT {

	/**
	 * 
	 *
	 */
	private CmTcaSummaryByFreq() {
		super();
	}
	
	
    /**
     * 
     * @param m
     */
    public CmTcaSummaryByFreq(CmStatusSummaryT m) {
    	super(m.getStatusSummary(), m.getCmtsResId(), m.getCmtsName(),
    			m.getHfcResId(), m.getHfcName());
    }
    
    /**
     * 
     * @param statusSummary
     * @param cmtsResId
     * @param cmtsName
     * @param hfcResId
     * @param hfcName
     */
    public CmTcaSummaryByFreq(StatusSummaryT statusSummary,
           BigInteger cmtsResId, String cmtsName, BigInteger hfcResId,
           String hfcName) {
    	super(statusSummary,cmtsResId,cmtsName,hfcResId,hfcName);
    }


    /**
     * @author Prem
     * @param dbSumm
     */
    public CmTcaSummaryByFreq(CmPerfThreshSummary dbSumm) {
    	super(dbSumm);
    }

    
    /**
     * @author Prem
     */
    public int compareTo(Object m) {
    	CmStatusSummaryT inO = (CmStatusSummaryT) m;
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
