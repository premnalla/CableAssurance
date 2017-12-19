/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common;


import javax.oss.UpdateProcedureResponse;


/**
 * An implementation class for the <CODE>javax.oss.UpdateProcedureResponse</CODE> interface.  
 * 
 * @see javax.oss.UpdateProcedureResponse
 * 
 * @author OSS through Java(tm) Initiative
 * @version 1.2.2 
 * @since September 2005 
 */


public class UpdateProcedureResponseImpl
implements UpdateProcedureResponse
{ 
	private int status = UpdateProcedureResponse.IN_PROGRESS;
	/**
	 * Constructs a new UpdateProcedureResponseImpl with empty attributes.
	 * 
	 */

	public UpdateProcedureResponseImpl() {
	}
	public UpdateProcedureResponseImpl(int value) {
		status = value;
	}

	//
	// All getters and setters for all declared attributes:
	//



	/**
	 * Returns this UpdateProcedureResponseImpl's status
	 * 
	 * @return the status
	 * 
	*/

	public int getStatus() {

		return status;
	}


	/**
	 * Returns this UpdateProcedureResponseImpl's successful
	 * 
	 * @return the successful
	 * 
	*/

	public boolean isSuccessful() {
		
		return (status == UpdateProcedureResponse.COMPLETE) ;
	}

}
