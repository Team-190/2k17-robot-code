
package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drive to left peg
 */
public class LeftPegAuto extends CommandGroup {
	
	Alliance alliance = DriverStation.getInstance().getAlliance();

    public LeftPegAuto() {
    	double distanceToDrive = alliance == Alliance.Red ? 64.666 : 66.5;
    	
    	addSequential(new GearCameraLightOnCommand());
    	addSequential(new DriveStraightForDistanceCommand(distanceToDrive), 3);
    	addSequential(new TurnToDegreesCommand(60));
    	addSequential(new WaitCommand(0.2));
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveHalfwayToPegCommand());
    	addSequential(new WaitCommand(0.2));
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveToPegCommand(0.5));
    	addSequential(new GearCameraLightOffCommand());
    	addSequential(new DriveStraightForTimeCommand(6, 0.25));
    } 
}
