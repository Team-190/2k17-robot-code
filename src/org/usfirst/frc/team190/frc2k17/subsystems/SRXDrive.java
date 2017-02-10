package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SRXDrive {
	private CANTalon leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor;
	
	public SRXDrive() {
		
		leftFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_FRONT);
		leftRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_REAR);
		rightFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_FRONT);
		rightRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_REAR);

		leftFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		leftFrontMotor.configPeakOutputVoltage(+12.0f, -12.0f);
			
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		rightFrontMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		
		// put left rear motor in slave mode, following the left front motor
		leftRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		
		// put right rear motor in slave mode, following the right front motor
		rightRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		leftFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_LEFT_INVERTED);
		rightFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_RIGHT_INVERTED);		
		
		leftFrontMotor.setProfile(0);
		leftFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KP);
		leftFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		leftFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		leftFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		rightFrontMotor.setProfile(0);
		rightFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KP);
		rightFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		rightFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		rightFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		leftFrontMotor.changeControlMode(TalonControlMode.Speed);
		rightFrontMotor.changeControlMode(TalonControlMode.Speed);
		
		
		// Setup Live Window
		LiveWindow.addActuator("Drive Train", "Left Motor Master", leftFrontMotor);
		LiveWindow.addActuator("Drive Train", "Left Motor Follower", leftRearMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Master", rightFrontMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Follower", rightRearMotor);
		
		diagnose();
		zeroEncoderPositions();
	}
	
	private double limit(double value) {
		if (value >= 1.0) {
			return 1.0;
		} else if (value <= -1.0) {
			return -1.0;
		} else {
			return value;
		}
	}
	
	/**
	 * Drive each side of the robot individually using values from -1 to 1
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		leftSpeed = limit(leftSpeed);
		rightSpeed = limit(rightSpeed);
		//TODO: Adjust for proper gear
		leftSpeed *= RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW;
		rightSpeed *= RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW;
		driveMotors(leftSpeed, rightSpeed);		
	}
	
	/**
	 * Drive the TalonSRX closed-loop speed control
	 * @param leftSpeed left speed in RPM
	 * @param rightSpeed right speed in RPM
	 */
	private void driveMotors(double leftSpeed, double rightSpeed) {
		leftFrontMotor.set(leftSpeed);
		rightFrontMotor.set(rightSpeed);
		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);
	}
	
	/**
	 * Drive each side using Joystick values
	 * @param leftJoy
	 * @param rightJoy
	 */
	public void tankDrive(Joystick leftJoy, Joystick rightJoy) {
		tankDrive(leftJoy.getY(), rightJoy.getY());
	}
	
	/**
	 * Drive the robot with a speed and rotational value
	 * @param moveValue the forward speed of the robot
	 * @param rotateValue the rotational value
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		moveValue = limit(moveValue);
		rotateValue = limit(rotateValue);
		// Copied from WPILib
		double leftMotorSpeed;
	    double rightMotorSpeed;
	    
	    if (moveValue > 0.0) {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = Math.max(moveValue, rotateValue);
	      } else {
	        leftMotorSpeed = Math.max(moveValue, -rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      }
	    } else {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      } else {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	      }
	    }
		
	    tankDrive(leftMotorSpeed, rightMotorSpeed);
	    
	}
	
	/**
	 * Log Encoder Values to Smart Dashboard
	 */
	public void outputEncoderValues() {
		SmartDashboard.putNumber("Left Drivetrain Encoder Velocity", getLeftEncoderSpeed());
		SmartDashboard.putNumber("Right Drivetrain Encoder Velocity", getRightEncoderSpeed());
		SmartDashboard.putNumber("Left Velocity Error", leftFrontMotor.getClosedLoopError());
		SmartDashboard.putNumber("Right Velocity Error", rightFrontMotor.getClosedLoopError());
	}
	
	/**
	 * Gets the left encoder's position
	 * @return encoder position in ticks
	 */
	public double getLeftEncoderPosition() {
		double value = leftFrontMotor.getEncPosition();
		return (RobotMap.Constants.DriveTrain.INVERT_LEFT_ENC ? -value : value);
	}
	
	/**
	 * Gets the right encoder's position
	 * @return encoder position in ticks
	 */
	public double getRightEncoderPosition() {
		double value = rightFrontMotor.getEncPosition();
		return (RobotMap.Constants.DriveTrain.INVERT_RIGHT_ENC ? -value : value);
	}
	
	/**
	 * Gets the left encoder's velocity
	 * @return encoder position in rpm
	 */
	public double getLeftEncoderSpeed() {
		double value = leftFrontMotor.getSpeed();
		return (RobotMap.Constants.DriveTrain.INVERT_LEFT_ENC ? -value : value);
	}
	
	/**
	 * Gets the right encoder's velocity
	 * @return encoder velocity in rpm
	 */
	public double getRightEncoderSpeed() {
		double value = rightFrontMotor.getSpeed();
		return (RobotMap.Constants.DriveTrain.INVERT_RIGHT_ENC ? -value : value);
	}
	
	/**
	 * Get average encoder position
	 * @return average position of both encoders in ticks
	 */
	public double averageEncoderPositions() {
		double value = (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
		SmartDashboard.putNumber("Encoder averages (ticks)", value);
		return value;
	}
	
	/**
	 * Reset encoders
	 */
	public void zeroEncoderPositions() {
		leftFrontMotor.setEncPosition(0);
		rightFrontMotor.setEncPosition(0);
	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	private void diagnose() {
		if (leftFrontMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Left front drivetrain motor has over-temperature sticky bit set.");
		}
		if (leftFrontMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Left front drivetrain motor has under-voltage sticky bit set.");
		}
		if (leftRearMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Left rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (leftRearMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Left rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (rightFrontMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Right front drivetrain motor has over-temperature sticky bit set.");
		}
		if (rightFrontMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Right front drivetrain motor has under-voltage sticky bit set.");
		}
		if (rightRearMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Right rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (rightRearMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Right rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (!leftFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Left front drivetrain motor is stopped by motor safety.");
		}
		if (!leftRearMotor.isAlive()) {
			Logger.defaultLogger.warn("Left rear drivetrain motor is stopped by motor safety.");
		}
		if (!rightFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Right front drivetrain motor is stopped by motor safety.");
		}
		if (!rightFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Right rear drivetrain motor is stopped by motor safety.");
		}
		if (leftFrontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Left drivetrain encoder not present.");
		} else {
			Logger.defaultLogger.debug("Left drivetrain encoder is present.");
		}
		if (rightFrontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Right drivetrain encoder not present.");
		} else {
			Logger.defaultLogger.debug("Right drivetrain encoder is present.");
		}
	}
}
