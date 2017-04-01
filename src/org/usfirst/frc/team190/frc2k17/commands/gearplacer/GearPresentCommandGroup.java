package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GearPresentCommandGroup extends CommandGroup {

    public GearPresentCommandGroup() {
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.EXTENDED));
    	addSequential(new TimedCommand(RobotMap.getInstance().GEAR_PRESENT_KICK_TIMEOUT.get()));
    	addSequential(new GearDriveBackCommand());
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.RETRACTED));
    }
    
    public GearPresentCommandGroup(Command afterwards) {
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.EXTENDED));
    	addSequential(new TimedCommand(RobotMap.getInstance().GEAR_PRESENT_KICK_TIMEOUT.get()));
    	addSequential(new GearDriveBackCommand());
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.RETRACTED));
    	if(afterwards != null) {
    		addSequential(afterwards);
    	}
    }
}
