package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
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
		diagnose();
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
    
    public void diagnose() {
		if (collectorMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Collector motor has over-temperature sticky bit set.");
		}
		if (collectorMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Collector motor has under-voltage sticky bit set.");
		}
		if (!collectorMotor.isAlive()) {
			Logger.defaultLogger.warn("Collector motor is stopped by motor safety.");
		}
    }
}

