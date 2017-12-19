/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
*/
package ossj.common.ex;

import ossj.common.*;

// Utility imports
import java.util.*;

import javax.ejb.CreateException;

// OSS/J imports
import javax.oss.*;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
*/
public class ManagedEntityEx2 {
    private static final String MIN = "0";
    private String ex2ApiClientId;
    private String ex2Description;
    private Date ex2CreationDate;
    private String ex2Name;
    private String ex2Value;
    private String ex2MinValue;
    private String ex2MaxValue;
    private ManagedEntityKey key;
    private StringMultiValueList ex2MultiValueList;

    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityKey CreateManagedEntityEx2(ManagedEntityEx2ValueImpl mev) {
        key = mev.getManagedEntityKey();
        setAttributes(mev);
        setEx2CreationDate(new Date());
        setEx2MinValue(MIN);

        return key;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx2ValueImpl getManagedEntityValue() {
        ManagedEntityEx2ValueImpl result = new ManagedEntityEx2ValueImpl();

        if (key != null) {
            result.setManagedEntityKey(key);
        }

        if (ex2ApiClientId != null) {
            result.setEx2ApiClientId(ex2ApiClientId);
        }

        if (ex2Description != null) {
            result.setEx2Description(ex2Description);
        }

        if (ex2Name != null) {
            result.setEx2Name(ex2Name);
        }

        if (ex2Value != null) {
            result.setEx2Value(ex2Value);
        }

        if (ex2MinValue != null) {
            result.setEx2MinValue(ex2MinValue);
        }

        if (ex2MaxValue != null) {
            result.setEx2MaxValue(ex2MaxValue);
        }

        if (ex2MultiValueList != null) {
            result.setEx2MultiValueList(ex2MultiValueList);
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityKey getManagedEntityKey() {
        return key;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2ApiClientId() {
        return ex2ApiClientId;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setEx2ApiClientId(String name) {
        this.ex2ApiClientId = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Date getEx2CreationDate() {
        return ex2CreationDate;
    }

    /**
     * DOCUMENT ME!
     *
     * @param date DOCUMENT ME!
     */
    public void setEx2CreationDate(Date date) {
        this.ex2CreationDate = date;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2MinValue() {
        return ex2MinValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param val DOCUMENT ME!
     */
    public void setEx2MinValue(String val) {
        this.ex2MinValue = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2Description() {
        return ex2Description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param text DOCUMENT ME!
     */
    public void setEx2Description(String text) {
        ex2Description = text;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2Name() {
        return ex2Name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setEx2Name(String name) {
        ex2Name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2Value() {
        return ex2Value;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void setEx2Value(String value) {
        ex2Value = value;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx2MaxValue() {
        return ex2MaxValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param maxvalue DOCUMENT ME!
     */
    public void setEx2MaxValue(String maxvalue) {
        ex2MaxValue = maxvalue;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public StringMultiValueList getEx2MultiValueList() {
        return ex2MultiValueList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param list DOCUMENT ME!
     */
    public void setEx2MultiValueList(StringMultiValueList list) {
        Collection col;

        if (list.getModifier() == MultiValueList.ADD) {
            if (ex2MultiValueList == null) {
                ex2MultiValueList = (StringMultiValueList) list.clone();
                ex2MultiValueList.setModifier(MultiValueList.SET);
            } else {
                col = Arrays.asList(ex2MultiValueList.get());
                col.addAll(Arrays.asList(list.get()));
                ex2MultiValueList.set((String[]) col.toArray(new String[col.size()]));
            }
        } else if (list.getModifier() == MultiValueList.REMOVE) {
            col = Arrays.asList(ex2MultiValueList.get());

            String[] removeList = list.get();

            for (int i = removeList.length - 1; i >= 0; i--)
                col.remove(removeList[i]);

            ex2MultiValueList.set((String[]) col.toArray(new String[col.size()]));
        } else if (list.getModifier() == MultiValueList.SET) {
            ex2MultiValueList.set(list.get());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     */
    public void setAttributes(ManagedEntityEx2ValueImpl mev) {
        String[] populatedAttributes = mev.getPopulatedAttributeNames();
        Object value = null;

        for (int i = populatedAttributes.length - 1; i >= 0; i--) {
            try {
                setAttribute(populatedAttributes[i], mev);
            } catch (java.lang.IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     */
    public void updateAttributes(ManagedEntityEx2ValueImpl mev) {
        // in actual practise use getDirtyattribute using attribute manager and then compare those which have changed value and then update 
        setAttributes(mev);
    }

    /**
     * DOCUMENT ME!
     *
     * @param attributeName DOCUMENT ME!
     * @param anValue DOCUMENT ME!
     */
    public void setAttribute(String attributeName, ManagedEntityEx2ValueImpl anValue) {
        if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_API_CLIENT_ID)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_API_CLIENT_ID)) {
            setEx2ApiClientId(anValue.getEx2ApiClientId());
        } else if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_NAME)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_NAME)) {
            setEx2Name(anValue.getEx2Name());
        } else if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_VALUE)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_VALUE)) {
            setEx2Value(anValue.getEx2Value());
        } else if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_MAX_VALUE)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_MAX_VALUE)) {
            setEx2MaxValue(anValue.getEx2MaxValue());
        } else if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_DESCRIPTION)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_DESCRIPTION)) {
            setEx2Description(anValue.getEx2Description());
        } else if (attributeName.equals(ManagedEntityEx2ValueImpl.EX_2_MULTI_VALUE_LIST)&&anValue.isPopulated(ManagedEntityEx2ValueImpl.EX_2_MULTI_VALUE_LIST)) {
            setEx2MultiValueList(anValue.getEx2MultiValueList());
        }
    }
}
