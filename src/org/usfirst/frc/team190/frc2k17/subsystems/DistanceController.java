package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.SimplePIDOutput;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceController {
	
	SRXDrive srxdrive;
	private final PIDController distancePID;
	private final SimplePIDOutput distanceOutput;

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
			return srxdrive.averageEncoderPositions();
		}
	}
	
	public DistanceController(SRXDrive drive) {
		srxdrive = drive;
		distanceOutput = new SimplePIDOutput();
		distancePID = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI,
											RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD,
											new RobotDistanceSource(),
											distanceOutput);
		distancePID.setOutputRange(-RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW,
										RobotMap.Constants.DriveTrain.DRIVE_MAX_SPEED_LOW);
		distancePID.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_DIST_TOLERANCE);		
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getDistanceControlLoopOutput() {
		return distanceOutput.getPidOutput();
	}
	
	/**
	 * Enables the distance control loop and resets the encoder positions to zero
	 * @param distance the distance to drive
	 */
	public void enableDistanceControl(double distance) {
	}
	
	/**
	 * Disables the distance control loop
	 */
	public void disableDistanceControl() {
		distancePID.disable();
	}
	
	/**
	 * Checks if the distance control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isDistanceControlOnTarget() {
		return distancePID.onTarget();
	}
	
	public void driveDistance(double inches) {
		srxdrive.zeroEncoderPositions();
		double tickstoDrive = Robot.drivetrain.inchesToTicks(inches);
		SmartDashboard.putNumber("Goal encoder ticks", tickstoDrive);
		distancePID.setSetpoint(tickstoDrive);
		distancePID.enable();
	}
}
