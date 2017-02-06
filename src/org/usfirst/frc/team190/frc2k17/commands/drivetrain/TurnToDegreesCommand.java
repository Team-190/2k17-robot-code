package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToDegreesCommand extends Command {
	
	double degrees = 0.0;

    public TurnToDegreesCommand(double degrees) {
        requires(Robot.drivetrain);
        
        this.degrees = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.enableTurningControl(degrees);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.controlTurning();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isTurningControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableTurningControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disableTurningControl();
    }
}
