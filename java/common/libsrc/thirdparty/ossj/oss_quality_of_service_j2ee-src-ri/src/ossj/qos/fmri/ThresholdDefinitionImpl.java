/*
 * Copyright: Copyright © 2001 Ericsson Radio Systems AB.
 *            All rights reserved. Use is subject to license terms.
 */
package ossj.qos.fmri;

import javax.oss.pm.threshold.ThresholdDefinition;
import javax.oss.pm.measurement.PerformanceAttributeDescriptor;
import javax.oss.IllegalArgumentException;
import ossj.qos.util.Util;

/**
 * Implementation of ThresholdDefinition.
 *
 * @author Henrik Lindstrom
 */
public class ThresholdDefinitionImpl implements ThresholdDefinition
{
   /**
    * The attribute descriptor.
    */
   private PerformanceAttributeDescriptor attributeDescriptor;

   /**
    * The direction of the threshold crossing.
    */
   private int direction;

   /**
    * The offset associated with the threshold definition.
    */
   private Object offset;

   /**
    * The threshold value quantity.
    */
   private Object value;

   /**
    * Create a new ThresholdDefinitionImpl.
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
            //System.out.println("Problem cloning ThresholdDefintion.");
        }
        return clone;
   }

   public PerformanceAttributeDescriptor getAttributeDescriptor()
   {
        return attributeDescriptor;
   }

   public int getDirection()
   {
        return direction;
   }

   public Object getOffset()
   {
        return offset;
   }

   public Object getValue()
   {
        return value;
   }

   public void setAttributeDescriptor(PerformanceAttributeDescriptor descriptor)
   {
        attributeDescriptor = descriptor;
   }

   public void setDirection(int direction)
   {
        this.direction = direction;
   }

   public void setOffset(Object offset)
   {
        this.offset = offset;
   }

   public void setValue(Object value)
   {
        this.value = value;
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
    
    public PerformanceAttributeDescriptor makePerformanceAttributeDescriptor() {
        return new PerformanceAttributeDescriptorImpl();
    }
    
    /**
     * Returns a string representation of the class.
     *
     * @return String - representation of the class.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(200);
        buffer.append(" << ThresholdDefinitionImpl >> \n" );
        buffer.append(Util.rightJustify(30,"attributeDescriptor = ") + Util.printObject(attributeDescriptor) );
        buffer.append(Util.rightJustify(30,"direction = ") + direction + "\n");
        buffer.append(Util.rightJustify(30,"value = ") + Util.printObject(value) );
        return buffer.toString();
    }
}
