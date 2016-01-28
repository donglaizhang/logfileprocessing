package org.donglai.logp.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.thread.RowCounterTask;
import org.donglai.logp.thread.ThreadManager;

/**
 * Rownumber in the memory evaluation: Total number of files: 1,000,000 Row
 * number:long 8bytes Use ArrayList to store total: 8mb
 * 
 * @author zdonking
 * 
 */
public class RowNumberCalculator {
	private static final Log LOG = LogFactory.getLog(RowNumberCalculator.class);
	RowNumberCalculator() {

	}

	private ThreadManager threadManager = new ThreadManager();
	

	public long[] calRowNumbers(String dir,List<String> logfiles) {
		long[] rownumbers = new long[logfiles.size()];
		try {
//			synchronized (logfiles) {
				for (int i = 0; i < logfiles.size(); i++) {
					String logfile = logfiles.get(i);
					RowCounterTask counterTask = new RowCounterTask(Paths.get(dir+"/"+logfile));
					Future<Long> f = threadManager.getThreadPool().submit(
							counterTask);
					long rownum = f.get();
					if (rownum > 0) {
						rownumbers[i] = rownum;
					}
				}
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return rownumbers;
	}
	
	public void calStoreStartRow(String dir, List<String> logfiles) {
		OperationRecorder recorder =OperationRecorder.getInstance();
		BufferedWriter outer = recorder.getRowStartWriter();
		long[] fileRows = calRowNumbers(dir,logfiles);
		long start=1l;
//		String start = "1";
		try {
			for (int i = 0; i < logfiles.size(); i++) {
				String path = logfiles.get(i);
				if (fileRows[i] > 0) {
					outer.append(dir+"/"+path).append("\t").append(start+"").append("\n");
					start+=fileRows[i];// = StringUtils.addLong(start, fileRows[i]);
					if(i%1000==0){
						outer.flush();
					}
				}
			}
			outer.flush();
		} catch (IOException e) {
			LOG.error("store the start number of the log file failed, caused by",e);
		}finally{
			try {
				outer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
