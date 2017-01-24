package org.usfirst.frc.team190.frc2k17.commands.boopers;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BooperPushOut extends Command {

    public BooperPushOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.boopers);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.boopers.forward();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// TODO: implement isFinished for PushOut
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
