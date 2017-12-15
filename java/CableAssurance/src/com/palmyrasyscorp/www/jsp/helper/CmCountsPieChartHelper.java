/**
 * 
 */
package com.palmyrasyscorp.www.jsp.helper;

import org.jfree.data.general.DefaultPieDataset;

import com.palmyrasys.www.webservices.CableAssurance.GenericCountsT;
import com.palmyrasyscorp.www.webservices.helper.ConversionHelper;

import org.jfree.data.general.PieDataset;

/**
 * @author Prem
 * 
 */
public class CmCountsPieChartHelper extends CountsPieChartHelper {

	public static final String CHART_NAME = "Cable Modem";

	private GenericCountsT m_cnts;

	protected CmCountsPieChartHelper() {

	}

	public CmCountsPieChartHelper(GenericCountsT c) {
		m_cnts = c;
	}

	public String getChartName() {
		return (CHART_NAME);
	}

	public PieDataset getDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        short sT = m_cnts.getTotalCm();
        short sOn = m_cnts.getOnlineCm();
        
        if (sT > 0) {
	        dataset.setValue("Online", 	new Float(
	        	ConversionHelper.getPercentToOneDecDigit(sT, sOn)));
	        dataset.setValue("Offline", new Float(
	        	ConversionHelper.getPercentToOneDecDigit(sT, sT-sOn)));
        } else {
	        dataset.setValue("No CM's", new Integer(0));        	
        }

        return (dataset);
	}
	
}
