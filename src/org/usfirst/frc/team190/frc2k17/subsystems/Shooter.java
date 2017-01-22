package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Fuel handling and shooting subsystem.
 */
public class Shooter extends Subsystem {
    
	private final CANTalon flywheelMotor, feedMotor;
	
	/**
	 * Constructor initializes private fields.
	 */
	public Shooter() {
		
		flywheelMotor = new CANTalon(RobotMap.CAN.SHOOTER_MOTOR_FLYWHEEL);
		feedMotor = new CANTalon(RobotMap.CAN.SHOOTER_MOTOR_FEED);
		
		flywheelMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		diagnose();
		
	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	private void diagnose() {
		if (flywheelMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Shooter flywheel motor has over-temperature sticky bit set.");
		}
		if (flywheelMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Shooter flywheel motor has under-voltage sticky bit set.");
		}
		if (feedMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Shooter feed motor has over-temperature sticky bit set.");
		}
		if (feedMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Shooter feed motor has under-voltage sticky bit set.");
		}
		if (!flywheelMotor.isAlive()) {
			Logger.defaultLogger.warn("Shooter flywheel motor is stopped by motor safety.");
		}
		if (!feedMotor.isAlive()) {
			Logger.defaultLogger.warn("Shooter feed motor is stopped by motor safety.");
		}
		if (flywheelMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Shooter flywheel encoder not present.");
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

