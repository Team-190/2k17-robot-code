package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenFeederCommand extends Command {

    public OpenFeederCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterFeeder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterFeeder.set(ShooterFeeder.State.OPEN);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
