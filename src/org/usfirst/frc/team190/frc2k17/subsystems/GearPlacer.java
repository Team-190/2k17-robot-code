package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearPlacer extends Subsystem {

	private final Solenoid solenoid;
	private final DigitalInput pegPresenceSensor, fullExtensionSensor;
	
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
		solenoid = new Solenoid(RobotMap.PCM.GEAR_PUSHER);
		pegPresenceSensor = new DigitalInput(RobotMap.DIO.PEG_LIMIT_SWITCH);
		fullExtensionSensor = new DigitalInput(RobotMap.DIO.PEG_LIMIT_SWITCH);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
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
	
	/**
	 * @return true if the gear placer piston is fully extended, false otherwise
	 */
	public boolean getFullyExtended() {
		return fullExtensionSensor.get();
	}
}
