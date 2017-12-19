/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.pm.threshold;

import javax.oss.pm.threshold.ThresholdDefinition;
import javax.oss.pm.threshold.ThresholdDirection;
import javax.oss.pm.measurement.PerformanceAttributeDescriptor;
import javax.oss.IllegalArgumentException;

import ossj.qos.util.Log;
import ossj.qos.util.Util;

/**
 * Implements the ThresholdDefinition. One thing is not really nice with the
 * class it requires the performance monitor reference implementation to be
 * work. Otherwise it will not find the PerformanceAttributeDescriptor
 * implementation. This could probably be re-thought and re-implemented in a
 * nicer way.
 *
 * @author Henrik Lindstrom, Ericsson
 * @see #makePerformanceAttributeDescriptor
 * @see #PM_ATTRIBUTE_DESCRIPTOR_IMPL
 */
public class ThresholdDefinitionImpl implements ThresholdDefinition
{
    /**
     * Class name for the PerformanceAttributeDescriptor implementation class
     * defined by the performance monitor reference implementation.
     *
     * If another implementation of the PM is used, then this constant need to
     * be redefined. (This could be solved with some nice property...) Class is
     * created using Class.forName().newInstance();
     */
    public static final String PM_ATTRIBUTE_DESCRIPTOR_IMPL =
        "ossj.qos.pm.measurement.PerformanceAttributeDescriptorImpl";

   /**
    * The attribute descriptor.
    */
   private PerformanceAttributeDescriptor attributeDescriptor;

   /**
    * The direction of the threshold crossing.
    */
   private int direction;

   private boolean  directionDefined = false;

   /**
    * The offset associated with the threshold definition.
    */
   private Object offset;

   /**
    * The threshold value quantity.
    */
   private Object value;


    /**
     * Constructs a ThresholdDefinition.
     */
   public ThresholdDefinitionImpl()
   {
   }

   public Object clone()
   {
        ThresholdDefinitionImpl clone = null;
        try {
            clone = (ThresholdDefinitionImpl)super.clone();
            if ( attributeDescriptor != null ) {
                clone.attributeDescriptor = (PerformanceAttributeDescriptor)attributeDescriptor.clone();
            }
            clone.offset = Util.clone( offset ); // try clone offset
            clone.value = Util.clone( value );   // try clone value
        } catch ( CloneNotSupportedException e ) {
            Log.write(this,Log.LOG_MAJOR,"clone()","exception="+e);
        }
        return clone;
   }

   public PerformanceAttributeDescriptor getAttributeDescriptor()
   {
        if ( attributeDescriptor==null ) {
            throw new IllegalStateException( "The attribute descriptor is not defined!" );
        }
        return attributeDescriptor;

   }

    /**
     * Creates a PerformanceAttributeDescriptor object using the performance
     * monitor reference implementation. This method is only successful if the
     * class defined by the PM_ATTRIBUTE_DESCRIPTOR_IMPL constant is found and
     * possible to instantiate.
     * @return instance of PM_ATTRIBUTE_DESCRIPTOR_IMPL class.
     */
    public PerformanceAttributeDescriptor makePerformanceAttributeDescriptor() {
        Log.write(this,Log.LOG_ALL,"makePerformanceAttributeDescriptor()",
            "creating implementation class from PM RI...");
        // this will only be successful if the PM RI implementation is available
        try {
            // lookup class
            Class cls = Class.forName( PM_ATTRIBUTE_DESCRIPTOR_IMPL );
            // create object
            return (PerformanceAttributeDescriptor)cls.newInstance();
        } catch ( ClassNotFoundException e ) {
            Log.write(this,Log.LOG_MAJOR,"makePerformanceAttributeDescriptor()","exception="+e);
            Log.write("Can not find the implementation class for PerformanceAttributeDescriptor!");
            Log.write("Make sure it is available in the class path.");
            throw new IllegalStateException( e.getMessage() );
        } catch ( IllegalAccessException e ) {
            Log.write(this,Log.LOG_MAJOR,"makePerformanceAttributeDescriptor()","exception="+e);
            Log.write("Not allowed to instantiate the class "+ PM_ATTRIBUTE_DESCRIPTOR_IMPL );
            throw new IllegalStateException( e.getMessage() );
        } catch ( InstantiationException e ) {
            Log.write(this,Log.LOG_MAJOR,"makePerformanceAttributeDescriptor()","exception="+e);
            Log.write("Not allowed to instantiate the class "+ PM_ATTRIBUTE_DESCRIPTOR_IMPL );
            throw new IllegalStateException( e.getMessage() );
        } catch ( ClassCastException e ) {
            Log.write(this,Log.LOG_MAJOR,"makePerformanceAttributeDescriptor()","exception="+e);
            Log.write("Cannot cast class to PerformanceAttributeDescriptor interface: "+ PM_ATTRIBUTE_DESCRIPTOR_IMPL );
            throw new IllegalStateException( e.getMessage() );
        }
    }

