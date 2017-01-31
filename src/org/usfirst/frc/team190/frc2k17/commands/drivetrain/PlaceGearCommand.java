package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.CameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.CameraLightOnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceGearCommand extends CommandGroup {

    public PlaceGearCommand() {
    	requires(Robot.drivetrain);
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
    	
    	addSequential(new CameraLightOnCommand());
    	addSequential(new TurnTowardPegCommand());
    	addSequential(new DriveToPegCommand());
    	addSequential(new CameraLightOffCommand());
    }
    
    
}
