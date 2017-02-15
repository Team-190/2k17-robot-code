package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForDistanceHeadingCorrectionCommand extends Command {
	private double inches;
	//TODO: Add heading correction (stay at 0 degrees)
	/**
	 * 
	 * @param inches Distance to drive in inches
	 */
    public DriveStraightForDistanceHeadingCorrectionCommand(double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.inches = inches;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.enableDistanceControl(inches);
    	Robot.drivetrain.enableTurningControl(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.controlTurningAndDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    	Robot.drivetrain.disableTurningControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
