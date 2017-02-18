package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SRXDrive {
	
	private DriveMotorPair left = new DriveMotorPair("Left motors",
												RobotMap.CAN.DRIVE_MOTOR_LEFT_FRONT,
												RobotMap.CAN.DRIVE_MOTOR_LEFT_REAR,
												RobotMap.Constants.Drivetrain.DRIVE_LEFT_INVERTED);
	private DriveMotorPair right= new DriveMotorPair("Right motors",
												RobotMap.CAN.DRIVE_MOTOR_RIGHT_FRONT,
												RobotMap.CAN.DRIVE_MOTOR_RIGHT_REAR,
												RobotMap.Constants.Drivetrain.DRIVE_RIGHT_INVERTED);
	
	public SRXDrive() {
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
		leftSpeed = limit(leftSpeed) * RobotMap.Constants.Drivetrain.MAX_SPEED_LOW;
		rightSpeed = limit(rightSpeed) * RobotMap.Constants.Drivetrain.MAX_SPEED_LOW;
		//TODO: Adjust for proper gear
		driveMotorsRPM(leftSpeed, rightSpeed);		
	}
	
	/**
	 * Drive the TalonSRX closed-loop speed control
	 * @param leftSpeed left speed in RPM
	 * @param rightSpeed right speed in RPM
	 */
	private void driveMotorsRPM(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
		SmartDashboard.putNumber("Left drivetrain motor output", leftSpeed);
		SmartDashboard.putNumber("Right drivetrain motor output", rightSpeed);
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
		SmartDashboard.putNumber("Left Drivetrain Encoder Velocity", left.getSpeed());
		SmartDashboard.putNumber("Right Drivetrain Encoder Velocity", right.getSpeed());
		SmartDashboard.putNumber("Left Velocity Error", left.getClosedLoopError());
		SmartDashboard.putNumber("Right Velocity Error", right.getClosedLoopError());
	}
	
	/**
	 * Get average encoder position
	 * @return average position of both encoders in inches
	 */
	public double averageEncoderPositions() {
		double value = (left.getEncoderPosition() + right.getEncoderPosition()) / 2;
		SmartDashboard.putNumber("Encoder averages (inches)", value);
		return value;
	}
	
	/**
	 * Reset encoders
	 */
	public void zeroEncoderPositions() {
		left.setEncoderPosition(0);
		right.setEncoderPosition(0);
	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	private void diagnose() {
		left.diagnose();
		right.diagnose();
	}
	public double getLeftRPM() {
		return left.getSpeed();
	}
	public double getRightRPM() {
		return right.getSpeed();
	}
}
