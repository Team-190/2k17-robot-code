package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSpinLeftCommand extends Command {

    public ShooterSpinLeftCommand() {
    	requires(Robot.shooter);
    }

    protected void initialize() {
    	Robot.shooter.shooterLeftOn();
    }
    
    protected void execute() {
    	Robot.shooter.outputEncoderValues();
    }

    protected boolean isFinished() {
    	return false;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.shooterOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
