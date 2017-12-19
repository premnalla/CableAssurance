/**
 * Class brief description -    This is the Helper class of TT RI.
 * Class detailed description - This class is used to read all the TTRI properties from the file.
 *
 *@ class TTHelper.java
 *@author  Amit Kumar Varshney(amitkumar.varshney@wipro.com), Shilpy Mittal(shilpy.mittal@wipro.com)
 * Copyright 2005 IP VALUE GmbH and WIPRO TECHNOLOGIES. All rights reserved.
 *@ date July 2005
 *
 */


package ossj.ttri;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;


public class TTHelper {

    static Properties projectProperties = null;

    static {

           try {
               String propFileBase = System.getProperty("PROPFILE_BASE");
               projectProperties = new Properties();
               if (propFileBase == null || propFileBase.equals("")) {
                   propFileBase = System.getProperty("catalina.base", ".");
               }

               String riPropFile = propFileBase + java.io.File.separator + "TTRI.properties";
			   File file = new File(riPropFile);
			   if ( file.exists() && file.isFile() ) {
				   FileInputStream fis = new FileInputStream(riPropFile);
				   projectProperties.load(fis);
			   } else {
				   InputStream in =TTHelper.class.getClassLoader().getResourceAsStream("TTRI.properties");
				   projectProperties.load(in);
			   }

           } catch (Exception nExp) {
               nExp.printStackTrace();
           }

    }
}