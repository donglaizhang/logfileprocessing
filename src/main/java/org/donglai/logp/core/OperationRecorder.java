package org.donglai.logp.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OperationRecorder {
	private static final String LOG_FILE_LIST = "log_files_list.log";
	private static final String LOG_FILE_ROWS = "log_files_rows.log";
	private static final String LOG_PROCESS_RESULT = "log_result_list.log";

	static void init() {
		try {
			if (!Files.exists(Paths.get(LOG_FILE_LIST))) {
				Files.createFile(Paths.get(LOG_FILE_LIST));
			}
			if (!Files.exists(Paths.get(LOG_PROCESS_RESULT))) {
				Files.createFile(Paths.get(LOG_PROCESS_RESULT));
			}
			if (!Files.exists(Paths.get(LOG_FILE_ROWS))) {
				Files.createFile(Paths.get(LOG_FILE_ROWS));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> loadlogList() {
		List<String> rs = new ArrayList<>();
		if (!Files.exists(Paths.get(LOG_FILE_LIST))) {
			return null;
		}
		BufferedReader br =null;
		try {
			br=Files.newBufferedReader(Paths.get(LOG_FILE_LIST));
			String line=br.readLine();
			while(line!=null){
				rs.add(line);
				line=br.readLine();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			try{
				if(br!=null){
					br.close();
				}
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		return rs;
	}
	public BufferedWriter getRowStartWriter() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(LOG_FILE_ROWS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	public BufferedReader getRowStartReader() {
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(Paths.get(LOG_FILE_ROWS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	public BufferedWriter getFileListWriter() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(LOG_FILE_LIST));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	// public void appendFileName(String fileName, String startNumber){
	// try {
	// BufferedWriter out = Files.newBufferedWriter(Paths.get(LOG_FILE_LIST));
	// out.append(fileName).append("\t").append(startNumber).append("\n");
	// out.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public BufferedWriter getResultWriter() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(Paths.get(LOG_PROCESS_RESULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

}
