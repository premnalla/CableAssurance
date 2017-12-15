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
public class MtaCountsPieChartHelper extends CountsPieChartHelper {

	public static final String CHART_NAME = "MTA";

	private GenericCountsT m_cnts;

	protected MtaCountsPieChartHelper() {

	}

	public MtaCountsPieChartHelper(GenericCountsT c) {
		m_cnts = c;
	}

	public String getChartName() {
		return (CHART_NAME);
	}

	public PieDataset getDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        short sT = m_cnts.getTotalMta();
        short sOn = m_cnts.getAvailableMta();
        
        if (sT > 0) {
	        dataset.setValue("Available", new Float(
	        	ConversionHelper.getPercentToOneDecDigit(sT, sOn)));
	        dataset.setValue("Unavailable", new Float(
	        	ConversionHelper.getPercentToOneDecDigit(sT, sT-sOn)));
        } else {
	        dataset.setValue("No MTA's", new Integer(0));        	
        }

        return (dataset);
	}

}
