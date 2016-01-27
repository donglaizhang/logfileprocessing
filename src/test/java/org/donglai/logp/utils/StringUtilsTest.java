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

}
