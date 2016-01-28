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
	private static OperationRecorder or=null;
	public static void init(String dir){
		if(or==null){
			or=new OperationRecorder(dir);
			or.init();
		}
	}
	public static OperationRecorder getInstance(){
		return or;
	}
	private String dir=null;
	private OperationRecorder(String dir2) {
		this.dir=dir2;
		init();
	}
	public void init() {
		try {
			if (!Files.exists(getFullPath(LOG_FILE_LIST))) {
				Files.createFile(getFullPath(LOG_FILE_LIST));
			}
			if (!Files.exists(getFullPath(LOG_PROCESS_RESULT))) {
				Files.createFile(getFullPath(LOG_PROCESS_RESULT));
			}
			if (!Files.exists(getFullPath(LOG_FILE_ROWS))) {
				Files.createFile(getFullPath(LOG_FILE_ROWS));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private Path getFullPath(String fileName){
		return Paths.get(this.dir+"/"+fileName);
	}
	public List<String> loadlogList() {
		List<String> rs = new ArrayList<>();
		if (!Files.exists(getFullPath(LOG_FILE_LIST))) {
			return null;
		}
		BufferedReader br =null;
		try {
			br=Files.newBufferedReader(getFullPath(LOG_FILE_LIST));
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
			out = Files.newBufferedWriter(getFullPath(LOG_FILE_ROWS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	public BufferedReader getRowStartReader() {
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(getFullPath(LOG_FILE_ROWS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	public BufferedWriter getFileListWriter() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(getFullPath(LOG_FILE_LIST));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public BufferedWriter getResultWriter() {
		BufferedWriter out = null;
		try {
			out = Files.newBufferedWriter(getFullPath(LOG_PROCESS_RESULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	/**
	 * delete the recorder files.
	 */
	public  void clear() {
		try {
			Files.deleteIfExists(getFullPath(LOG_FILE_LIST));
			Files.deleteIfExists(getFullPath(LOG_PROCESS_RESULT));
			Files.deleteIfExists(getFullPath(LOG_FILE_ROWS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
