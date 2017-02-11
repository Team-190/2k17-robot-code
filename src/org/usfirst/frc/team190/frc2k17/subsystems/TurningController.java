package org.usfirst.frc.team190.frc2k17.subsystems;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurningController implements DriveController {

	private final PIDController turningPID;
	private AHRS navx = null;

	private double loopOutput = 0;
	private Instant onTargetSince;
	
	public TurningController(AHRS navx) {
		this.navx = navx;
		// the RobotMap PID values are only defaults
		turningPID = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP,
				RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD, navx,
				output -> this.loopOutput = output);
		Robot.prefs.putDouble("Turning PID P", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP);
		Robot.prefs.putDouble("Turning PID I", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI);
		Robot.prefs.putDouble("Turning PID D", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD);
		turningPID.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_TOLERANCE);
		// set the real PID values
		getSmartDashboardPidValues();
	}

	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return (turningPID.onTarget() && Duration.between(onTargetSince, Instant.now())
				.compareTo(Duration.ofMillis(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_WAIT)) > 0);
	}
	
	/**
	 * Gets the output of the loop
	 * @return loop output
	 */
	public double getLoopOutput() {
		SmartDashboard.putNumber("Turning PID loop output", loopOutput);
		if (turningPID.onTarget()) {
			if (onTargetSince == null) {
				onTargetSince = Instant.now();
			}
		} else {
			onTargetSince = null;
		}
		return -loopOutput;
	}

	/**
	 * Enables the control loop
	 * @param degrees
	 */
	public void enable(double degrees) {
		navx.reset();
		getSmartDashboardPidValues();
		turningPID.setSetpoint(degrees);
		turningPID.enable();
	}
	
	/**
	 * Disables the control loop
	 */
	public void disable() {
		turningPID.disable();
		SmartDashboard.putNumber("Turning PID loop output", 0);
	}
	
	/**
	 * Get and use PID values from SmartDashboard.
	 */
	public void getSmartDashboardPidValues() {
		turningPID.setPID(
				Robot.prefs.getDouble("Turning PID P", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP),
				Robot.prefs.getDouble("Turning PID I", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI),
				Robot.prefs.getDouble("Turning PID D", RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD));
	}
}
