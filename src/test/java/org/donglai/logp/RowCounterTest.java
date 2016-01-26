package org.donglai.logp;

import static org.junit.Assert.*;

import org.junit.Test;

public class RowCounterTest {
	RowCounter counter=new RowCounter();
	@Test
	public void testCountFileRowNumbers() {
		String filename="/testlogfornumber.log";
		String file_path=RowCounterTest.class.getResource(filename).getFile();
		long rown=counter.countFileRowNumbers(file_path);
		assertEquals(rown, 13);
	}
	@Test
	public void testCountFileRowNumbers_for_noexist_file() {
		String filename="noexist.log";
		long rown=counter.countFileRowNumbers(filename);
		assertEquals(rown, 0);
	}
}
