package org.donglai.logp;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class ProcessorUTest {

	String uxpath = ProcessorUTest.class.getResource("/ut")
			.getFile();
	@Before
	public void setUp() throws IOException{
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
	Processor pro=new Processor();
	@Test
	public void testFuntion() throws IOException {
		pro.exeucte(uxpath);
	}

}
