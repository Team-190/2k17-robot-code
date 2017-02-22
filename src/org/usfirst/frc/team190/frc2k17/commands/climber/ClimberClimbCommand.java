package org.usfirst.frc.team190.frc2k17.commands.climber;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.DSPFilter;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberClimbCommand extends Command {
	
	private Instant start;
	private DSPFilter filter;

    public ClimberClimbCommand() {
    	requires(Robot.climber);
    }

    protected void initialize() {
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
    	Logger.defaultLogger.info("Climbing current: " + current);
    	if(current > RobotMap.getInstance().CLIMBER_KILL_CURRENT.get()) {
    		Logger.defaultLogger.info("Climbing stopped due to current.");
    		return true;
    	}
    	return false;
    }

    protected void end() {
    	Robot.climber.set(Climber.State.STOP);
    	Robot.climber.disableCurrentPid();
    	Logger.defaultLogger.info("Climbing finished in " + Duration.between(start, Instant.now()).toMillis() + " milliseconds.");
    }

    protected void interrupted() {
    	end();
    }
}
