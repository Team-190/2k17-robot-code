
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.SimplePIDOutput;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.ArcadeDriveCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
	
	private final CANTalon leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor;
	private AHRS navx = null;
	private final DoubleSolenoid shifters;
	
	private final PIDController turningControl;
	private final SimplePIDOutput turningOutput;
	private final PIDController distanceControl;
	private final SimplePIDOutput distanceOutput;
	
	private class RobotDistanceSource implements PIDSource {
		
		CANTalon leftTalon, rightTalon;
		
		public RobotDistanceSource(CANTalon left, CANTalon right) {
			leftTalon = left;
			rightTalon = right;
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			double value = (leftTalon.getEncPosition() + rightTalon.getEncPosition()) / 2;
			value *= 0.0123; // convert encoder ticks to inches (using 1024 ticks / rev and 4 inch wheels)
			SmartDashboard.putNumber("Encoder averages (inches)", value);
			return value;
		}
		
	}
	
	/**
	 * The gears that the transmission may be shifted into.
	 */
	public enum Gear {
		HIGH, LOW
	}
	
	/**
	 * Constructor initializes private fields.
	 */
	public Drivetrain(){
		
		leftFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_FRONT);
		leftRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_REAR);
		rightFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_FRONT);
		rightRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_REAR);
		
		shifters = new DoubleSolenoid(RobotMap.PCM.SHIFTERS_SHIFT_HIGH, RobotMap.PCM.SHIFTERS_SHIFT_LOW);
		
		leftFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftFrontMotor.reverseSensor(RobotMap.Constants.DriveTrain.INVERT_LEFT_ENC);
		leftFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		leftFrontMotor.configPeakOutputVoltage(+12.0f, -12.0f);
			
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightFrontMotor.reverseSensor(RobotMap.Constants.DriveTrain.INVERT_RIGHT_ENC);
		rightFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		rightFrontMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		
		// put the left front motor in speed mode
		//leftFrontMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
		leftFrontMotor.set(0);
		
		// put the right front motor in speed mode
		//rightFrontMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
		rightFrontMotor.set(0);
		
		// put left rear motor in slave mode, following the left front motor
		leftRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		
		// put right rear motor in slave mode, following the right front motor
		rightRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		leftFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_LEFT_INVERTED);
		rightFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_RIGHT_INVERTED);
		
		leftFrontMotor.setProfile(0);
		leftFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		leftFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		leftFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		leftFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		// TODO: Tune sides individually.
		// Set one side to follower mode and turn the other to a proper speed
		// on the ground, then set the other side to speed control and tune
		// it with both on the same setpoint
		rightFrontMotor.setProfile(0);
		rightFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		rightFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		rightFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KP);
		rightFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		try {
			navx = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex ) {
			Logger.defaultLogger.error("Error instantiating navX-MXP:  " + ex.getMessage());
		}
		
		turningOutput = new SimplePIDOutput();
		turningControl = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP,
										   RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, 
										   RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD, 
										   navx,
										   turningOutput);
		
		turningControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_TOLERANCE);
		
		distanceOutput = new SimplePIDOutput();
		distanceControl = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD,
											new RobotDistanceSource(leftFrontMotor, rightFrontMotor),
											distanceOutput);
		distanceControl.setOutputRange(-RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW,
										RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW);
		distanceControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_DIST_TOLERANCE);
		
		diagnose();
		
		// Setup Live Window
		LiveWindow.addActuator("Drive Train", "Left Motor Master", leftFrontMotor);
		LiveWindow.addActuator("Drive Train", "Left Motor Follower", leftRearMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Master", rightFrontMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Follower", rightRearMotor);
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
	 * Enable speed control
	 */
	public void enableSpeedControl() {
		leftFrontMotor.changeControlMode(TalonControlMode.Speed);
		rightFrontMotor.changeControlMode(TalonControlMode.Speed);
	}
	
	/**
	 * Disable speed control
	 */
	public void disableSpeedControl() {
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/**
	 * Get the output of the turning PID loop
	 * @return Turning PID loop output
	 */
	public double getTurningControlLoopOutput() {
		return turningOutput.getPidOutput();
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getDistanceControlLoopOutput() {
		return distanceOutput.getPidOutput();
	}
	
	/**
	 * Get's the current heading from the NavX MXP
	 * @return heading in degrees
	 */
	public double getNavxHeading() {
		return navx.getAngle();
	}
	
	/**
	 * Enables the turning control loop and resets the NAVX heading
	 * @param angle angle to turn to in degrees
	 */
	public void enableTurnControl(double angle) {
		navx.reset();
		
		turningControl.setSetpoint(angle);
		turningControl.enable();
	}
	
	/**
	 * Disables the turning control loop
	 */
	public void disableTurnControl() {
		turningControl.disable();
	}
	
	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isTurnControlOnTarget() {
		return turningControl.onTarget();
	}
	
	/**
	 * Enables the distance control loop and resets the encoder positions to zero
	 * @param distance the distance to drive
	 */
	public void enableDistanceControl(double distance) {
		leftFrontMotor.setPosition(0);
		rightFrontMotor.setPosition(0);
		
		distanceControl.setSetpoint(distance);
		distanceControl.enable();
	}
	
	/**
	 * Disables the distance control loop
	 */
	public void disableDistanceControl() {
		distanceControl.disable();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isDistanceControlOnTarget() {
		return distanceControl.onTarget();
	}
	
	/**
	 * Shift the transmission into the specified gear.
	 * @param gear the gear to shift into
	 */
	public void shift(Gear gear){
		
		if(gear == Gear.HIGH){
			shifters.set(Value.kForward);
		}else if(gear == Gear.LOW){
			shifters.set(Value.kReverse);
		}
	}
	
	/**
	 * Drive the robot with a speed and rotational value
	 * @param moveValue the forward speed of the robot
	 * @param rotateValue the rotational value
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		moveValue = limit(moveValue);
		rotateValue = limit(rotateValue);
		
		if (leftFrontMotor.getControlMode() == TalonControlMode.PercentVbus &&
			rightFrontMotor.getControlMode() == TalonControlMode.PercentVbus) {
			
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
		    
		    leftFrontMotor.set(leftMotorSpeed);
		    rightFrontMotor.set(rightMotorSpeed);
			
		} else {
			leftFrontMotor.set(0);
			rightFrontMotor.set(0);
		}
		
		SmartDashboard.putNumber("Left Speed", leftFrontMotor.get());
		SmartDashboard.putNumber("Right Speed", rightFrontMotor.get());
	}
	
	/**
	 * Drive each side of the robot individually
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		leftSpeed = limit(leftSpeed);
		rightSpeed = limit(rightSpeed);
		
		if (leftFrontMotor.getControlMode() == TalonControlMode.PercentVbus &&
			rightFrontMotor.getControlMode() == TalonControlMode.PercentVbus) {
			leftFrontMotor.set(leftSpeed);
			rightFrontMotor.set(rightSpeed);
		} else {
			leftFrontMotor.set(0);
			rightFrontMotor.set(0);
		}
		
		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);
	}
	
	/**
	 * Drive each side of the robot using speed control
	 * @param leftSpeed left side speed in RPM
	 * @param rightSpeed right side speed in RPM
	 */
	public void tankDriveAtSpeed(double leftSpeed, double rightSpeed) {
		if (leftFrontMotor.getControlMode() == TalonControlMode.Speed &&
			rightFrontMotor.getControlMode() == TalonControlMode.Speed) {
			leftFrontMotor.set(leftSpeed);
			rightFrontMotor.set(rightSpeed);
		} else {
			leftFrontMotor.set(0);
			rightFrontMotor.set(0);
		}
	}
	
	/**
	 * Send encoder values to SmartDashboard.
	 */
	public void outputEncoderValues() {
		SmartDashboard.putNumber("Left Drivetrain Encoder Velocity", leftFrontMotor.getSpeed());
		SmartDashboard.putNumber("Right Drivetrain Encoder Velocity", rightFrontMotor.getSpeed());
		SmartDashboard.putNumber("Left Velocity Error", leftFrontMotor.getClosedLoopError());
		SmartDashboard.putNumber("Right Velocity Error", rightFrontMotor.getClosedLoopError());
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
	
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveCommand());
	}
}

