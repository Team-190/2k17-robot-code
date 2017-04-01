package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.shooter.FeederFeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinToSpeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterStopCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootingPlusGearCommandGroup extends CommandGroup {

    public AutoShootingPlusGearCommandGroup() {
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
    	addSequential(new ShooterSpinToSpeedCommand());
    	addSequential(new FeederFeedCommand(2));
    	addSequential(new ShooterStopCommand());
    	if(DriverStation.getInstance().getAlliance()  == Alliance.Blue) {
    		addSequential(new TankDriveValueCommand(0, 0.5, 1));
    		addSequential(new LeftPegAuto(true));
    	} else {
    		addSequential(new TankDriveValueCommand(0.5, 0, 1));
    		addSequential(new RightPegAuto(true));
    	}
    	   	
    	
    }
}
