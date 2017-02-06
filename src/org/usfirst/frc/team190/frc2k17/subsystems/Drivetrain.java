
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.SimplePIDOutput;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TankDriveCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
	
	private final DoubleSolenoid shifters;
	private final TurningController turningController;
	private final DistanceController distanceController;
	
	private final SRXDrive srxdrive;
	
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
		turningController = new TurningController(srxdrive);
		distanceController = new DistanceController(srxdrive);
		shifters = new DoubleSolenoid(RobotMap.PCM.SHIFTERS_SHIFT_HIGH, RobotMap.PCM.SHIFTERS_SHIFT_LOW);
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
	
	public void turnToHeading(double degrees) {
		turningController.turnToHeading(degrees);
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
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getDistanceControlLoopOutput() {
		return distanceController.getDistanceControlLoopOutput();
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
		distanceController.disableDistanceControl();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isDistanceControlOnTarget() {
		return distanceController.isDistanceControlOnTarget();
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
		distanceController.driveDistance(distance);
		srxdrive.zeroEncoderPositions();
	}
	
	public double inchesToTicks(double inches) {
		return inches / RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public double ticksToInches(double ticks) {
		return ticks * RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveCommand());
	}
}

