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
    	Robot.drivetrain.enableTurnControl(degrees);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.arcadeDrive(0, Robot.drivetrain.getTurningControlLoopOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //Robot.drivetrain.isTurnControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableTurnControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disableTurnControl();
    }
}