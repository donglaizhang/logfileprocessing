package org.donglai.logp;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RowNumberCalculatorTest {
	RowNumberCalculator cal=new RowNumberCalculator();
	List<Path> list =null;
	String dir="/files1";
	
	@Before
	public void before(){
		SorterProcessor sp=new SorterProcessor();
		String real_path=RowNumberCalculatorTest.class.getResource(dir).getFile();
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
		Map<Path, Long> map = cal.calRowNumbers(list);
		assertTrue( map.get(Paths.get(p1))== 5l);
		assertTrue( map.get(Paths.get(p2))== 7l);
		assertNull( map.get(Paths.get(p3)));
	}

	@Test
	public void testCalFileStartRowNumber(){
		Map<Path, String> map = cal.calFileStartRowNumber(list);
		assertTrue(map.size()==3);
		assertEquals(map.get(Paths.get(p1)),"1");
		assertEquals(map.get(Paths.get(p2)),"6");
		assertEquals(map.get(Paths.get(p4)),"13");
	}
	
}
