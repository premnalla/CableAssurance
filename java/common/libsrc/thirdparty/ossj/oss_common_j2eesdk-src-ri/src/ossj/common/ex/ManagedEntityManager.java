/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import java.util.Date;
import java.util.HashMap;

import javax.oss.*;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ManagedEntityManager {
    private HashMap listManagedEntityEx1;
    private HashMap listManagedEntityEx2;

    /**
     * Creates a new ManagedEntityManager object.
     */
    public ManagedEntityManager() {
        listManagedEntityEx1 = new HashMap();
        listManagedEntityEx2 = new HashMap();
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx1 createManagedEntityEx1(ManagedEntityEx1ValueImpl value) {
        ManagedEntityEx1 mang = new ManagedEntityEx1();
        ManagedEntityEx1KeyImpl ret = (ManagedEntityEx1KeyImpl) mang.CreateManagedEntityEx1(value);

        listManagedEntityEx1.put(ret, mang);

        return mang;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx2 createManagedEntityEx2(ManagedEntityEx2ValueImpl value) {
        ManagedEntityEx2 mang = new ManagedEntityEx2();
        ManagedEntityEx2KeyImpl ret = (ManagedEntityEx2KeyImpl) mang.CreateManagedEntityEx2(value);
        listManagedEntityEx2.put(ret, mang);

        return mang;
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     */
    public void removeManagedEntityEx1(ManagedEntityExKey key) {
        listManagedEntityEx1.remove(key);
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     */
    public void removeManagedEntityEx2(ManagedEntityExKey key) {
        listManagedEntityEx2.remove(key);
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx1ValueImpl getManagedEntityEx1Value(ManagedEntityExKey key) {
        if (listManagedEntityEx1.containsKey(key)) {
            return ((ManagedEntityEx1) listManagedEntityEx1.get(key)).getManagedEntityValue();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx2ValueImpl getManagedEntityEx2Value(ManagedEntityExKey key) {
        if (listManagedEntityEx2.containsKey(key)) {
            return ((ManagedEntityEx2) listManagedEntityEx2.get(key)).getManagedEntityValue();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void setManagedEntityEx1(ManagedEntityEx1ValueImpl value) {
        ManagedEntityEx1KeyImpl ret1 = (ManagedEntityEx1KeyImpl) value.getManagedEntityKey();

        if (listManagedEntityEx1.containsKey(ret1)) {
            ManagedEntityEx1 val = (ManagedEntityEx1) listManagedEntityEx1.get(ret1);
            val.updateAttributes(value);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void setManagedEntityEx2(ManagedEntityEx2ValueImpl value) {
        ManagedEntityEx2KeyImpl ret1 = (ManagedEntityEx2KeyImpl) value.getManagedEntityKey();

        if (listManagedEntityEx2.containsKey(ret1)) {
            ManagedEntityEx2 val = (ManagedEntityEx2) listManagedEntityEx2.get(ret1);
            val.updateAttributes(value);
        }
    }
}
