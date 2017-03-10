
package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drive to peg from some distance away using the camera to track on course
 */
public class LeftPegAuto extends CommandGroup {
	
	Alliance alliance = DriverStation.getInstance().getAlliance();

    public LeftPegAuto() {
    	double distanceToDrive = alliance == Alliance.Red ? 64.666 : 66.5;
    	
    	addSequential(new GearCameraLightOnCommand());
    	addSequential(new DriveStraightForDistanceCommand(distanceToDrive), 5);
    	addSequential(new TurnToDegreesCommand(30));
    	addSequential(new SearchForPegCommand());
    	addSequential(new WaitCommand(0.5));
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveToPegCommand());
    	addSequential(new GearCameraLightOffCommand());
    } 
}
