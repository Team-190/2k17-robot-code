package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToDegreesCommand extends Command {
	
	private boolean navxPresent;
	private double degrees = 0.0;

    public TurnToDegreesCommand(double degrees) {
        requires(Robot.drivetrain);
        this.degrees = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// assignment inside if-statement is intentional
    	if(navxPresent = Robot.drivetrain.isNavxPresent()) {
	    	Logger.defaultLogger.info("Turning " + degrees + " degrees.");
	    	Robot.drivetrain.enableCoast(false);
	    	Robot.drivetrain.enableTurningControl(degrees);
    	}
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(navxPresent) {
    		Robot.drivetrain.controlTurning();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !navxPresent || Robot.drivetrain.isTurningControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(navxPresent) {
    		Robot.drivetrain.disableTurningControl();
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
