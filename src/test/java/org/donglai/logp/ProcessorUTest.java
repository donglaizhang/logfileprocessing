package org.donglai.logp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.donglai.logp.core.OperationRecorder;
import org.junit.Before;
import org.junit.Test;

public class ProcessorUTest {

	String uxpath = ProcessorUTest.class.getResource("/ut")
			.getFile();
	@Before
	public void setUp() throws IOException{
		OperationRecorder.init(uxpath);
		
		//init the test data.
		String bk_path = ProcessorUTest.class.getResource("/files1")
				.getFile();
		copy(Paths.get(bk_path), Paths.get(uxpath));
		
	}
	/**
	 * copy test log files to  /ut
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	private void copy(Path source, Path target) throws IOException{
		Files.walkFileTree(target, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				Files.delete((Path)file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir,
					IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

		});
	
		Stream<Path> list = Files.list(source);
		Iterator<Path> it = list.iterator();
		while (it.hasNext()) {
			Path file = it.next();
			Files.copy(file, Paths.get(target.toString()+"/"+file.getFileName()));
		}
	}
	/*
	"logtest.2011-07-11.log", //5 lines
	"logtest.2014-07-11.log", //7 lines
	"logtest.2014-07-12.log",  //empty
	"logtest.2014-09-11.log"//4 lines
	*/
	@Test
	public void testFuntion() throws IOException {
		OperationRecorder.init(uxpath);
		Processor pro=new Processor();
		pro.exeucte(uxpath);
//		List<String> total = Files.readAllLines(Paths.get(uxpath+"/log_result_list.log"));
//		assertEquals(total.size(), 3);
//		assertTrue(total.contains(uxpath+"/logtest.2014-07-11.log\tsuccess"));
//		assertTrue(!total.contains(uxpath+"/logtest.2014-07-12.log\tsuccess"));
		List<String> lines = Files.readAllLines(Paths.get(uxpath+"/logtest.2011-07-11.log"));
		assertEquals(lines.size(), 5);
		assertTrue(lines.get(0).startsWith("1"));
		assertTrue(lines.get(1).startsWith("2"));
		assertTrue(lines.get(2).startsWith("3"));
		assertTrue(lines.get(3).startsWith("4"));
		assertTrue(lines.get(4).startsWith("5"));
		
		List<String> lines2 = Files.readAllLines(Paths.get(uxpath+"/logtest.2014-07-11.log"));
		assertEquals(lines2.size(), 7);
		assertTrue(lines2.get(0).startsWith("6"));
		assertTrue(lines2.get(6).startsWith("12"));
		
		List<String> lines3 = Files.readAllLines(Paths.get(uxpath+"/logtest.2014-09-11.log"));
		assertEquals(lines3.size(), 4);
		assertTrue(lines3.get(0).startsWith("13"));
		assertTrue(lines3.get(3).startsWith("16"));
	}

}
