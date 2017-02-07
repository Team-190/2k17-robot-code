
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TankDriveCommand;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
	
	private final DoubleSolenoid shifters;
	private final TurningController turningController;
	private final DriveController distanceController;
	
	private final SRXDrive srxdrive;
	private final AHRS navx;
	
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
		navx = new AHRS(SPI.Port.kMXP);
		
		turningController = new TurningController(navx);
		distanceController = new DistanceController(srxdrive);
		shifters = new DoubleSolenoid(RobotMap.PCM.SHIFTERS_SHIFT_HIGH, RobotMap.PCM.SHIFTERS_SHIFT_LOW);
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getDistanceControlLoopOutput() {
		return distanceController.getLoopOutput();
	}
	
	/**
	 * Enables the distance control loop and resets the encoder positions to zero
	 * @param distance the distance to drive
	 */
	public void enableDistanceControl(double distance) {
		distanceController.enable(distance);
	}
	
	/**
	 * Disables the distance control loop
	 */
	public void disableDistanceControl() {
		distanceController.disable();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isDistanceControlOnTarget() {
		return distanceController.isOnTarget();
	}
	
	/**
	 * Get the output of the turning PID loop
	 * @return Turning PID loop output
	 */
	public double getTurningControlLoopOutput() {
		return turningController.getLoopOutput();
	}
	
	/**
	 * Enables the turning control loop and resets the navx positions to zero
	 * @param angle the angle to turn in degrees
	 */
	public void enableTurningControl(double angle) {
		turningController.enable(angle);
	}
	
	/**
	 * Disables the turning control loop
	 */
	public void disableTurningControl() {
		turningController.disable();
	}
	
	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isTurningControlOnTarget() {
		return turningController.isOnTarget();
	}
	
	/**
	 * Drives the robot based off the driving control loop's output
	 */
	public void controlDistance() {
		arcadeDrive(getDistanceControlLoopOutput(), 0);
	}

	/**
	 * Drives the robot based off the turning control loop's output
	 */
	public void controlTurning() {
		arcadeDrive(0, getTurningControlLoopOutput());
	}
	
	/**
	 * Drives the robot based off the output of the turning and driving control loops
	 */
	public void controlTurningAndDistance() {
		arcadeDrive(getTurningControlLoopOutput(), getTurningControlLoopOutput());
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
	
	public static double inchesToTicks(double inches) {
		return inches / RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public static double ticksToInches(double ticks) {
		return ticks * RobotMap.Constants.DriveTrain.INCHES_PER_TICK;
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveCommand());
	}
	
	/**
	 * Swap turning PID values to the ones for maintaining heading while driving.
	 */
	public void enableMaintainHeadingPidValues() {
		turningController.enableMaintainHeading();
	}
	
	/**
	 * Swap turning PID values to the normal ones.
	 */
	public void disableMaintainHeadingPidValues() {
		turningController.disableMaintainHeading();
	}
}

