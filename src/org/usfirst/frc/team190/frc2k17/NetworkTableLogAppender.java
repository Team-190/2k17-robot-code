package org.usfirst.frc.team190.frc2k17;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NetworkTableLogAppender extends AbstractAppender {
	
	private NetworkTable table;

	protected NetworkTableLogAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout);
	}

	public NetworkTableLogAppender(String name, Filter filter, Layout<? extends Serializable> layout,
			boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}
	
	private void initNetworkTable() {
		table = NetworkTable.getTable("/kangaroo");
	}

	@Override
	public void append(LogEvent event) {
		List<String> messages = Arrays.asList(table.getStringArray(RobotMap.NetworkTable.Kangaroo.VOICE_LOG, new String[0]));
		messages.add(event.toString());
		table.putStringArray(RobotMap.NetworkTable.Kangaroo.VOICE_LOG, messages.toArray(new String[0]));
	}

}
