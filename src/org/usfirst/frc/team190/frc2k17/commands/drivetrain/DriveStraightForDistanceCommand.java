package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightForDistanceCommand extends Command {
	private double distance;

	/**
	 * 
	 * @param distance Distance to drive in inches
	 */
    public DriveStraightForDistanceCommand(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.distance = distance;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.driveDistance(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Robot.drivetrain.getDistanceControlLoopOutput();
    	Robot.drivetrain.tankDriveAtSpeed(speed, speed);
    	SmartDashboard.putNumber("Robot Speed (RPM)", speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disableDistanceControl();
    }
}
