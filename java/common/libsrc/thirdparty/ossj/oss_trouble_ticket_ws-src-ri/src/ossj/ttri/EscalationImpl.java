package ossj.ttri;

import javax.oss.trouble.Escalation;
import javax.oss.trouble.PersonReach;

/**
 * Escalation Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public final class EscalationImpl implements Escalation,
        java.io.Serializable,
        java.lang.Cloneable {

    private int RequestState;      //enumerated type RequestStateType
    private java.util.Date EscalationTime;
    private PersonReach RequestPerson;
    private int Level;      //enumerated type OrgLevelType
    private PersonReach EscalationPerson;


    public EscalationImpl() {
    }

    public EscalationImpl(int stateIn, //enumerated type RequestStateType
                          java.util.Date escTime,
                          PersonReach requestPerson,
                          int level, //enumerated type OrgLevelType
                          PersonReach escPerson) {
        this.RequestState = stateIn;
        this.EscalationTime = escTime;
        this.RequestPerson = requestPerson;
        this.Level = level;
        this.EscalationPerson = escPerson;
    }

    public int getRequestState() {
        return RequestState;
    }

    public java.util.Date getEscalationTime() {
        return EscalationTime;
    }

    public PersonReach getRequestPerson() {
        return RequestPerson;
    }

    public int getLevel() {
        return Level;
    }

    public PersonReach getEscalationPerson() {
        return EscalationPerson;
    }

    public void setRequestState(int value) {
        RequestState = value;
    }

    public void setEscalationTime(java.util.Date value) {
        EscalationTime = value;
    }

    public void setRequestPerson(PersonReach value) {
        RequestPerson = value;
    }

    public void setLevel(int value) {
        Level = value;
    }

    public void setEscalationPerson(PersonReach value) {
        EscalationPerson = value;
    }

    public Object clone() {

        EscalationImpl newVal = new EscalationImpl();
        newVal.setRequestState(getRequestState());
        if (getEscalationTime() != null) {
            newVal.setEscalationTime((java.util.Date) getEscalationTime().clone());
        } else {
            newVal.setEscalationTime(null);
        }
        if (getRequestPerson() != null) {
            newVal.setRequestPerson((PersonReach) ((PersonReach) getRequestPerson()).clone());
        } else {
            newVal.setRequestPerson(null);
        }

        newVal.setLevel(getLevel());

        if (getEscalationPerson() != null) {
            newVal.setEscalationPerson((PersonReach) ((PersonReachImpl) getEscalationPerson()).clone());
        } else {
            newVal.setEscalationPerson(null);
        }


        return newVal;
    }


    public boolean equals(Object escaObj) {
        if ((escaObj != null) && (escaObj instanceof Escalation)) {
            Escalation esca = (Escalation) escaObj;

            Logger.log("---Escalation equals---");


            if (RequestState != esca.getRequestState()) {
                Logger.log("---RequestState not equal---");
                return false;
            }
            if (Level != esca.getLevel()) {
                Logger.log("---Level not equal---");
                return false;
            }
            if (!utils.matchDate(EscalationTime, esca.getEscalationTime())) {
                Logger.log("---EscalationTime---" + EscalationTime);
                Logger.log("---esca.getEscalationTime()---" + esca.getEscalationTime());
                Logger.log("---EscalationTime not equal---");
                return false;
            }
            if (!utils.matchPersonReach(RequestPerson, esca.getRequestPerson())) {
                Logger.log("---RequestPerson not equal---");
                return false;
            }
            if (!utils.matchPersonReach(EscalationPerson, esca.getEscalationPerson())) {
                Logger.log("---EscalationPerson not equal---");
                return false;
            }
            Logger.log("---Escalation equals TRUE ---");
            return true;
        }
        Logger.log("---Escalation equals FALSE ---");
        return false;
    }

}
