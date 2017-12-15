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
public class CmTcaSummary extends CmStatusSummaryT {

	/**
	 * 
	 *
	 */
	private CmTcaSummary() {
		super();
	}
	
	
    /**
     * 
     * @param m
     */
    public CmTcaSummary(CmStatusSummaryT m) {
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
    public CmTcaSummary(StatusSummaryT statusSummary,
           BigInteger cmtsResId, String cmtsName, BigInteger hfcResId,
           String hfcName) {
    	super(statusSummary,cmtsResId,cmtsName,hfcResId,hfcName);
    }


    /**
     * @author Prem
     * @param dbSumm
     */
    public CmTcaSummary(CmPerfThreshSummary dbSumm) {
    	super(dbSumm);
    }
    
    
}
