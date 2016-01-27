package org.donglai.logp;

import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.thread.AppendRowNumberTask;
import org.donglai.logp.thread.ThreadManager;
/**
 * utilize mutil-threads to insert row number to all the log files.
 * If appending successfully, record the log file name. 
 * @author zdonking
 *
 */
public class LogRowNumberProcessor {
	private static final Log LOG = LogFactory.getLog(LogRowNumberProcessor.class);
	private ThreadManager threadManager=new ThreadManager();
	public void executeAppendRowNumber(Map<Path,String> filesRows){
		ThreadPoolExecutor threadPool = threadManager.getThreadPool();
		Set<Entry<Path, String>> set = filesRows.entrySet();
		synchronized (set) {
			for(Entry<Path,String> ent:set){
				AppendRowNumberTask rtask=new AppendRowNumberTask(ent.getKey(),ent.getValue()); 
				Future<Boolean> f=threadPool.submit(rtask);
				try {
					System.out.println(f.get());
				} catch (InterruptedException e) {
					System.out.println(e);
					LOG.error("execute failed cause by",e);
				} catch (ExecutionException e) {
					System.out.println(e);
					LOG.error("execute failed cause by",e);
				}
			}
		}
	}
	

}
