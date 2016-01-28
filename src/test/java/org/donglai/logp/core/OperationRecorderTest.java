package org.donglai.logp.core;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OperationRecorderTest {
	OperationRecorder or=new OperationRecorder();
	@Before
	public void setUp() throws IOException{
		OperationRecorder.init();
		BufferedWriter w1 = or.getFileListWriter();
		w1.write("logtest.2014-07-11.log\n");
		w1.write("logtest.2014-07-12.log\n");
		w1.write("logtest.2014-07-13.log\n");
		w1.write("logtest.2014-07-14.log\n");
		w1.flush();
		w1.close();
	}
	@Test
	public void testLoadlogList() {
		List<String> list = or.loadlogList();
		assertEquals(list.size(), 4);
		assertEquals(list.get(0),"logtest.2014-07-11.log");
		//logtest.2014-07-11.log> but was: java.lang.String<logtest.2014-07-11.log>
		//logtest.2014-07-11.log	
	}

	

}
