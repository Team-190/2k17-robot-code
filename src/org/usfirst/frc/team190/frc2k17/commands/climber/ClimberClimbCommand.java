package org.usfirst.frc.team190.frc2k17.commands.climber;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberClimbCommand extends Command {

    public ClimberClimbCommand() {
    	requires(Robot.climber);
    }

    protected void initialize() {
    	Robot.climber.set(Climber.State.CLIMB);
    }

    protected void execute() {
    	if(Robot.climber.getOutputCurrent() > RobotMap.getInstance().CLIMBER_KILL_CURRENT.get()) {
    		
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.climber.set(Climber.State.STOP);
    }

    protected void interrupted() {
    	end();
    }
}
