package ossj.ttri;


/**
 * TroubleTicket Event Base Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public abstract class TroubleTicketEvent implements javax.oss.Event,
        Cloneable {

    //data
    private String eventType;
    private java.util.Date eventTime;
    private String applicationDN;
    private String versionNumber;

    /**
     * Deep copy of this Event
     *
     * @return deep copy of this Event
     */
    public Object clone()
            //throws CloneNotSupportedException
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    //getters
    public String getEventType() {
        return eventType;
    }

    public java.util.Date getEventTime() {
        return eventTime;
    }

    public String getApplicationDN() {
        return applicationDN;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    //setters

    public void setEventType(String eventType)
            throws java.lang.IllegalArgumentException {
        //  this.versionNumber = versionNumber;
        this.eventType = eventType;       //WIPRO
    }

    public void setVersionNumber(String versionNumber)
            throws java.lang.IllegalArgumentException {
        this.versionNumber = versionNumber;
    }

    public void setEventTime(java.util.Date time)
            throws java.lang.IllegalArgumentException {
        this.eventTime = time;
    }

    public void setApplicationDN(String applicationDN)
            throws java.lang.IllegalArgumentException {
        this.applicationDN = applicationDN;
    }

}

