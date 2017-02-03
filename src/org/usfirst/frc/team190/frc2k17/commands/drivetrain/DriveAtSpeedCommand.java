package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAtSpeedCommand extends Command {
	
	double speed = 0;
	
    public DriveAtSpeedCommand(double speed) {
        requires(Robot.drivetrain);
        
        //this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.enableSpeedControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	this.speed = Robot.oi.getDriverJoystick1Throttle() * 400;
    	SmartDashboard.putNumber("Speed Target (RPM)", speed);
    	
    	Robot.drivetrain.tankDriveAtSpeed(speed, speed);
    	Robot.drivetrain.outputEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableSpeedControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disableSpeedControl();
    }
}
