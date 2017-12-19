package ossj.ttri;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageNotWriteableException;
import javax.oss.trouble.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Utility Class for the Trouble Ticket Reference Implementation
 *
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */

public class utils {

    public static void setMessageProperties(Message msg,
                                            Hashtable properties) {

        Enumeration keys = properties.keys();
        String key = null;
        Object value = null;

        while (keys.hasMoreElements()) {
            Object okey = keys.nextElement();
            if (!(okey instanceof String))
                Logger.log("utils.setMessageProperties - Invalid property name");
            else
                key = (String) okey;

            value = properties.get(key);

            try {
                if (value instanceof Integer)
                    msg.setIntProperty(key, ((Integer) value).intValue());
                else if (value instanceof Byte)
                    msg.setByteProperty(key, ((Byte) value).byteValue());
                else if (value instanceof Short)
                    msg.setShortProperty(key, ((Short) value).shortValue());
                else if (value instanceof Boolean)
                    msg.setBooleanProperty(key, ((Boolean) value).booleanValue());
                else if (value instanceof Double)
                    msg.setDoubleProperty(key, ((Double) value).doubleValue());
                else if (value instanceof Long)
                    msg.setLongProperty(key, ((Long) value).longValue());
                else if (value instanceof Float)
                    msg.setFloatProperty(key, ((Float) value).floatValue());
                else if (value instanceof String)
                    msg.setStringProperty(key, (String) value);
                else
                    Logger.log("utils::setMessageProperty: unknown value type, property ignored");

            } catch (MessageNotWriteableException mex) {
                Logger.log("utils::setMessageProperty: MessageNotWriteableException occured");
            } catch (JMSException jex) {
                Logger.log("utils::setMessageProperty: JMSException occured");
            }

        }

    }


