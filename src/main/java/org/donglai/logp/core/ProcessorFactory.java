package org.donglai.logp.core;

public class ProcessorFactory {
	private static FileNameProcessor fileNameProcessor = null;
	private static LogRowNumberProcessor rowNumberProcessor = null;
	private static RowNumberCalculator rowNumberCalculator = null;

	public static FileNameProcessor getFileNameProcessor() {
		if (fileNameProcessor == null) {
			fileNameProcessor = new FileNameProcessor();
		}
		return fileNameProcessor;
	}

	public static LogRowNumberProcessor getLogRowNumberProcessor() {
		if (rowNumberProcessor == null) {
			rowNumberProcessor = new LogRowNumberProcessor();
		}
		return rowNumberProcessor;
	}

	public static RowNumberCalculator getRowNumberCalculator() {
		if (rowNumberCalculator == null) {
			rowNumberCalculator = new RowNumberCalculator();
		}
		return rowNumberCalculator;
	}
//	private static OperationRecorder logRecord=null;
//	public static OperationRecorder getOperationRecorder(String dir) {
//		if (logRecord == null) {
//			logRecord = new OperationRecorder(dir);
//		}
//		return logRecord;
//	}
//	

}
