package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.PIDController;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderDifferenceController implements DriveController{
	
	private SRXDrive srxdrive;
	private final PIDController encoderDiffPID;
	private double loopOutput = 0;

	/**
	 * A source for the PID encoder difference controller using values in inches
	 */
	private class RobotEncoderDifferenceSource implements PIDSource {
		public RobotEncoderDifferenceSource() {
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
			return srxdrive.getRightEncoderPosition() - srxdrive.getLeftEncoderPosition();
		}
	}
	
	public EncoderDifferenceController(SRXDrive drive) {
		srxdrive = drive;
		// the RobotMap PID values are only defaults
		encoderDiffPID = new PIDController(RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KP.get(),
											RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KI.get(),
											RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KD.get(),
											new RobotEncoderDifferenceSource(),
											output -> this.loopOutput = output);
		encoderDiffPID.setOutputRange(-1, 1);
		encoderDiffPID.setAbsoluteTolerance(RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_TOLERANCE.get());
		encoderDiffPID.setMaxErrorToIntegrate(RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_I_ERROR_LIMIT.get());
		// reset SmartDashboard values to the RobotMap values
		Robot.prefs.putDouble("Encoder difference PID P", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KP.get());
		Robot.prefs.putDouble("Encoder difference PID I", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KI.get());
		Robot.prefs.putDouble("Encoder difference PID D", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KD.get());
	}
	
	/**
	 * Get the output of the distance PID loop
	 * @return Distance PID loop output
	 */
	public double getLoopOutput() {
		if(Robot.debug()) {
			SmartDashboard.putNumber("Encoder difference PID loop output", loopOutput);
		}
		return loopOutput;
	}
	
	/**
	 * Enables the encoder difference control loop
	 * @param inches the desired difference in inches
	 */
	public void enable(double inches) {
		srxdrive.zeroEncoderPositions();
		getSmartDashboardPidValues();
		loopOutput = 0;
		// reset *before* enabling
		encoderDiffPID.reset();
		encoderDiffPID.setSetpoint(inches);
		encoderDiffPID.enable();
		Logger.defaultLogger.debug("Encoder difference PID enabled.");
	}
	
	/**
	 * Disables the encoder difference control loop
	 */
	public void disable() {
		encoderDiffPID.disable();
		if(Robot.debug()) {
			SmartDashboard.putNumber("Encoder difference PID loop output", 0);
		}
		Logger.defaultLogger.debug("Encoder difference PID disabled.");
	}
	
	/**
	 * Checks if the encoder difference control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isOnTarget() {
		return encoderDiffPID.onTarget();
	}
	
	/**
	 * Get and use PID values from SmartDashboard.
	 */
	public void getSmartDashboardPidValues() {
		encoderDiffPID.setPID(
				Robot.prefs.getDouble("Encoder difference PID P", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KP.get()),
				Robot.prefs.getDouble("Encoder difference PID I", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KI.get()),
				Robot.prefs.getDouble("Encoder difference PID D", RobotMap.getInstance().DRIVE_PID_ENCODERDIFF_KD.get()));
	}
}
