/*
Copyright 2002-2005 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.ex;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import javax.oss.ManagedEntityKey;
import javax.oss.MultiValueList;
import ossj.common.StringMultiValueList;


/**
 * DOCUMENT ME!
 *
 * @author OSS through Java Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.1
 * @since March 2005
 */
public class ManagedEntityEx1 {
    private static final String MIN = "0";
    private String ex1ApiClientId;
    private String ex1Description;
    private Date ex1CreationDate;
    private String ex1Name;
    private String ex1Value;
    private String ex1MinValue;
    private String ex1MaxValue;
    private ManagedEntityKey key;
    private StringMultiValueList ex1MultiValueList;
    
    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityKey CreateManagedEntityEx1(ManagedEntityEx1ValueImpl mev) {
        key = mev.getManagedEntityKey();
        if (mev.isPopulated(ManagedEntityEx1Value.EX_1_API_CLIENT_ID)){
            System.out.println("hi the value passed till here is " + mev.getEx1ApiClientId());
        }
        System.out.println("hi the key is" + key);
        setAttributes(mev);
        setEx1CreationDate(new Date());
        setEx1MinValue(MIN);
        System.out.println("!" + MIN);
        System.out.println("2" + ex1ApiClientId);
        
        return key;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ManagedEntityEx1ValueImpl getManagedEntityValue() {
        ManagedEntityEx1ValueImpl result = new ManagedEntityEx1ValueImpl();
        
        if (key != null) {
            result.setManagedEntityKey(key);
        }
        
        if (ex1ApiClientId != null) {
            result.setEx1ApiClientId(ex1ApiClientId);
        }
        
        if (ex1Description != null) {
            result.setEx1Description(ex1Description);
        }
        
        if (ex1Name != null) {
            result.setEx1Name(ex1Name);
        }
        
        if (ex1Value != null) {
            result.setEx1Value(ex1Value);
        }
        
        if (ex1MinValue != null) {
            result.setEx1MinValue(ex1MinValue);
        }
        
        if (ex1MaxValue != null) {
            result.setEx1MaxValue(ex1MaxValue);
        }
        
        if (ex1CreationDate != null) {
            result.setEx1CreationDate(ex1CreationDate);
        }
        
        if (ex1MultiValueList != null) {
            result.setEx1MultiValueList(ex1MultiValueList);
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
    public String getEx1ApiClientId() {
        return ex1ApiClientId;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setEx1ApiClientId(String name) {
        this.ex1ApiClientId = name;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Date getEx1CreationDate() {
        return ex1CreationDate;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param date DOCUMENT ME!
     */
    public void setEx1CreationDate(Date date) {
        this.ex1CreationDate = date;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx1MinValue() {
        return ex1MinValue;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param val DOCUMENT ME!
     */
    public void setEx1MinValue(String val) {
        this.ex1MinValue = val;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx1Description() {
        return ex1Description;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param text DOCUMENT ME!
     */
    public void setEx1Description(String text) {
        ex1Description = text;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx1Name() {
        return ex1Name;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setEx1Name(String name) {
        ex1Name = name;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx1Value() {
        return ex1Value;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     */
    public void setEx1Value(String value) {
        ex1Value = value;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getEx1MaxValue() {
        return ex1MaxValue;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param maxvalue DOCUMENT ME!
     */
    public void setEx1MaxValue(String maxvalue) {
        ex1MaxValue = maxvalue;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public StringMultiValueList getEx1MultiValueList() {
        return ex1MultiValueList;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param list DOCUMENT ME!
     */
    public void setEx1MultiValueList(StringMultiValueList list) {
        Collection col;
        
        if (list.getModifier() == MultiValueList.ADD) {
            if (ex1MultiValueList == null) {
                ex1MultiValueList = (StringMultiValueList) list.clone();
                ex1MultiValueList.setModifier(MultiValueList.SET);
            } else {
                col = Arrays.asList(ex1MultiValueList.get());
                col.addAll(Arrays.asList(list.get()));
                ex1MultiValueList.set((String[]) col.toArray(new String[col.size()]));
            }
        } else if (list.getModifier() == MultiValueList.REMOVE) {
            col = Arrays.asList(ex1MultiValueList.get());
            
            String[] removeList = list.get();
            
            for (int i = removeList.length - 1; i >= 0; i--)
                col.remove(removeList[i]);
            
            ex1MultiValueList.set((String[]) col.toArray(new String[col.size()]));
        } else if (list.getModifier() == MultiValueList.SET) {
            ex1MultiValueList.set(list.get());
        }
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param mev DOCUMENT ME!
     */
    public void setAttributes(ManagedEntityEx1ValueImpl mev) {
        String[] populatedAttributes = mev.getPopulatedAttributeNames();
        System.out.println("hi number of populateed attributes is " + populatedAttributes.length);
        
        Object value = null;
        
        for (int i = 0; i < populatedAttributes.length; i++) {
            try {
                System.out.println("hi " + i + "  " + populatedAttributes[i]);
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
    public void updateAttributes(ManagedEntityEx1ValueImpl mev) {
        // in actual practise use getDirtyattribute using attribute manager and then compare those which have changed value and then update
        setAttributes(mev);
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param attributeName DOCUMENT ME!
     * @param anValue DOCUMENT ME!
     */
    public void setAttribute(String attributeName, ManagedEntityEx1ValueImpl anValue) {
        if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_API_CLIENT_ID)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_API_CLIENT_ID)){
            setEx1ApiClientId(anValue.getEx1ApiClientId());
        } else if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_NAME)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_NAME)) {
            setEx1Name(anValue.getEx1Name());
        } else if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_VALUE)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_VALUE)) {
            setEx1Value(anValue.getEx1Value());
        } else if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_MAX_VALUE)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_MAX_VALUE)) {
            setEx1MaxValue(anValue.getEx1MaxValue());
        } else if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_DESCRIPTION)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_DESCRIPTION)) {
            setEx1Description(anValue.getEx1Description());
        } else if (attributeName.equals(ManagedEntityEx1ValueImpl.EX_1_MULTI_VALUE_LIST)&&anValue.isPopulated(ManagedEntityEx1ValueImpl.EX_1_MULTI_VALUE_LIST)) {
            setEx1MultiValueList(anValue.getEx1MultiValueList());
        }
    }
}
