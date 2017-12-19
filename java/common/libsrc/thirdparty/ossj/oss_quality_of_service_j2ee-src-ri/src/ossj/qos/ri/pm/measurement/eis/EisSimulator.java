package ossj.qos.ri.pm.measurement.eis;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;
import javax.oss.pm.measurement.*;
import javax.oss.*;
import javax.oss.ManagedEntityValueIterator;
import javax.oss.pm.util.*;


/**
 * This is the Enterprise Information System (EIS) Simulator Interface.
 * <p>
 * The idea is that the EIS simulator should represent an external from the
 * application server proprietary system that needs to be integrated.
 * <p>
 *
 * Copyright (c) 2001 Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 1.0
 * @see javax.oss.pm.measurement.JVTPerformanceMonitorSession
 */


public interface EisSimulator extends Remote {

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getManagedEntityTypes()
   */
  public String[] simulatorGetManagedEntityTypes()
  throws java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getVersion()
   */
  public String[] simulatorGetVersion()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getSupportedOptionalOperations()
   */
  public String[] simulatorGetSupportedOptionalOperations()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getEventTypes()
   */
  public String[] simulatorGetEventTypes()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getEventDescriptor( String eventType)
   */
  public EventPropertyDescriptor simulatorGetEventDescriptor(String eventType)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#makePerformanceMonitorValue( String value)
   */
  public PerformanceMonitorValue simulatorMakePerformanceMonitorValue(String value)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getQueryTypes()
   */
  public String[] simulatorGetQueryTypes()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#makeQueryValue(String type)
   */
  public QueryValue simulatorMakeQueryValue(String type)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getReportFormats()
   */
  public ReportFormat[] simulatorGetReportFormats()
  throws java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getCurrentReportFormat()
   */
  public ReportFormat simulatorGetCurrentReportFormat()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getReportModes()
   */
  public int[] simulatorGetReportModes()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getObservableObjectClasses()
   */
  public ObservableObjectClassIterator simulatorGetObservableObjectClasses()
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getObservableObjects(String observableObjectClassName, String base )
   */
  public ObservableObjectIterator simulatorGetObservableObjects(String observableObjectClassName, String base )
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getSupportedObservableObjects( String[] dnList )
   */
  public String[] simulatorGetSupportedObservableObjects(String[] dnList)
  throws java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getObservableAttributes( String observableObjectClassName )
   */
  public PerformanceAttributeDescriptor[] simulatorGetObservableAttributes( String observableObjectClassName )
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getSupportedGranularities( PerformanceMonitorValue pmValue )
   */
  public int[] simulatorGetSupportedGranularities(PerformanceMonitorValue pmValue)
  throws java.rmi.RemoteException, javax.oss.IllegalArgumentException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)
   */
  public ReportInfoIterator simulatorGetPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date)
  throws java.rmi.RemoteException, javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getPerformanceMonitorByKey(PerformanceMonitorKey pmKey, String[] attributes)
   */
  public PerformanceMonitorValue simulatorGetPerformanceMonitorByKey(PerformanceMonitorKey pmKey, String[] attributes)
  throws javax.ejb.ObjectNotFoundException, java.rmi.RemoteException, javax.oss.IllegalArgumentException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKey, String[] attributes)
   */
  public PerformanceMonitorValueIterator simulatorGetPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKey, String[] attributes)
  throws javax.ejb.FinderException, java.rmi.RemoteException, javax.oss.IllegalArgumentException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#queryPerformanceMonitors( QueryValue query , String[] attrNames)
   */
  public PerformanceMonitorValueIterator simulatorQueryPerformanceMonitors(QueryValue query , String[] attrNames)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#createPerformanceMonitorByValue(PerformanceMonitorValue pmValue)
   */
  public PerformanceMonitorKey simulatorCreatePerformanceMonitorByValue(PerformanceMonitorValue pmValue)
  throws javax.ejb.CreateException, javax.ejb.DuplicateKeyException, javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#tryCreatePerformanceMonitorsByValues(PerformanceMonitorValue[] pmValues)
   */
  public PerformanceMonitorKeyResult[] simulatorTryCreatePerformanceMonitorsByValues(PerformanceMonitorValue[] pmValues)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException, java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#removePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
   */
  public void simulatorRemovePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
  throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, javax.ejb.RemoveException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getMeasurementJobsList()
   */

  public PerformanceMonitorValue[] simulatorGetMeasurementJobsList() throws java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#tryRemovePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
   */

  public PerformanceMonitorKeyResult[] simulatorTryRemovePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
  throws javax.oss.UnsupportedOperationException, javax.oss.IllegalArgumentException, java.rmi.RemoteException;


  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#suspendPerformanceMonitorByKey(PerformanceMonitorKey pmKey)
   */
  public void simulatorSuspendPerformanceMonitorByKey(PerformanceMonitorKey pmKey)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
         javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#trySuspendPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
   */
  public PerformanceMonitorKeyResult[] simulatorTrySuspendPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#resumePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
   */
  public void simulatorResumePerformanceMonitorByKey(PerformanceMonitorKey pmKey)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
         javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#tryResumePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
   */
  public PerformanceMonitorKeyResult[] simulatorTryResumePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException,
         java.rmi.RemoteException;

  /**
   *@see javax.oss.pm.measurement.JVTPerformanceMonitorSession#getCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)
   */
  public CurrentResultReport simulatorGetCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format)
  throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.oss.IllegalStateException, javax.ejb.ObjectNotFoundException, java.rmi.RemoteException;

  /**
   *@see javax.oss.JVTSession#makeManagedEntityValue(String valueType)
   */
  public ManagedEntityValue simulatorMakeManagedEntityValue(String valueType)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;

  /**
   *@see javax.oss.JVTSession#queryManagedEntities(QueryValue query, String[] attrNames)
   */
  public ManagedEntityValueIterator simulatorQueryManagedEntities(QueryValue query, String[] attrNames)
  throws javax.oss.IllegalArgumentException, java.rmi.RemoteException;


}
