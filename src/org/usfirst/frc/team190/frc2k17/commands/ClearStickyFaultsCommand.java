package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClearStickyFaultsCommand extends Command {

    public ClearStickyFaultsCommand() {
    	
    }

    protected void initialize() {
    	if(DriverStation.getInstance().isAutonomous()) {
    		// only do anything if the robot is in autonomous mode
    		// this is just to make it harder to accidentally reset the sticky faults
    		// resetting the sticky faults should not be taken lightly
    		Robot.clearStickyFaults();
    	}
    }

    protected boolean isFinished() {
        return true;
    }

}
