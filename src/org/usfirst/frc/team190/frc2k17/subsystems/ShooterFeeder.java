package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ShooterFeeder extends Subsystem {
	
	private final Solenoid sol;
	
	public enum State {
		OPEN(true), CLOSED(false);
		
		private final boolean value;
		
		private State(boolean value) {
			this.value = value;
		}
		
		private boolean get() {
			return value;
		}
	}
	
	public ShooterFeeder() {
		sol = new Solenoid(RobotMap.getInstance().CAN_PCM.get(), RobotMap.getInstance().PCM_FEEDER_DOOR.get());
		LiveWindow.addActuator("shooting", "feeder", sol);
	}
	
	public void set(State state) {
		sol.set(state.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}

