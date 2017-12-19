package ossj.qos.fmri;

import javax.oss.fm.monitor.AlarmValue;
import javax.oss.fm.monitor.JVTAlarmMonitorSession;
import javax.oss.fm.monitor.JVTAlarmMonitorSessionOptionalOpt;
import javax.oss.fm.monitor.QueryByFilterableAttributesValue;

/**
 * SessionResourceConstants
 *
 * @author  Audrey Ward
 * @version 1.0 
 *
 * ? Copyright 2001-2002, MOTOROLA INC. 
 * All Rights Reserved
 */
public interface SessionResourceConstants {
    
    public static final String AM_CONNECTION_FACTORY=
    "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/TopicConnectionFactory";
    public static final String AM_TOPIC=
    "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTEventTopic";
    public static final String APPLICATION_DOMAIN =
    "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTHome";
    public static final String CURRENT_VERSION = "v1.0";
    
    public static final String BEAM_TOPIC=
    "Private/System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/BackEndEventTopic";
    
    //public static final String FMDB = "jdbc.com.motorola.oss.ossj.qos.fm.ri.DataSource";
    //VP
    // public static final String FMDB = "java:comp/env/jdbc/ossjqosfmri";
    public static final String FMDB = "jdbc/ossjqosfmri";
    
    
    public static final String ALARM_ITERATOR_HOME = "Private/System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/AlarmValueIteratorHome";
    
    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";  
    
    public static final String JVT_HOME = "System/DFW/ApplicationType/QoS/AlarmMonitor/Application/1-0;1-0;JSR90FMRI/Comp/JVTHome";
    
    //supported query types
    public static final String[] queryTypes = { QueryByFilterableAttributesValue.QUERY_TYPE };

    //supported optional operations
    public static final String[] supportedOptionalOperations = { JVTAlarmMonitorSessionOptionalOpt.TRY_UNACKNOWLEDGE_ALARMS,
                                                                 JVTAlarmMonitorSessionOptionalOpt.QUERY_ALARM_COUNTS,
                                                                 JVTAlarmMonitorSessionOptionalOpt.TRY_COMMENT_ALARMS }; 
    
    public static final String[] managedEntityTypes = { AlarmValue.VALUE_TYPE };
}
