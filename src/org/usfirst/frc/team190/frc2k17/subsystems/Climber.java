package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder.State;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	private final CANTalon climberMotor;
	
	public enum State {
		CLIMB(1), STOP(0), LOWER(-1);
		
		private final double value;
		
		private State(double value) {
			this.value = value;
		}
		
		private double get() {
			return value;
		}
	}
	
	public Climber(){
		climberMotor = new CANTalon(RobotMap.getInstance().CAN_CLIMBER_MOTOR.get());
		diagnose();
	}
	
    public void set(State state) {
		climberMotor.set(state.get());
	}
    
    public double getOutputCurrent() {
    	return climberMotor.getOutputCurrent();
    }
    
    public void diagnose() {
		if (climberMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Collector motor has over-temperature sticky bit set.");
		}
		if (climberMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Collector motor has under-voltage sticky bit set.");
		}
		if (!climberMotor.isAlive()) {
			Logger.defaultLogger.warn("Collector motor is stopped by motor safety.");
		}
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

