package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeGearKickAfterwardsCommand extends Command {

	private Class<? extends Command> afterwardsClass;
	private Command afterwards;
	
    public ChangeGearKickAfterwardsCommand(Class<? extends Command> afterwardsClass) {
    	this.afterwardsClass = afterwardsClass;
    	afterwards = null;
    }

    protected void initialize() {
		if (afterwardsClass != null) {
			try {
				afterwards = afterwardsClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				Logger.defaultLogger.error("Error instantiating " + this.getClass().getSimpleName(), e);
			}
		}
    	Robot.changeGearKickAfterwardsCommand(afterwards);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
