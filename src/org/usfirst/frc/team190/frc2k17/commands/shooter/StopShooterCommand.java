package org.usfirst.frc.team190.frc2k17.commands.shooter;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder.State;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopShooterCommand extends Command {

    public StopShooterCommand() {
    	requires(Robot.shooter);
    	requires(Robot.shooterFeeder);
    }

    protected void initialize() {
    	Robot.shooterFeeder.set(State.CLOSED);
    	setTimeout(0.5);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.shooter.shooterOff();
    }

    protected void interrupted() {
    	end();
    }
}
