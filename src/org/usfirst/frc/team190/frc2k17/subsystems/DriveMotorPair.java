package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveMotorPair {
	
	private CANTalon frontMotor;
	private CANTalon rearMotor;
	private String name;
	private boolean inverted;

	public DriveMotorPair(String name, int frontID, int rearID, boolean inverted) {
		frontMotor = new CANTalon(frontID);
		frontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		frontMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		frontMotor.setInverted(inverted);
		frontMotor.setProfile(0);
		frontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KP);
		frontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		frontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		frontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		frontMotor.changeControlMode(TalonControlMode.Speed);
		LiveWindow.addActuator("Drive Train", name + " motor", frontMotor);
		
		rearMotor = new CANTalon(rearID);
		rearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rearMotor.set(frontMotor.getDeviceID());
		this.name = name;
		this.inverted = inverted;
	}

	public void set(double speed) {
		frontMotor.set(speed);
	}
	
	public double getSpeed() {
		return frontMotor.getSpeed();
	}
	
	public double getClosedLoopError() {
		return frontMotor.getClosedLoopError();
	}
	
	/**
	 * Get the encoder position in inches
	 * @return encoder position in inches
	 */
	public double getEncoderPosition() {
		double pos = ticksToInches(frontMotor.getEncPosition());
		return inverted ? pos : -pos;
	}
	
	public void setEncoderPosition(int position) {
		frontMotor.setEncPosition(position);
	}
	
	private double inchesToTicks(double inches) {
		return inches / RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	private double ticksToInches(double ticks) {
		return ticks * RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public void diagnose() {
		if (frontMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn(name + "front drivetrain motor has over-temperature sticky bit set.");
		}
		if (frontMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn(name + " front drivetrain motor has under-voltage sticky bit set.");
		}
		if (rearMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (rearMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (!frontMotor.isAlive()) {
			Logger.defaultLogger.warn(name + " front drivetrain motor is stopped by motor safety.");
		}
		if (!rearMotor.isAlive()) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor is stopped by motor safety.");
		}
		if (frontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn(name + " drivetrain encoder not present.");
		} else {
			Logger.defaultLogger.debug(name + " drivetrain encoder is present.");
		}
	}	
}
