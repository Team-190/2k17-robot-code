package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurningController implements DriveController {

	private final PIDController turningControl;
	private AHRS navx = null;
	
	private double loopOutput = 0;
	
	public TurningController(AHRS navx) {
		this.navx = navx;

		turningControl = new PIDController(
				RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP,
				RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD, navx,
				output -> this.loopOutput = output);
		
		turningControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_TOLERANCE);
	}

	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return turningControl.onTarget();
	}
	
	/**
	 * Gets the output of the loop
	 * @return loop output
	 */
	public double getLoopOutput() {
		SmartDashboard.putNumber("Turning PID loop output", loopOutput);
		return loopOutput;
	}

	/**
	 * Enables the control loop
	 * @param degrees
	 */
	public void enable(double degrees) {
		navx.reset();
		turningControl.setSetpoint(degrees);
		turningControl.enable();
	}
	
	/**
	 * Disables the control loop
	 */
	public void disable() {
		turningControl.disable();
		SmartDashboard.putNumber("Turning PID loop output", 0);
	}
	
	/**
	 * Swap PID values to the ones for maintaining heading while driving. 
	 */
	public void enableMaintainHeading() {
		turningControl.setPID(RobotMap.Constants.DriveTrain.DRIVE_PID_HEADING_CORRECTION_KP, RobotMap.Constants.DriveTrain.DRIVE_PID_HEADING_CORRECTION_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_HEADING_CORRECTION_KD);
	}
	
	/**
	 * Swap PID values back to the normal turning ones.
	 */
	public void disableMaintainHeading() {
		turningControl.setPID(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP, RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD);
	}
	
}
