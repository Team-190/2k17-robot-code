package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartShooterCommand extends Command {

    public StartShooterCommand() {
    	requires(Robot.shooter);
    }

    protected void initialize() {
    	Robot.shooter.shooterOn(1500);
    }

    protected boolean isFinished() {
        return Robot.shooter.isAtSpeed();
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
