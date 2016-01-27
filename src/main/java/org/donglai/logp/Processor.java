package org.donglai.logp;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Processor {
	private static final Log LOG = LogFactory.getLog(Processor.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProcessorContext.contextInitialized();
		if(args.length<1){
			LOG.error("The necessary arguement is lack, the directory of log files should input by arguement");
			System.exit(0);
		}
		
		SorterProcessor sp=new SorterProcessor();
		String dir=args[0];
		List<Path> logfiles = sp.getlogfiles(dir);
		LOG.info("load log file name finished");
		RowNumberCalculator rncal=new RowNumberCalculator();
		Map<Path, String> filesRows = rncal.calFileStartRowNumber(logfiles);
		LogRowNumberProcessor rp=new LogRowNumberProcessor();
		rp.executeAppendRowNumber(filesRows);
		
	}

}
