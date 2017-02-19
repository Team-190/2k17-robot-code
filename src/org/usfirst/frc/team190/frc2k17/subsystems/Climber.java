package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	private final CANTalon climberMotor;
	
	public Climber(){
		climberMotor = new CANTalon(RobotMap.getInstance().CAN_CLIMBER_MOTOR.get());
		diagnose();
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    // TODO: Implement speed control for climber
    public void climb() {
    	climberMotor.set(1.0);
    }
    
    public void lower() {
    	climberMotor.set(-1.0);
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
    
}

