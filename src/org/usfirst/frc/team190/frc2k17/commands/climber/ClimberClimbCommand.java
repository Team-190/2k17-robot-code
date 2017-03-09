package org.usfirst.frc.team190.frc2k17.commands.climber;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.DSPFilter;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
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
public class ClimberClimbCommand extends Command {
	
	private Instant start;
	private DSPFilter filter;

    public ClimberClimbCommand() {
    	requires(Robot.climber);
    }

    protected void initialize() {
    	(new GearPlacerSetCommand(GearPlacer.State.RETRACTED)).start();
    	(new SetAutoKickEnabledCommand(false)).start();
    	filter = new DSPFilter(DSPFilter.FilterType.LOW_PASS, RobotMap.getInstance().CLIMBER_FREQ_CUTOFF.get(),
				Robot.climber.getOutputCurrent(), RobotMap.getInstance().CLIMBER_SAMPLE_RATE.get());
    	Robot.climber.enableCurrentPid();
    	Robot.climber.set(Climber.State.CLIMB);
    	Logger.defaultLogger.info("Climbing started.");
    	start = Instant.now();
    }

    protected boolean isFinished() {
    	if(Robot.climber.isLimitSwitchPressed()) {
    		Logger.defaultLogger.info("Climbing stopped due to limit switch.");
    		return true;
    	}
    	double current = filter.processNextPoint(Robot.climber.getOutputCurrent());
    	Logger.defaultLogger.info("Climbing current: " + Robot.climber.getOutputCurrent() + " (filtered: " + current + "), voltage: " + Robot.climber.getOutputVoltage());
    	if(current > RobotMap.getInstance().CLIMBER_KILL_CURRENT.get()) {
    		Logger.defaultLogger.info("Climbing stopped due to current.");
    		return true;
    	}
    	return false;
    }

    protected void end() {
    	Robot.climber.set(Climber.State.STOP);
    	Robot.climber.disableCurrentPid();
    	Logger.defaultLogger.info("Climbing canceled after " + Duration.between(start, Instant.now()).toMillis() + " milliseconds.");
    	(new SetAutoKickEnabledCommand(true)).start();
    }

    protected void interrupted() {
    	end();
    }
}
