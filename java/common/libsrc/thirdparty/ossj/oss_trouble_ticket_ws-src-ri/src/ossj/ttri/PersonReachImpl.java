package ossj.ttri;


import javax.oss.trouble.Address;
import javax.oss.trouble.PersonReach;

/**
 * PersonReach Implementation Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class PersonReachImpl implements PersonReach, java.io.Serializable {

    private String Number;
    private String Name;
    private String Phone;
    private Address Location;
    private String Email;
    private String Fax;
    private String Responsible;
    private String SMSAddress;
    private String organizationName;

    public void setNumber(String value)
            throws java.lang.IllegalArgumentException {
        Number = value;
    }

    public void setName(String value)
            throws java.lang.IllegalArgumentException {
        Name = value;
    }

    public void setPhone(String value)
            throws java.lang.IllegalArgumentException {
        Phone = value;
    }

    public void setLocation(Address value)
            throws java.lang.IllegalArgumentException {
        Location = value;
    }

    public void setEmail(String value)
            throws java.lang.IllegalArgumentException {
        Email = value;
    }

    public void setFax(String value)
            throws java.lang.IllegalArgumentException {
        Fax = value;
    }

    public void setResponsible(String value)
            throws java.lang.IllegalArgumentException {
        Responsible = value;
    }

    public void setSMSAddress(String value)
            throws java.lang.IllegalArgumentException {
        SMSAddress = value;
    }

    public void setOrganizationName(String value)
            throws java.lang.IllegalArgumentException {
        organizationName = value;
    }

    public String getNumber() {
        return Number;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public Address getLocation() {
        return Location;
    }

    public String getEmail() {
        return Email;
    }

    public String getFax() {
        return Fax;
    }

    public String getResponsible() {
        return Responsible;
    }

    public String getSMSAddress() {
        return SMSAddress;
    }

    public String getOrganizationName() {
        return organizationName;
    }


    //add to string and reuse print
    public Object clone() {

        PersonReachImpl newVal = new PersonReachImpl();
        if (getNumber() != null)
            newVal.setNumber(new String(getNumber()));
        else
            newVal.setNumber(null);
        if (getName() != null)
            newVal.setName(new String(getName()));
        else
            newVal.setName(null);
        if (getPhone() != null)
            newVal.setPhone(new String(getPhone()));
        else
            newVal.setPhone(null);
        if (getLocation() != null)
            newVal.setLocation((Address) ((Address) getLocation()).clone());
        else
            newVal.setLocation(null);
        if (getEmail() != null)
            newVal.setEmail(new String(getEmail()));
        else
            newVal.setEmail(null);
        if (getFax() != null)
            newVal.setFax(new String(getFax()));
        else
            newVal.setFax(null);
        if (getResponsible() != null)
            newVal.setResponsible(new String(getResponsible()));
        else
            newVal.setResponsible(null);
        if (getSMSAddress() != null)
            newVal.setSMSAddress(new String(getSMSAddress()));
        else
            newVal.setSMSAddress(null);

        if (getOrganizationName() != null)
            newVal.setOrganizationName(new String(getOrganizationName()));
        else
            newVal.setOrganizationName(null);


        return newVal;
    }


    //measure equality for other objects that implement PersonReach i/f
    public boolean equals(Object prObj) {
        // Logger.log("**** PersonReachImpl.equals ****");


        if ((prObj != null) && (prObj instanceof PersonReach)) {
            PersonReach pr = (PersonReach) prObj;

            /*
             Logger.log("**** Number (1) = " + Number);
             Logger.log("**** Number (2) = " + pr.getNumber());
             Logger.log("**** Name (1)= " + Name);
             Logger.log("**** Name (2)= " + pr.getName());
             Logger.log("**** Phone (1)= " + Phone);
             Logger.log("**** Phone (2)= " + pr.getPhone());
             Logger.log("**** Email (1)= " + Email);
             Logger.log("**** Email (2)= " + pr.getEmail());
             Logger.log("**** Fax (1)= " + Fax);
             Logger.log("**** Fax (2)= " + pr.getFax());
             Logger.log("**** Responsible (1)= " + Responsible);
             Logger.log("**** Responsible (2)= " + pr.getResponsible());
             Logger.log("**** SMSAddress (1)= " + SMSAddress);
             Logger.log("**** SMSAddress (2)= " + pr.getSMSAddress());
             Logger.log("**** organizationName (1)= " + organizationName);
             Logger.log("**** organizationName (2)= " + pr.getOrganizationName());

           */


            if (!utils.matchString(Number, pr.getNumber())) {
                //Logger.log("**** Number NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(Name, pr.getName())) {
                //Logger.log("**** Name NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(Phone, pr.getPhone())) {
                //Logger.log("**** Phone NOT EQUAL ****");
                return false;
            }
            if (!utils.matchAddress(Location, pr.getLocation())) {
                //Logger.log("**** Location NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(Email, pr.getEmail())) {
                //Logger.log("**** Email NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(Fax, pr.getFax())) {
                //Logger.log("**** Fax NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(Responsible, pr.getResponsible())) {
                //Logger.log("**** Responsible NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(SMSAddress, pr.getSMSAddress())) {
                //Logger.log("**** SMSAddress NOT EQUAL ****");
                return false;
            }
            if (!utils.matchString(organizationName, pr.getOrganizationName())) {
                //Logger.log("**** organizationName NOT EQUAL ****");
                return false;
            }
            //Logger.log("**** PersonReachImpl EQUAL ****");
            return true;

        }
        //Logger.log("**** PersonReachImpl NOT EQUAL ****");
        return false;
    }


    private void log(String s) {
        Logger.log(s);
    }


}




