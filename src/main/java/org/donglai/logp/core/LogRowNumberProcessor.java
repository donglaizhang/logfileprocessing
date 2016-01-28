package org.donglai.logp.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.thread.AppendRowNumberTask;
import org.donglai.logp.thread.ThreadManager;

/**
 * utilize multi-threads to insert row number to all the log files. If appending
 * successfully, record the log file name.
 * 
 * @author zdonking
 * 
 */
public class LogRowNumberProcessor {
	private static final Log LOG = LogFactory
			.getLog(LogRowNumberProcessor.class);

	protected LogRowNumberProcessor() {

	}

	private ThreadManager threadManager = new ThreadManager();

	// batch get the file names less than 100000, if one name is 100bytes,
	// memory is less than 10MB
	private LinkedList<String> batchGetFileRows(BufferedReader reader)
			throws IOException {
		LinkedList<String> res = new LinkedList<String>();
		String line;
		line = reader.readLine();
		while (line != null) {
			res.add(line);
			if (res.size() > 100000) {
				break;
			}
			line = reader.readLine();
		}
		return res;
	}

	public void executeAppendRowNumber(String dir) {
		OperationRecorder logRecord = OperationRecorder.getInstance();
		ThreadPoolExecutor threadPool = threadManager.getThreadPool();

		BufferedReader reader = logRecord.getRowStartReader();
		// BufferedWriter resultout = logRecord.getResultWriter();
		try {
			LinkedList<String> list = null;
			while (true) {
				list = batchGetFileRows(reader);
				if (list.isEmpty()) {
					break;
				}
				String line = null;
				while (!list.isEmpty()) {
					line = list.pop();
					String[] arr = line.split("\t");
					String fileName = arr[0];
					long startNumber = Long.parseLong(arr[1]);
					AppendRowNumberTask rtask = new AppendRowNumberTask(
							Paths.get(fileName), startNumber);
					Future<Boolean> f = threadPool.submit(rtask);
					Boolean res = f.get();
					if (res) {
						LOG.info("execute fileName success:" + fileName);
						// resultout.append(fileName).append("\tsuccess")
						// .append("\n");
						// resultout.flush();
					}
				}
			}
		} catch (Exception e) {
			LOG.error("IO/thread execute error:", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.error("close reader error, cause by", e);
			}
		}
		// synchronized (reader) {
		// String line;
		// try {
		// line = reader.readLine();
		// while (line != null) {
		// String[] arr = line.split("\t");
		// String fileName = arr[0];
		// long startNumber = Long.parseLong(arr[1]);
		// AppendRowNumberTask rtask = new AppendRowNumberTask(
		// Paths.get(fileName), startNumber);
		// Future<Boolean> f = threadPool.submit(rtask);
		// Boolean res = f.get();
		// if (res) {
		// resultout.append(fileName).append("\tsuccess")
		// .append("\n");
		// resultout.flush();
		// }
		// line = reader.readLine();
		// }
		// } catch (Exception e) {
		// LOG.error("IO/thread execute error:", e);
		// } finally {
		// try {
		// reader.close();
		// } catch (IOException e) {
		// LOG.error("close reader error, cause by", e);
		// }
		// }
		// }
	}

}
