
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.SimplePIDOutput;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.ArcadeDriveCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TankDriveCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
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
	
	private AHRS navx = null;
	private final DoubleSolenoid shifters;
	
	private final PIDController turningControl;
	private final SimplePIDOutput turningOutput;
	private final PIDController distanceControl;
	private final SimplePIDOutput distanceOutput;
	private final SRXDrive srxdrive;
	
	private class RobotDistanceSource implements PIDSource {
		public RobotDistanceSource() {
			
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
			return srxdrive.averageEncoderPositions();

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
		srxdrive = new SRXDrive();
		shifters = new DoubleSolenoid(RobotMap.PCM.SHIFTERS_SHIFT_HIGH, RobotMap.PCM.SHIFTERS_SHIFT_LOW);
		
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
											new RobotDistanceSource(),
											distanceOutput);
		distanceControl.setOutputRange(-RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW,
										RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW);
		distanceControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_DIST_TOLERANCE);
		
		
		

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
		//leftFrontMotor.changeControlMode(TalonControlMode.Speed);
		//rightFrontMotor.changeControlMode(TalonControlMode.Speed);
	}
	
	/**
	 * Disable speed control
	 */
	public void disableSpeedControl() {
		//leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		//rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
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
	 * Drive each side of the robot individually
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		srxdrive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public void arcadeDrive(double moveValue, double rotateValue) {
		srxdrive.arcadeDrive(moveValue, rotateValue);
		
	}
	
	
	/**
	 * Drive each side of the robot using speed control
	 * @param leftSpeed left side speed in RPM
	 * @param rightSpeed right side speed in RPM
	 */
	public void tankDriveAtSpeed(double leftSpeed, double rightSpeed) {
		srxdrive.tankDriveRPM(leftSpeed, rightSpeed);
	}
	
	/**
	 * Send encoder values to SmartDashboard.
	 */
	
	public void outputEncoderValues() {
		srxdrive.outputEncoderValues();
	}
	
	/**
	 * 
	 * @param distance Distance to drive in inches
	 */
	public void driveDistance(double distance) {
		srxdrive.zeroEncoderPositions();
		double tickstoDrive = inchesToTicks(distance);
		SmartDashboard.putNumber("Goal encoder ticks", tickstoDrive);
		distanceControl.setSetpoint(tickstoDrive);
		distanceControl.enable();
	}
	
	private double inchesToTicks(double inches) {
		return inches / RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	private double ticksToInches(double ticks) {
		return ticks * RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveCommand());
	}
}

