package org.usfirst.frc.team190.frc2k17.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterShootCommandGroup extends CommandGroup {

    public ShooterShootCommandGroup() {
    	addSequential(new ShooterSpinToSpeedCommand());
    	addSequential(new FeederFeedCommand());
    }
}
