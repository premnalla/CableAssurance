/*
 * Copyright © 2001 Ericsson Radio Systems AB. All rights reserved.
 * Use is subject to license terms.
 */
package ossj.qos.ri.pm.threshold.adapter;

/**
 * Message constants used by the threshold monitor EJBs.
 * @author Henrik Lindstrom, Ericsson
 */
public interface MessageConstants {
    /**
     *  Error message for not supported value type.
     */
    public static final String ERR_VALUE_TYPE_NOT_SUPPORTED =
        "The value type is not supported.";

    /**
     * Error message when attribute name array is null.
     */
    public static final String ERR_ATTRIBUTE_NAME_ARRAY_IS_NULL =
        "The attribute name array is null.";

    /**
     * Error message when threshold monitor key array is null.
     */
    public static final String ERR_THRESHOLD_MONITOR_KEY_ARRAY_IS_NULL =
        "The threshold monitor key array is null.";

    /**
     * Error message for not valid simple threshold monitor value.
     */
    public static final String ERR_NOT_VALID_SIMPLE_THRESHOLD_MONITOR_VALUE =
        "The SimpleThresholdMonitorValue is not valid.";

    /**
    * Error message for not valid trigger on all threshold monitor value.
    */
    public static final String ERR_NOT_VALID_TRIGGER_ON_ALL_THRESHOLD_MONITOR_VALUE =
        "The TriggerOnAllThresholdMonitorValue is not valid.";

   /**
    * Error message for not valid trigger on any threshold monitor value.
    */
    public static final String ERR_NOT_VALID_TRIGGER_ON_ANY_THRESHOLD_MONITOR_VALUE =
        "The TriggerOnAnyThresholdMonitorValue is not valid.";

    /**
     * Error message when trying to use an implementation class that do not
     * belong to the threshold monitor reference implementation.
     */
    public static final String ERR_NOT_CORRECT_IMPLEMENTATION_CLASS =
        "Not the correct implementation class.";

    /**
     * Error message for not null threshold monitor key in threshold monitor
     * value when monitor is created.
     */
    public static final String ERR_THRESHOLD_MONITOR_KEY_NOT_NULL =
        "The threshold monitor key must be null when a threshold monitor is created.";

    /**
     * Error message when a threshold monitor name is already used.
     */
    public static final String ERR_THRESHOLD_MONITOR_NAME_ALREADY_USED =
        "Threshold monitor name already used.";

    /**
     * Error message when the threshold monitor name is not defined.
     */
    public static final String ERR_THRESHOLD_MONITOR_NAME_NOT_DEFINED =
        "The threshold monitor name is not defined.";

    /**
     * Error message when the observable object is null.
     */
    public static final String ERR_OBSERVABLE_OBJECT_IS_NULL =
        "The observable object is null.";

    /**
     * Error message when the threshold definition is null.
     */
    public static final String ERR_THRESHOLD_DEFINITION_IS_NULL =
        "The threshold definition is null.";

    /**
     * Error message when the threshold definition is not valid.
     */
    public static final String ERR_THRESHOLD_DEFINITION_IS_NOT_VALID =
        "The threshold definition is not valid.";

    /**
     * Error message when the alarm configuration is null.
     */
    public static final String ERR_ALARM_CONFIG_IS_NULL =
        "The alarm configuration is null.";

    /**
     * Error message when the alarm configurations perceived severity is not valid.
     */
    public static final String ERR_ALARM_CONFIG_IS_NOT_VALID =
        "The alarm configuration is not valid.";

    /**
     * Error message when the schedule is null.
     */
    public static final String ERR_SCHEDULE_IS_NULL =
        "The schedule is null.";

    /**
     * Error message when the schedule is not valid.
     */
    public static final String ERR_SCHEDULE_NOT_VALID =
        "The schedule is not valid.";

    /**
     * Error message when the granularity is not supported.
     */
    public static final String ERR_GRANULARITY_IS_NOT_SUPPORTED =
        "The granularity period is not supported.";

    /**
     * Error message when the granularity is not valid.
     */
    public static final String ERR_GRANULARITY_IS_NOT_VALID =
        "The granularity period is not valid.";

    /**
     * Error message when the granularity is not set.
     */
    public static final String ERR_GRANULARITY_IS_NOT_SET =
        "The granularity period is not set.";

    /**
     * Error message when the performance attribute descriptor is not valid.
     */
    public static final String ERR_PERFORMANCE_ATTRIBUTE_DESCRIPTOR_NOT_VALID =
        "The performance attribute descriptor is not valid.";

    /**
     * Error message for null value type.
     */
    public static final String ERR_VALUE_TYPE_NULL =
        "The value type is null.";

    /**
     *  Error message for not supported event type.
     */
    public static final String ERR_EVENT_TYPE_NOT_SUPPORTED =
        "The event type is not supported.";

    /**
     * Error message for null event type.
     */
    public static final String ERR_EVENT_TYPE_NULL =
        "The event type is null.";

