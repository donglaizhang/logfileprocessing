package org.donglai.logp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class FileNameProcessorTest {
	FileNameProcessor sp=new FileNameProcessor();
	@Test
	public void testGetlogfiles() {
		String dir="/files1";
		String real_path=FileNameProcessorTest.class.getResource(dir).getFile();
		List<String> list = sp.getlogfiles(real_path);
		assertEquals(list.size(), 4);
		assertTrue(list.get(0).toString().endsWith("logtest.2011-07-11.log"));
		assertTrue(list.get(2).toString().endsWith("logtest.2014-07-12.log"));
	}
	@Test
	public void testGetlogfiles_for_Empty() {
		String dir="./design";
		List<String> list = sp.getlogfiles(dir);
		assertTrue(list.isEmpty());
	}
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//TODO
	public void testPerformanceForMill() throws ParseException{
		List<Path> large=new java.util.ArrayList<Path>();
		String dt="1970-01-01";
//		Date date = dateFormat.parse("1970-01-01");
		//1000000
		for(int i=0;i<1000000;i++){
			Calendar calendar = Calendar.getInstance();
		      calendar.setTime(dateFormat.parse(dt));
		    calendar.add(5, 1);
		    dt=dateFormat.format(calendar.getTime());
			large.add(Paths.get("logtest."+dt+".log"));
			
		}
		long t1 = System.currentTimeMillis();
		Collections.sort(large, new TimeComparator());
		long t2 = System.currentTimeMillis();
		System.out.println("spend:"+(t2-t1)+"ms");
	}
	class TimeComparator implements Comparator<Path> {
		@Override
		public int compare(Path o1, Path o2) {
			String p1=o1.toString();
			String p2=o2.toString();
			String strDate1=o1.toString().substring(p1.length()-14, p1.length()-4);
			String strDate2=o1.toString().substring(p2.length()-14, p2.length()-4);
			try {
				Date dt1 = dateFormat.parse(strDate1);
				Date dt2= dateFormat.parse(strDate2);
				if(dt1.getTime()==dt2.getTime()){
					return 0;
				}
				return dt1.getTime()>dt2.getTime()?1:-1;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
	public static void main(String args[]) throws ParseException{
		FileNameProcessorTest test=new FileNameProcessorTest();
		test.testPerformanceForMill();
	}

}
