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
import org.donglai.logp.utils.StringUtils;

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
	

//	public Map<Path, Long> calRowNumbers1(List<Path> logfiles) {
//		long[] rownumbers = new long[logfiles.size()];
//		Map<Path, Long> fileNum = new HashMap<>();
//		try {
//			synchronized (logfiles) {
//				for (int i = 0; i < logfiles.size(); i++) {
//					Path logfile = logfiles.get(i);
//					RowCounterTask counterTask = new RowCounterTask(logfile);
//					Future<Long> f = threadManager.getThreadPool().submit(
//							counterTask);
//					long rownum = f.get();
//					if (rownum > 0) {
//						rownumbers[i] = rownum;
//						fileNum.put(logfile, rownum);
//					}
//				}
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//		return fileNum;
//	}

	public long[] calRowNumbers(String dir,List<String> logfiles) {
		long[] rownumbers = new long[logfiles.size()];
		try {
			synchronized (logfiles) {
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
			}
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
		String start = "1";
		try {
			for (int i = 0; i < logfiles.size(); i++) {
				String path = logfiles.get(i);
				if (fileRows[i] > 0) {
					outer.append(dir+"/"+path).append("\t").append(start).append("\n");
					start = StringUtils.addLong(start, fileRows[i]);
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

//	public Map<Path, String> calFileStartRowNumber(List<Path> logfiles) {
//		LinkedHashMap<Path, String> res = new LinkedHashMap<Path, String>();
//		Map<Path, Long> fileRows = calRowNumbers1(logfiles);
//		String start = "1";
//		for (int i = 0; i < logfiles.size(); i++) {
//			Path path = logfiles.get(i);
//			if (fileRows.containsKey(path)) {
//				res.put(path, start);
//				Long rowNums = fileRows.get(path);
//				start = StringUtils.addLong(start, rowNums);
//			}
//		}
//		return res;
//	}

}
