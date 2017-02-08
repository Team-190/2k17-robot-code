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
		distancePID = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD,
											new RobotDistanceSource(),
											output -> this.loopOutput = output);
		
		distancePID.setOutputRange(-RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW,
										RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW);
		distancePID.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_DIST_TOLERANCE);		
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
}
