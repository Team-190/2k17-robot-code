
package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.commands.ChangeGearKickAfterwardsCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drive to right peg
 */
public class RightPegAuto extends CommandGroup {
	
	Alliance alliance = DriverStation.getInstance().getAlliance();

    public RightPegAuto(boolean driveAcrossField) {
    	double distanceToDrive = alliance == Alliance.Red ? 66.5 : 64.666;
    	
    	addSequential(new GearCameraLightOnCommand());
    	addSequential(new DriveStraightForDistanceCommand(distanceToDrive), 3);
    	addSequential(new TurnToDegreesCommand(-60));
    	addSequential(new WaitCommand(0.2));
    	addSequential(new TurnTowardPegCommand());
    	if(driveAcrossField) {
    		addSequential(new ChangeGearKickAfterwardsCommand(RightPegAutoDriveAcrossField.class));
    	}
    	addSequential(new DriveToPegCommand(0.5));
    	addSequential(new GearCameraLightOffCommand());
    	addSequential(new DriveStraightForTimeCommand(6, 0.25));
    } 
}
