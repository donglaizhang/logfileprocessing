package org.donglai.logp.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.donglai.logp.thread.AppendRowNumberTaskTest;
import org.junit.Before;
import org.junit.Test;

public class LogRowNumberProcessorTest {
	LogRowNumberProcessor lp=null;
	String dir=AppendRowNumberTaskTest.class.getResource("/files2").getFile();
	@Before
	public void setup() throws IOException{
		OperationRecorder.init(dir);
		lp=new LogRowNumberProcessor();
		RowNumberCalculator rncal=ProcessorFactory.getRowNumberCalculator();
		String[] fileNames=new String[]{
		"logtest.2011-07-11.log",
		"logtest.2014-07-11.log",
		"logtest.2014-07-12.log",
		"logtest.2014-09-11.log"
		};
		String oripath=AppendRowNumberTaskTest.class.getResource("/files1").getFile();
		for (int i = 0; i < fileNames.length; i++) {
			Files.deleteIfExists(Paths.get(dir+"/"+fileNames[i]));
			Files.copy(Paths.get(oripath+"/"+fileNames[i]), Paths.get(dir+"/"+fileNames[i]));
		}
		rncal.calStoreStartRow(dir, Arrays.asList(fileNames));
		
	}
	@Test
	public void testExecuteAppendRowNumber() {
		lp.executeAppendRowNumber(dir);
		
	}

}
