package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.PIDController;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurningController implements DriveController {

	private final PIDController turningPID;
	private AHRS navx = null;

	private double loopOutput = 0;
	private Instant onTargetSince, enabledAt;
	
	public TurningController(AHRS navx) {
		this.navx = navx;
		// the RobotMap PID values are only defaults
		turningPID = new PIDController(RobotMap.getInstance().DRIVE_PID_TURN_KP.get(),
				RobotMap.getInstance().DRIVE_PID_TURN_KI.get(), RobotMap.getInstance().DRIVE_PID_TURN_KD.get(), navx,
				output -> this.loopOutput = output);
		turningPID.setMaxErrorToIntegrate(RobotMap.getInstance().DRIVE_PID_TURN_I_ERROR_LIMIT.get());
		// reset SmartDashboard values to the RobotMap values
		Robot.prefs.putDouble("Turning PID P", RobotMap.getInstance().DRIVE_PID_TURN_KP.get());
		Robot.prefs.putDouble("Turning PID I", RobotMap.getInstance().DRIVE_PID_TURN_KI.get());
		Robot.prefs.putDouble("Turning PID D", RobotMap.getInstance().DRIVE_PID_TURN_KD.get());
		turningPID.setAbsoluteTolerance(RobotMap.getInstance().DRIVE_PID_TURN_TOLERANCE.get());
	}

	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return (turningPID.onTarget() && onTargetSince != null && Duration.between(onTargetSince, Instant.now())
				.compareTo(Duration.ofMillis(500)) > 0);
	}
	
	/**
	 * Gets the output of the loop
	 * @return loop output
	 */
	public double getLoopOutput() {
		if(Robot.debug()) {
			SmartDashboard.putNumber("Turning PID loop output", loopOutput);
		}
		if (turningPID.onTarget()) {
			if (onTargetSince == null) {
				onTargetSince = Instant.now();
				Logger.defaultLogger.trace("Turning PID is on target. Waiting for " + RobotMap.getInstance().DRIVE_PID_TURN_WAIT.get() + " milliseconds.");
			}
		} else if (onTargetSince != null){
			onTargetSince = null;
			Logger.defaultLogger.trace("Turning PID is *no longer* on target.");
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
		loopOutput = 0;
		// reset *before* enabling
		turningPID.reset();
		turningPID.setSetpoint(degrees);
		turningPID.enable();
		Logger.defaultLogger.debug("Turning PID enabled.");
		enabledAt = Instant.now();
	}
	
	/**
	 * Disables the control loop
	 */
	public void disable() {
		turningPID.disable();
		if(Robot.debug()) {
			SmartDashboard.putNumber("Turning PID loop output", 0);
		}
		assert enabledAt != null;
		Logger.defaultLogger.debug("Turning PID disabled. Ran for " + Duration.between(enabledAt, Instant.now()).toMillis() + " milliseconds.");
	}
	
	/**
	 * Get and use PID values from SmartDashboard.
	 */
	public void getSmartDashboardPidValues() {
		turningPID.setPID(
				Robot.prefs.getDouble("Turning PID P", RobotMap.getInstance().DRIVE_PID_TURN_KP.get()),
				Robot.prefs.getDouble("Turning PID I", RobotMap.getInstance().DRIVE_PID_TURN_KI.get()),
				Robot.prefs.getDouble("Turning PID D", RobotMap.getInstance().DRIVE_PID_TURN_KD.get()));
	}
}
