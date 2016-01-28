package org.donglai.logp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RowNumberCalculatorTest {
	String dir=RowNumberCalculatorTest.class.getResource("/files1").getFile();;
	RowNumberCalculator cal=null;
	List<String> list =null;
//	String dir="/files1";
	
	OperationRecorder or=null;
	@Before
	public void before(){
		OperationRecorder.init(dir);
		cal=new RowNumberCalculator();
		FileNameProcessor sp=new FileNameProcessor();
		or=OperationRecorder.getInstance();
		list = sp.getlogfiles(dir);
	}
	/**
	 * The order in the test log file list is:
	 *  logtest.2011-07-11.log
	 *  logtest.2014-07-11.log
	 *  logtest.2014-07-12.log (empty)
	 *  logtest.2014-09-11.log
	 */
	//5 rows
	String p1=dir+"/logtest.2011-07-11.log";
	//7 rows
	String p2=dir+"/logtest.2014-07-11.log";
	//empty file
	String p3=dir+"/logtest.2014-07-12.log";
	//4 rows
	String p4=dir+"/logtest.2014-09-11.log";
	@Test
	public void testCalRowNumbers() {
		long[] rowNumber= cal.calRowNumbers(dir,list);
		assertTrue( rowNumber[0]== 5l);
		assertTrue( rowNumber[1]== 7l);
		assertTrue(!(rowNumber[2] >0));
	}
	@Test
	public void testCa() throws IOException {
		cal.calStoreStartRow(dir, list);
		BufferedReader rd = or.getRowStartReader();
		List<String> lines=new ArrayList<String>();
		String line=rd.readLine();
		while(line!=null){
			lines.add(line);
			line=rd.readLine();
		}
		System.out.println(lines);
		assertEquals(lines.size(),3);
		assertTrue(lines.contains(p2+"\t"+"6"));
		assertTrue(lines.contains(p4+"\t"+"13"));
	}
	
	
}
