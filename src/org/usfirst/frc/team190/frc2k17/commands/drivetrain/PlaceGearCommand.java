package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOff;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive to peg from some distance away using the camera to track on course
 */
public class PlaceGearCommand extends CommandGroup {

    public PlaceGearCommand() {
    	requires(Robot.drivetrain);
    	
    	addSequential(new GearCameraLightOn());
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveToPegCommand());
    	addSequential(new GearCameraLightOff());
    } 
}
