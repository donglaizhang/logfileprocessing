package org.donglai.logp.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessorContextTest {
	
	@Test
	public void testGetThreadNumbers() {
		ProcessorContext.contextInitialized();
		assertEquals(ProcessorContext.getThreadNumbers(), 100);
	}

}
