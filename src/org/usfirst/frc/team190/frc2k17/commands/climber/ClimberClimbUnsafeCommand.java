package org.usfirst.frc.team190.frc2k17.commands.climber;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.GearPlacerSetCommand;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Climb the rope.
 * NOTE: Auto-kick is disabled when this command starts, and is
 * re-enabled when this command is interrupted. It is NOT re-enabled if this
 * command finishes normally.
 */
public class ClimberClimbUnsafeCommand extends Command {
	
	private Instant start;

    public ClimberClimbUnsafeCommand() {
    	requires(Robot.climber);
    }

    protected void initialize() {
    	(new GearPlacerSetCommand(GearPlacer.State.RETRACTED)).start();
    	(new SetAutoKickEnabledCommand(false)).start();
    	Robot.climber.set(Climber.State.CLIMB);
    	Logger.defaultLogger.info("Climbing (unsafe mode) started.");
    	start = Instant.now();
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
    	Robot.climber.set(Climber.State.STOP);
    	Logger.defaultLogger.info("Climbing canceled after " + Duration.between(start, Instant.now()).toMillis() + " milliseconds.");
    }

    protected void interrupted() {
    	Robot.climber.set(Climber.State.STOP);
    	Logger.defaultLogger.info("Climbing finished in " + Duration.between(start, Instant.now()).toMillis() + " milliseconds.");
    }
}
