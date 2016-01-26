package org.donglai.logp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * ProcessorConext is used to initialize the context of the project. which
 * includes all content in /config.properties
 * 
 * @author zdonking
 * 
 */
public class ProcessorConext {
	private static final String CONTEXT_CONFIG_FILE_NAME = "/config.properties";
	private static final int MAX_THREADS_NUMBER = 48;
	private static final int MIN_THREADS_NUMBER = 2;

	static {
		contextInitialized();
	}
	/**
	 * the number of threads which are used for executing append row number to
	 * log files
	 */
	private static int THREAD_NUMBERS = MIN_THREADS_NUMBER;
	/**
	 * log file charset which is config in /config.properties
	 */
	private static String CHARSET = "utf-8";

	public static void contextInitialized() {
		Properties props = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = ProcessorConext.class
					.getResourceAsStream(CONTEXT_CONFIG_FILE_NAME);
			props.load(inputStream);
			String strNum = (String) props.get("Thread.number");
			String file_charset = (String) props.get("logfile.charset");
			if (file_charset != null && file_charset.length() > 0) {
				if (Charset.isSupported(file_charset)) {
					CHARSET = file_charset;
				}
			}
			int configNumbers = Integer.parseInt(strNum);
			if (configNumbers > MAX_THREADS_NUMBER
					|| configNumbers < MIN_THREADS_NUMBER) {
				THREAD_NUMBERS = MIN_THREADS_NUMBER;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static int getThreadNumbers() {
		return THREAD_NUMBERS;
	}

	public static String getCharset() {
		return CHARSET;
	}

}
