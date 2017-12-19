/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.cbe.report;

import java.rmi.RemoteException;

import javax.oss.cbe.report.ReportInfo;
import javax.oss.cbe.report.ReportInfoIterator;


/**
 * An implementation class for the <CODE>javax.oss.cbe.report.ReportInfoIterator</CODE> interface.
 *
 * @see javax.oss.cbe.report.ReportInfoIterator
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ReportInfoIteratorImpl implements ReportInfoIterator {
    private ReportInfo[] values = null;
    private int currentPos = -1;
    
    /**
     * Constructs a new ReportInfoIteratorImpl with empty attributes.
     *
     */
    private ReportInfoIteratorImpl() {
    }
    
    /**
     * Creates a new ReportInfoIteratorImpl object.
     *
     * @param givenValues
     */
    public ReportInfoIteratorImpl(ReportInfo[] givenValues) {
        
        if (values != null) {
            values = new ReportInfoImpl[givenValues.length];
            System.arraycopy(givenValues, 0, values, 0, givenValues.length);
        }
    }
    /**
     * same as getNext
     */
    public ReportInfo[] getNextReportInfos(int howMany) throws RemoteException {
        return getNext(howMany);
    }
    
    /**
     * Returns a list of measurement report information.
     *
     *@return ReportInfo[] List of measurement report keys.
     *@param howMany Maximum of items to return, the implementation can decide to return less.
     *@exception java.rmi.RemoteException Is raised when a unexpected system exception occurrs.
     */
    public ReportInfo[] getNext(int howMany) throws RemoteException {
        int numberOfObjectsToReturn;
        ReportInfo[] nextValues;
        
        if (values == null) {
            return new ReportInfoImpl[0];
        }
        
        // Determine the number of objects to return
        if ((currentPos + howMany) <= (values.length - 1)) {
            numberOfObjectsToReturn = howMany;
        } else {
            numberOfObjectsToReturn = (values.length - 1) - currentPos;
        }
        
        if (numberOfObjectsToReturn > 0) {
            nextValues = new ReportInfoImpl[numberOfObjectsToReturn];
            
            try {
                System.arraycopy(values, currentPos + 1, nextValues, 0, numberOfObjectsToReturn);
                currentPos += numberOfObjectsToReturn;
            } catch (Exception e) {
                throw new RemoteException();
            }
        } else {
            nextValues = new ReportInfoImpl[0];
        }
        
        return nextValues;
    }
    
    
    /**
     * Remove the iterator.
     */
    public void remove() {
        values = null;
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        ReportInfoIterator val = null;
        try {
            val = (ReportInfoIterator)super.clone();
            
        } catch (CloneNotSupportedException cnse) {
            //cnse.printStackTrace();
            throw new java.lang.RuntimeException("ManagedEntityKeyImpl: Error invoking clone() CloneNotSupportedException, " +cnse.getMessage());
            //return null;
        }
        return val;
    }
}
