
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.ArcadeDriveCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem implements PIDOutput {
	
	private final CANTalon leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor;
	private final RobotDrive driveController;
	private AHRS navx = null;
	private final DoubleSolenoid shifters;
	
	private final PIDController turningControl;
	private final PIDController distanceControl;
	
	/**
	 * The gears that the transmission may be shifted into.
	 */
	public enum Gear {
		HIGH, LOW
	}
	
	/**
	 * Constructor initializes private fields.
	 */
	public Drivetrain(){
		
		leftFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_FRONT);
		leftRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_REAR);
		rightFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_FRONT);
		rightRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_REAR);
		
		shifters = new DoubleSolenoid(RobotMap.PCM.SHIFTERS_SHIFT_HIGH, RobotMap.PCM.SHIFTERS_SHIFT_LOW);
		
		leftFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftFrontMotor.configEncoderCodesPerRev(256);
		leftFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		leftFrontMotor.configPeakOutputVoltage(+12.0f, 0.0f);
		
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightFrontMotor.configEncoderCodesPerRev(256);
		rightFrontMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		rightFrontMotor.configPeakOutputVoltage(+12.0f, 0.0f);
		
		// put left rear motor in slave mode, following the left front motor
		leftRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		
		// put right rear motor in slave mode, following the right front motor
		rightRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		leftFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_LEFT_INVERTED);
		rightFrontMotor.setInverted(RobotMap.Constants.DriveTrain.DRIVE_RIGHT_INVERTED);
		
		leftFrontMotor.setProfile(0);
		leftFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		leftFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		leftFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		leftFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		rightFrontMotor.setProfile(0);
		rightFrontMotor.setP(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KD);
		rightFrontMotor.setI(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KI);
		rightFrontMotor.setD(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KP);
		rightFrontMotor.setF(RobotMap.Constants.DriveTrain.DRIVE_PID_SPEED_KF);
		
		try {
			navx = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex ) {
			Logger.defaultLogger.error("Error instantiating navX-MXP:  " + ex.getMessage());
		}
		
		turningControl = new PIDController(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KP,
										   RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KI, 
										   RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_KD, 
										   navx, this);
		
		turningControl.setAbsoluteTolerance(RobotMap.Constants.DriveTrain.DRIVE_PID_TURN_TOLERANCE);
		
		distanceControl = null;
		
		diagnose();
		
		driveController = new RobotDrive(leftFrontMotor, rightFrontMotor);
		
		// Setup Live Window
		LiveWindow.addActuator("Drive Train", "Left Motor Master", leftFrontMotor);
		LiveWindow.addActuator("Drive Train", "Left Motor Follower", leftRearMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Master", rightFrontMotor);
		LiveWindow.addActuator("Drive Train", "Right Motor Follower", rightRearMotor);
	}
	
	/**
	 * Get's the current heading from the NavX MXP
	 * @return heading in degrees
	 */
	public double getNavxHeading() {
		return navx.getAngle();
	}
	
	/**
	 * Enables the turning control loop and resets the NAVX heading
	 * @param angle angle to turn to in degrees
	 */
	public void enableTurnControl(double angle) {
		navx.reset();
		
		turningControl.setSetpoint(angle);
		turningControl.enable();
	}
	
	/**
	 * Disables the turning control loop
	 */
	public void disableTurnControl() {
		turningControl.disable();
	}
	
	/**
	 * Checks if the turning control loop is on target
	 * @return true if the loop is on target
	 */
	public boolean isTurnControlOnTarget() {
		return turningControl.onTarget();
	}
	
	/**
	 * Shift the transmission into the specified gear.
	 * @param gear the gear to shift into
	 */
	public void shift(Gear gear){
		
		if(gear == Gear.HIGH){
			shifters.set(Value.kForward);
		}else if(gear == Gear.LOW){
			shifters.set(Value.kReverse);
		}
	}
	
	/**
	 * Drive the robot with a speed and rotational value
	 * @param speed the forward speed of the robot
	 * @param rotation the rotational value
	 */
	public void arcadeDrive(double speed, double rotation) {
		driveController.arcadeDrive(speed, rotation);
		
		SmartDashboard.putNumber("Left Speed", leftFrontMotor.get());
		SmartDashboard.putNumber("Right Speed", rightFrontMotor.get());
	}
	
	/**
	 * Drive each side of the robot individually
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		driveController.tankDrive(leftSpeed, rightSpeed);
		
		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);
	}
	
	/**
	 * Perform health checks and log warnings.
	 */
	private void diagnose() {
		if (leftFrontMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Left front drivetrain motor has over-temperature sticky bit set.");
		}
		if (leftFrontMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Left front drivetrain motor has under-voltage sticky bit set.");
		}
		if (leftRearMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Left rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (leftRearMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Left rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (rightFrontMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Right front drivetrain motor has over-temperature sticky bit set.");
		}
		if (rightFrontMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Right front drivetrain motor has under-voltage sticky bit set.");
		}
		if (rightRearMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Right rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (rightRearMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Right rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (!leftFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Left front drivetrain motor is stopped by motor safety.");
		}
		if (!leftRearMotor.isAlive()) {
			Logger.defaultLogger.warn("Left rear drivetrain motor is stopped by motor safety.");
		}
		if (!rightFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Right front drivetrain motor is stopped by motor safety.");
		}
		if (!rightFrontMotor.isAlive()) {
			Logger.defaultLogger.warn("Right rear drivetrain motor is stopped by motor safety.");
		}
		if (leftFrontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Left drivetrain encoder not present.");
		}
		if (rightFrontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn("Right drivetrain encoder not present.");
		}
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveCommand());
	}

	@Override
	public void pidWrite(double output) {
		// TODO: Create a more robust way to use the outputs of both PID loops
		 if (turningControl.isEnabled()) {
			 this.arcadeDrive(0, output);
		 } else if (distanceControl.isEnabled()) {
			 this.arcadeDrive(output, 0);
		 }
	}
}

