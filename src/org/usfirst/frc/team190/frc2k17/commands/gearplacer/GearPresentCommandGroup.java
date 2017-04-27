package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GearPresentCommandGroup extends CommandGroup {

    public GearPresentCommandGroup() {
    	addCommands();
    }
    
    public GearPresentCommandGroup(Command afterwards) {
    	addCommands();
    	if(afterwards != null) {
    		addSequential(afterwards);
    	}
    }
    
    private void addCommands() {
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.EXTENDED));
    	addSequential(new ConditionalCommand(new TimedCommand(RobotMap.getInstance().GEAR_PRESENT_KICK_TIMEOUT_AUTO.get()),
				new TimedCommand(RobotMap.getInstance().GEAR_PRESENT_KICK_TIMEOUT.get())) {
			@Override
			protected boolean condition() {
				return DriverStation.getInstance().isAutonomous();
			}
		});
    	addSequential(new GearDriveBackCommand());
    	addSequential(new GearPlacerSetCommand(GearPlacer.State.RETRACTED));
    }
}
