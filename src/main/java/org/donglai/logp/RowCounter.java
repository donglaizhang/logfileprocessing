package org.donglai.logp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
/**
 * Row Counter is used to calculate the total number of a file. 
 * If the logfile doesn't exist, it will return 0, and record log.
 * @author zdonking
 */
public class RowCounter {
	public long countFileRowNumbers(String logfile){
		long numberOfLines;
		Charset charset = Charset.forName(ProcessorConext.getCharset());
	    try (Stream<String> s = Files.lines(Paths.get(logfile),
	    		charset)) {
	        numberOfLines = s.count();
	        return numberOfLines;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return 0;
	}
	
}
