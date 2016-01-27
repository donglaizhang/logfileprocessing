package org.donglai.logp.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.donglai.logp.ProcessorConext;

public class ThreadManager {
	ThreadPoolExecutor pool = new ThreadPoolExecutor(
			ProcessorConext.MIN_THREADS_NUMBER,
			ProcessorConext.getThreadNumbers(), 10000, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());
	public ThreadPoolExecutor getThreadPool(){
		return pool;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
