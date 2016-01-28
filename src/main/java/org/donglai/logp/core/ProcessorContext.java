package org.donglai.logp.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.donglai.logp.utils.StringUtils;

/**
 * ProcessorConext is used to initialize the context of the project. which
 * includes all content in /config.properties
 * 
 * @author zdonking
 * 
 */
public class ProcessorContext {
	private static final String CONTEXT_CONFIG_FILE_NAME = "/config.properties";
	public static final int MAX_THREADS_NUMBER = 200;
	public static final int MIN_THREADS_NUMBER = 2;
	static int FLUSH_SIZE=500;
	private static boolean INITED = false;
	static {
		contextInitialized();
		INITED = true;
	}
	/**
	 * the number of threads which are used for executing append row number to
	 * log files
	 */
	private static int THREAD_NUMBERS ;
	/**
	 * log file charset which is config in /config.properties
	 */
	private static String CHARSET = "UTF-8";
	
	public static void contextInitialized() {
		if (INITED) {
			return;
		}
		// init log4j config
		java.net.URL resource = ProcessorContext.class
				.getResource("log4j.properties");
		if (resource != null) {
			PropertyConfigurator.configure(resource);
		}
		//init execution log
		
		Properties props = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = ProcessorContext.class
					.getResourceAsStream(CONTEXT_CONFIG_FILE_NAME);
			props.load(inputStream);
			String file_charset = (String) props.get("logfile.charset");
			if (file_charset != null && file_charset.length() > 0) {
				if (Charset.isSupported(file_charset)) {
					CHARSET = file_charset;
				}
			}
			Log LOG = LogFactory.getLog(ProcessorContext.class);
			String strNum = (String) props.get("thread.number");
			if (!StringUtils.isPositiveInt(strNum)) {
				return;
			}
			int configNumbers = Integer.parseInt(strNum);
			if (configNumbers <= MAX_THREADS_NUMBER
					&& configNumbers >= MIN_THREADS_NUMBER) {
				THREAD_NUMBERS = configNumbers;
			}else{
				THREAD_NUMBERS =MIN_THREADS_NUMBER;
			}
			LOG.info("thread numbers:"+THREAD_NUMBERS);
			//the whole FLUSH SIZE should less 100m, 
			//assuming the size of line is 500bytes. and just use 60mb for processing 
			FLUSH_SIZE=60*1024*1024/(THREAD_NUMBERS*500);
//			THREAD_NUMBERS*150*FLUSH_SIZE/1024<80
			INITED=true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static int getflush_size(){
		return FLUSH_SIZE;
	}
	public static int getThreadNumbers() {
		return THREAD_NUMBERS;
	}

	public static Charset getCharset() {
		return Charset.forName(CHARSET);
	}

}
