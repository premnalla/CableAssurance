/**
 * MtaAlarmConfigT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.palmyrasys.www.webservices.CableAssurance;

// Prem:
//import java.util.List;
//import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.palmyrasyscorp.www.jsp.bean.AdminMtaAlarmFormBean;
import com.palmyrasyscorp.www.jsp.helper.HourOfDayListBox;
import com.palmyrasyscorp.www.jsp.helper.SoakDurationListBox;
import com.palmyrasyscorp.db.tables.AppConfig;

public class MtaAlarmConfigT  implements java.io.Serializable {

	private static Log log = LogFactory.getLog(MtaAlarmConfigT.class);

    private com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes;

    public MtaAlarmConfigT() {
    }

    public MtaAlarmConfigT(
           com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes) {
           this.alarmTypes = alarmTypes;
    }


    /**
     * @author Prem
     * @param bean
     */
    public MtaAlarmConfigT(AdminMtaAlarmFormBean bean) {
    	
    	try {
	    	AlarmTypeConfigT atc;
	    	SoakWindowT sw;
	    	
	    	LinkedList l = new LinkedList();
	    	
	    	HourOfDayListBox hod = HourOfDayListBox.getInstance();
	    	SoakDurationListBox sd = SoakDurationListBox.getInstance();
	
	    	/*
			 * Unavailable alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_unavail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaUnavailWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaUnavailWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaUnavailWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaUnavailWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * CMS LOC alarm
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_cmsloc_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaCmsLocWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaCmsLocWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaCmsLocWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaCmsLocWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * Battery Missing
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_battmiss_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaBattMissWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaBattMissWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaBattMissWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaBattMissWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * On-Battery
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_onbatt_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaOnBattWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaOnBattWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaOnBattWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaOnBattWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * Replace Battery
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_replbatt_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaReplBattWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaReplBattWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaReplBattWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaReplBattWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * Hardware Failure
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_hwfail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaHwFailWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaHwFailWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaHwFailWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaHwFailWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);
			
			/*
			 * Line Card Failure
			 */
			atc = new AlarmTypeConfigT();
			atc.setAlarmType(AppConfig.mta_lcfail_alarm_nm);
			sw = new SoakWindowT();
			sw.setSoakWindow_1_Duration(sd.convertToStrSeconds(bean.getMtaLcFailWin1SoakDuration()));
			sw.setSoakWindow_1_StartTime(hod.convertToStrHour(bean.getMtaLcFailWin1SoakStartTime()));
			sw.setSoakWindow_2_Duration(sd.convertToStrSeconds(bean.getMtaLcFailWin2SoakDuration()));
			sw.setSoakWindow_2_StartTime(hod.convertToStrHour(bean.getMtaLcFailWin2SoakStartTime()));
			atc.setSoakWindow(sw);
			l.add(atc);		
	
			this.alarmTypes = new AlarmTypeConfigT[l.size()];
			for (int i=0; i<l.size(); i++) {
				this.alarmTypes[i] = (AlarmTypeConfigT) l.get(i);
			}
			l.clear();
    	} catch (Exception e) {
			// e.printStackTrace();
			log.error(null, e);	    		
    	}
    }
    
    
    /**
     * Gets the alarmTypes value for this MtaAlarmConfigT.
     * 
     * @return alarmTypes
     */
    public com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] getAlarmTypes() {
        return alarmTypes;
    }


    /**
     * Sets the alarmTypes value for this MtaAlarmConfigT.
     * 
     * @param alarmTypes
     */
    public void setAlarmTypes(com.palmyrasys.www.webservices.CableAssurance.AlarmTypeConfigT[] alarmTypes) {
        this.alarmTypes = alarmTypes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MtaAlarmConfigT)) return false;
        MtaAlarmConfigT other = (MtaAlarmConfigT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.alarmTypes==null && other.getAlarmTypes()==null) || 
             (this.alarmTypes!=null &&
              java.util.Arrays.equals(this.alarmTypes, other.getAlarmTypes())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAlarmTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAlarmTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAlarmTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MtaAlarmConfigT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "MtaAlarmConfigT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alarmTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alarmTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.palmyrasys.com/webservices/CableAssurance", "AlarmTypeConfigT"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
