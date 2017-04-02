package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.ChangeGearKickAfterwardsCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftPegAutoDriveAcrossField extends CommandGroup {

    public LeftPegAutoDriveAcrossField() {
    	addSequential(new ChangeGearKickAfterwardsCommand(null));
    	addSequential(new GearCameraLightOffCommand());
    	addSequential(new TurnToDegreesCommand(-60));
    	addSequential(new DriveStraightForDistanceCommand(247.65));
    }
}
