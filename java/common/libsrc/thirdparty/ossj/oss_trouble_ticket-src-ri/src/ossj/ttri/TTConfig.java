package ossj.ttri;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.oss.ApplicationContext;

/**
 * Global Config Singleton Class
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
public class TTConfig {


    //singleton pattern
    private static TTConfig singleton = new TTConfig();

    public static final TTConfig getInstance() {
        return singleton;
    }

    //config items. Should be loaded at startup.  Hard code for now.
    private TTConfig() {
        Logger.log("-----####TTCONFIG GETTING CONFIGURATION######-----");

        String URLCFG = null;
        String factoryClassCFG = null;
        String applicationDNCFG = null;
        try {
            Context ctx = new InitialContext();
            URLCFG = (String) ctx.lookup("java:comp/env/URL");
            factoryClassCFG = (String) ctx.lookup("java:comp/env/factoryClass");
            applicationDNCFG = (String) ctx.lookup("java:comp/env/applicationDN");
        } catch (javax.naming.NamingException e) {
            Logger.log("-----EXCEPTION IN LOOKUP CONFIG-----");
        }
        Logger.log("FactoryClass-->" + factoryClassCFG);
        Logger.log("ApplciationDN-->" + applicationDNCFG);
        Logger.log("URL-->" + URLCFG);
        ApplicationContextImpl newAppContext = new ApplicationContextImpl();
        newAppContext.setFactoryClass(factoryClassCFG);
        newAppContext.setURL(URLCFG);
        setApplicationContext(newAppContext);
        setApplicationTypeDN(applicationDNCFG);

    }

    //review these - some may be deprecated.



    public String versionNumber = "Trouble Ticket Early Access v 0.1";
    public String applicationTypeDN = "Trouble Ticket";
    public ApplicationContext appContext = null;

    public void setApplicationTypeDN(String appDN) {
        applicationTypeDN = appDN;
    }

    public void setApplicationContext(ApplicationContext ctx) {
        appContext = ctx;
    }

    public ApplicationContext getApplicationContext() {
        Logger.log("---getApplicationContext()---");
        if (appContext == null) Logger.log("---NULL APP CONTEXT---");
        return appContext;

    }

    public String getApplicationTypeDN() {
        Logger.log("---getApplicationTypeDN()---");
        if (applicationTypeDN == null) Logger.log("---NULL APP TYPE DN---");
        return applicationTypeDN;
    }


}
