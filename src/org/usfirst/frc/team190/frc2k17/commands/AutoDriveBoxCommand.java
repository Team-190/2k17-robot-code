package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveBoxCommand extends CommandGroup {

    public AutoDriveBoxCommand() {
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
    	addSequential(new DriveStraightForDistanceCommand(72));
    	addSequential(new TurnToDegreesCommand(-90));
    	addSequential(new DriveStraightForDistanceCommand(72));
    	addSequential(new TurnToDegreesCommand(-90));
    	addSequential(new DriveStraightForDistanceCommand(72));
    	addSequential(new TurnToDegreesCommand(-90));
    	addSequential(new DriveStraightForDistanceCommand(72));
    	
    	
    }
}