package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class handles the Gear Placer pneumatic device and the sensor
 * that determines if the peg is in the robot
 */
public class GearPlacer extends Subsystem {

	private static final Solenoid solenoid = new Solenoid(RobotMap.PCM.GEAR_PUSHER);
	private static final DigitalInput pegPresenceSensor = new DigitalInput(RobotMap.DIO.PEG_LIMIT_SWITCH);

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
	}

	public void initDefaultCommand() {
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}
	
	public void set(final State state) {
		solenoid.set(state.get());
	}
	
	/**
	 * @return true if the peg is present, false otherwise
	 */
	public boolean getPegPresent() {
		return pegPresenceSensor.get();
	}
	
}
