package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceHeadingCorrectionCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToHopperTurnCommand extends CommandGroup {

    public AutoDriveToHopperTurnCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(Robot.drivetrain);
    	addSequential(new DriveStraightForDistanceHeadingCorrectionCommand(40));
    	addSequential(new TurnToDegreesCommand(30.0));
    	addSequential(new DriveStraightForDistanceCommand(126.0));
    }
}
