package org.donglai.logp.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.thread.AppendRowNumberTask;
import org.donglai.logp.thread.ThreadManager;
/**
 * utilize multi-threads to insert row number to all the log files.
 * If appending successfully, record the log file name. 
 * @author zdonking
 *
 */
public class LogRowNumberProcessor {
	private static final Log LOG = LogFactory.getLog(LogRowNumberProcessor.class);
	
	protected LogRowNumberProcessor(){
		
	}
	private ThreadManager threadManager=new ThreadManager();
	public void executeAppendRowNumber(String dir){
		OperationRecorder logRecord =OperationRecorder.getInstance();
		ThreadPoolExecutor threadPool = threadManager.getThreadPool();
		BufferedReader reader = logRecord.getRowStartReader();
		BufferedWriter resultout = logRecord.getResultWriter();
		synchronized (reader) {
			String line;
			try {
				line = reader.readLine();
				while(line!=null){
					String[] arr=line.split("\t");
					String fileName=arr[0];
					long startNumber=Long.parseLong(arr[1]);
					AppendRowNumberTask rtask=new AppendRowNumberTask(Paths.get(fileName),startNumber); 
					Future<Boolean> f=threadPool.submit(rtask);
					Boolean res = f.get();
					if(res){
						resultout.append(fileName).append("\tsuccess").append("\n");
						resultout.flush();
					}
					line=reader.readLine();
				}
			} catch (Exception e) {
				LOG.error("IO/thread execute error:",e);
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					LOG.error("close reader error, cause by",e);
				}
			}
		}
	}
	

}
