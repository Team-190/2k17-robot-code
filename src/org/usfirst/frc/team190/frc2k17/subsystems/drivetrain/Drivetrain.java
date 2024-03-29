
package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TankDriveCommand;

import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
	
	private DriveController turningController, encoderDiffController;
	private final DriveController distanceController, leftDistanceController, rightDistanceController;
	
	private final SRXDrive srxdrive;
	private AHRS navx;
	
	
	/**
	 * Constructor initializes private fields.
	 */
	public Drivetrain(){
		srxdrive = new SRXDrive();
		navx = new AHRS(SPI.Port.kMXP);
		distanceController = new DistanceController(srxdrive);
		leftDistanceController = new LeftDistanceController(srxdrive);
		rightDistanceController = new RightDistanceController(srxdrive);
		turningController = new TurningController(navx);
		encoderDiffController = new EncoderDifferenceController(srxdrive);
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
	 * Enables the left distance control loop
	 * @param distance the distance to drive
	 */
	public void enableLeftDistanceControl(double distance) {
		leftDistanceController.enable(distance);
	}
	
	/**
	 * Disables the left distance control loop
	 */
	public void disableLeftDistanceControl() {
		leftDistanceController.disable();
	}
	
	/**
	 * Enables the right distance control loop
	 * @param distance the distance to drive
	 */
	public void enableRightDistanceControl(double distance) {
		rightDistanceController.enable(distance);
	}
	
	/**
	 * Disables the right distance control loop
	 */
	public void disableRightDistanceControl() {
		rightDistanceController.disable();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isDistanceControlOnTarget() {
		return distanceController.isOnTarget();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isLeftDistanceControlOnTarget() {
		return leftDistanceController.isOnTarget();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isRightDistanceControlOnTarget() {
		return rightDistanceController.isOnTarget();
	}
			
	/**
	 * Enables the encoder difference control loop
	 * @param inches the desired difference in inches
	 */
	public void enableEncoderDiffControl(double inches) {
		encoderDiffController.enable(inches);
	}
	
	/**
	 * Disables the encoder difference control loop
	 */
	public void disableEncoderDiffControl() {
		encoderDiffController.disable();
	}
	
	/**
	 * Checks if the encoder difference control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isEncoderDiffControlOnTarget() {
		return encoderDiffController.isOnTarget();
	}
	
	
	/**
	 * Enables the turning control loop and resets the navx positions to zero
	 * @param angle the angle to turn in degrees
	 */
	public void enableTurningControl(double angle) {
		if(isNavxPresent()) {
			if(Robot.debug()) {
				SmartDashboard.putNumber("Degrees to turn", angle);
			}
			turningController.enable(angle);
		} else {
			Logger.defaultLogger.severe("NavX not connected! Not enabling turning control.");
		}
	}
	
	/**
	 * Disables the turning control loop
	 */
	public void disableTurningControl() {
		if(turningController != null) {
			turningController.disable();
		}
	}
	
	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isTurningControlOnTarget() {
		if(isNavxPresent()) {
			return turningController.isOnTarget();
		} else {
			return true;
		}
	}
	
	/**
	 * Drives the robot based off the driving control loop's output
	 */
	public void controlDistance() {
		arcadeDrive(distanceController.getLoopOutput(), 0);
	}
	
	/**
	 * Drives the robot based off the driving control loop's output
	 * @param rotation the rotation value for arcade drive
	 */
	public void controlDistance(double rotation) {
		arcadeDrive(distanceController.getLoopOutput(), rotation);
	}
	
	/**
	 * Drives the robot based off the driving control loop's output
	 * @param rotation the rotation value for arcade drive
	 * @param speedLimit the maximum speed to drive
	 */
	public void controlDistance(double rotation, double speedLimit) {
		arcadeDrive(Math.min(distanceController.getLoopOutput(), speedLimit), rotation);
	}
	
	/**
	 * Drive the left and right sides of the drivetrain independently based on the separate left and right distance control loops
	 */
	public void controlLeftRightDistance() {
		tankDrive(leftDistanceController.getLoopOutput(), rightDistanceController.getLoopOutput());
	}
	
	/**
	 * Drives the robot based off the turning control loop's output
	 */
	public void controlTurning() {
		if(isNavxPresent()) {
			arcadeDrive(0, turningController.getLoopOutput());
		}
	}
	
	/**
	 * Drives the robot based off the output of the turning and driving control loops
	 */
	public void controlTurningAndDistance() {
		controlTurningAndDistance(1);
	}
	
	/**
	 * Drives the robot based off the output of the turning and driving control loops
	 * @param speedLimit the maximum speed of the robot
	 */
	public void controlTurningAndDistance(double speedLimit) {
		if(isNavxPresent()) {
			arcadeDrive(Math.min(distanceController.getLoopOutput(), speedLimit), turningController.getLoopOutput());
		} else {
			controlDistance(0);
		}
	}
	
	/**
	 * Drives the robot based off the output of the encoder difference and driving control loops
	 * @param speedLimit the maximum speed of the robot
	 */
	public void controlEncoderDiffAndDistance(double speedLimit) {
		arcadeDrive(Math.min(distanceController.getLoopOutput(), speedLimit), encoderDiffController.getLoopOutput());
	}
	
	/**
	 * Drives the robot based off the output of the turning, encoder difference, and driving control loops
	 * @param speedLimit the maximum speed of the robot
	 */
	public void controlTurningAndEncoderDiffAndDistance(double speedLimit) {
		if(isNavxPresent()) {
			arcadeDrive(Math.min(distanceController.getLoopOutput(), speedLimit), turningController.getLoopOutput() + encoderDiffController.getLoopOutput());
		} else {
			controlEncoderDiffAndDistance(speedLimit);
		}
	}
	
	public boolean isMoving() {
		if(isNavxPresent()) {
			return navx.isMoving();
		} else {
			return false;
		}
	}
	
	/**
	 * Drive each side of the robot individually
	 * @param leftSpeed (-1 to 1) the left speed of the robot
	 * @param rightSpeed (-1 to 1) the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		srxdrive.tankDrive(leftSpeed, rightSpeed);
	}
	
	/**
	 * Drive with arcade drive
	 * @param moveValue the speed to drive (-1 to 1)
	 * @param rotateValue the amount of turn (-1 to 1)
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		srxdrive.arcadeDrive(moveValue, rotateValue);
	}
	
	/**
	 * Send encoder values to SmartDashboard.
	 */
	public void outputEncoderValues() {
		srxdrive.outputEncoderValues();
	}
	
	/**
	 * Get the average of the last setpoints, in RPM.
	 * @return the average of the setpoints
	 */
	public double getAverageSetpoint() {
		return srxdrive.getAverageSetpoint();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TankDriveCommand());
	}
	
	public double getLeftRPM() {
		return srxdrive.getLeftRPM();
	}
	
	public double getRightRPM() {
		return srxdrive.getRightRPM();
	}
	
	/**
	 * Set the control mode, either speed or percent vbus.
	 * @param mode the mode to change to
	 */
	public void setControlMode(TalonControlMode mode) {
		srxdrive.setControlMode(mode);
	}
	
	public void zeroEncoderPositions() {
		srxdrive.zeroEncoderPositions();
	}
	
	public double getLeftEncoderPosition() {
		return srxdrive.getLeftEncoderPosition();
	}
	
	public double getRightEncoderPosition() {
		return srxdrive.getRightEncoderPosition();
	}
	
	public void enableCoast(boolean set) {
		srxdrive.enableCoast(set);
	}
	
	public void diagnose() {
		srxdrive.diagnose();
		
		if (navx == null) {
			Logger.defaultLogger.info("NavX is null, not checking.");
		} else {
			Logger.defaultLogger.info("Checking NavX, please wait...");
			Robot.resetNavxErrorCount();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// do nothing
			}
			if (Robot.getNavxErrorCount() == 0) {
				Logger.defaultLogger.info("NavX is present.");
			} else {
				Logger.defaultLogger.warn("NavX is not connected.");
				navx.free();
				navx = null;
				turningController = null;
				Logger.voice.warn("check Nav-X");
			}
		}
	}
	
	public void clearStickyFaults() {
		srxdrive.clearStickyFaults();
	}
	
	public boolean isNavxPresent() {
		return navx != null;
    }
}