    /**
     *  Error message for not supported query type.
     */
    public static final String ERR_QUERY_TYPE_NOT_SUPPORTED =
        "The query type is not supported.";

    /**
     *  Error message for not supported query attribute.
     */
    public static final String ERR_QUERY_ATTRIBUTE_NOT_SUPPORTED =
        "The query attribute is not supported.";

    /**
     * Error message for null query type.
     */
    public static final String ERR_QUERY_TYPE_NULL =
        "The query type is null.";

    /**
     * Error message when it is not possible to create a performance monitor
     * session.
     */
    public static final String ERR_PERFORMANCE_MONITOR_SESSION_CREATE_FAILED =
        "Could not create a performance monitor session.";

    /**
     * Error message when a RemoteException is thrown while calling methods on
     * performance monitor.
     */
    public static final String ERR_REMOTE_INVOCATION_ON_PERFORMANCE_MONITOR =
        "Remote method invocation error on performance monitor.";

    /**
     * Error message if the threshold monitor could not be resumed.
     */
    public static final String ERR_COULD_NOT_RESUME_THRESHOLD_MONITOR =
        "Could not resume the threshold monitor.";

    /**
     * Error message if the threshold monitor could not be suspended.
     */
    public static final String ERR_COULD_NOT_SUSPEND_THRESHOLD_MONITOR =
        "Could not resume the threshold monitor.";

    /**
     * Error message if an associated performance monitor could not be found.
     */
    public static final String ERR_COULD_NOT_FIND_PERFORMANCE_MONITOR =
        "Could not find the associated performance monitor.";

    /**
     * Error message if an associated performance monitor could not be removed
     * while a threshold monitor is removed.
     */
    public static final String ERR_COULD_NOT_REMOVE_PERFORMANCE_MONITOR =
        "Could not remove the associated performance monitor.";

    /**
     * Error message when the associated performance monitor could not be
     * created during creation of threshold monitor.
     */
    public static final String ERR_COULD_NOT_CREATE_PERFORMANCE_MONITOR =
        "Could not create the associated performance monitor.";

    /**
     * Error message when it is not possible to create an alarm monitor
     * session.
     */
    public static final String ERR_ALARM_MONITOR_SESSION_CREATE_FAILED =
        "Could not create an alarm monitor session.";

    /**
     * Error message when a RemoteException is thrown while calling methods on
     * alarm monitor.
     */
    public static final String ERR_REMOTE_INVOCATION_ON_ALARM_MONITOR =
        "Remote method invocation error on alarm monitor.";

    /**
     * Error message when it is not possible to create the threshold monitor.
     */
    public static final String ERR_COULD_NOT_CREATE_THRESHOLD_MONITOR =
        "Could not create threshold monitor.";

    /**
     * Error message when a null threshold monitor key is used.
     */
    public static final String ERR_THRESHOLD_MONITOR_KEY_NULL =
        "The threshold monitor key is null.";

    /**
     * Error message when a performance monitor value could not be created from
     * a threshold monitor value.
     */
    public static final String ERR_COULD_NOT_CREATE_PERFORMANCE_MONITOR_VALUE =
        "Could not create performance monitor value.";

    /**
     * Error message when the threshold value is null.
     */
    public static final String ERR_THRESHOLD_MONITOR_VALUE_NULL =
        "The threshold monitor value is null.";
    /**
     * Error message when a threshold monitor is not found.
     */
    public static final String ERR_THRESHOLD_MONITOR_NOT_FOUND =
        "The threshold monitor was not found.";

    /**
     * Error message if a threshold m monitor could not be removed.
     */
    public static final String ERR_COULD_NOT_REMOVE_THRESHOLD_MONITOR =
        "Could not remove the threshold monitor.";

    /**
     * Error message when no threshold monitors were found.
     */
    public static final String ERR_NO_THRESHOLD_MONITORS_FOUND =
        "No threshold monitors were found.";

    /**
     * Error message when not supported report format is found.
     */
    public static final String ERR_NOT_SUPPORTED_REPORT_FORMAT =
        "Not supported report format received.";

    /**
     * Error message for not supported measurement (performance) value format.
     */
    public static final String ERR_NOT_SUPPORTED_VALUE_FORMAT =
        "Not supported value format received.";

    /**
     * Error message for not supported direction, i.e. not specified by
     * ThresholdDirection class.
     */
    public static final String ERR_NOT_SUPPORTED_DIRECTION =
        "Not supported direction.";

    /**
     * Error message when an alarm event could not be created.
     */
    public static final String ERR_COULD_NOT_CREATE_EVENT =
        "Could not create alarm event.";

    /**
     * Error message when the application context could not be initialized.
     */
    public static final String ERR_COULD_NOT_INITIALIZE_APPLICATION_CONTEXT =
        "Could not initialize application context.";

    /**
     * Error message when the report format used by performance monitor could not
     * be retrieved.
     */
    public static final String ERR_COULD_NOT_GET_REPORT_FORMAT =
        "Could not get report format.";
}