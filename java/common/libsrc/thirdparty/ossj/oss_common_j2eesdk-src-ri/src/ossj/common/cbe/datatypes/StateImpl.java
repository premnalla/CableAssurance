/*
Copyright 2002-2006 The Members of the OSS through Java(TM) Initiative.
All rights reserved. Use is subject to license terms.
 */
package ossj.common.cbe.datatypes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.oss.cbe.datatypes.State;
import ossj.common.Utils;


/**
 * An implementation class for the <CODE>javax.oss.cbe.datatypes.State</CODE> interface.
 *
 * @see javax.oss.cbe.datatypes.State
 *
 * @author OSS through Java(tm) Initiative, Vincent Perrot Sun Microsystems Inc.
 * @version 1.3
 * @since January 2006
 */


public class StateImpl implements State {
    
    private ArrayList states = new ArrayList();
    private java.lang.String _stateimpl_state = null;
    
    /**
     * Constructs a new StateImpl with empty attributes.
     *
     */
    
    public StateImpl() {
        
    }
    
    /**
     * Constructs a new StateImpl using the given states attributes.
     *
     */
    
    public StateImpl(String[] states) {
        for (int i = states.length-1;i>=0;i--){
            this.states.add(states[i]);
        }
    }
    
    /**
     * Constructs a new StateImpl using the given state definition.
     * <p>
     * Example:
     * <PRE>
     *  State myState = new StateImpl(AdministrativeState.class);
     *  System.out.println("state contains: "+myState.getSupportedStates().length+"\n");
     *  String[] sList = myState.getSupportedStates();
     *  for (int i = sList.length -1 ; i >= 0 ; i--) {
     *     System.out.println("- "+sList[i]);
     *  }
     * </PRE>
     */
    
    public StateImpl(Class state) throws IllegalArgumentException{
        if (State.class.isAssignableFrom(state)) {
            Field[] fList = state.getFields();
            String value = null;
            for (int i=fList.length-1;i>=0;i--){
                try {
                    value = (String)fList[i].get(new String());
                    if (value.equals(State.SEPARATOR)||value.equals(State.DATA_TYPE)){
                        continue;
                    }
                    this.states.add(value);
                } catch(IllegalAccessException iaex){
                    
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid paramter class: "+state.getName());
        }
    }
    
    /**
     * Returns true if the given state is available in the managed list.
     *
     * @param state value to look for
     * @return true if the given state is in the list
     */
    public boolean contains(java.lang.String state){
        return states.contains(state);
    }
    
    /**
     * Changes the state to be equal to the given argument.
     *
     * This method support a null argument.
     *
     * @param value the new state for this object.
     * @exception java.lang.IllegalArgumentException - Is thrown to report
     * that a bad argument was provided to the method.
     */
    
    public void setState(java.lang.String value)
    throws java.lang.IllegalArgumentException    {
        _stateimpl_state = value;
    }
    
    
    /**
     * Returns this StateImpl's state
     *
     * @return the state
     *
     */
    
    public java.lang.String getState() {
        return _stateimpl_state;
    }
    
    
    /**
     * Returns true if the proposed new given state can be applied.
     * This method <B>do not change</B> the current value of the state,
     * but it is used to verify if the proposed new state is applicable.
     * <p>
     * A returned false means that the state transition from the current value to the given one is not valid.
     * <P>
     *
     * @param state the new proposed state value
     * @return true if the transition to this new state is valid, false otherwise.
     */
    public boolean trySetState(java.lang.String state){
        if (states.contains(state)){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns this StateImpl's supportedStates
     *
     * @return the supportedStates
     *
     */
    
    public java.lang.String[] getSupportedStates() {
        return (String[])states.toArray(new String[0]);
    }
    
    /**
     * Creates a new object of the same class and with the same contents as this object.
     * @return a clone of this instance.
     */
    public Object clone() {
        State val = null;
        try {
            val = (State)super.clone();
            
            return val;
        } catch( CloneNotSupportedException ex) {
            throw new RuntimeException("StateImpl: Unable to clone this object: "+ ex.getMessage());
        }
    }
    
    /**
     * Checks whether two State are equal.
     * The result is true if and only if the argument is not null
     * and is an State object that has the arguments.
     *
     * @param value the Object to compare with this State
     * @return if the objects are equal; false otherwise.
     */
    
    public boolean equals(Object value) {
        if ( this == value ){ return true;}
        if ( (value != null) && ( value instanceof State)) {
            State argVal = (State) value;
            if( !Utils.compareAttributes( getState(), argVal.getState())) { return false; }
            if( !Utils.compareAttributes( getSupportedStates(), argVal.getSupportedStates())) { return false; }
            
            return true;
        } else {
            return super.equals(value);
        }
    }
    
}
