package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

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
			return srxdrive.averageEncoderPositions();
		}
	}
	
	public DistanceController(SRXDrive drive) {
		srxdrive = drive;
		// the RobotMap PID values are only defaults
		distancePID = new PIDController(RobotMap.Constants.Drivetrain.PID.DISTANCE_KP,
											RobotMap.Constants.Drivetrain.PID.DISTANCE_KI,
											RobotMap.Constants.Drivetrain.PID.DISTANCE_KD,
											new RobotDistanceSource(),
											output -> this.loopOutput = output);
		distancePID.setOutputRange(-RobotMap.Constants.Drivetrain.PID.DISTANCE_MAX,
				RobotMap.Constants.Drivetrain.PID.DISTANCE_MAX);
		distancePID.setAbsoluteTolerance(RobotMap.Constants.Drivetrain.PID.DISTANCE_TOLERANCE);
		distancePID.setMaxErrorToIntegrate(RobotMap.Constants.Drivetrain.PID.DISTANCE_I_ERROR_LIMIT);
		// reset SmartDashboard values to the RobotMap values
		Robot.prefs.putDouble("Distance PID P", RobotMap.Constants.Drivetrain.PID.DISTANCE_KP);
		Robot.prefs.putDouble("Distance PID I", RobotMap.Constants.Drivetrain.PID.DISTANCE_KI);
		Robot.prefs.putDouble("Distance PID D", RobotMap.Constants.Drivetrain.PID.DISTANCE_KD);
		Robot.prefs.putDouble("Distance PID MAX", RobotMap.Constants.Drivetrain.PID.DISTANCE_MAX);
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getLoopOutput() {
		SmartDashboard.putNumber("Distance PID loop output", loopOutput);
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
		SmartDashboard.putNumber("Distance PID loop output", 0);
		Logger.defaultLogger.debug("Distance PID disabled.");
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return distancePID.onTarget();
	}
	
	/**
	 * Get and use PID values from SmartDashboard.
	 */
	public void getSmartDashboardPidValues() {
		distancePID.setPID(
				Robot.prefs.getDouble("Distance PID P", RobotMap.Constants.Drivetrain.PID.DISTANCE_KP),
				Robot.prefs.getDouble("Distance PID I", RobotMap.Constants.Drivetrain.PID.DISTANCE_KI),
				Robot.prefs.getDouble("Distance PID D", RobotMap.Constants.Drivetrain.PID.DISTANCE_KD));
		distancePID.setOutputRange(
				-Robot.prefs.getDouble("Distance PID MAX", RobotMap.Constants.Drivetrain.PID.DISTANCE_MAX),
				Robot.prefs.getDouble("Distance PID MAX", RobotMap.Constants.Drivetrain.PID.DISTANCE_MAX));
	}
}
