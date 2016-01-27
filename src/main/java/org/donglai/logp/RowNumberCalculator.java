package org.donglai.logp;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.donglai.logp.thread.RowCounterTask;
import org.donglai.logp.thread.ThreadManager;
import org.donglai.logp.utils.StringUtils;

public class RowNumberCalculator {
	private ThreadManager threadManager=new ThreadManager();

	public Map<Path, Long> calRowNumbers(List<Path> logfiles) {
		Map<Path, Long> fileNum = new HashMap<>();
		try {
			for (int i = 0; i < logfiles.size(); i++) {
				synchronized (logfiles) {
					Path logfile = logfiles.get(i);
					RowCounterTask counterTask = new RowCounterTask(logfile);
					Future<Long> f = threadManager.getThreadPool().submit(counterTask);
					long rownum;
					rownum = f.get();
					if (rownum > 0) {
						fileNum.put(logfile, rownum);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return fileNum;
	}

	public Map<Path,String> calFileStartRowNumber(List<Path> logfiles){
		LinkedHashMap<Path,String> res=new LinkedHashMap<Path,String>();
		Map<Path, Long> fileRows = calRowNumbers(logfiles);
		String start="1";
		for (int i = 0; i < logfiles.size(); i++) {
			Path path=logfiles.get(i);
			if(fileRows.containsKey(path)){
				res.put(path, start);
				Long rowNums=fileRows.get(path);
				start=StringUtils.addLong(start, rowNums);
			}
		}
		return res;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// executor.submit(new BlogDomainSyncTask(sohupassport, sname));
		// ExecutorService executor =
		// Executors.newSingleThreadScheduledExecutor();
		// new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
		// milliseconds,runnableTaskQueue, handler);

		ThreadPoolExecutor pool = null;
		System.out.println(Long.MAX_VALUE);
		// pool.execute(command);
	}

}
