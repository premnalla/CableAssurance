/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
package org.apache.axis.types;

import org.apache.axis.utils.Messages;

import java.io.ObjectStreamException;
import java.math.BigInteger;
import java.util.Random;

/**
 * Custom class for supporting primitive XSD data type nonNegativeInteger
 *
 * @author Russell Butek <butek@us.ibm.com>
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#nonNegativeInteger">XML Schema 3.3.20</a>
 */
public class NonNegativeInteger extends BigInteger {

    public NonNegativeInteger(byte[] val) {
        super(val);
        checkValidity();
    } // ctor

    public NonNegativeInteger(int signum, byte[] magnitude) {
        super(signum, magnitude);
        checkValidity();
    } // ctor

    public NonNegativeInteger(int bitLength, int certainty, Random rnd) {
        super(bitLength, certainty, rnd);
        checkValidity();
    } // ctor

    public NonNegativeInteger(int numBits, Random rnd) {
        super(numBits, rnd);
        checkValidity();
    } // ctor

    public NonNegativeInteger(String val) {
        super(val);
        checkValidity();
    }

    public NonNegativeInteger(String val, int radix) {
        super(val, radix);
        checkValidity();
    } // ctor

    /**
     * validate the value against the xsd definition
     */
    private BigInteger zero = new BigInteger("0");
    private void checkValidity() {
        if (compareTo(zero) < 0) {
            throw new NumberFormatException(
                    Messages.getMessage("badNonNegInt00")
                    + ":  " + this);
        }
    } // checkValidity
    
    /**
     * Work-around for http://developer.java.sun.com/developer/bugParade/bugs/4378370.html
     * @return BigIntegerRep
     * @throws ObjectStreamException
     */ 
    public Object writeReplace() throws ObjectStreamException {
        return new BigIntegerRep(toByteArray());
    }
    
    protected static class BigIntegerRep implements java.io.Serializable {
        private byte[] array;
        protected BigIntegerRep(byte[] array) {
            this.array = array;
        }
        protected Object readResolve() throws java.io.ObjectStreamException {
            return new NonNegativeInteger(array);
        }
    }
} // class NonNegativeInteger
