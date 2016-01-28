package org.donglai.logp.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * #FileNameProcessor: lists and sort file names in the specific directory.
 * The memory evaluation:
 * 	single file name :logtest.2011-07-11.log, len=22 and charset is UTF-8,
 *  22*2=44 bytes
 *  1,000,000 files: 44* 1,000,000 =42MB
 * @author zdonking
 * 
 */
public class FileNameProcessor {
	private static final Log LOG = LogFactory.getLog(FileNameProcessor.class);
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected FileNameProcessor(){
		
	}
	public List<String> getlogfiles(String dir) {
		OperationRecorder recorder =OperationRecorder.getInstance();
		Path logdir = Paths.get(dir);
		LinkedList<String> logfiles = new LinkedList<>();
		if ((!Files.exists(logdir)) || (!Files.isDirectory(logdir))) {
			LOG.error("The directory of log files is not exist: " + dir);
			return logfiles;
		}
		BufferedWriter writer = recorder.getFileListWriter();
		try {
			Stream<Path> streams = Files.list(logdir);
			Iterator<Path> it = streams.iterator();
			while (it.hasNext()) {
				Path file = it.next();
				if (isLegelFile(file.toString())) {
					logfiles.add(file.getFileName().toString());
				}
			}
			Collections.sort(logfiles, new TimeComparator());
			for (int i = 0; i < logfiles.size(); i++) {
				writer.append(logfiles.get(i).toString()).append("\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(writer!=null){
					writer.close();
				}
			}catch(IOException e){
				LOG.error("close log writer failed");
			}
		}
		Collections.sort(logfiles);
		return logfiles;
	}

	private boolean isLegelFile(String fileName) {
		if(fileName.length()<15){
			return false;
		}
		if(!fileName.endsWith(".log")){
			return false;
		}
		String strDate=fileName.substring(fileName.length()-14, fileName.length()-4);
		try {
			Date dt = dateFormat.parse(strDate);
			return dateFormat.format(dt).equals(strDate);
		} catch (ParseException e) {
			LOG.debug("file:"+fileName+" is not a log file  ");
			return false;
		}
	}


	class TimeComparator implements Comparator<String> {
		@Override
		public int compare(String p1, String p2) {
//			String p1=o1.toString();
//			String p2=o2.toString();
			String strDate1=p1.substring(p1.length()-14, p1.length()-4);
			String strDate2=p2.substring(p2.length()-14, p2.length()-4);
			try {
				Date dt1 = dateFormat.parse(strDate1);
				Date dt2= dateFormat.parse(strDate2);
				if(dt1.getTime()==dt2.getTime()){
					return 0;
				}
				return dt1.getTime()>dt2.getTime()?1:-1;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
}
