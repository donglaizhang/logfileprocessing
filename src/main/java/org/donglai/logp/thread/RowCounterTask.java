package org.donglai.logp.thread;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.core.ProcessorContext;

/**
 * Row Counter is used to calculate the total number of a file. If the logfile
 * doesn't exist, it will return 0, and record log.
 * 
 * @author zdonking
 */
public class RowCounterTask implements Callable<Long> {
	private static final Log LOG = LogFactory.getLog(RowCounterTask.class);
	public long countFileRowNumbers() {
		long numberOfLines;
		
		try (Stream<String> s = Files.lines(logfile, ProcessorContext.getCharset())) {
			numberOfLines = s.count();
			return numberOfLines;
		} catch (IOException e) {
			LOG.error("file is not exist or can't read:  "+logfile);
		}
		return -1;
	}

	private Path logfile;

	public RowCounterTask(Path logfile) {
		this.logfile = logfile;
	}
	@Override
	public Long call() throws Exception {
		long rowNumber=countFileRowNumbers();		
		return rowNumber;
	}

}
