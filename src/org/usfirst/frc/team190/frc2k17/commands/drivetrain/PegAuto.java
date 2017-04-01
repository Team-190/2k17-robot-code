
package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.Robot.Peg;
import org.usfirst.frc.team190.frc2k17.commands.ChangeGearKickAfterwardsCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drive to left peg
 */
public class PegAuto extends ConditionalCommand {
	
	Alliance alliance = DriverStation.getInstance().getAlliance();

    public PegAuto(boolean driveAcrossField) {
    	super(new LeftPegAuto(driveAcrossField), new RightPegAuto(driveAcrossField));
    }

	@Override
	protected boolean condition() {
		return Robot.getPeg() == Peg.LEFT;
	} 
    
    
}
