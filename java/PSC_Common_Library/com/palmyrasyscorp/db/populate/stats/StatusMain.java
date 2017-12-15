/**
 * 
 */
package com.palmyrasyscorp.db.populate.stats;

/**
 * @author Prem
 *
 */
public class StatusMain {

	/**
	 * 
	 */
	public StatusMain() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting ...");
		
		long sleepTimeMillis = 1 * 1000;
		CmStatusLog cmLog = new CmStatusLog();
		MtaPingStatusLog mtaPingLog = new MtaPingStatusLog();
		MtaProvStatusLog mtaProvLog = new MtaProvStatusLog();
		cmLog.start();
		mtaPingLog.start();
		mtaProvLog.start();
		
		while (cmLog.isWorkDone()==false ||
				mtaPingLog.isWorkDone()==false ||
				mtaProvLog.isWorkDone()==false) {
			try {
				Thread.sleep(sleepTimeMillis);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Completed task ...");
	}

}
