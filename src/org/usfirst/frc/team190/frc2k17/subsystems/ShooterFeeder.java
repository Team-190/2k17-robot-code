package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.shooter.CloseFeederCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ShooterFeeder extends Subsystem {
	
	private final DoubleSolenoid sol;
	
	public enum State {
		OPEN(Value.kForward), CLOSED(Value.kReverse);
		
		private final Value value;
		
		private State(Value value) {
			this.value = value;
		}
		
		private Value get() {
			return value;
		}
	}
	
	public ShooterFeeder() {
		sol = new DoubleSolenoid(RobotMap.getInstance().CAN_PCM.get(), RobotMap.getInstance().PCM_FEEDDOOR_OPEN.get(),
				RobotMap.getInstance().PCM_FEEDDOOR_CLOSED.get());
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

