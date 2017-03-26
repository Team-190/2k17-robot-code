package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeGearKickAfterwardsCommand extends Command {

	private Command afterwards;
	
    public ChangeGearKickAfterwardsCommand(Command afterwards) {
    	this.afterwards = afterwards;
    }

    protected void initialize() {
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
