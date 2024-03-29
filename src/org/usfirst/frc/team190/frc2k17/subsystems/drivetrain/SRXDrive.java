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
		private boolean motorInverted;
		private boolean encoderInverted;
		private boolean inSpeedControlMode;
		private double setpoint, encoderOffset;

		public DriveMotorPair(String name, int masterID, int slaveID, boolean motorInverted, boolean encoderInverted) {
			this.name = name;
			this.motorInverted = motorInverted;
			this.encoderInverted = encoderInverted;
			encoderOffset = 0;
			
			master = new CANTalon(masterID);
			
			master.setFeedbackDevice(RobotMap.getInstance().DRIVE_FEEDBACK_DEV.get());
			if(RobotMap.getInstance().DRIVE_FEEDBACK_DEV.get() == FeedbackDevice.QuadEncoder)
			{
				//you don't have to configure encoder ticks if using the ctr encoders but do for quad encoders
				master.configEncoderCodesPerRev(RobotMap.getInstance().DRIVE_TICKS_PER_REV2.get());
			}
			
			master.configNominalOutputVoltage(+0.0f, -0.0f);
			master.configPeakOutputVoltage(+12.0f, -12.0f);
			master.setProfile(0);
			master.setP(RobotMap.getInstance().DRIVE_PID_SPEED_KP.get());
			master.setI(RobotMap.getInstance().DRIVE_PID_SPEED_KI.get());
			master.setD(RobotMap.getInstance().DRIVE_PID_SPEED_KD.get());
			master.setF(RobotMap.getInstance().DRIVE_PID_SPEED_KF.get());
			
			LiveWindow.addActuator("Drive Train", name + " motor", master);
			
			slave = new CANTalon(slaveID);
			slave.changeControlMode(CANTalon.TalonControlMode.Follower);
			slave.set(master.getDeviceID());

			setControlMode(TalonControlMode.Speed);
			
			LiveWindow.addActuator("drivetrain", name + " master", master);
			LiveWindow.addActuator("drivetrain", name + " slave", slave);
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
				double maxSpeed = Robot.shifters.getGear() == Shifters.Gear.HIGH ? RobotMap.getInstance().DRIVE_MAX_SPEED_HIGH.get() : RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				speed *= maxSpeed;
			}
			
			if(Robot.debug()) {
				SmartDashboard.putNumber(name + " setpoint", speed);
			}
			
			// TODO: Implement switching between speed and percentvbus mode somewhere
			// 		 Most likely need to implement a failsafe if an encoder fails and
			//		 a manual control incase the failsafe also fails
			master.set(speed);
			setpoint = speed;
		}
		
		/**
		 * Sets the control mode of the master motor
		 * @param mode control mode, either Speed or PercentVbus
		 */
		public void setControlMode(TalonControlMode mode) {
			master.changeControlMode(mode);
			if (mode == TalonControlMode.Speed) {
				inSpeedControlMode = true;
				master.setInverted(false);
				master.reverseOutput(motorInverted);
				master.reverseSensor(encoderInverted);
			} else {
				inSpeedControlMode = false;
				master.setInverted(motorInverted);
				master.reverseOutput(false);
				master.reverseSensor(encoderInverted);
			}
		}
		
		/**
		 * Gets the control mode of the master motor
		 * @return control mode, either Speed or PercentVbus
		 */
		public TalonControlMode getControlMode() {
			return master.getControlMode();
		}
		
		/**
		 * Gets the pair's speed
		 * @return speed in inches/second
		 */
		public double getSpeed() {
			return master.getSpeed();
		}
		
		/**
		 * Get the last setpoint, in RPM.
		 * If the Talon is not in speed control mode, do a really sketchy conversion.
		 * @return the setpoint, in RPM
		 */
		public double getSetpoint() {
			if (getControlMode() == TalonControlMode.PercentVbus) {
				double maxSpeed = Robot.shifters.getGear() == Shifters.Gear.HIGH ? RobotMap.getInstance().DRIVE_MAX_SPEED_HIGH.get() : RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				return setpoint*maxSpeed;
			} else if (getControlMode() == TalonControlMode.Speed) {
				return setpoint;
			} else {
				assert false;
				return 0;
			}
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
			return ticksToInches(getEncoderPositionTicks());
		}
		
		public double getEncoderPositionTicks() {
			return encoderInverted ? -master.getEncPosition() + encoderOffset : master.getEncPosition() + encoderOffset;
		}
		
		/**
		 * Sets the encoder's current position
		 * @param position position in inches
		 */
		public void setEncoderPosition(int position) {
			encoderOffset += position - getEncoderPositionTicks();
		}
		
		/**
		 * Converts encoder ticks to inches
		 * @param encoder ticks
		 * @return inches
		 */
		private double ticksToInches(double ticks) {
			return ticks * RobotMap.getInstance().DRIVE_WHEEL_DIAMETER.get() * Math.PI / RobotMap.getInstance().DRIVE_TICKS_PER_REV.get();
		}
		
		/**
		 * Diagnose various sensors for the drive pair
		 */
		public void diagnose() {
			if (master.getBusVoltage() != 4.0) {
				if (master.getStickyFaultOverTemp() != 0) {
					Logger.defaultLogger.warn(name + " - front drivetrain motor has over-temperature sticky bit set.");
				}
				if (master.getStickyFaultUnderVoltage() != 0) {
					Logger.defaultLogger.warn(name + " - front drivetrain motor has under-voltage sticky bit set.");
				}
				if (RobotMap.getInstance().DRIVE_FEEDBACK_DEV.get() == FeedbackDevice.CtreMagEncoder_Relative) {
					if (master.isSensorPresent(RobotMap.getInstance().DRIVE_FEEDBACK_DEV
							.get()) != FeedbackDeviceStatus.FeedbackStatusPresent) {
						Logger.defaultLogger.warn(name + " - drivetrain encoder not present.");
					} else {
						Logger.defaultLogger.debug(name + " - drivetrain encoder is present.");
					}
				}
			} else {
				Logger.defaultLogger.warn(name + " - front drivetrain motor controller not reachable over CAN.");
				Logger.voice.warn("check drivetrain front " + name);
			}
			if (slave.getBusVoltage() != 4.0) {
				if (slave.getStickyFaultOverTemp() != 0) {
					Logger.defaultLogger.warn(name + " - rear drivetrain motor has over-temperature sticky bit set.");
				}
				if (slave.getStickyFaultUnderVoltage() != 0) {
					Logger.defaultLogger.warn(name + " - rear drivetrain motor has under-voltage sticky bit set.");
				}
			} else {
				Logger.defaultLogger.warn(name + " - rear drivetrain motor controller not reachable over CAN.");
				Logger.voice.warn("check drivetrain rear " + name);
			}
			if (!master.isAlive()) {
				Logger.defaultLogger.warn(name + " - front drivetrain motor is stopped by motor safety.");
			}
			if (!slave.isAlive()) {
				Logger.defaultLogger.warn(name + " - rear drivetrain motor is stopped by motor safety.");
			}
		}
		
		public void clearStickyFaults() {
			master.clearStickyFaults();
			slave.clearStickyFaults();
		}
	}
	
	private DriveMotorPair left = new DriveMotorPair("Left",
												RobotMap.getInstance().CAN_DRIVE_MOTOR_LEFT_MASTER.get(),
												RobotMap.getInstance().CAN_DRIVE_MOTOR_LEFT_SLAVE.get(),
												RobotMap.getInstance().DRIVE_LEFT_MOTOR_INVERTED.get(),
												RobotMap.getInstance().DRIVE_LEFT_ENC_INVERTED.get());
	
	private DriveMotorPair right= new DriveMotorPair("Right",
												RobotMap.getInstance().CAN_DRIVE_MOTOR_RIGHT_MASTER.get(),
												RobotMap.getInstance().CAN_DRIVE_MOTOR_RIGHT_SLAVE.get(),
												RobotMap.getInstance().DRIVE_RIGHT_MOTOR_INVERTED.get(),
												RobotMap.getInstance().DRIVE_RIGHT_ENC_INVERTED.get());
	
	/**
	 * Constructor
	 */
	public SRXDrive() {
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
		if(Robot.debug()) {
			SmartDashboard.putNumber("Left Drivetrain Encoder Position (ticks)", left.getEncoderPositionTicks());
			SmartDashboard.putNumber("Right Drivetrain Encoder Position (ticks)", right.getEncoderPositionTicks());
			SmartDashboard.putNumber("Left Velocity Error", left.getClosedLoopError());
			SmartDashboard.putNumber("Right Velocity Error", right.getClosedLoopError());
		}
	}
	
	/**
	 * Get average encoder position
	 * @return average position of both encoders in inches
	 */
	public double getAverageEncoderPosition() {
		double value = (left.getEncoderPosition() + right.getEncoderPosition()) / 2;
		SmartDashboard.putNumber("Encoder averages (inches)", value);
		
		return value;
	}
	
	/**
	 * Get left encoder position
	 * @return position of left encoder in inches
	 */
	public double getLeftEncoderPosition() {
		return left.getEncoderPosition();
	}
	
	/**
	 * Get right encoder position
	 * @return position of right encoder in inches
	 */
	public double getRightEncoderPosition() {
		return right.getEncoderPosition();
	}
	
	/**
	 * Reset encoders
	 */
	public void zeroEncoderPositions() {
		left.setEncoderPosition(0);
		right.setEncoderPosition(0);
	}
	
	/**
	 * Get the average of the last setpoints, in RPM.
 	 * If the Talons are not in speed control mode, do a really sketchy conversion.
	 * @return the average of the setpoints
	 */
	public double getAverageSetpoint() {
		return (left.getSetpoint() + right.getSetpoint()) / 2;
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
	public void diagnose() {
		left.diagnose();
		right.diagnose();
	}
	
	public void clearStickyFaults() {
		left.clearStickyFaults();
		right.clearStickyFaults();
	}
	
	public double getLeftRPM() {
		return left.getSpeed();
	}
	
	public double getRightRPM() {
		return right.getSpeed();
	}
	
	public void enableCoast(boolean set) {
		left.enableCoast(set);
		right.enableCoast(set);
	}
}
