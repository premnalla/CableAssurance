package ossj.ttri;

import javax.oss.trouble.AccessHours;
import javax.oss.trouble.Address;
import javax.oss.trouble.PersonReach;
import javax.oss.trouble.TroubleLocationInfo;

/**
 * TimeLocation Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public final class TroubleLocationInfoImpl implements TroubleLocationInfo,
        java.io.Serializable {


    private Address PremiseAddress;
    private String PremiseName;
    private String ObjectIdDN;
    private PersonReach LocationPerson;
    private AccessHours[] AccessHoursList;


    public TroubleLocationInfoImpl() {
    }


    public Address getPremiseAddress() {
        return PremiseAddress;
    }

    public String getPremiseName() {
        return PremiseName;
    }

    public String getObjectIdDN() {
        return ObjectIdDN;
    }

    public PersonReach getLocationPerson() {
        return LocationPerson;
    }

    public AccessHours[] getAccessHoursList() {
        return AccessHoursList;
    }

    public void setPremiseAddress(Address value) {
        PremiseAddress = value;
    }

    public void setPremiseName(String value) {
        PremiseName = value;
    }

    public void setObjectIdDN(String value) {
        ObjectIdDN = value;
    }

    public void setLocationPerson(PersonReach value) {
        LocationPerson = value;
    }

    public void setAccessHoursList(AccessHours[] value) {
        AccessHoursList = value;
    }

    public Object clone() {


        TroubleLocationInfoImpl newVal = new TroubleLocationInfoImpl();

        if (getPremiseName() != null)
            newVal.setPremiseName(new String(getPremiseName()));
        else
            newVal.setPremiseName(null);

        if (getObjectIdDN() != null)
            newVal.setObjectIdDN(new String(getObjectIdDN()));
        else
            newVal.setObjectIdDN(null);

        if (getLocationPerson() != null)
            newVal.setLocationPerson((PersonReach) ((PersonReach) getLocationPerson()).clone());
        else
            newVal.setLocationPerson(null);

        if (getPremiseAddress() != null)
            newVal.setPremiseAddress((Address) ((Address) getPremiseAddress()).clone());
        else
            newVal.setPremiseAddress(null);
        if (AccessHoursList != null) {
            AccessHours[] newAccessHoursList = new AccessHours[AccessHoursList.length];
            for (int i = 0; i < AccessHoursList.length; i++) {
                newAccessHoursList[i] = (AccessHours) ((AccessHours) AccessHoursList[i]).clone();
            }
            newVal.setAccessHoursList(newAccessHoursList);
        } else
            newVal.setAccessHoursList(null);


        return newVal;
    }


    //PG not type safe casting
    public boolean equals(Object tliObj) {
        if (tliObj == null) return false;
        TroubleLocationInfo tli = (TroubleLocationInfo) tliObj;

        if (!utils.matchAddress(PremiseAddress, tli.getPremiseAddress())) return false;
        if (!utils.matchString(PremiseName, tli.getPremiseName())) return false;
        if (!utils.matchString(ObjectIdDN, tli.getObjectIdDN())) return false;
        if (!utils.matchPersonReach(LocationPerson, tli.getLocationPerson())) return false;

        if ((AccessHoursList == null) && (tli.getAccessHoursList() == null)) return true;

        AccessHours[] ahList = tli.getAccessHoursList();
        if ((AccessHoursList != null) && (ahList != null)) {
            if (AccessHoursList.length != ahList.length) return false;

            for (int x = 0; x < AccessHoursList.length; x++)
                if (!utils.matchAccessHours(AccessHoursList[x], ahList[x])) return false;
        }

        return true;

    }

    private void log(String s) {
        Logger.log(s);
    }


}
