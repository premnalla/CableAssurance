package com.nokia.oss.ossj.common.ri.ex;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

import com.nokia.oss.ossj.common.ri.*;

public interface JmsSenderHome  extends EJBHome {
    public JmsSender create() throws CreateException, RemoteException;
}

