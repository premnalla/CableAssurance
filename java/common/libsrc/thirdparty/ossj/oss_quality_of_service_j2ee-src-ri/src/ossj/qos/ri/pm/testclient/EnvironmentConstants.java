package ossj.qos.ri.pm.testclient;

/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */

/**
 * Environment property constants.
 *
 * @author Henrik Lindstrom, Ericsson
 */
public interface EnvironmentConstants {
    /**
     * Property name for JVTThresholdMonitorSession JNDI name.
     */
    public static final String THRESHOLD_MONITOR_HOME_JNDI_PROPERTY_NAME =
        "THRESHOLD_MONITOR_HOME_JNDI_NAME";

    /**
     * Property name for performance monitor home JNDI name.
     */
    public static final String PERFORMANCE_MONITOR_HOME_JNDI_PROPERTY_NAME =
        "PERFORMANCE_MONITOR_HOME_JNDI_NAME";

    /**
     * Property name for performance monitor connection factory.
     */
    public static final String PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME =
        "PERFORMANCE_MONITOR_JMS_CONNECTION_FACTORY";

    /**
     * Property name for performance monitor topic.
     */
    public static final String PERFORMANCE_MONITOR_JMS_TOPIC_PROPERTY_NAME =
        "PERFORMANCE_MONITOR_JMS_TOPIC";

    /**
     * Property name for alarm monitor home JNDI name.
     */
    public static final String ALARM_MONITOR_HOME_JNDI_PROPERTY_NAME =
        "ALARM_MONITOR_HOME_JNDI_NAME";

    /**
     * Property name for alarm monitor connection factory.
     */
    public static final String ALARM_MONITOR_JMS_CONNECTION_FACTORY_PROPERTY_NAME =
        "ALARM_MONITOR_JMS_CONNECTION_FACTORY";

    /**
     * Property name for alarm monitor topic.
     */
    public static final String ALARM_MONITOR_JMS_TOPIC_PROPERTY_NAME =
        "ALARM_MONITOR_JMS_TOPIC";

}
