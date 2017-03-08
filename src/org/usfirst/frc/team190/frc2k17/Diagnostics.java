package org.usfirst.frc.team190.frc2k17;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DriverStation;

public class Diagnostics {

	private static DriverStation ds = DriverStation.getInstance();
	private static boolean disconnectedRun, brownedOutRun;
	private static List<LedState> ledStates = new ArrayList<LedState>();
	private static LedState currentLedState;
	private static Timer timer;
	
	private enum LedState {
		YELLOW,
		CYAN;
	}
	
	public static void start() {
		timer = new Timer();
		timer.schedule(new FastLoop(), 0, 250);
		timer.schedule(new FastLoop2(), 0, 250);
		timer.schedule(new SlowLoop(), 20000, 20000);
	}
	
	private static void updateLEDs() {
		if(ledStates.contains(LedState.CYAN)) {
			if(currentLedState != LedState.CYAN){
				if(currentLedState != null) {
					Robot.leftLEDs.disableOverride();
					Robot.rightLEDs.disableOverride();
				}
				Robot.leftLEDs.override(Color.CYAN);
				Robot.rightLEDs.override(Color.CYAN);
				currentLedState = LedState.CYAN;
			}
		} else if(ledStates.contains(LedState.YELLOW)) {
			if(currentLedState != LedState.YELLOW){
				if(currentLedState != null) {
					Robot.leftLEDs.disableOverride();
					Robot.rightLEDs.disableOverride();
				}
				Robot.leftLEDs.override(Color.YELLOW);
				Robot.rightLEDs.override(Color.YELLOW);
				currentLedState = LedState.YELLOW;
			}
		} else if(currentLedState != null) {
			Robot.leftLEDs.disableOverride();
			Robot.rightLEDs.disableOverride();
			currentLedState = null;
		}
	}
	
	public static synchronized void resetDisconnected() {
		if(disconnectedRun) {
			Logger.defaultLogger.info("Driver station reconnected.");
			ledStates.remove(LedState.YELLOW);
			updateLEDs();
			disconnectedRun = false;
		}
	}
	
	private Diagnostics() {
		// private constructor so Diagnostics can't be instantiated
	}
	
	private static class SlowLoop extends TimerTask {

		@Override
		public void run() {
			Robot.diagnose();
		}
		
	}
	
	private static class FastLoop extends TimerTask {
		
		@Override
		public void run() {
			boolean dsAttached = ds.isDSAttached(), robotBrownedOut = ds.isBrownedOut();
			if (!dsAttached) {
				if (!disconnectedRun) {
					Logger.defaultLogger.warn("Driver station disconnected.");
					ledStates.add(LedState.YELLOW);
					updateLEDs();
					disconnectedRun = true;
				}
			} else {
				resetDisconnected();
			}
			if (robotBrownedOut) {
				if (!brownedOutRun) {
					Logger.defaultLogger.error("Robot is browned-out.");
					ledStates.add(LedState.CYAN);
					updateLEDs();
					brownedOutRun = true;
				}
			} else {
				if (brownedOutRun) {
					Logger.defaultLogger.info("Robot is no longer browned-out.");
					ledStates.remove(LedState.CYAN);
					updateLEDs();
					brownedOutRun = false;
				}
			}
		}
		
	}
	
private static class FastLoop2 extends TimerTask {
		
		private double lastSpoke;
	
		public FastLoop2() {
			super();
			lastSpoke = DriverStation.getInstance().getMatchTime();
		}
	
		@Override
		public void run() {
			double time = DriverStation.getInstance().getMatchTime();
			if(time <= 6d) {
				if(lastSpoke > 6d) {
					Logger.voice.info("five");
					lastSpoke = time;
				}
			} else if(time <= 11d) {
				if (lastSpoke > 11d) {
					Logger.voice.info("ten");
					lastSpoke = time;
				}
			} else if(time <= 31) {
				if (lastSpoke > 31d) {
					Logger.voice.info("thirty");
					lastSpoke = time;
				}
			}
		}
		
	}

}
