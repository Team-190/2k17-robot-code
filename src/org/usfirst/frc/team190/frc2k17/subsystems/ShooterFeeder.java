package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.Agitator.State;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ShooterFeeder extends Subsystem {
	
private final CANTalon motor;
	
	public enum State {
		ON(1), OFF(0);
		
		private final double value;
		
		private State(double value) {
			this.value = value;
		}
		
		private double get() {
			return value;
		}
	}
	
	public ShooterFeeder() {
		motor = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FEED.get());
		LiveWindow.addActuator("shooting", "feeder", motor);
	}
	
	public void set(State state) {
		motor.set(state.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void diagnose() {
		if (motor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Feeder motor has over-temperature sticky bit set.");
		}
		if (motor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Feeder motor has under-voltage sticky bit set.");
		}
		if (!motor.isAlive()) {
			Logger.defaultLogger.warn("Feeder motor is stopped by motor safety.");
		}
    }
}