    public static TroubleTicketValue setDesiredAttributes(TroubleTicketValue ttValIn,
                                                          String[] attrNames)
            throws IllegalArgumentException {

        //null or zero length attrNames means all attrs.  Just return the input ttValue.
        if (attrNames == null) return ttValIn;
        if (attrNames.length == 0) return ttValIn;

        TroubleTicketValue retTTVal = new TroubleTicketValueImpl();
        //System.out.println("Selected attributes: ");

        for (int x = 0; x < attrNames.length; x++) {

            String attrName = attrNames[x];
            //System.out.println(attrName);
            if (attrName.equals(TroubleTicketValue.TROUBLETICKETKEY))
                retTTVal.setTroubleTicketKey(ttValIn.getTroubleTicketKey());
            else if (attrName.equals(TroubleTicketValue.ACTIVITYDURATIONLIST))
                retTTVal.setActivityDurationList(ttValIn.getActivityDurationList());
            else if (attrName.equals(TroubleTicketValue.RELATEDALARMLIST))
                retTTVal.setRelatedAlarmList(ttValIn.getRelatedAlarmList());
            else if (attrName.equals(TroubleTicketValue.ADDITIONALTROUBLEINFOLIST))
                retTTVal.setAdditionalTroubleInfoList(ttValIn.getAdditionalTroubleInfoList());
            else if (attrName.equals(TroubleTicketValue.CLOSEOUTNARR))
                retTTVal.setCloseOutNarr(ttValIn.getCloseOutNarr());
            else if (attrName.equals(TroubleTicketValue.RECEIVEDTIME))
                retTTVal.setReceivedTime(ttValIn.getReceivedTime());
            else if (attrName.equals(TroubleTicketValue.RELATEDTROUBLETICKETKEYLIST))
                retTTVal.setRelatedTroubleTicketKeyList(ttValIn.getRelatedTroubleTicketKeyList());
            else if (attrName.equals(TroubleTicketValue.REPAIRACTIVITYLIST))
                retTTVal.setRepairActivityList(ttValIn.getRepairActivityList());
            else if (attrName.equals(TroubleTicketValue.RESTOREDTIME))
                retTTVal.setRestoredTime(ttValIn.getRestoredTime());
            else if (attrName.equals(TroubleTicketValue.SPROLEASSIGNMENTLIST))
                retTTVal.setSPRoleAssignmentList(ttValIn.getSPRoleAssignmentList());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDESCRIPTION))
                retTTVal.setTroubleDescription(ttValIn.getTroubleDescription());
            else if (attrName.equals(TroubleTicketValue.CLEARANCEPERSON))
                retTTVal.setClearancePerson(ttValIn.getClearancePerson());
            else if (attrName.equals(TroubleTicketValue.TROUBLEFOUND))
                retTTVal.setTroubleFound(ttValIn.getTroubleFound());
            else if (attrName.equals(TroubleTicketValue.TROUBLELOCATION))
                retTTVal.setTroubleLocation(ttValIn.getTroubleLocation());
            else if (attrName.equals(TroubleTicketValue.TROUBLENUMLIST))
                retTTVal.setTroubleNumList(ttValIn.getTroubleNumList());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECT))
                retTTVal.setTroubledObject(ttValIn.getTroubledObject());
            else if (attrName.equals(TroubleTicketValue.TROUBLETYPE))
                retTTVal.setTroubleType(ttValIn.getTroubleType());
            else if (attrName.equals(TroubleTicketValue.TROUBLESTATE))
                retTTVal.setTroubleState(ttValIn.getTroubleState());
            else if (attrName.equals(TroubleTicketValue.TROUBLESTATUS))
                retTTVal.setTroubleStatus(ttValIn.getTroubleStatus());
            else if (attrName.equals(TroubleTicketValue.TROUBLESTATUSTIME))
                retTTVal.setTroubleStatusTime(ttValIn.getTroubleStatusTime());
            else if (attrName.equals(TroubleTicketValue.AFTERHOURSREPAIRAUTHORITY))
                retTTVal.setAfterHoursRepairAuthority(ttValIn.getAfterHoursRepairAuthority());
            else if (attrName.equals(TroubleTicketValue.AUTHORIZATIONLIST))
                retTTVal.setAuthorizationList(ttValIn.getAuthorizationList());
            else if (attrName.equals(TroubleTicketValue.CANCELREQUESTEDBYCUSTOMER))
                retTTVal.setCancelRequestedByCustomer(ttValIn.getCancelRequestedByCustomer());
            else if (attrName.equals(TroubleTicketValue.CLOSEOUTVERIFICATION))
                retTTVal.setCloseOutVerification(ttValIn.getCloseOutVerification());
            else if (attrName.equals(TroubleTicketValue.COMMITMENTTIME))
                retTTVal.setCommitmentTime(ttValIn.getCommitmentTime());
            else if (attrName.equals(TroubleTicketValue.COMMITMENTTIMEREQUESTED))
                retTTVal.setCommitmentTimeRequested(ttValIn.getCommitmentTimeRequested());
            else if (attrName.equals(TroubleTicketValue.CUSTOMERROLEASSIGNMENTLIST))
                retTTVal.setCustomerRoleAssignmentList(ttValIn.getCustomerRoleAssignmentList());
            else if (attrName.equals(TroubleTicketValue.CUSTOMERTROUBLENUM))
                retTTVal.setCustomerTroubleNum(ttValIn.getCustomerTroubleNum());
            else if (attrName.equals(TroubleTicketValue.DIALOG))
                retTTVal.setDialog(ttValIn.getDialog());
            else if (attrName.equals(TroubleTicketValue.ESCALATIONLIST))
                retTTVal.setEscalationList(ttValIn.getEscalationList());
            else if (attrName.equals(TroubleTicketValue.INITIATINGMODE))
                retTTVal.setInitiatingMode(ttValIn.getInitiatingMode());
            else if (attrName.equals(TroubleTicketValue.LASTUPDATETIME))
                retTTVal.setLastUpdateTime(ttValIn.getLastUpdateTime());
            else if (attrName.equals(TroubleTicketValue.MAINTSERVICECHARGE))
                retTTVal.setMaintServiceCharge(ttValIn.getMaintServiceCharge());
            else if (attrName.equals(TroubleTicketValue.OUTAGEDURATION))
                retTTVal.setOutageDuration(ttValIn.getOutageDuration());
            else if (attrName.equals(TroubleTicketValue.PERCEIVEDTROUBLESEVERITY))
                retTTVal.setPerceivedTroubleSeverity(ttValIn.getPerceivedTroubleSeverity());
            else if (attrName.equals(TroubleTicketValue.PREFERREDPRIORITY))
                retTTVal.setPreferredPriority(ttValIn.getPreferredPriority());
            else if (attrName.equals(TroubleTicketValue.REPEATREPORT))
                retTTVal.setRepeatReport(ttValIn.getRepeatReport());
            else if (attrName.equals(TroubleTicketValue.SUSPECTOBJECTLIST))
                retTTVal.setSuspectObjectList(ttValIn.getSuspectObjectList());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDETECTIONTIME))
                retTTVal.setTroubleDetectionTime(ttValIn.getTroubleDetectionTime());
            else if (attrName.equals(TroubleTicketValue.TROUBLELOCATIONINFOLIST))
                retTTVal.setTroubleLocationInfoList(ttValIn.getTroubleLocationInfoList());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSFROMTIME))
                retTTVal.setTroubledObjectAccessFromTime(ttValIn.getTroubledObjectAccessFromTime());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSHOURSLIST))
                retTTVal.setTroubledObjectAccessHoursList(ttValIn.getTroubledObjectAccessHoursList());
            else if (attrName.equals(TroubleTicketValue.TROUBLEDOBJECTACCESSTOTIME))
                retTTVal.setTroubledObjectAccessToTime(ttValIn.getTroubledObjectAccessToTime());
            else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEBEGINTIME))
                retTTVal.setServiceUnavailableBeginTime(ttValIn.getServiceUnavailableBeginTime());
            else if (attrName.equals(TroubleTicketValue.SERVICEUNAVAILABLEENDTIME))
                retTTVal.setServiceUnavailableEndTime(ttValIn.getServiceUnavailableEndTime());
            else if (attrName.equals(TroubleTicketValue.ORIGINATOR))
                retTTVal.setOriginator(ttValIn.getOriginator());
            else if (attrName.equals(TroubleTicketValue.TROUBLESYSTEMDN))
                retTTVal.setTroubleSystemDN(ttValIn.getTroubleSystemDN());
            else if (attrName.equals(TroubleTicketValue.CUSTOMER))
                retTTVal.setCustomer(ttValIn.getCustomer());
            else if (attrName.equals(TroubleTicketValue.ACCOUNTOWNER))
                retTTVal.setAccountOwner(ttValIn.getAccountOwner());
            else {
                Logger.log("utils:setDesiredAttributes - unknown attribute name: " + attrName);
                //throw new IllegalArgumentException();
            }

        } //end for


        return retTTVal;

    }

    //"match" methods used by equality operators for complex attributes.
    public static boolean matchString(String str1, String str2) {


        if ((str1 == null) && (str2 == null)) return true;
        if ((str1 != null) && (str2 != null))
            return str1.equals(str2);
        return false;
    }

    public static boolean matchPersonReach(PersonReach pr1, PersonReach pr2) {

        if ((pr1 == null) && (pr2 == null)) return true;
        if ((pr1 != null) && (pr2 != null))
            return ((PersonReachImpl) pr1).equals(pr2);
        return false;

    }

    public static boolean matchDate(java.util.Date date1, java.util.Date date2) {
        if ((date1 == null) && (date2 == null)) return true;
        if ((date1 != null) && (date2 != null)) {
            Logger.log("---date1---" + date1);
            Logger.log("---date2---" + date2);
            String date1String = new String(date1.toString());
            String date2String = new String(date2.toString());
            boolean val = date1String.equals(date2String);
            if (val == true) {
                Logger.log("---Dates are equal---");
            } else {
                Logger.log("---Dates are not equal---");
            }
            return val;
        }
        return false;
    }

    public static boolean matchAddress(Address addr1, Address addr2) {
        if ((addr1 == null) && (addr2 == null)) return true;
        if ((addr1 != null) && (addr2 != null))
            return (addr1).equals(addr2);
        return false;
    }

    public static boolean matchTime(Time tm1, Time tm2) {
        if ((tm1 == null) && (tm2 == null)) return true;
        if ((tm1 != null) && (tm2 != null))
            return ((TimeImpl) tm1).equals(tm2);
        return false;
    }

    public static boolean matchTimeInterval(TimeInterval ti1, TimeInterval ti2) {
        if ((ti1 == null) && (ti2 == null)) return true;
        if ((ti1 != null) && (ti2 != null))
            return ((TimeInterval) ti1).equals(ti2);
        return false;
    }

    public static boolean matchAccessHours(AccessHours ah1, AccessHours ah2) {
        if ((ah1 == null) && (ah2 == null)) return true;
        if ((ah1 != null) && (ah2 != null))
            return ((AccessHoursImpl) ah1).equals(ah2);
        return false;
    }

    public static boolean testEqualityOfArrays(Object[] ArrayArg1, Object[] ArrayArg2) {
        if ((ArrayArg1 == null) && (ArrayArg2 != null)) return false;
        if ((ArrayArg1 != null) && (ArrayArg2 == null)) return false;
        Collection col1 = Arrays.asList(ArrayArg1);
        Collection col2 = Arrays.asList(ArrayArg2);

        if (col1.containsAll(col2))
            return true;
        else
            return false;
    }

    public static boolean compareValues(TroubleTicketValue ttVal1, TroubleTicketValue ttVal2) {


        String[] attrNamesForVal1 = ttVal1.getPopulatedAttributeNames();
        String[] attrNamesForVal2 = ttVal2.getPopulatedAttributeNames();
        log("-------START COMPARE------------------");

        log("Number of Populated Attributes in Value 1");
        log(new Integer(attrNamesForVal1.length).toString());

        log("Number of Populated Attributes in Value 2");

        log(new Integer(attrNamesForVal2.length).toString());


        if (attrNamesForVal1.length == attrNamesForVal2.length) {
            log("Same number of Populated Attributes");
        } else {
            log("Different number of Populated Attributes");
        }

        for (int i = 0; i < attrNamesForVal1.length; i++) {


            Object attributeValue1 = null;
            Object attributeValue2 = null;
            boolean att1isGet = false;
            boolean att2isGet = false;
            try {


                log("-----Testing Equality for Attribute----->" + attrNamesForVal1[i]);


                att1isGet = true;
                attributeValue1 = ttVal1.getAttributeValue(attrNamesForVal1[i]);
                att1isGet = false;

                if (attributeValue1 == null) {
                    log("--Attribute Value 1 is NULL---");
                    log(attrNamesForVal1[i]);
                }
                log(attrNamesForVal1[i]);

                att2isGet = true;
                attributeValue2 = ttVal2.getAttributeValue(attrNamesForVal1[i]);
                att2isGet = false;


                if (compareAttributes(attributeValue1, attributeValue2)) {
                    log("Attribute are the same");
                } else {
                    log("Attribute Values are not the same for: " + attrNamesForVal1[i]);

                    return false;
                }


            } catch (java.lang.IllegalArgumentException e1) {

                log("IllegalArgumentException Exception getting attribute value on");
                if (att2isGet)
                    log("On Value 2 ");
                else
                    log("On Value 1");
                log(attrNamesForVal1[i]);
                return false;
            } catch (java.lang.IllegalStateException e2) {
                log("IllegalStateException Exception getting attribute value on");
                if (att2isGet)
                    log("On Value 2 ");
                else
                    log("On Value 1");
                log(attrNamesForVal1[i]);
                return false;
            } catch (Exception e) {
                log("System Exception getting attribute value on");
                log("Exception is: " + e.getMessage());
                e.printStackTrace();
                if (att2isGet)
                    log("On Value 2 ");
                else
                    log("On Value 1");
                log(attrNamesForVal1[i]);
                log(e.toString());
                String failure = "System Exception getting attribute value on" + attrNamesForVal1[i];
                log("SYSTEM EXCEPTION" + failure);

                return false;


            }
        }
        return true;
    }

    public static boolean compareAttributes(Object attributeValue1, Object attributeValue2) {


        if ((attributeValue1 == null) && (attributeValue2 == null)) return true;
        if ((attributeValue1 != null) && (attributeValue2 == null)) return false;
        if ((attributeValue1 == null) && (attributeValue2 != null)) return false;

        log("------Attribute Values are-------");
        log(attributeValue1.getClass().getName());
        boolean isArray = attributeValue1.getClass().isArray();
        if (isArray) {
            log("------This is an Array-------");
        } else {
            log("------This is not an Array-------");
        }


        log(attributeValue1.toString());

        log(attributeValue2.toString());
        if (isArray) {
            log("START Processing Array Attribute");
            return compareArrays((Object[]) attributeValue1, (Object[]) attributeValue2);

        } else {

            if (attributeValue1.equals(attributeValue2)) {

                log("attribute values are equal");
                return true;

            } else {
                log("attribute values are not equal");
                return false;

            }

        }


    }

    public static boolean compareArrays(Object[] attributeValue1, Object[] attributeValue2) {

        log("-----TEST EQUALITY OF ARRAYS--- ");
        if (attributeValue1.length == attributeValue2.length) {
            log("-----SAME LENGTH--- ");
        } else {
            log("-----NOT SAME LENGTH--- ");

            return false;

        }


        for (int i = 0; i < attributeValue1.length; i++) {
            log("--- BEGIN OF attribute value[i]----");
            log(attributeValue1[i].toString());
            log(attributeValue2[i].toString());
            log("----END OF attribute value[i]---");
        }

        log("BEFORE COLLECTION COMPARE");


        Collection col1 = Arrays.asList(attributeValue1);
        Collection col2 = Arrays.asList(attributeValue2);


        if (col1.containsAll(col2)) {
            log("attribute ARRAY values are equal under COLLECTION");
            return true;
        } else {
            log("attribute ARRAY values are NOT equal under COLLECTION");

            return false;
        }

    }

    public static void log(String log) {
        Logger.log(log);
    }


}














