package org.donglai.logp.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.donglai.logp.core.ProcessorContext;

public class ThreadManager {
	ThreadPoolExecutor pool = null;
	public ThreadPoolExecutor getThreadPool(){
		if(pool==null){
			pool=new ThreadPoolExecutor(
					ProcessorContext.MIN_THREADS_NUMBER,
					ProcessorContext.getThreadNumbers(), 10000, TimeUnit.MINUTES,
					new LinkedBlockingQueue<Runnable>(),new DefaultThreadFactory() );
		}
		return pool;
	}
	static class DefaultThreadFactory implements ThreadFactory {

//        static final AtomicInteger poolNumber = new AtomicInteger(1);

        final ThreadGroup group;

        final String namePrefix;

        final AtomicInteger threadNumber = new AtomicInteger(1);

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "ThreadManager-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
