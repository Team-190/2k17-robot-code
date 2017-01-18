package org.usfirst.frc.team190.frc2k17;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTableLogHandler extends Handler {
	
	private String fieldName;
	private NetworkTable table;

	public NetworkTableLogHandler(String tableName, String fieldName) {
		super();
		this.fieldName = fieldName;
		table = NetworkTable.getTable("/" + tableName);
	}
	
	@Override
	public void publish(LogRecord record) {
		List<String> messages = Arrays.asList(table.getStringArray(fieldName, new String[0]));
		messages.add(record.getMessage());
		table.putStringArray(fieldName, messages.toArray(new String[0]));
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

}
