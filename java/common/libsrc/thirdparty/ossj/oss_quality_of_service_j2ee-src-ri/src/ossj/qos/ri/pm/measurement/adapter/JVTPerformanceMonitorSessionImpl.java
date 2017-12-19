package ossj.qos.ri.pm.measurement.adapter;

import java.util.*;
import javax.oss.pm.measurement.*;
import javax.oss.*;
import javax.oss.pm.util.*;
import java.rmi.*;
import javax.ejb.*;
import ossj.qos.ri.pm.measurement.eis.*;



/**
 * Title:        JSR090 Reference Implementation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ericsson
 * @author Andreas Jirven, Anna Eriksson, Ali Feizabadi
 * @version 0.2
 */

public class JVTPerformanceMonitorSessionImpl implements SessionBean {
  private SessionContext sessionContext;
  private EisSimulator eisSimulator = null;



  public void ejbCreate() {
System.out.println("This is JVTPerformanceMonitorSessionImpl's contruction");
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    try {
      eisSimulator = (EisSimulator) java.rmi.Naming.lookup("//localhost/EisSimulator");
    }
    catch (Exception e) {
    }
  }

  public void ejbRemove() throws RemoteException {
  }

  public void ejbActivate() throws RemoteException {
  }

  public void ejbPassivate() throws RemoteException {
  }

  public void setSessionContext(SessionContext sessionContext) throws RemoteException {
    this.sessionContext = sessionContext;
  }

  public String[] getManagedEntityTypes() throws EJBException {
  	try {
    return eisSimulator.simulatorGetManagedEntityTypes();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public String[] getVersion() throws EJBException {
  	try {
   return eisSimulator.simulatorGetVersion();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public String[] getSupportedOptionalOperations() throws EJBException {
  	try {
    return eisSimulator.simulatorGetSupportedOptionalOperations();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public String[] getEventTypes() throws EJBException {
  	try {
    return eisSimulator.simulatorGetEventTypes();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public EventPropertyDescriptor getEventDescriptor(String eventType) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorGetEventDescriptor(eventType);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorValue makePerformanceMonitorValue(String value) throws EJBException, javax.oss.IllegalArgumentException {
    PerformanceMonitorValue pmValue = null;
  	try {
	pmValue = eisSimulator.simulatorMakePerformanceMonitorValue(value);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
    return pmValue;
  }

  public String[] getQueryTypes() throws EJBException {
  	try {
    return eisSimulator.simulatorGetQueryTypes();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public QueryValue makeQueryValue(String type) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorMakeQueryValue(type);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ReportFormat[] getReportFormats() throws EJBException {
  	try {
    return eisSimulator.simulatorGetReportFormats();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ReportFormat getCurrentReportFormat() throws EJBException {
  	try {
    return eisSimulator.simulatorGetCurrentReportFormat();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public int[] getReportModes() throws EJBException {
  	try {
    return eisSimulator.simulatorGetReportModes();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ObservableObjectClassIterator getObservableObjectClasses() throws EJBException {
  	try {
    return eisSimulator.simulatorGetObservableObjectClasses();
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ObservableObjectIterator getObservableObjects(String observableObjectClassName, String base) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorGetObservableObjects(observableObjectClassName, base);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public String[] getSupportedObservableObjects(String[] dnList) throws EJBException {
  	try {
    return eisSimulator.simulatorGetSupportedObservableObjects(dnList);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceAttributeDescriptor[] getObservableAttributes(String observableObjectClassName) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorGetObservableAttributes(observableObjectClassName);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public int[] getSupportedGranularities(PerformanceMonitorValue pmValue) throws EJBException, javax.oss.IllegalArgumentException {
  	try {
    return eisSimulator.simulatorGetSupportedGranularities(pmValue);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ReportInfoIterator getPerformanceReportInfo(PerformanceMonitorKey pmKey, java.util.Calendar date) throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, EJBException {
  	try {
    return eisSimulator.simulatorGetPerformanceReportInfo(pmKey, date);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorValue getPerformanceMonitorByKey(PerformanceMonitorKey pmKey, String[] attributes) throws javax.ejb.ObjectNotFoundException, EJBException, javax.oss.IllegalArgumentException {
  	try {
    return eisSimulator.simulatorGetPerformanceMonitorByKey(pmKey, attributes);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorValueIterator getPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKey, String[] attributes) throws javax.ejb.FinderException, EJBException, javax.oss.IllegalArgumentException {
  	try {
    return eisSimulator.simulatorGetPerformanceMonitorsByKeys(pmKey, attributes);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorValueIterator queryPerformanceMonitors(QueryValue query, String[] attrNames) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorQueryPerformanceMonitors(query, attrNames);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorKey createPerformanceMonitorByValue(PerformanceMonitorValue pmValue) throws javax.oss.IllegalArgumentException, javax.ejb.CreateException, javax.ejb.DuplicateKeyException, EJBException {
  	try {
     return eisSimulator.simulatorCreatePerformanceMonitorByValue(pmValue);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorKeyResult[] tryCreatePerformanceMonitorsByValues(PerformanceMonitorValue[] pmValues) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.DuplicateKeyException, EJBException {
  	try {
    return eisSimulator.simulatorTryCreatePerformanceMonitorsByValues(pmValues);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public void removePerformanceMonitorByKey(PerformanceMonitorKey pmKey) throws javax.oss.IllegalArgumentException, javax.ejb.ObjectNotFoundException, javax.ejb.RemoveException, EJBException {
  	try {
    eisSimulator.simulatorRemovePerformanceMonitorByKey(pmKey);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorKeyResult[] tryRemovePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys) throws javax.oss.UnsupportedOperationException, javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorTryRemovePerformanceMonitorsByKeys(pmKeys);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public void suspendPerformanceMonitorByKey(PerformanceMonitorKey pmKey) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.ObjectNotFoundException, EJBException {
  	try {
    eisSimulator.simulatorSuspendPerformanceMonitorByKey(pmKey);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorKeyResult[] trySuspendPerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, EJBException {
  	try {
    return eisSimulator.simulatorTrySuspendPerformanceMonitorsByKeys(pmKeys);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public void resumePerformanceMonitorByKey(PerformanceMonitorKey pmKey) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.ejb.ObjectNotFoundException, EJBException {
  	try {
    eisSimulator.simulatorResumePerformanceMonitorByKey(pmKey);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public PerformanceMonitorKeyResult[] tryResumePerformanceMonitorsByKeys(PerformanceMonitorKey[] pmKeys) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, EJBException {
  	try {
    return eisSimulator.simulatorTryResumePerformanceMonitorsByKeys(pmKeys);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public CurrentResultReport getCurrentResultReport(PerformanceMonitorKey pmKey, ReportFormat format) throws javax.oss.IllegalArgumentException, javax.oss.UnsupportedOperationException, javax.oss.IllegalStateException, javax.ejb.ObjectNotFoundException, EJBException {
  	try {
    return eisSimulator.simulatorGetCurrentResultReport(pmKey, format);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ManagedEntityValue makeManagedEntityValue(String valueType) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorMakeManagedEntityValue(valueType);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }

  public ManagedEntityValueIterator queryManagedEntities(QueryValue query, String[] attrNames) throws javax.oss.IllegalArgumentException, EJBException {
  	try {
    return eisSimulator.simulatorQueryManagedEntities(query, attrNames);
	} catch (RemoteException re){
	  throw new EJBException(re.getMessage());
	}
  }
}
