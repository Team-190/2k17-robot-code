
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
	
	private final CANTalon leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor;
	private final RobotDrive driveController;
	
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
		
		leftFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		// put left rear motor in slave mode, following the left front motor
		leftRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		
		// put right rear motor in slave mode, following the right front motor
		rightRearMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		diagnose();
		
		// TODO Temporary code for use on 2k14 test-bot. Pass in proper CANTalons for actual robot
		driveController = new RobotDrive(0, 2, 1, 3);
	}
	
	/**
	 * Shift the transmission into the specified gear.
	 * @param gear the gear to shift into
	 */
	public void shift(Gear gear){
		// TODO: Implement gear shifting
	}
	
	/**
	 * Drive the robot with a speed and rotational value
	 * @param speed the forward speed of the robot
	 * @param rotation the rotational value
	 */
	public void arcadeDrive(double speed, double rotation) {
		driveController.arcadeDrive(speed, rotation);
	}
	
	/**
	 * Drive each side of the robot individually
	 * @param leftSpeed the left speed of the robot
	 * @param rightSpeed the right speed of the robot
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		driveController.tankDrive(leftSpeed, rightSpeed);
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
			Logger.defaultLogger.severe("Left drivetrain encoder not present.");
		}
		if (rightFrontMotor.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.severe("Right drivetrain encoder not present.");
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

