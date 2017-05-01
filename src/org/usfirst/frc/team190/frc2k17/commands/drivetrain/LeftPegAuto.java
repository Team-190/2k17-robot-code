
package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.ChangeGearKickAfterwardsCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drive to left peg
 */
public class LeftPegAuto extends CommandGroup {
	
	Alliance alliance = DriverStation.getInstance().getAlliance();

    public LeftPegAuto(boolean driveAcrossField) {
    	double distanceToDrive = alliance == Alliance.Red ? 64.666 : 66.5;
    	
    	addSequential(new GearCameraLightOnCommand());
    	addSequential(new SetAutoKickEnabledCommand(false));
    	addSequential(new DriveStraightForDistanceCommand(distanceToDrive), 3);
    	addSequential(new TurnToDegreesCommand(60));
    	addSequential(new WaitCommand(0.2));
    	addSequential(new TurnTowardPegCommand());
    	if(driveAcrossField) {
    		addSequential(new ChangeGearKickAfterwardsCommand(LeftPegAutoDriveAcrossField.class));
    	}
    	addSequential(new SetAutoKickEnabledCommand(true));
    	addSequential(new DriveToPegCommand(0.5));
    	addSequential(new GearCameraLightOffCommand());
    	addSequential(new DriveStraightForTimeCommand(6, 0.25));
    } 
}
