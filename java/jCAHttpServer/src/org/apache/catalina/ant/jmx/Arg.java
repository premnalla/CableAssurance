/*
 * Copyright 2002,2004 The Apache Software Foundation.
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

package org.apache.catalina.ant.jmx;

/**
 *
 * @author Peter Rossbach
 * @version $Revision: 1.1 $ $Date: 2007/03/01 20:19:03 $
 * @since 5.5.10
 */
public class Arg {
    String name;
    String type;
    String value;

    public void setName( String name) {
        this.name=name;
    }
    public void setType( String type) {
        this.type=type;
    }
    public void setValue( String value ) {
        this.value=value;
    }
    public void addText( String text ) {
        this.value=text;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
    public String getType() {
        return type;
    }
}
