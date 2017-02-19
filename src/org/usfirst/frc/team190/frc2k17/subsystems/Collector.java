package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {

	private final CANTalon collectorMotor;
	
	public Collector() {
		collectorMotor = new CANTalon(RobotMap.getInstance().CAN_COLLECTOR_MOTOR.get());
	}
	// TODO: implement speed control for collector
	public void collect() {
		collectorMotor.set(1);
	}
	
	public void stop() {
		collectorMotor.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    // TODO: implement collector diagnose
}

