package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeederFeedCommand extends Command {

    public FeederFeedCommand() {
    	requires(Robot.shooterFeeder);
    }

    protected void initialize() {
    	Robot.shooterFeeder.set(ShooterFeeder.State.OPEN);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shooterFeeder.set(ShooterFeeder.State.CLOSED);
    }

    protected void interrupted() {
    	end();
    }
}
