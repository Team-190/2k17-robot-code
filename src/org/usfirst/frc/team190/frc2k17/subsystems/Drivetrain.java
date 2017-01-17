
package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The robot's drivetrain.
 */
public class Drivetrain extends Subsystem {
    
	private final Encoder leftEncoder, rightEncoder;
	private final CANSpeedController leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor;
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
    	
    	leftEncoder = new Encoder(RobotMap.DIO.DRIVE_ENCODER_LEFT_A, RobotMap.DIO.DRIVE_ENCODER_LEFT_B);
    	rightEncoder = new Encoder(RobotMap.DIO.DRIVE_ENCODER_RIGHT_A, RobotMap.DIO.DRIVE_ENCODER_RIGHT_B);
    	
    	leftFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_FRONT);
    	leftRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_LEFT_REAR);
    	rightFrontMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_FRONT);
    	rightRearMotor = new CANTalon(RobotMap.CAN.DRIVE_MOTOR_RIGHT_REAR);
    	
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

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

