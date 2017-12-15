/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

/**
 * @author Prem
 *
 */
public class CountsMain {
	
	public static void main(String args[])
	{
		System.out.println("Starting ...");
		
		long sleepTimeMillis = 1 * 1000;
		CmtsCmCountLog CmtsCm = new CmtsCmCountLog();
		CmtsMtaCountLog CmtsMta = new CmtsMtaCountLog();
		HfcCmCountLog HfcCm = new HfcCmCountLog();
		HfcMtaCountLog HfcMta = new HfcMtaCountLog();
		ChannelCmCountLog ChnlCm = new ChannelCmCountLog();
		ChannelMtaCountLog ChnlMta = new ChannelMtaCountLog();
		CmtsCm.start();
		CmtsMta.start();
		HfcCm.start();
		HfcMta.start();
		ChnlCm.start();
		ChnlMta.start();
		
		while (CmtsCm.isWorkDone()==false ||
				CmtsMta.isWorkDone()==false ||
				HfcCm.isWorkDone()==false ||
				HfcMta.isWorkDone()==false ||
				ChnlCm.isWorkDone()==false ||
				ChnlMta.isWorkDone()==false) {
			try {
				Thread.sleep(sleepTimeMillis);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Completed task ...");
				
	}
	
}
