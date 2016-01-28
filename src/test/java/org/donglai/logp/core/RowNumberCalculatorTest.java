package org.donglai.logp.core;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.donglai.logp.core.RowNumberCalculator;
import org.donglai.logp.core.FileNameProcessor;
import org.junit.Before;
import org.junit.Test;

public class RowNumberCalculatorTest {
	RowNumberCalculator cal=new RowNumberCalculator();
	List<String> list =null;
	String dir="/files1";
	
	String real_path=RowNumberCalculatorTest.class.getResource(dir).getFile();
	@Before
	public void before(){
		FileNameProcessor sp=new FileNameProcessor();
		list = sp.getlogfiles(real_path);
	}
	/**
	 * The order in the test log file list is:
	 *  logtest.2011-07-11.log
	 *  logtest.2014-07-11.log
	 *  logtest.2014-07-12.log (empty)
	 *  logtest.2014-09-11.log
	 */
	//5 rows
	String p1=RowNumberCalculatorTest.class.getResource(dir+"/logtest.2011-07-11.log").getFile();
	//7 rows
	String p2=RowNumberCalculatorTest.class.getResource(dir+"/logtest.2014-07-11.log").getFile();
	//empty file
	String p3=RowNumberCalculatorTest.class.getResource(dir+"/logtest.2014-07-12.log").getFile();
	//4 rows
	String p4=RowNumberCalculatorTest.class.getResource(dir+"/logtest.2014-09-11.log").getFile();
	@Test
	public void testCalRowNumbers() {
		long[] rowNumber= cal.calRowNumbers(real_path,list);
		assertTrue( rowNumber[0]== 5l);
		assertTrue( rowNumber[1]== 7l);
		assertTrue(!(rowNumber[2] >0));
	}

	
	
}
