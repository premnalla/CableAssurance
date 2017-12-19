/*
 *
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is Copyright (C) 2005 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified 
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 *
 * This file was originally part of the JRobin distribution but was modified extensively
 * to stress test the Rrd implementation of OpenNMS.
 *
 * ============================================================
 * JRobin : Pure java implementation of RRDTool's functionality
 * ============================================================
 * 
 * Project Info: http://www.jrobin.org Project Lead: Sasa Markovic (saxon@jrobin.org);
 * 
 * (C) Copyright 2003, by Sasa Markovic.
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * Developers: Sasa Markovic (saxon@jrobin.org) Arne Vandamme (cobralord@jrobin.org)
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package org.opennms.netmgt.rrd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class RrdStresser {

    static int currFileNum = 0;

    static int[] dataPosition = null;

    static final String FACTORY_NAME = System.getProperty("stresstest.factory", "FILE");

    static final int FILE_COUNT = Integer.getInteger("stresstest.filecount", 10000).intValue();

    static final int ZERO_FILES = Integer.getInteger("stresstest.zerofiles", (FILE_COUNT / 2)).intValue();

    static final int FILES_PER_DIR = Integer.getInteger("stresstest.filesperdir", 0).intValue();

    static Date firstUpdateComplete = null;

    static final int MAX_UPDATES = Integer.getInteger("stresstest.maxupdates", 1000).intValue();

    static final int MODULUS = Integer.getInteger("stresstest.modulus", 1000).intValue();

    static final int RRD_DATASOURCE_COUNT = Integer.getInteger("stresstest.datasourcecount", 1).intValue();

    static final String RRD_DATASOURCE_NAME = "T";

    static final String RRD_PATH = System.getProperty("stresstest.file", "/var/opennms/rrd/stressTest/stress");

    static final long RRD_START = 946710000L;

    static final long RRD_STEP = Integer.getInteger("stresstest.rrdstep", 300).intValue();

    static final int THREAD_COUNT = Integer.getInteger("stresstest.threadcount", 1).intValue();

    static final long TIME_END = 1080013472L;

    static final long TIME_START = 1060142010L;

    private static final String UPDATE_FILE = System.getProperty("stresstest.updatefile", "stress-test-simple.txt");

    static int updateCount = 0;

    static String[] updateData = null;

    static final int UPDATES_PER_OPEN = Integer.getInteger("stresstest.updatesperopen", 1).intValue();

    static Date updateStart = null;

    static final boolean USE_JNI = "true".equals(System.getProperty("stresstest.usejni", "false"));

    static final boolean USE_QUEUE = "true".equals(System.getProperty("stresstest.usequeue", "true"));

    static final boolean QUEUE_CREATES = "true".equals(System.getProperty("stresstest.queuecreates", "true"));

    static final int UPDATE_TIME = Integer.getInteger("stresstest.updatetime", 300).intValue();

    static final String EXTENSION = ".rrd";

    static long filesPerZero = FILE_COUNT / ZERO_FILES;

    static RrdStrategy rrd = null;

    /**
     * 
     */
    private static synchronized void countUpdate() {
        if (updateCount == FILE_COUNT) {
            firstUpdateComplete = new Date();
        }
        if (++updateCount % MODULUS == 0) {
            printStats();
        }
    }

    /**
     * 
     */
    private static void printStats() {
        Date now = new Date();
        long avgSpeed = (long) (updateCount * 1000.0 / (now.getTime() - updateStart.getTime()));
        long updateSpeed = (long) ((updateCount - FILE_COUNT) * 1000.0 / (now.getTime() - firstUpdateComplete.getTime()));
        print(updateCount + " samples stored, " + updateSpeed + " updates/sec avg since first update " + avgSpeed + " updates/sec avg for all time: " + rrdGetStats());
    }

    static String getFileName(int fileNum) {
        if (FILES_PER_DIR == 0) {
            return RRD_PATH + fileNum + EXTENSION;
        } else {
            int dirNum = fileNum / FILES_PER_DIR;
            return RRD_PATH + File.separator + dirNum + File.separator + fileNum + EXTENSION;
        }

    }

    private static synchronized String getNextLine(int fileNum) {
        int pos = dataPosition[fileNum];
        if (pos >= updateData.length) {
            dataPosition[fileNum] = 0;
        } else {
            dataPosition[fileNum]++;
        }
        String update = updateData[pos];

        if (ZERO_FILES > 0) {
            if (fileNum % filesPerZero == 0) {
                update = makeZeroUpdate(fileNum, update);
            }
        }
        return update;
    }

    private static String makeZeroUpdate(int fileNum, String update) {
        try {
            int colon = update.indexOf(':');
            if (colon >= 0) {
                long initialTimeStamp = Long.parseLong(update.substring(0, colon));
                if (initialTimeStamp == 0)
                    print("ZERO ERROR: created a zero update with ts=0 for file: " + getFileName(fileNum) + " data: " + update);

                update = initialTimeStamp + ":0";
            }
        } catch (NumberFormatException e) {

        }
        return update;
    }

    public static void main(final String[] args) throws Exception {
        printHeader();
        print("Starting demo at " + new Date());

        rrdInitialize();

        print("Loading update file");
        BufferedReader rdr = new BufferedReader(new InputStreamReader(RrdStresser.class.getResourceAsStream(UPDATE_FILE)));
        List dataList = new LinkedList();
        String line;
        while ((line = rdr.readLine()) != null) {
            dataList.add(line);
        }
        updateData = (String[]) dataList.toArray(new String[dataList.size()]);

        print("Finished loading update file.  Initializing data positions");

        dataPosition = new int[FILE_COUNT];

        Date createStart = new Date();

        print("Creating " + FILE_COUNT + " RRD files");
        for (int i = 0; i < FILE_COUNT; i++) {
            dataPosition[i] = 0;
            File f = new File(getFileName(i));
            if (!f.exists()) {
                // create RRD database
                Object rrdDef = rrdCreateDefinition(i);
                rrdCreateFile(rrdDef);
            }
        }

        Date now = new Date();
        long speed = (long) (FILE_COUNT * 1000.0 / (now.getTime() - createStart.getTime()));
        print(FILE_COUNT + " files created, " + speed + " creates/sec");

        updateStart = new Date();
        firstUpdateComplete = new Date();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable r = new Runnable() {

                public void run() {
                    try {
                        RrdStresser test = new RrdStresser();
                        test.execute(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread t = new Thread(r);
            t.start();
        }
    }

    private static synchronized boolean moreUpdates() {
        return updateCount < MAX_UPDATES;
    }

    static synchronized int nextFileNum() {
        currFileNum = (currFileNum + 1) % FILE_COUNT;
        return currFileNum;
    }

    /**
     * 
     */
    private static void printHeader() {
        System.out.println("********************************************************************");
        System.out.println("* OpenNMS Collectoin StressTest                                    *");
        System.out.println("*                                                                  *");
        System.out.println("* This demo creates single RRD file and tries to update it         *");
        System.out.println("* more than 600.000 times. Real data (> 20Mb) is obtained from the *");
        System.out.println("* stress-test.txt file provided by Vadim Tkachenko                 *");
        System.out.println("* (http://diy-zoning.sourceforge.net).                             *");
        System.out.println("*                                                                  *");
        System.out.println("* Finally, a single PNG graph will be created from the RRD file.   *");
        System.out.println("* The stress test takes about one hour to complete on a 1.6GHz     *");
        System.out.println("* computer with 256MB of RAM.                                      *");
        System.out.println("********************************************************************");
        System.out.println("useJni = " + USE_JNI);
        System.out.println("modulus = " + MODULUS);
        System.out.println("fileCount = " + FILE_COUNT);
        System.out.println("zeroFiles = " + ZERO_FILES);
        System.out.println("threadCount = " + THREAD_COUNT);
        System.out.println("useQueuing = " + USE_QUEUE);
        System.out.println("queueCreates = " + QUEUE_CREATES);
        System.out.println();

    }

    static void print(String message) {
        System.out.println(message);
    }

    private static void rrdCloseFile(Object rrdFile) throws Exception {
        rrd.closeFile(rrdFile);
    }

    private static Object rrdCreateDefinition(int fileNum) throws Exception {
        String fileName = getFileName(fileNum);
        File file = new File(fileName);
        String dir = file.getParent();
        String[] rraList = { "RRA:AVERAGE:0.5:1:8928", "RRA:AVERAGE:0.5:12:8784", "RRA:MIN:0.5:12:8784", "RRA:MAX:0.5:12:8784", };
        String dsName = file.getName();
        if (dsName.endsWith(".rrd")) {
            dsName = dsName.substring(0, dsName.length() - ".rrd".length());
        }
        return rrd.createDefinition("stressTest", dir, dsName, 300, "GAUGE", 600, "U", "U", Arrays.asList(rraList));
    }

    private static void rrdCreateFile(Object rrdDef) throws Exception {
        rrd.createFile(rrdDef);
    }

    private static void rrdInitialize() throws Exception {
        if (USE_JNI) {
            rrd = new JniRrdStrategy();
        } else {
            rrd = new JRobinRrdStrategy();
        }
        if (USE_QUEUE) {
            rrd = new QueuingRrdStrategy(rrd);
        }
        rrd.initialize();
    }

    private static Object rrdOpenFile(String fileName) throws Exception {
        return rrd.openFile(fileName);
    }

    private static String rrdGetStats() {
        return rrd.getStats();
    }

    private static void rrdUpdateFile(Object rrdFile, String data) throws Exception {
        rrd.updateFile(rrdFile, data);

    }

    public void execute(String[] args) throws Exception {

        double millisPerUpdate = ((double) UPDATE_TIME * 1000) / ((double) (MAX_UPDATES));

        while (moreUpdates()) {
            int fileNum = nextFileNum();
            Object rrd = rrdOpenFile(getFileName(fileNum));

            for (int i = 0; i < UPDATES_PER_OPEN; i++) {
                Date now = new Date();
                long elapsedTime = now.getTime() - updateStart.getTime();
                long expectedTime = (long) (millisPerUpdate * (double) updateCount);
                if (expectedTime > elapsedTime) {
                    try {
                        Thread.sleep(expectedTime - elapsedTime);
                    } catch (InterruptedException e) {
                    }
                }

                String line = getNextLine(fileNum);
                try {
                    rrdUpdateFile(rrd, line);
                    countUpdate();
                } catch (Exception e) {
                    print("RRD ERROR: " + line + " : " + e.getMessage());
                }
            }

            rrdCloseFile(rrd);
        }

    }

}