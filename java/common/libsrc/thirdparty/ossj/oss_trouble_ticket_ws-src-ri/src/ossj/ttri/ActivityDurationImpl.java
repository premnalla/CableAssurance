package ossj.ttri;

import javax.oss.trouble.ActivityDuration;
import javax.oss.trouble.TimeLength;

/**
 * ActivityDuration Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class ActivityDurationImpl implements ActivityDuration,
        java.io.Serializable {
    private TimeLength Duration;
    private boolean Billable;
    private int ActivityType;   //enumerated type ActivityType


    public TimeLength getDuration() {
        return Duration;
    }

    public boolean getBillable() {
        return Billable;
    }

    public int getActivityType() {
        return ActivityType;
    }

    public void setDuration(TimeLength value) {
        Duration = value;
    }

    public void setBillable(boolean value) {
        Billable = value;
    }

    public void setActivityType(int value) {
        ActivityType = value;
    }

    public Object clone() {

        ActivityDurationImpl newVal = new ActivityDurationImpl();
        if (getDuration() != null)
            newVal.setDuration((TimeLength) ((TimeLengthImpl) getDuration()).clone());
        else
            newVal.setDuration(null);

        newVal.setBillable(getBillable());
        newVal.setActivityType(getActivityType());
        return newVal;
    }


    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("ActivityDuration:");
        if (Duration != null)
            sb.append(Duration);
        else
            sb.append("null");
        sb.append("\n");
        sb.append("Billable: " + Billable);
        sb.append("\n");
        sb.append("ActivityType: " + ActivityType);
        sb.append("\n");
        return sb.toString();

    }


    public boolean equals(Object adObj) {

        if (adObj == null) return false;
        ActivityDuration adi = (ActivityDuration) adObj;

        if (Billable != adi.getBillable()) return false;
        if (ActivityType != adi.getActivityType()) return false;
        if ((Duration == null) && (adi.getDuration() == null))
            return true;
        if (Duration.equals(adi.getDuration()))
            return true;

        return false;

    }


}