   public int getDirection()
   {
        if ( directionDefined==false ) {
            throw new IllegalStateException( "The direction is not defined!" );
        }
        return direction;
   }

   public Object getOffset()
   {
        if ( offset == null ) {
            throw new IllegalStateException( "The offset is not defined!" );
        }
        return offset;
   }

   public Object getValue()
   {
        if ( value == null ) {
            throw new IllegalStateException( "The value is not defined!" );
        }
        return value;
   }

   public void setAttributeDescriptor(PerformanceAttributeDescriptor descriptor)
   {
        if ( descriptor==null ) {
            throw new java.lang.IllegalArgumentException( "The attribute descriptor is null!" );
        }
        attributeDescriptor = descriptor;
   }

   public void setDirection(int direction)
   {
        if ( direction == ThresholdDirection.FALLING ||
          direction == ThresholdDirection.RISING ) {
            this.direction = direction;
            directionDefined = true;
        } else {
           throw new java.lang.IllegalArgumentException( "The direction is illegal!" );
        }
   }

   public void setOffset(Object offset)
   {
        if ( offset==null ) {
            throw new java.lang.IllegalArgumentException( "The offset is null!" );
        }
        this.offset = offset;
   }

   public void setValue(Object value)
   {
        if ( value==null ) {
          throw new java.lang.IllegalArgumentException( "The value is null!" );
        }
        this.value = value;
   }

   public String toString()
   {
        // just a simple text version for debugging...
        return "[" + getPerformanceAttributeDescriptorString(attributeDescriptor)  + ",value=" + value + ",offset=" + offset
            + ",direction="+direction+"]";
   }

    /**
     * @return String representation of PerformanceAttributeDescriptor
     */
    private static String getPerformanceAttributeDescriptorString( PerformanceAttributeDescriptor pad ) {
        if ( pad == null ) {
            return null;
        }
        int type = pad.getType();
        String typeString = "undef";
        switch ( type ) {
            case PerformanceAttributeDescriptor.INTEGER:
                typeString = "INTEGER";
                break;
            case PerformanceAttributeDescriptor.REAL:
                typeString = "REAL";
                break;
            case PerformanceAttributeDescriptor.STRING:
                typeString = "STRING";
                break;
            default:
        }
        String string = "[" + pad.getName() + ", type=" + typeString
            + ", collection method=" + pad.getCollectionMethod() + "]";
        return string;
    }


    /**
     * Equals implementation for threshold definition. Checks if all attributes
     * in the defintions are equal.
     *
     * @param o object to compare with
     * @return true if equal
     */
    public boolean equals( Object o ) {
        if ( o!=null && o instanceof ThresholdDefinitionImpl ) {
            ThresholdDefinitionImpl obj = (ThresholdDefinitionImpl)o;

            // Compare direction
            if ( obj.direction == this.direction ) {

                // Compare the value (Object) variable, not that this implies that
                // the equals method for Object is correctly defined. Since value
                // should be Integer, Real(double) or String this works.
                // See the PerformanceAttributeDescriptor.
                if ( (obj.value==null && this.value==null) || obj.value.equals( this.value ) ) {

                    // Compare the offset. In the same way as for value.
                    if ( (obj.offset==null && this.offset==null) || obj.offset.equals( this.offset ) ) {

                        // Compare attribute descriptor content. Since this is the
                        // PerformanceAttributeDescriptor, which is not part of the
                        // threshold Reference Implementation, it is not possible to
                        // assume that the equals() method is correctly defined/overridden.
                        PerformanceAttributeDescriptor descriptorOne = obj.getAttributeDescriptor(),
                                                       descriptorTwo = this.getAttributeDescriptor();

                        if ( descriptorOne==null && descriptorTwo==null ) {
                            return true;
                        } else if ( descriptorOne!=null && descriptorTwo!=null ) {
                            // check all attributes in descriptor

                            // compare type and isArray
                            if ( descriptorOne.getType() == descriptorTwo.getType()
                              && descriptorOne.isArray() == descriptorTwo.isArray() ) {

                                // compare collection method
                                String stringOne = descriptorOne.getCollectionMethod(),
                                       stringTwo = descriptorTwo.getCollectionMethod();
                                if ( (stringOne==null && stringTwo==null) || stringOne.equals(stringTwo) ) {

                                    // compare name
                                    stringOne = descriptorOne.getName();
                                    stringTwo = descriptorTwo.getName();
                                    if ( (stringOne==null && stringTwo==null) || stringOne.equals(stringTwo) ) {
                                        return true;
                                    }
                                }
                            }
                        } // descriptor
                    } // offset
                } // value
            } // direction
        } // null/instance of
        return false; // the default return
    }
}
