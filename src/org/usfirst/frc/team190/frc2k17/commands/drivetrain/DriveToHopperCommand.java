package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToHopperCommand extends CommandGroup {

    public DriveToHopperCommand() {
    	requires(Robot.drivetrain);
    	addSequential(new DriveStraightForDistanceCommand(32.5));
    	addSequential(new TurnToDegreesCommand(-20));
    	addSequential(new DriveStraightForDistanceCommand(75));
    }
}
