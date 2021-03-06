/*
 * Copyright 2004-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.storeconfig;

import java.io.InputStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.mbeans.MBeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

/**
 * Load and Register StoreConfig MBean <i>Catalina:type=StoreConfig,resource="url"</i>
 * 
 * @author Peter Rossbach
 */
public class StoreConfigLifecycleListener implements LifecycleListener {
    private static Log log = LogFactory
            .getLog(StoreConfigLifecycleListener.class);

    IStoreConfig storeConfig;

    private String storeConfigClass = "org.apache.catalina.storeconfig.StoreConfig";

    private String storeRegistry = null;

    /*
     * register StoreRegistry after Start the complete Server
     * 
     * @see org.apache.catalina.LifecycleListener#lifecycleEvent(org.apache.catalina.LifecycleEvent)
     */
    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.AFTER_START_EVENT.equals(event.getType())) {
            if (event.getSource() instanceof StandardServer) {
                createMBean();
            }
        }
    }

    /**
     * create StoreConfig MBean and load StoreRgistry MBeans name is
     * <i>Catalina:type=StoreConfig </i>
     */
    protected void createMBean() {
        StoreLoader loader = new StoreLoader();
        try {
            Class clazz = Class.forName(getStoreConfigClass(), true, this
                    .getClass().getClassLoader());
            storeConfig = (IStoreConfig) clazz.newInstance();
            if (null == getStoreRegistry())
                // default Loading
                loader.load();
            else
                // load a spezial file registry (url)
                loader.load(getStoreRegistry());
            // use the loader Registry
            storeConfig.setRegistry(loader.getRegistry());
        } catch (Exception e) {
            log.error("createMBean load", e);
            return;
        }
        MBeanServer mserver = MBeanUtils.createServer();
        InputStream descriptor = null;
        try {
            ObjectName objectName = new ObjectName("Catalina:type=StoreConfig" );
            if (!mserver.isRegistered(objectName)) {
                descriptor = this.getClass().getResourceAsStream(
                        "mbeans-descriptors.xml");
                Registry registry = MBeanUtils.createRegistry();
                registry.loadMetadata(descriptor);
                mserver.registerMBean(getManagedBean(storeConfig), objectName);
            }
        } catch (Exception ex) {
            log.error("createMBean register MBean", ex);

        } finally {
            if (descriptor != null) {
                try {
                    descriptor.close();
                    descriptor = null;
                } catch (Exception ex) {
                    log.error("createMBean register MBean", ex);
                }
            }
        }
    }

    /**
     * Create a ManagedBean (StoreConfig)
     * 
     * @param object
     * @return The bean
     * @throws Exception
     */
    protected ModelMBean getManagedBean(Object object) throws Exception {
        ManagedBean managedBean = Registry.getRegistry(null, null)
                .findManagedBean(object.getClass().getName());
        return managedBean.createMBean(object);
    }

    /**
     * @return Returns the storeConfig.
     */
    public IStoreConfig getStoreConfig() {
        return storeConfig;
    }

    /**
     * @param storeConfig
     *            The storeConfig to set.
     */
    public void setStoreConfig(IStoreConfig storeConfig) {
        this.storeConfig = storeConfig;
    }

    /**
     * @return Returns the storeConfigClass.
     */
    public String getStoreConfigClass() {
        return storeConfigClass;
    }

    /**
     * @param storeConfigClass
     *            The storeConfigClass to set.
     */
    public void setStoreConfigClass(String storeConfigClass) {
        this.storeConfigClass = storeConfigClass;
    }

    /**
     * @return Returns the storeRegistry.
     */
    public String getStoreRegistry() {
        return storeRegistry;
    }

    /**
     * @param storeRegistry
     *            The storeRegistry to set.
     */
    public void setStoreRegistry(String storeRegistry) {
        this.storeRegistry = storeRegistry;
    }
}
