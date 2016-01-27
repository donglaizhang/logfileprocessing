package org.donglai.logp;

import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.donglai.logp.thread.ThreadManager;

public class LogRowNumberProcessor {
	private ThreadManager threadManager=new ThreadManager();
	public void executeAppendRowNumber(Map<Path,String> filesRows){
		ThreadPoolExecutor threadPool = threadManager.getThreadPool();
		Set<Entry<Path, String>> set = filesRows.entrySet();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
