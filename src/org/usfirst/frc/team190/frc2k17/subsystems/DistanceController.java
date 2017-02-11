package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceController implements DriveController {
	
	SRXDrive srxdrive;
	private final PIDController distancePID;
	private double loopOutput = 0;

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
			return Drivetrain.ticksToInches(srxdrive.averageEncoderPositions());
		}
	}
	
	public DistanceController(SRXDrive drive) {
		srxdrive = drive;
		// the RobotMap PID values are only defaults
		distancePID = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD,
											new RobotDistanceSource(),
											output -> this.loopOutput = output);
		Robot.prefs.putDouble("Distance PID P", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP);
		Robot.prefs.putDouble("Distance PID I", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI);
		Robot.prefs.putDouble("Distance PID D", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD);
		distancePID.setOutputRange(-RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_MAX,
				RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_MAX);
		distancePID.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_DIST_TOLERANCE);
		// set the real PID values
		getSmartDashboardPidValues();
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
	 * @param distance the distance to drive in inches
	 */
	public void enable(double distance) {
		srxdrive.zeroEncoderPositions();
		getSmartDashboardPidValues();
		loopOutput = 0;
		// reset *before* enabling
		distancePID.reset();
		distancePID.setSetpoint(distance);
		distancePID.enable();
	}
	
	/**
	 * Disables the distance control loop
	 */
	public void disable() {
		distancePID.disable();
		SmartDashboard.putNumber("Distance PID loop output", 0);
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
				Robot.prefs.getDouble("Distance PID P", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP),
				Robot.prefs.getDouble("Distance PID I", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI),
				Robot.prefs.getDouble("Distance PID D", RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD));
	}
}
