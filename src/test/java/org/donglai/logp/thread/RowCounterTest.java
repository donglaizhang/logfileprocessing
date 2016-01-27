package org.donglai.logp.thread;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.donglai.logp.thread.RowCounterTask;
import org.junit.Test;

public class RowCounterTest {
	@Test
	public void testCountFileRowNumbers() {
		String filename="/testlogfornumber.log";
		String file_path=RowCounterTest.class.getResource(filename).getFile();
		RowCounterTask counter=new RowCounterTask(Paths.get(file_path));
		long rown=counter.countFileRowNumbers();
		assertEquals(rown, 13);
	}
	@Test
	public void testCountFileRowNumbers_for_noexist_file() {
		String filename="noexist.log";
		RowCounterTask counter=new RowCounterTask(Paths.get(filename));
		long rown=counter.countFileRowNumbers();
		assertEquals(rown, -1);
	}
}
