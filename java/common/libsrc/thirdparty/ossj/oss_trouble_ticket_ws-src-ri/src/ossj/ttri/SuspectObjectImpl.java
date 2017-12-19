package ossj.ttri;

import javax.oss.trouble.SuspectObject;

/**
 * SuspectObject Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class SuspectObjectImpl implements SuspectObject,
        java.io.Serializable {

    private String SuspectObjectType;
    private String SuspectObjectId;
    private int FailureProbability; //percentage (1 - 100)


    public String getSuspectObjectType() {
        return SuspectObjectType;
    }

    public String getSuspectObjectId() {
        return SuspectObjectId;
    }

    public int getFailureProbability() {
        return FailureProbability;
    }

    public void setSuspectObjectType(String value) {
        SuspectObjectType = value;
    }

    public void setSuspectObjectId(String value) {
        SuspectObjectId = value;
    }

    public void setFailureProbability(int value) {
        FailureProbability = value;
    }

    public Object clone() {

        SuspectObjectImpl newVal = new SuspectObjectImpl();
        newVal.setFailureProbability(getFailureProbability());
        if (getSuspectObjectType() != null)
            newVal.setSuspectObjectType(getSuspectObjectType());
        else
            newVal.setSuspectObjectType(null);

        if (getSuspectObjectId() != null)
            newVal.setSuspectObjectId(getSuspectObjectId());
        else
            newVal.setSuspectObjectId(null);


        return newVal;
    }

    public void print(int indent) {

        String sp = null;
        if ((indent > 0) && (indent < 60)) {
            char[] spaces = new char[indent];
            for (int x = 0; x < indent; x++) spaces[x] = ' ';
            sp = new String(spaces);
        }
        Logger.log(sp + "SuspectObject:");
        Logger.log(sp + "     SuspectObjectType: " + SuspectObjectType);
        Logger.log(sp + "       SuspectObjectId: " + SuspectObjectId);
        Logger.log(sp + "    FailureProbability: " + FailureProbability);

    }

    public boolean equals(Object soObj) {

        if (soObj == null) return false;
        SuspectObject so = (SuspectObject) soObj;

        if (!utils.matchString(SuspectObjectType, so.getSuspectObjectType())) return false;
        if (!utils.matchString(SuspectObjectId, so.getSuspectObjectId())) return false;
        if (FailureProbability != so.getFailureProbability()) return false;

        return true;
    }


}
