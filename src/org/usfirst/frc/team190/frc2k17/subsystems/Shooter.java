package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Fuel handling and shooting subsystem.
 */
public class Shooter extends Subsystem {
    
	private final CANTalon flywheelMotor1, flywheelMotor2, feedMotor;
	private final int f = 0,
					  p = 0,
					  i = 0,
					  d = 0;
	
	/**
	 * Constructor initializes private fields.
	 */
	public Shooter() {
		
		flywheelMotor1 = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FLYWHEEL1.get());
		flywheelMotor2 = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FLYWHEEL2.get());
		feedMotor = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FEED.get());
		
		flywheelMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flywheelMotor1.configEncoderCodesPerRev(256);
		flywheelMotor1.configNominalOutputVoltage(+0.0f, -0.0f);
		flywheelMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
		
		flywheelMotor2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flywheelMotor2.configEncoderCodesPerRev(256);
		flywheelMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
		flywheelMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
		
		flywheelMotor1.setProfile(0);
		flywheelMotor1.setF(f);
		flywheelMotor1.setP(p);
		flywheelMotor1.setI(i);
		flywheelMotor1.setD(d);
		
		flywheelMotor2.setProfile(0);
		flywheelMotor2.setF(f);
		flywheelMotor2.setP(p);
		flywheelMotor2.setI(i);
		flywheelMotor2.setD(d);

	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	public void diagnose() {
		if (flywheelMotor1.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Shooter flywheel motor has over-temperature sticky bit set.");
		}
		if (flywheelMotor1.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Shooter flywheel motor has under-voltage sticky bit set.");
		}
		if (feedMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Shooter feed motor has over-temperature sticky bit set.");
		}
		if (feedMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Shooter feed motor has under-voltage sticky bit set.");
		}
		if (!flywheelMotor1.isAlive()) {
			Logger.defaultLogger.warn("Shooter flywheel motor is stopped by motor safety.");
		}
		if (!feedMotor.isAlive()) {
			Logger.defaultLogger.warn("Shooter feed motor is stopped by motor safety.");
		}
		if (flywheelMotor1.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Shooter flywheel encoder not present.");
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shooterOn(int flywheelSpeed){
    	flywheelMotor1.changeControlMode(TalonControlMode.Speed);
    	flywheelMotor2.changeControlMode(TalonControlMode.Speed);
    	
    	flywheelMotor1.set(flywheelSpeed);
    	flywheelMotor2.set(flywheelSpeed);
    }
    
}

