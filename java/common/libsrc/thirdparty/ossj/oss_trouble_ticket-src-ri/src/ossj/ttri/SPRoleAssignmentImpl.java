package ossj.ttri;

import javax.oss.trouble.PersonReach;
import javax.oss.trouble.SPRoleAssignment;

/**
 * SPRoleAssignment Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class SPRoleAssignmentImpl implements SPRoleAssignment,
        java.io.Serializable {

    private String Role;
    private PersonReach ContactPerson;


    public String getRole() {
        return Role;
    }

    public PersonReach getContactPerson() {
        return ContactPerson;
    }

    public void setRole(String value) {
        Role = value;
    }

    public void setContactPerson(PersonReach value) {
        ContactPerson = value;
    }

    public Object clone() {

        SPRoleAssignmentImpl newVal = new SPRoleAssignmentImpl();
        if (getRole() != null)
            newVal.setRole(getRole());
        else
            newVal.setRole(null);

        if (getContactPerson() != null)
            newVal.setContactPerson((PersonReach) getContactPerson().clone());
        else
            newVal.setContactPerson(null);

        return newVal;
    }


    public boolean equals(Object spraObj) {

        if (spraObj == null) return false;
        SPRoleAssignment spra = (SPRoleAssignment) spraObj;

        if (!utils.matchString(Role, spra.getRole())) return false;
        if (!utils.matchPersonReach(ContactPerson, spra.getContactPerson())) return false;

        return true;
    }


}
