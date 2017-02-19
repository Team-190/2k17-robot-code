package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive to peg from some distance away using the camera to track on course
 */
public class PlaceGearCommand extends CommandGroup {

    public PlaceGearCommand() {
    	addSequential(new GearCameraLightOnCommand());
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveToPegCommand());
    	addSequential(new GearCameraLightOffCommand());
    } 
}
