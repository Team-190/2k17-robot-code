package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;

public class TurningController {

	private final SRXDrive srxdrive;
	private final PIDController turningControl;
	private AHRS navx = null;
	private final PIDOutput turningOutput;

	public TurningController(SRXDrive drive) {
		srxdrive = drive;

//		try {
			navx = new AHRS(SPI.Port.kMXP);
//		} catch (RuntimeException ex) {
//			Logger.defaultLogger.error("Error instantiating navX-MXP:  " + ex.getMessage());
//		}

		turningControl = new PIDController(
				RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP,
				RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD, navx,
				output -> srxdrive.arcadeDrive(0, -output));
		turningControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_TOLERANCE);
	}

	/**
	 * Checks if the turning control loop is on target
	 * 
	 * @return true if the loop is on target
	 */
	public boolean isTurnControlOnTarget() {
		return turningControl.onTarget();
	}

	public void turnToHeading(double degrees) {
		navx.reset();
		turningControl.setSetpoint(degrees);
		turningControl.enable();
	}
}
