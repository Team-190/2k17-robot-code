package org.usfirst.frc.team190.frc2k17;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Logger {
	
	public static Logger defaultLogger = new Logger(new StdOutputter());
	public static Logger kangarooVoice = new Logger(new NetworkTablesOutputter(RobotMap.NetworkTable.Kangaroo.TABLE_NAME, RobotMap.NetworkTable.Kangaroo.VOICE_LOG));
	private static long beginningOfTime = 0;
	private ArrayList<Outputter> outputters; 
	
	public static enum Level {
		TRACE,
		DEBUG,
		INFO,
		WARN,
		ERROR,
		SEVERE,
		CRITICAL;
		
		public String getKangarooVoice() {
			switch(this) {
			case TRACE:
				return "trace";
			case DEBUG:
				return "debug";
			case INFO:
				return "";
			case WARN:
				return "warning";
			case ERROR:
				return "error";
			case SEVERE:
				return "severe error";
			case CRITICAL:
				return "holy shit";
			default:
				return "";
			}
		}
	}
	
	public static void resetTimestamp() {
		beginningOfTime = System.currentTimeMillis();
		defaultLogger.info("Resetting log timestamps to 0 from this point.");
	}
	
	public Logger(Outputter output) {
		outputters = new ArrayList<Outputter>();
		addOutput(output);
	}
	
	private void addOutput(Outputter output) {
		outputters.add(output);
	}
	
	public void trace(String msg) {
		output(new Message(Level.TRACE, msg));
	}
	
	public void debug(String msg) {
		output(new Message(Level.DEBUG, msg));
	}
	
	public void info(String msg) {
		output(new Message(Level.INFO, msg));
	}
	
	public void warn(String msg) {
		output(new Message(Level.WARN, msg));
	}
	
	public void error(String msg) {
		output(new Message(Level.ERROR, msg));
	}
	
	public void severe(String msg) {
		output(new Message(Level.SEVERE, msg));
	}

	public void critical(String msg) {
		output(new Message(Level.CRITICAL, msg));
	}
	
	private void output(Message msg) {
		for(Outputter out : outputters) {
			out.output(msg);
		}
	}
	
	private static class Message {
		
		private Level level;
		private String msg;
		
		public Message(Level level, String msg) {
			this.level = level;
			this.msg = msg;
		}
		
		public Level getLevel() {
			return level;
		}
		
		public String getMessage() {
			return msg;
		}
		
	}
	
	private static interface Outputter {
		public void output(Message msg);
	}
	
	private static class StdOutputter implements Outputter {

		@Override
		public void output(Message msg){
			System.out.println((System.currentTimeMillis() - beginningOfTime) + " [" + msg.getLevel() + "] " + msg.getMessage());
		}
		
	}
	
	private static class NetworkTablesOutputter implements Outputter {
		
		private String tableName;
		private String fieldName;

		public NetworkTablesOutputter(String tableName, String fieldName) {
			this.tableName = tableName;
			this.fieldName = fieldName;
		}
		
		@Override
		public void output(Message msg) {
			NetworkTable table = NetworkTable.getTable("/" + tableName);
			ArrayList<String> messages = new ArrayList<String>(Arrays.asList(table.getStringArray(fieldName, new String[0])));
			messages.add(msg.getLevel().getKangarooVoice() + " " + msg.getMessage());
			table.putStringArray(fieldName, messages.toArray(new String[0]));
		}

	}

}
