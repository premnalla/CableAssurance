/**
 * 
 */
package com.palmyrasyscorp.db.populate.netobj;


/**
 * @author Prem
 *
 */
public class Main {

	/**
	 * 
	 */
	public Main() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ProgramProperties props = ProgramProperties.getInstance();
		String lcType = props.getValue("local.system.type");
		String lcName = props.getValue("local.system.name");
				
		System.err.println("Loaded properties file....exitting");
		
		NetworkObjCreator worker1 = new NetworkObjCreator(lcType, lcName);
		
		System.out.println("Starting work....");
		
		worker1.start();
		// worker2.start();
		
		while (worker1.isWorkDone() == false) {
				// worker2.isWorkDone() == false) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Completed work....exitting");
	}

}
