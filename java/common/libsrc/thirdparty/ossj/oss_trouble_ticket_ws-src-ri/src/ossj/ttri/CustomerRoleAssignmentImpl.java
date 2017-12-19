package ossj.ttri;

import javax.oss.trouble.CustomerRoleAssignment;
import javax.oss.trouble.PersonReach;

/**
 * CustomerRoleAssignment Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class CustomerRoleAssignmentImpl implements CustomerRoleAssignment,
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

        CustomerRoleAssignmentImpl newVal = new CustomerRoleAssignmentImpl();
        if (getRole() != null)
            newVal.setRole(getRole());
        else
            newVal.setRole(null);

        if (getContactPerson() != null)
            newVal.setContactPerson((PersonReach) ((PersonReach) getContactPerson()).clone());
        else
            newVal.setContactPerson(null);

        return newVal;
    }


    public boolean equals(Object craObj) {

        if (craObj == null) return false;
        CustomerRoleAssignment cra = (CustomerRoleAssignment) craObj;
        if (!utils.matchString(Role, cra.getRole())) return false;
        if (!utils.matchPersonReach(ContactPerson, cra.getContactPerson())) return false;
        return true;

    }

}
