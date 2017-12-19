package ossj.ttri;

import javax.oss.trouble.PersonReach;
import javax.oss.trouble.RepairActivity;

/**
 * RepairActivity Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class RepairActivityImpl implements RepairActivity,
        java.io.Serializable {

    private java.util.Date EntryTime;
    private String ActivityInfo;
    private PersonReach ActivityPerson;
    private int ActivityCode;    //enumerated type ActivityCode


    public java.util.Date getEntryTime() {
        return EntryTime;
    }

    public String getActivityInfo() {
        return ActivityInfo;
    }

    public PersonReach getActivityPerson() {
        return ActivityPerson;
    }

    public int getActivityCode() {
        return ActivityCode;
    }

    public void setEntryTime(java.util.Date value) {
        EntryTime = value;
    }

    public void setActivityInfo(String value) {
        ActivityInfo = value;
    }

    public void setActivityPerson(PersonReach value) {
        ActivityPerson = value;
    }

    public void setActivityCode(int value) {
        ActivityCode = value;
    }

    public Object clone() {

        RepairActivityImpl newVal = new RepairActivityImpl();

        newVal.setActivityCode(getActivityCode());

        if (getActivityInfo() != null)
            newVal.setActivityInfo(new String(getActivityInfo()));
        else
            newVal.setActivityInfo(null);

        if (getEntryTime() != null)
            newVal.setEntryTime((java.util.Date) getEntryTime().clone());
        else
            newVal.setEntryTime(null);

        if (getActivityPerson() != null)
            newVal.setActivityPerson((PersonReach) ((PersonReach) getActivityPerson()).clone());
        else
            newVal.setActivityPerson(null);

        return newVal;
    }

    public void print(int indent) {

        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }
        Logger.log(sp + "RepairActivity:");
        Logger.log(sp + "         EntryTime: " + EntryTime);
        Logger.log(sp + "      ActivityInfo: " + ActivityInfo);
        Logger.log(sp + "    ActivityPerson: " + ActivityPerson);
        Logger.log(sp + "      ActivityCode: " + ActivityCode);

    }

    //PG check casting not safe

    public boolean equals(Object raObj) {

        if (raObj == null) return false;
        RepairActivity ra = (RepairActivity) raObj;

        if (!utils.matchDate(EntryTime, ra.getEntryTime())) return false;
        if (!utils.matchString(ActivityInfo, ra.getActivityInfo())) return false;
        if (!utils.matchPersonReach(ActivityPerson, ra.getActivityPerson())) return false;
        if (ActivityCode != ra.getActivityCode()) return false;

        return true;
    }


}
