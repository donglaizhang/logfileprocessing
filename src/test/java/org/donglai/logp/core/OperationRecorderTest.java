package org.donglai.logp.core;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OperationRecorderTest {
	String dir=FileNameProcessorTest.class.getResource("/files3").getFile();
	OperationRecorder or =null;
	@Before
	public void setUp() throws IOException{
		OperationRecorder.init(dir);
		or= OperationRecorder.getInstance();
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
	}
//	@Test
//	public void testDirSetting(){
//		OperationRecorder.init("/opt");
//		System.out.println(OperationRecorder.getInstance().getDir());
//		OperationRecorder.init("/usr");
//		System.out.println(OperationRecorder.getInstance().getDir());
//		OperationRecorder.init("/Application");
//		System.out.println(OperationRecorder.getInstance().getDir());
//	}

}
