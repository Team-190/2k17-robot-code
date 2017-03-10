package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForDistanceCommand extends Command {
	
	private double inches;
	private double speedLimit;
	
	/**
	 * @param inches Distance to drive in inches
	 */
    public DriveStraightForDistanceCommand(double inches) {
    	requires(Robot.drivetrain);
    	this.inches = inches;
    	speedLimit = 1;
    }
    
    /**
	 * @param inches Distance to drive in inches
	 * @param speedLimit the maximum speed to drive
	 */
    public DriveStraightForDistanceCommand(double inches, double speedLimit) {
    	requires(Robot.drivetrain);
    	this.inches = inches;
    	this.speedLimit = speedLimit;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.enableCoast(false);
    	Robot.drivetrain.enableDistanceControl(inches);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.controlDistance(0, speedLimit);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
