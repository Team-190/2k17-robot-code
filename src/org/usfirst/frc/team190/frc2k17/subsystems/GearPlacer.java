package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This class handles the Gear Placer pneumatic device and the sensor
 * that determines if the peg is in the robot
 */
public class GearPlacer extends Subsystem {

	private final Solenoid solenoid;
	private final DigitalInput pegPresenceSensor;
	
	public enum State {
		EXTENDED(true), RETRACTED(false);
		
		private final boolean enabled;
		
		private State(boolean enabled) {
			this.enabled = enabled;
		}
		
		private boolean get() {
			return enabled;
		}
	}

	public GearPlacer() {
		solenoid = new Solenoid(RobotMap.getInstance().CAN_PCM.get(),RobotMap.getInstance().PCM_GEAR_PUSHER.get());
		pegPresenceSensor = new DigitalInput(RobotMap.getInstance().DIO_PEG_LIMIT_SWITCH.get());
		LiveWindow.addActuator("gear and boopers", "gear placer", solenoid);
		LiveWindow.addSensor("gear and boopers", "gear limit switch", pegPresenceSensor);
	}

	public void initDefaultCommand() {
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}
	
	public void set(final State state) {
		Logger.defaultLogger.trace("Setting gear placer to state " + state.name());
		solenoid.set(state.get());
	}
	
	/**
	 * @return true if the peg is present, false otherwise
	 */
	public boolean getPegPresent() {
		return pegPresenceSensor.get();
	}
	
	public void diagnose() {
		if(pegPresenceSensor.get()) {
			Logger.defaultLogger.warn("Peg presence sensor is activated.");
		}
	}
	
}
