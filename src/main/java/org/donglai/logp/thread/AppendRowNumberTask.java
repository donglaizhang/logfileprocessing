package org.donglai.logp.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.utils.StringUtils;

public class AppendRowNumberTask implements Callable<Boolean> {
	private static final Log LOG = LogFactory.getLog(AppendRowNumberTask.class);
	static final int FLUSH_SIZE=100;
	/**
	 * append row number to log files
	 * the process description: 
	 * 1.Renaming the original log file to the backup file, the rule is logtest.2011-07-11.log ->logtest.2011-07-11.log_
	 * 2.reading the backup log file, while append the row number in the front to the original log file.
	 * 3.delete  the backup log file
	 * @return
	 */
	public boolean appendRowNumber() {
		Path bkfile = Paths.get(logfile.toString() + "_");
		BufferedReader br = null;
		BufferedWriter writer = null;
		try {
			if (!Files.exists(bkfile)) {
				Files.move(logfile, bkfile);
			}
			if(Files.exists(logfile)){
				Files.delete(logfile);
			}
			br = Files.newBufferedReader(bkfile);
			Files.createFile(logfile);
			writer = Files.newBufferedWriter(logfile);
			long row=1;
			String line = br.readLine();
			while (line != null) {
				writer.append(start + line);
				start=StringUtils.addLong(start, 1l);
				writer.newLine();
				row++;
				line = br.readLine();
				if(row%FLUSH_SIZE==0){
					writer.flush();
				}
			}
			writer.flush();
		} catch (IOException e) {
			LOG.error("write row number for log file:"+logfile.getFileName()+" failed, caused by",e);
			return false;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				LOG.error("close reader or writer failed: "+logfile.getFileName()+" , caused by",e);
				return false;
			}
		}
		LOG.info("write row number for log file:"+bkfile.getFileName()+" successful");
		try {
			Files.delete(bkfile);
		} catch (IOException e) {
			LOG.error("rename log file :"+bkfile.getFileName()+" failed, caused by",e);
			return false;
		}
		return true;
	}
	private Path logfile;
	private String start;
	public AppendRowNumberTask(Path path, String start){
		this.logfile=path;
		this.start=start;
	}
	@Override
	public Boolean call() throws Exception {
		return appendRowNumber();
	}

}
