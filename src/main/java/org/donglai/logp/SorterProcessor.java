package org.donglai.logp;

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
 * Sorter processor: lists and sort all logfiles in the specific directory.
 * 
 * @author zdonking
 * 
 */
public class SorterProcessor {
	private static final Log LOG = LogFactory.getLog(SorterProcessor.class);
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public List<Path> getlogfiles(String dir) {
		Path logdir = Paths.get(dir);
		LinkedList<Path> logfiles = new LinkedList<>();
		if ((!Files.exists(logdir)) || (!Files.isDirectory(logdir))) {
			LOG.error("The directory of log files is not exist: " + dir);
			return logfiles;
		}
		try {
			Stream<Path> streams = Files.list(logdir);
			Iterator<Path> it = streams.iterator();
			while (it.hasNext()) {
				Path file = it.next();
				if (isLegelFile(file.toString())) {
					logfiles.add(file);
				}
			}
			Collections.sort(logfiles, new TimeComparator());
		} catch (IOException e) {
			e.printStackTrace();
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

	private  class TimeComparator implements Comparator<Path> {
		@Override
		public int compare(Path o1, Path o2) {
			String p1=o1.toString();
			String p2=o2.toString();
			String strDate1=o1.toString().substring(p1.length()-14, p1.length()-4);
			String strDate2=o1.toString().substring(p2.length()-14, p2.length()-4);
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
