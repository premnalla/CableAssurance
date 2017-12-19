package ossj.ttri;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.oss.trouble.JVTTroubleTicketSessionHome;
import java.util.Properties;

/**
 * Finder Helper Class
 * Common finder to find the EJB Homes used in the TTRI.
 *
 *
 * @author Copyright 2002 MetaSolv Software, Inc. All rights reserved.
 * @version 1.0
 * @since February 2002
 */
class Finder {

    InitialContext initCtx = null;

    private static Finder singleton = new Finder();

    static public final Finder getInstance() {
        return singleton;
    }

    protected Finder() {

        try {
            initCtx = new InitialContext();
        } catch (NamingException nex) {
            log("TTSession:ejbCreate:  Naming exception caught while creating InitialContext");
            nex.printStackTrace();
        }
    }

    //Trouble Ticket JVT Session Bean Home
    public JVTTroubleTicketSessionHome lookupJVTSessionHome() {

        JVTTroubleTicketSessionHome ttSessionHome = null;
        try {
            log("initCtx: " + initCtx);
            //java:/comp/env/ejb/JVTHome
            ttSessionHome = (JVTTroubleTicketSessionHome) initCtx.lookup("java:comp/env/ejb/JVTHome");
            //initCtx.lookup("oss.JVTTroubleTicketSessionHome");
        } catch (NamingException nex) {
            log("Finder.lookupJVTSessionHome:  Naming exception caught on lookup of JVTTTSessionHome");
            nex.printStackTrace();
        }

        //log ("Finder.lookupEntityHome:  TT Entity Home found OK");
        return ttSessionHome;

    }

    //Trouble Ticket Entity Bean Home
    public TroubleTicketHome lookupEntityHome() {

        TroubleTicketHome ttEntityHome = null;
        try {
            log("initCtx: " + initCtx);
            //java:/comp/env/ejb/TroubleTicketHome
            ttEntityHome = (TroubleTicketHome) initCtx.lookup("java:comp/env/ejb/TroubleTicketHome");
            //initCtx.lookup("oss.TroubleTicketHome");
        } catch (NamingException nex) {
            log("Finder.lookupEntityHome:  Naming exception caught on lookup of TroubleTicketHome");
            nex.printStackTrace();
        }

        //log ("Finder.lookupEntityHome:  TT Entity Home found OK");
        return ttEntityHome;

    }

    //TroubleTicketValueIterator Stateful Session Bean Home
    public TroubleTicketValueIteratorHome lookupTTValueIteratorHome() {
        TroubleTicketValueIteratorHome ttValueIterHome = null;
        try {
            //java:/comp/env/ejb/TroubleTicketValueIteratorHome
            ttValueIterHome = (TroubleTicketValueIteratorHome)
                    initCtx.lookup("java:comp/env/ejb/TroubleTicketValueIteratorHome");
            //initCtx.lookup("oss.TroubleTicketValueIteratorHome");
        } catch (NamingException nex) {
            log("Finder:lookupTTValueIteratorHome:  Naming exception caught on lookup of TroubleTicketValueIteratorHome");
            nex.printStackTrace();
        }
        //log ("Finder:lookupTTValueIteratorHome: TroubleTicketValueIteratorHome found OK");
        return ttValueIterHome;
    }

    //TroubleTicketKeyResultIterator Stateful Session Bean Home
    public TroubleTicketKeyResultIteratorHome lookupTTKeyResultIteratorHome() {
        TroubleTicketKeyResultIteratorHome ttKeyResultIterHome = null;
        try {
            //java:/comp/env/ejb/TroubleTicketKeyResultIteratorHome
            ttKeyResultIterHome = (TroubleTicketKeyResultIteratorHome)

                    initCtx.lookup("java:comp/env/ejb/TroubleTicketKeyResultIteratorHome");
            //initCtx.lookup("sessHomeName");
        } catch (NamingException nex) {
            log("Finder:lookupTTKeyResultIteratorHome:  Naming exception caught on lookup of TroubleTicketKeyResultIteratorHome");
            nex.printStackTrace();
        }
        //log ("Finder:lookupTTKeyResultIteratorHome: TroubleTicketKeyResultIteratorHome found OK");
        return ttKeyResultIterHome;
    }

    public boolean lookupLogingEnabled()
            throws NamingException {

        boolean logEnbl = false;
        try {
            logEnbl = ((java.lang.Boolean) initCtx.lookup("java:comp/env/loggingEnabled")).booleanValue();
        } catch (NamingException nex) {
            log("Finder.lookupLogingEnabled:  Naming exception caught on lookup of logingEnabled");
            throw nex;
        }
        log("Logging status: " + logEnbl);
        return logEnbl;

    }

    // You might also consider using WebLogic's log service
    private void log(String s) {
        Logger.log(s);
    }
}
