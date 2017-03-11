package org.usfirst.frc.team190.frc2k17.subsystems;


import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.boopers.BooperSetCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
		
		private boolean get() {
			return enabled;
		}
	}

	public Boopers() {
		solenoid = new Solenoid(RobotMap.getInstance().CAN_PCM.get(), RobotMap.getInstance().PCM_BOOPERS.get());
		LiveWindow.addActuator("gear and boopers", "boopers", solenoid);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new BooperSetCommand(Boopers.State.RETRACTED));
	}

	public void toggle() {
		solenoid.set(!solenoid.get());
	}
	
    public void set(final State state) {
		solenoid.set(state.get());
	}

	// TODO: implement diagnose for Boopers
}

