package org.donglai.logp;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

public class SorterProcessorTest {
	SorterProcessor sp=new SorterProcessor();
	@Test
	public void testGetlogfiles() {
		String dir="/files1";
		String real_path=SorterProcessorTest.class.getResource(dir).getFile();
		List<Path> list = sp.getlogfiles(real_path);
		assertEquals(list.size(), 4);
		assertEquals(list.get(0).toString(), "logtest.2011-07-11.log");
		assertEquals(list.get(2).toString(), "logtest.2014-07-12.log");
	}
	@Test
	public void testGetlogfiles_for_Empty() {
		String dir="./design";
		List<Path> list = sp.getlogfiles(dir);
		assertTrue(list.isEmpty());
	}

}
