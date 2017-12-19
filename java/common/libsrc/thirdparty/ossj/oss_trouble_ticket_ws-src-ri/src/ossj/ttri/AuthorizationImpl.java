package ossj.ttri;


import javax.oss.trouble.Authorization;
import javax.oss.trouble.PersonReach;

/**
 * Authorization Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class AuthorizationImpl implements Authorization,
        java.io.Serializable {

    private int RequestState;   //enumerated type RequestState
    private int ActivityType;    //enumerated type ActivityType
    private java.util.Date AuthTime;
    private PersonReach AuthPerson;


    public AuthorizationImpl() {
    }

    public AuthorizationImpl(int state, //enumerated type RequestState
                             int type, //enumerated type ActivityType
                             java.util.Date authTime,
                             PersonReach authPerson) {
        this.RequestState = state;
        this.ActivityType = type;
        this.AuthTime = authTime;
        this.AuthPerson = authPerson;
    }

    public int getRequestState() {
        return RequestState;
    }

    public int getActivityType() {
        return ActivityType;
    }

    public java.util.Date getAuthTime() {
        return AuthTime;
    }

    public PersonReach getAuthPerson() {
        return AuthPerson;
    };

    public void setRequestState(int value) {
        RequestState = value;
    }

    public void setActivityType(int value) {
        ActivityType = value;
    }

    public void setAuthTime(java.util.Date value) {
        AuthTime = value;
    }

    public void setAuthPerson(PersonReach value) {
        AuthPerson = value;
    }

    public Object clone() {

        AuthorizationImpl newVal = new AuthorizationImpl();
        newVal.setRequestState(getRequestState());
        newVal.setActivityType(getActivityType());
        if (getAuthTime() != null)
            newVal.setAuthTime((java.util.Date) getAuthTime().clone());
        else
            newVal.setAuthTime(null);
        if (getAuthPerson() != null)
            newVal.setAuthPerson((PersonReach) ((PersonReach) getAuthPerson()).clone());
        else
            newVal.setAuthPerson(null);

        return newVal;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Authorization:");
        sb.append("State: " + RequestState);
        sb.append("Type: " + ActivityType);
        sb.append("AuthTime: " + AuthTime);
        if (AuthPerson != null)
            sb.append("AuthPerson: " + AuthPerson);
        else
            sb.append("AuthPerson: " + "null");

        return sb.toString();

    }


    public boolean equals(Object aiObj) {
        Logger.log("AuthorizationImpl equals Called");
        if (aiObj == null) return false;
        Logger.log("AuthorizationImpl about to cast");
        Authorization ai = (Authorization) aiObj;
        Logger.log("AuthorizationImpl after cast");
        if (RequestState != ai.getRequestState()) return false;
        if (ActivityType != ai.getActivityType()) return false;
        if (!(utils.matchDate(AuthTime, ai.getAuthTime()))) return false;
        if (!(utils.matchPersonReach(AuthPerson, ai.getAuthPerson()))) return false;

        return true;

    }

}
