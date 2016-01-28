package org.donglai.logp;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.donglai.logp.core.LogRowNumberProcessor;
import org.donglai.logp.core.ProcessorContext;
import org.donglai.logp.core.ProcessorFactory;
import org.donglai.logp.core.RowNumberCalculator;
import org.donglai.logp.core.FileNameProcessor;

public class Processor {
	static{
		System.out.println("asdf");
		ProcessorContext.contextInitialized();
	}
	private static final Log LOG = LogFactory.getLog(Processor.class);

	FileNameProcessor sp=ProcessorFactory.getFileNameProcessor();
	RowNumberCalculator rncal=ProcessorFactory.getRowNumberCalculator();
	LogRowNumberProcessor rp=ProcessorFactory.getLogRowNumberProcessor();
	
	public void exeucte(String dir){
		List<String> logfiles = sp.getlogfiles(dir);
		LOG.info("load log file names finished");
		rncal.calStartRow(dir,logfiles);
		rp.executeAppendRowNumber(null);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<1){
			LOG.error("The necessary arguement is lack, the directory of log files should input by arguement.");
			LOG.error("The directory should follow unix style, like: /local/usr/logs/");
			System.exit(0);
		}
		ProcessorContext.contextInitialized();
		String dir=args[0];
		LOG.info("The directory:"+dir+" will be operated");
		Processor processor=new Processor();
		processor.exeucte(dir);
	}

}
