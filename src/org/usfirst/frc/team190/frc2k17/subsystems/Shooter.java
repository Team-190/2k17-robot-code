package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Fuel handling and shooting subsystem.
 */
public class Shooter extends Subsystem {
    
	private final CANTalon flywheelMotor1, flywheelMotor2;
	private double requestedSpeedLeft, requestedSpeedRight, smartDashboardSpeedLeft, smartDashboardSpeedRight;
	
	/**
	 * Constructor initializes private fields.
	 */
	public Shooter() {
		
		flywheelMotor1 = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FLYWHEEL1.get());
		flywheelMotor2 = new CANTalon(RobotMap.getInstance().CAN_SHOOTER_MOTOR_FLYWHEEL2.get());
		
		flywheelMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flywheelMotor1.configEncoderCodesPerRev(256);
		flywheelMotor1.configNominalOutputVoltage(+0.0f, -0.0f);
		flywheelMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
		flywheelMotor1.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_1Ms);
		flywheelMotor1.SetVelocityMeasurementWindow(1);
		
		flywheelMotor2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flywheelMotor2.configEncoderCodesPerRev(256);
		flywheelMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
		flywheelMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
		flywheelMotor2.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_1Ms);
		flywheelMotor2.SetVelocityMeasurementWindow(1);
		
		flywheelMotor1.setProfile(0);
		flywheelMotor1.setF(RobotMap.getInstance().SHOOTER_PID_KF.get());
		flywheelMotor1.setP(RobotMap.getInstance().SHOOTER_PID_KP.get());
		flywheelMotor1.setI(RobotMap.getInstance().SHOOTER_PID_KI.get());
		flywheelMotor1.setD(RobotMap.getInstance().SHOOTER_PID_KD.get());
		
		flywheelMotor1.reverseSensor(RobotMap.getInstance().SHOOTER_LEFT_INVERTED.get());
		flywheelMotor1.reverseOutput(!RobotMap.getInstance().SHOOTER_LEFT_INVERTED.get());
		
		flywheelMotor2.setProfile(0);
		flywheelMotor2.setF(RobotMap.getInstance().SHOOTER_PID_KF.get());
		flywheelMotor2.setP(RobotMap.getInstance().SHOOTER_PID_KP.get());
		flywheelMotor2.setI(RobotMap.getInstance().SHOOTER_PID_KI.get());
		flywheelMotor2.setD(RobotMap.getInstance().SHOOTER_PID_KD.get());
		
		flywheelMotor2.reverseSensor(RobotMap.getInstance().SHOOTER_RIGHT_INVERTED.get());
		flywheelMotor2.reverseOutput(!RobotMap.getInstance().SHOOTER_RIGHT_INVERTED.get());
		
		Robot.prefs.putDouble("Shooter PID F", RobotMap.getInstance().SHOOTER_PID_KF.get());
		Robot.prefs.putDouble("Shooter PID P", RobotMap.getInstance().SHOOTER_PID_KP.get());
		Robot.prefs.putDouble("Shooter PID I", RobotMap.getInstance().SHOOTER_PID_KI.get());
		Robot.prefs.putDouble("Shooter PID D", RobotMap.getInstance().SHOOTER_PID_KD.get());
		Robot.prefs.putDouble("Shooter target speed left", RobotMap.getInstance().SHOOTER_TARGET_SPEED_LEFT.get());
		Robot.prefs.putDouble("Shooter target speed right", RobotMap.getInstance().SHOOTER_TARGET_SPEED_RIGHT.get());

	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	public void diagnose() {
		if (flywheelMotor1.getBusVoltage() != 4.0) {
			if (flywheelMotor1.getStickyFaultOverTemp() != 0) {
				Logger.defaultLogger.warn("Shooter flywheel motor 1 has over-temperature sticky bit set.");
			}
			if (flywheelMotor1.getStickyFaultUnderVoltage() != 0) {
				Logger.defaultLogger.warn("Shooter flywheel motor 1 has under-voltage sticky bit set.");
			}
		} else {
			Logger.defaultLogger.warn("Shooter flywheel motor 1 controller not reachable over CAN.");
		}
		if (flywheelMotor2.getBusVoltage() != 4.0) {
			if (flywheelMotor2.getStickyFaultOverTemp() != 0) {
				Logger.defaultLogger.warn("Shooter flywheel motor 2 has over-temperature sticky bit set.");
			}
			if (flywheelMotor2.getStickyFaultUnderVoltage() != 0) {
				Logger.defaultLogger.warn("Shooter flywheel motor 2 has under-voltage sticky bit set.");
			}
		} else {
			Logger.defaultLogger.warn("Shooter flywheel motor 2 controller not reachable over CAN.");
		}
		if (!flywheelMotor1.isAlive()) {
			Logger.defaultLogger.warn("Shooter flywheel motor 1 is stopped by motor safety.");
		}
		if (!flywheelMotor2.isAlive()) {
			Logger.defaultLogger.warn("Shooter flywheel motor 2 is stopped by motor safety.");
		}
	}
	
	public void clearStickyFaults() {
		flywheelMotor1.clearStickyFaults();
		flywheelMotor2.clearStickyFaults();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shooterOn() {
    	getSmartDashboardPidValues();
    	spin(smartDashboardSpeedLeft, smartDashboardSpeedRight);
    }
    
    public void shooterLeftOn() {
    	getSmartDashboardPidValues();
    	spin(smartDashboardSpeedLeft, 0);
    }
    
    public void shooterRightOn() {
    	getSmartDashboardPidValues();
    	spin(0, smartDashboardSpeedRight);
    }
    
    public void shooterOn(double leftFlywheel, double rightFlywheel) {
    	getSmartDashboardPidValues();
		spin(Math.max(Math.abs(leftFlywheel), 1) * smartDashboardSpeedLeft * Math.signum(leftFlywheel),
				Math.max(Math.abs(rightFlywheel), 1) * smartDashboardSpeedRight * Math.signum(rightFlywheel));
    }
    
    private void spin(double leftFlywheelSpeed, double rightFlywheelSpeed){
    	getSmartDashboardPidValues();
    	
    	requestedSpeedLeft = leftFlywheelSpeed;
    	requestedSpeedRight = rightFlywheelSpeed;
    	
    	flywheelMotor1.changeControlMode(TalonControlMode.Speed);
    	flywheelMotor2.changeControlMode(TalonControlMode.Speed);
    	
    	flywheelMotor1.set(leftFlywheelSpeed);
    	flywheelMotor2.set(rightFlywheelSpeed);
    }
    
    public boolean isAtSpeed() {
    	double speed1 = flywheelMotor1.getSpeed();
    	double speed2 = flywheelMotor2.getSpeed();
    	int rpmTolerance = RobotMap.getInstance().SHOOTER_RPM_TOLERANCE.get();
    	outputEncoderValues();
    	return (Math.abs(speed1 - requestedSpeedLeft) <= rpmTolerance) && (Math.abs(speed2 - requestedSpeedRight) <= rpmTolerance);
    }
    
    public void shooterOff() {
    	flywheelMotor1.changeControlMode(TalonControlMode.PercentVbus);
    	flywheelMotor2.changeControlMode(TalonControlMode.PercentVbus);
    	flywheelMotor1.set(0);
    	flywheelMotor2.set(0);
    }
    
    public void getSmartDashboardPidValues() {
    	double f = Robot.prefs.getDouble("Shooter PID F", RobotMap.getInstance().SHOOTER_PID_KF.get());
    	double p = Robot.prefs.getDouble("Shooter PID P", RobotMap.getInstance().SHOOTER_PID_KP.get());
    	double i = Robot.prefs.getDouble("Shooter PID I", RobotMap.getInstance().SHOOTER_PID_KI.get());
    	double d = Robot.prefs.getDouble("Shooter PID D", RobotMap.getInstance().SHOOTER_PID_KD.get());
    	smartDashboardSpeedLeft = Robot.prefs.getDouble("Shooter target speed left", RobotMap.getInstance().SHOOTER_TARGET_SPEED_LEFT.get());
    	smartDashboardSpeedRight = Robot.prefs.getDouble("Shooter target speed right", RobotMap.getInstance().SHOOTER_TARGET_SPEED_RIGHT.get());
    	flywheelMotor1.setPID(p, i, d);
    	flywheelMotor1.setF(f);
    	flywheelMotor2.setPID(p, i, d);
    	flywheelMotor2.setF(f);
    }
    
    public void outputEncoderValues() {
    	SmartDashboard.putNumber("Flywheel1 Speed", flywheelMotor1.getSpeed());
    	SmartDashboard.putNumber("Flywheel2 Speed", flywheelMotor2.getSpeed());
    	SmartDashboard.putNumber("Flywheel1 setpoint", flywheelMotor1.getSetpoint());
    	SmartDashboard.putNumber("Flywheel2 setpoint", flywheelMotor2.getSetpoint());
    }
}

