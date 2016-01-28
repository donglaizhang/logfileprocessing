package org.donglai.logp.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testIsPositiveInt() {
		assertTrue(StringUtils.isPositiveInt("1234"));
		assertFalse(StringUtils.isPositiveInt("12.34"));
		assertFalse(StringUtils.isPositiveInt("-1234"));
	}

	@Test
	public void testAddLong() {
		String r = StringUtils.addLong("99999", 1);
		assertEquals(r, "100000");
	}
//	@Test
	//for performance test
	public void testAddLongPerformance() {
		String big= Long.MAX_VALUE+"";
		long t1 = System.currentTimeMillis();
		for (long i = 0; i < Long.MAX_VALUE; i++) {
			big = StringUtils.addLong(big, i);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("spend"+(t2-t1)+"ms");
	}
	/**
	 * 
	 */
	
}
