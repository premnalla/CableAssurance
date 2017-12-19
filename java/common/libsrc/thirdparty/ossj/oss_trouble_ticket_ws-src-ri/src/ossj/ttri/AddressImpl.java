package ossj.ttri;

import javax.oss.trouble.Address;

/**
 * Address Implementation Class
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class AddressImpl implements Address, java.io.Serializable {

    private String addressInfo;

    public void setAddressInfo(String addressInfo)
            throws java.lang.IllegalArgumentException {
        this.addressInfo = addressInfo;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public Object clone() {

        AddressImpl newVal = new AddressImpl();

        if (getAddressInfo() != null)
            newVal.setAddressInfo(new String(getAddressInfo()));
        else
            newVal.setAddressInfo(null);

        return newVal;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Address:");
        sb.append("AddressInfo: " + addressInfo);
        return sb.toString();

    }


    public boolean equals(Object addrObj) {

        Logger.log("**** AddressImpl.equals ****");
        if (addrObj == null) return false;

        Address addr = (Address) addrObj;
        if (!utils.matchString(addressInfo, addr.getAddressInfo())) return false;

        return true;

    }

}
