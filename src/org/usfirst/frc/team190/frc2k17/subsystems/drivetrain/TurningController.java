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
		turningPID = new PIDController(RobotMap.getInstance().DRIVE_PID_TURN1_KP.get(),
				RobotMap.getInstance().DRIVE_PID_TURN1_KI.get(), RobotMap.getInstance().DRIVE_PID_TURN1_KD.get(), navx,
				output -> this.loopOutput = output);
		turningPID.setMaxErrorToIntegrate(RobotMap.getInstance().DRIVE_PID_TURN_I_ERROR_LIMIT.get());
		// reset SmartDashboard values to the RobotMap values
		Robot.prefs.putDouble("Turning1 PID P", RobotMap.getInstance().DRIVE_PID_TURN1_KP.get());
		Robot.prefs.putDouble("Turning1 PID I", RobotMap.getInstance().DRIVE_PID_TURN1_KI.get());
		Robot.prefs.putDouble("Turning1 PID D", RobotMap.getInstance().DRIVE_PID_TURN1_KD.get());
		Robot.prefs.putDouble("Turning2 PID P", RobotMap.getInstance().DRIVE_PID_TURN2_KP.get());
		Robot.prefs.putDouble("Turning2 PID I", RobotMap.getInstance().DRIVE_PID_TURN2_KI.get());
		Robot.prefs.putDouble("Turning2 PID D", RobotMap.getInstance().DRIVE_PID_TURN2_KD.get());
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
	 * @param degrees degrees to turn
	 */
	public void enable(double degrees) {
		enable(degrees, degrees == 0);
	}

	/**
	 * Enables the control loop
	 * @param degrees degrees to turn
	 * @param values which set of PID values to use
	 */
	public void enable(double degrees, boolean values) {
		navx.reset();
		getSmartDashboardPidValues(values);
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
	 * @param values which set of values to use
	 */
	public void getSmartDashboardPidValues(boolean values) {
		if(values) {
			turningPID.setPID(
					Robot.prefs.getDouble("Turning2 PID P", RobotMap.getInstance().DRIVE_PID_TURN2_KP.get()),
					Robot.prefs.getDouble("Turning2 PID I", RobotMap.getInstance().DRIVE_PID_TURN2_KI.get()),
					Robot.prefs.getDouble("Turning2 PID D", RobotMap.getInstance().DRIVE_PID_TURN2_KD.get()));
		} else {
			turningPID.setPID(
				Robot.prefs.getDouble("Turning1 PID P", RobotMap.getInstance().DRIVE_PID_TURN1_KP.get()),
				Robot.prefs.getDouble("Turning1 PID I", RobotMap.getInstance().DRIVE_PID_TURN1_KI.get()),
				Robot.prefs.getDouble("Turning1 PID D", RobotMap.getInstance().DRIVE_PID_TURN1_KD.get()));
		}
	}
}
