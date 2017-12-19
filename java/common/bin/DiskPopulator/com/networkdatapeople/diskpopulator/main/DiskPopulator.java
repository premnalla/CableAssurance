/**
 * 
 */
package com.networkdatapeople.diskpopulator.main;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

/**
 * @author Prem
 *
 */
public class DiskPopulator {

	/**
	 * 
	 */
	public DiskPopulator() {
		super();
	}

	public void createFileOnDisk(int fillSizeInMegaBytes, int maxFileSizeInMegaBytes) {
		SimpleDateFormat df = new SimpleDateFormat("MMdd-HHmmss");

		/*
		System.out.println(date);
		System.out.println(date.toGMTString());
		System.out.println(date.toLocaleString());
		System.out.println(df.format(date));
		*/
		
		// String testString = "Foo";
		
		final int BYTE_ARRAY_SIZE = 1024 * 1024;
		final int FILL_ARRAY_SIZE = BYTE_ARRAY_SIZE - 1;
		byte foo[] = new byte[BYTE_ARRAY_SIZE];
		for (int i=0; i<FILL_ARRAY_SIZE; i++) {
			foo[i] = '\0';
		}
		foo[FILL_ARRAY_SIZE] = '\n';
		
		try {
			final int div = fillSizeInMegaBytes/maxFileSizeInMegaBytes;
			final int mod = fillSizeInMegaBytes % maxFileSizeInMegaBytes;
			final int numFiles = div + (mod==0?0:1);
			final int numIterations = maxFileSizeInMegaBytes;
			for (int i=0; i<numFiles; i++) {
				GregorianCalendar cal = new GregorianCalendar();
				// System.out.println("outerloop");
				Date date = cal.getTime();
				StringBuffer fileName = new StringBuffer();
				
				// DIR ==> C:\data\TEMP
				fileName.append("c:").append(File.separator).append("data").append(File.separator)
				.append("TEMP").append(File.separator).append(df.format(date));
				
				// System.out.println(fileName);
				
				File file = new File(fileName.toString());
				FileOutputStream foStream = new FileOutputStream(file);
				// foStream.write(testString.getBytes());
				
				for (int j=0; j<numIterations; j++) {
					// System.out.println("-innerloop");
					foStream.write(foo);
					Thread.sleep(100);
				}
				
				foStream.close();
				
				date = null;
				cal = null;
				fileName = null;
				file = null;
				foStream = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("Usage: <progname> fill-size max-file-size");
			System.exit(-1);
		}
		
		String fillSizeS = args[0];
		String maxFileSizeS = args[1];
		int fillSize = 0;
		int maxFileSize = 0;
		
		try {
			fillSize = Integer.parseInt(fillSizeS);
			maxFileSize = Integer.parseInt(maxFileSizeS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// System.out.println(args.length);
		// System.out.println(args[0]);
		
		DiskPopulator d = new DiskPopulator();
		// d.testDate();
		d.createFileOnDisk(fillSize, maxFileSize);
	}

	public void testDate() {
		// GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat df = new SimpleDateFormat("MMdd-HHmmss");
		try {
			for (int i=0; i<2; i++) {
				GregorianCalendar cal = new GregorianCalendar();
				Date date = cal.getTime();
				System.out.println(date);
				System.out.println(date.toGMTString());
				System.out.println(date.toLocaleString());
				long t = cal.getTimeInMillis();
				System.out.println(t);
				System.out.println("\n\n\n");
				Thread.sleep(2000);
				date = null;
				cal = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
