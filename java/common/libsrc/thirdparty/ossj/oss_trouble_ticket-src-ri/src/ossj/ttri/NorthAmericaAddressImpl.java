package ossj.ttri;

import javax.oss.trouble.NorthAmericaAddress;

/**
 * NorthAmericaAddress Implementation Class
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class NorthAmericaAddressImpl implements NorthAmericaAddress, java.io.Serializable {

    private String CivicAddress = null;
    private String City = null;
    private String State = null;
    private String Zip = null;
    private String addressInfo = null;


    public String getAddressInfo() {
        return addressInfo;
    }

    public String getCivicAddress() {
        return CivicAddress;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getZip() {
        return Zip;
    }

    public void setCivicAddress(String value)
            throws java.lang.IllegalArgumentException {
        CivicAddress = value;
    }

    public void setCity(String value)
            throws java.lang.IllegalArgumentException {
        City = value;
    }

    public void setState(String value)
            throws java.lang.IllegalArgumentException {
        State = value;
    }

    public void setZip(String value)
            throws java.lang.IllegalArgumentException {
        Zip = value;
    }

    public void setAddressInfo(String value)
            throws java.lang.IllegalArgumentException {
        addressInfo = value;
    }

    public Object clone() {
        Logger.log("**** NorthAmericaAddressImpl.clone() ****");
        NorthAmericaAddress newVal = new NorthAmericaAddressImpl();
        if (getCivicAddress() != null)
            newVal.setCivicAddress(new String(getCivicAddress()));
        else
            newVal.setCivicAddress(null);
        if (getCity() != null)
            newVal.setCity(new String(getCity()));
        else
            newVal.setCity(null);
        if (getState() != null)
            newVal.setState(new String(getState()));
        else
            newVal.setState(null);
        if (getZip() != null)
            newVal.setZip(new String(getZip()));
        else
            newVal.setZip(null);
        if (getAddressInfo() != null)
            newVal.setAddressInfo(new String(getAddressInfo()));
        else
            newVal.setAddressInfo(null);

        return newVal;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Address:");
        sb.append("CivicAddress: " + CivicAddress);
        sb.append("City: " + City);
        sb.append("State: " + State);
        sb.append("Zip: " + Zip);
        return sb.toString();

    }


    public boolean equals(Object addrObj) {

        //Logger.log("**** NorthAmericaAddressImpl.equals ****");
        if (addrObj == null) return false;


        NorthAmericaAddress addr = (NorthAmericaAddress) addrObj;
        /*
        Logger.log("**** addressInfo (1) = " + getAddressInfo());

        Logger.log("**** addressInfo (2) = " + addr.getAddressInfo());
        Logger.log("**** CivicAddress (1)= " + CivicAddress);
        Logger.log("**** CivicAddress (2)= " + addr.getCivicAddress());
        Logger.log("**** City (1)= " + City);
        Logger.log("**** City (2)= " + addr.getCity());
        Logger.log("**** State (1)= " + State);
        Logger.log("**** State (2)= " + addr.getState());
        Logger.log("**** Zip (1)= " + Zip);
        Logger.log("**** Zip (2)= " + addr.getZip());


        */

        if (!utils.matchString(CivicAddress, addr.getCivicAddress())) {
            //Logger.log("**** CivicAddress NOT EQUAL ****");
            return false;
        }

        if (!utils.matchString(addressInfo, addr.getAddressInfo())) {
            //Logger.log("**** addressInfo NOT EQUAL ****");
            return false;
        }

        if (!utils.matchString(City, addr.getCity())) {
            //Logger.log("**** City NOT EQUAL ****");
            return false;
        }
        if (!utils.matchString(State, addr.getState())) {
            //Logger.log("**** State NOT EQUAL ****");
            return false;
        }
        if (!utils.matchString(Zip, addr.getZip())) {
            //Logger.log("**** Zip NOT EQUAL ****");
            return false;
        }

        return true;

    }

}
