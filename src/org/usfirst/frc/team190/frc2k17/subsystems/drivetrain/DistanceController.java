package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.PIDController;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceController implements DriveController{
	
	SRXDrive srxdrive;
	private final PIDController distancePID;
	private double loopOutput = 0;
	private Instant onTargetSince;

	/**
	 * A source for the PID distance controller using values in inches
	 *
	 */
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

		/**
		 * Return the encoder positions in inches
		 */
		@Override
		public double pidGet() {
			return srxdrive.getAverageEncoderPosition();
		}
	}
	
	public DistanceController(SRXDrive drive) {
		srxdrive = drive;
		// the RobotMap PID values are only defaults
		distancePID = new PIDController(RobotMap.getInstance().DRIVE_PID_DISTANCE_KP.get(),
											RobotMap.getInstance().DRIVE_PID_DISTANCE_KI.get(),
											RobotMap.getInstance().DRIVE_PID_DISTANCE_KD.get(),
											new RobotDistanceSource(),
											output -> this.loopOutput = output);
		distancePID.setOutputRange(-RobotMap.getInstance().DRIVE_PID_DISTANCE_MAX.get(),
				RobotMap.getInstance().DRIVE_PID_DISTANCE_MAX.get());
		distancePID.setAbsoluteTolerance(RobotMap.getInstance().DRIVE_PID_DISTANCE_TOLERANCE.get());
		distancePID.setMaxErrorToIntegrate(RobotMap.getInstance().DRIVE_PID_DISTANCE_I_ERROR_LIMIT.get());
		// reset SmartDashboard values to the RobotMap values
		Robot.prefs.putDouble("Distance PID P", RobotMap.getInstance().DRIVE_PID_DISTANCE_KP.get());
		Robot.prefs.putDouble("Distance PID I", RobotMap.getInstance().DRIVE_PID_DISTANCE_KI.get());
		Robot.prefs.putDouble("Distance PID D", RobotMap.getInstance().DRIVE_PID_DISTANCE_KD.get());
		Robot.prefs.putDouble("Distance PID MAX", RobotMap.getInstance().DRIVE_PID_DISTANCE_MAX.get());
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getLoopOutput() {
		if(Robot.debug()) {
			SmartDashboard.putNumber("Distance PID loop output", loopOutput);
		}
		if (distancePID.onTarget()) {
			if (onTargetSince == null) {
				onTargetSince = Instant.now();
				Logger.defaultLogger.trace("Distance PID is on target. Waiting for " + RobotMap.getInstance().DRIVE_PID_DIST_WAIT.get() + " milliseconds.");
			}
		} else if (onTargetSince != null){
			onTargetSince = null;
			Logger.defaultLogger.trace("Distance PID is *no longer* on target.");
		}
		return loopOutput;
	}
	
	/**
	 * Enables the distance control loop and resets the encoder positions to zero
	 * @param inches the distance to drive in inches
	 */
	public void enable(double inches) {
		srxdrive.zeroEncoderPositions();
		getSmartDashboardPidValues();
		loopOutput = 0;
		// reset *before* enabling
		distancePID.reset();
		distancePID.setSetpoint(inches);
		distancePID.enable();
		Logger.defaultLogger.debug("Distance PID enabled.");
	}
	
	/**
	 * Disables the distance control loop
	 */
	public void disable() {
		distancePID.disable();
		if(Robot.debug()) {
			SmartDashboard.putNumber("Distance PID loop output", 0);
		}
		Logger.defaultLogger.debug("Distance PID disabled.");
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return (distancePID.onTarget() && onTargetSince != null && Duration.between(onTargetSince, Instant.now())
				.compareTo(Duration.ofMillis(500)) > 0);
	}
	
	/**
	 * Get and use PID values from SmartDashboard.
	 */
	public void getSmartDashboardPidValues() {
		distancePID.setPID(
				Robot.prefs.getDouble("Distance PID P", RobotMap.getInstance().DRIVE_PID_DISTANCE_KP.get()),
				Robot.prefs.getDouble("Distance PID I", RobotMap.getInstance().DRIVE_PID_DISTANCE_KI.get()),
				Robot.prefs.getDouble("Distance PID D", RobotMap.getInstance().DRIVE_PID_DISTANCE_KD.get()));
		distancePID.setOutputRange(
				-Robot.prefs.getDouble("Distance PID MAX", RobotMap.getInstance().DRIVE_PID_DISTANCE_MAX.get()),
				Robot.prefs.getDouble("Distance PID MAX", RobotMap.getInstance().DRIVE_PID_DISTANCE_MAX.get()));
	}
}
