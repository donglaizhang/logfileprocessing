package org.donglai.logp.thread;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AppendRowNumberTaskTest {
	AppendRowNumberTask appendtask=null;
	String dir="/files2/";
	String dir_path=AppendRowNumberTaskTest.class.getResource(dir).getFile();
	Path logfile=null;
	@Before
	public void setup() throws IOException{
		logfile=Paths.get(dir_path+"/logtest.2011-07-11.log");
		
		String orifile="/files1/logtest.2011-07-11.log";
		String oripath=AppendRowNumberTaskTest.class.getResource(orifile).getFile();
		if(Files.exists(logfile)){
			Files.delete(logfile);
		}
		Files.copy(Paths.get(oripath), logfile);
		
	}
	@Test
	public void testAppendRowNumber() throws IOException {
		appendtask=new AppendRowNumberTask(logfile,"100");
		boolean r = appendtask.appendRowNumber();
		assertTrue(r);
		assertTrue(Files.exists(logfile));
		assertTrue(!Files.exists(Paths.get(dir_path+"/logtest.2011-07-11.log_")));
		List<String> lines = Files.readAllLines(logfile);
		assertTrue(lines.get(0).startsWith("100"));
		assertTrue(lines.get(1).startsWith("101"));
		assertEquals(lines.size(),5);
	}

}
