package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SRXDrive {
	
	private static class DriveMotorPair {
		private CANTalon master;
		private CANTalon slave;
		private String name;
		private boolean encoderInverted;
		private boolean inSpeedControlMode;

		public DriveMotorPair(String name, int masterID, int slaveID, boolean motorInverted, boolean encoderInverted) {
			this.name = name;
			this.encoderInverted = encoderInverted;
			
			master = new CANTalon(masterID);
			
			master.setFeedbackDevice(RobotMap.getInstance().DRIVE_FEEDBACK_DEV.get());
			if(RobotMap.getInstance().DRIVE_FEEDBACK_DEV.get() == FeedbackDevice.QuadEncoder)
			{
				//you don't have to configure encoder ticks if using the ctr encoders but do for quad encoders
				master.configEncoderCodesPerRev(RobotMap.getInstance().DRIVE_TICKS_PER_REV.get());
				SmartDashboard.putNumber("Encoder Ticks", RobotMap.getInstance().DRIVE_TICKS_PER_REV.get());
			}
			
			master.configNominalOutputVoltage(+0.0f, -0.0f);
			master.configPeakOutputVoltage(+12.0f, -12.0f);
			master.setProfile(0);
			master.setP(RobotMap.getInstance().DRIVE_PID_SPEED_KP.get());
			master.setI(RobotMap.getInstance().DRIVE_PID_SPEED_KI.get());
			master.setD(RobotMap.getInstance().DRIVE_PID_SPEED_KD.get());
			master.setF(RobotMap.getInstance().DRIVE_PID_SPEED_KF.get());
			
			master.reverseOutput(motorInverted);
			master.reverseSensor(encoderInverted);
			LiveWindow.addActuator("Drive Train", name + " motor", master);
			
			slave = new CANTalon(slaveID);
			slave.changeControlMode(CANTalon.TalonControlMode.Follower);
			slave.set(master.getDeviceID());

			setControlMode(TalonControlMode.Speed);
		}
		public void enableCoast(boolean set) {
				master.enableBrakeMode(!set);
				slave.enableBrakeMode(!set);
		}
		/**
		 * Set the master motor's control
		 * @param speed in motor units (-1 to 1)
		 */
		public void set(double speed) {
			if (inSpeedControlMode) {
				double maxSpeed = Robot.shifters.isInHighGear() ? RobotMap.getInstance().DRIVE_MAX_SPEED_HIGH.get() : RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				speed *= maxSpeed;
			}
			
			SmartDashboard.putNumber(name + " setpoint", speed);
			
			// TODO: Implement switching between speed and percentvbus mode somewhere
			// 		 Most likely need to implement a failsafe if an encoder fails and
			//		 a manual control incase the failsafe also fails
			master.set(speed);
		}
		
		/**
		 * Sets the control mode of the master motor
		 * @param mode control mode, either Speed or PercentVbus
		 */
		private void setControlMode(TalonControlMode mode) {
			if (mode == TalonControlMode.Speed) {
				master.changeControlMode(TalonControlMode.Speed);
				inSpeedControlMode = true;
			} else if (mode == TalonControlMode.PercentVbus){
				master.changeControlMode(TalonControlMode.PercentVbus);
				inSpeedControlMode = false;
			}
		}
		
		/**
		 * Gets the pair's speed
		 * @return speed in inches/second
		 */
		public double getSpeed() {
			return master.getSpeed();
		}
		
		/**
		 * Get closed-loop speed control error
		 * @return
		 */
		public double getClosedLoopError() {
			return master.getClosedLoopError();
		}
		
		/**
		 * Get the encoder position
		 * @return encoder position in inches
		 */
		public double getEncoderPosition() {
			double pos = ticksToInches(master.getEncPosition());
			return encoderInverted ? -pos : pos;
		}
		
		/**
		 * Sets the encoder's current position
		 * @param position position in inches
		 */
		public void setEncoderPosition(int position) {
			master.setEncPosition(position);
		}
		
		/**
		 * Converts inches to encoder ticks
		 * @param inches
		 * @return encoder ticks
		 */
		private double inchesToTicks(double inches) {
			return inches / (Math.PI / RobotMap.getInstance().DRIVE_TICKS_PER_REV.get());
		}
		
		/**
		 * Converts encoder ticks to inches
		 * @param encoder ticks
		 * @return inches
		 */
		private double ticksToInches(double ticks) {
			return ticks * (Math.PI / RobotMap.getInstance().DRIVE_TICKS_PER_REV.get());
		}
		
		/**
		 * Diagnose various sensors for the drive pair
		 */
		public void diagnose() {
			if (master.getStickyFaultOverTemp() != 0) {
				Logger.defaultLogger.warn(name + " - front drivetrain motor has over-temperature sticky bit set.");
			}
			if (master.getStickyFaultUnderVoltage() != 0) {
				Logger.defaultLogger.warn(name + " - front drivetrain motor has under-voltage sticky bit set.");
			}
			if (slave.getStickyFaultOverTemp() != 0) {
				Logger.defaultLogger.warn(name + " - rear drivetrain motor has over-temperature sticky bit set.");
			}
			if (slave.getStickyFaultUnderVoltage() != 0) {
				Logger.defaultLogger.warn(name + " - rear drivetrain motor has under-voltage sticky bit set.");
			}
			if (!master.isAlive()) {
				Logger.defaultLogger.warn(name + " - front drivetrain motor is stopped by motor safety.");
			}
			if (!slave.isAlive()) {
				Logger.defaultLogger.warn(name + " - rear drivetrain motor is stopped by motor safety.");
			}
			if (master.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
				Logger.defaultLogger.warn(name + " - drivetrain encoder not present.");
			} else {
				Logger.defaultLogger.debug(name + " - drivetrain encoder is present.");
			}
		}	
	}
	
	private DriveMotorPair left = new DriveMotorPair("Left",
												RobotMap.getInstance().CAN_DRIVE_MOTOR_LEFT_FRONT.get(),
												RobotMap.getInstance().CAN_DRIVE_MOTOR_LEFT_REAR.get(),
												RobotMap.getInstance().DRIVE_LEFT_MOTOR_INVERTED.get(),
												RobotMap.getInstance().DRIVE_LEFT_ENC_INVERTED.get());
	
	private DriveMotorPair right= new DriveMotorPair("Right",
												RobotMap.getInstance().CAN_DRIVE_MOTOR_RIGHT_FRONT.get(),
												RobotMap.getInstance().CAN_DRIVE_MOTOR_RIGHT_REAR.get(),
												RobotMap.getInstance().DRIVE_RIGHT_MOTOR_INVERTED.get(),
												RobotMap.getInstance().DRIVE_RIGHT_ENC_INVERTED.get());
	
	/**
	 * Constructor
	 */
	public SRXDrive() {
		diagnose();
		zeroEncoderPositions();
	}
	
	/**
	 * Limit a value from -1 to 1
	 * @param value a value
	 * @return value limited from -1 to 1
	 */
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
	 * Drive each side of the robot individually
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		leftSpeed = limit(leftSpeed);
		rightSpeed = limit(rightSpeed);
		
		driveMotors(leftSpeed, rightSpeed);		
	}
	
	/**
	 * Drive the MotorPairs
	 * @param leftSpeed left speed in RPM
	 * @param rightSpeed right speed in RPM
	 */
	private void driveMotors(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
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
		SmartDashboard.putNumber("Left Drivetrain Encoder Position", left.getEncoderPosition());
		SmartDashboard.putNumber("Right Drivetrain Encoder Position", right.getEncoderPosition());
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
	 * Sets the control mode of the motors
	 * @param mode control mode, either Speed or PercentVbus
	 */
	public void setControlMode(TalonControlMode mode) {
		left.setControlMode(mode);
		right.setControlMode(mode);
	}

	/**
	 * Perform health checks and log warnings.
	 */
	private void diagnose() {
		left.diagnose();
		right.diagnose();
	}
	
	public void enableCoast(boolean set) {
		left.enableCoast(set);
		right.enableCoast(set);
	}
}
