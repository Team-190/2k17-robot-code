package org.usfirst.frc.team190.frc2k17;

public class Logger {
	
	private static long beginningOfTime = 0;
	
	public static enum Level {
		TRACE,
		DEBUG,
		INFO,
		WARN,
		ERROR,
		SEVERE,
		CRITICAL;
	}
	
	public static void resetTimestamp() {
		beginningOfTime = System.currentTimeMillis();
		info("Resetting log timestamps to 0 from this point.");
	}
	
	public static void trace(String msg) {
		output(new Message(Level.TRACE, msg));
	}
	
	public static void debug(String msg) {
		output(new Message(Level.DEBUG, msg));
	}
	
	public static void info(String msg) {
		output(new Message(Level.INFO, msg));
	}
	
	public static void warn(String msg) {
		output(new Message(Level.WARN, msg));
	}
	
	public static void error(String msg) {
		output(new Message(Level.ERROR, msg));
	}
	
	public static void severe(String msg) {
		output(new Message(Level.SEVERE, msg));
	}

	public static void critical(String msg) {
		output(new Message(Level.CRITICAL, msg));
	}
	
	private static void output(Message message){
		System.out.println((System.currentTimeMillis() - beginningOfTime) + " [" + message.getLevel() + "] " + message.getMessage());
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
}
