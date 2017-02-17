package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.boopers.BooperToggleCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Boopers extends Subsystem {

	private final Solenoid solenoid;
	
	public enum State {
		EXTENDED(true), RETRACTED(false);
		
		private final boolean enabled;
		
		private State(boolean enabled) {
			this.enabled = enabled;
		}
		
		public boolean get() {
			return enabled;
		}
	}

	public Boopers() {
		solenoid = new Solenoid(RobotMap.PCM.BOOPERS);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new BooperToggleCommand());
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}
	
	public void set(final State state) {
		solenoid.set(state.get());
	}

	// TODO: implement diagnose for Boopers
}
