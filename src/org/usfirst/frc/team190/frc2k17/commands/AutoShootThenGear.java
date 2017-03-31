package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterShootCommandGroup;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootThenGear extends CommandGroup {

    public AutoShootThenGear() {
        addSequential(new ShooterShootCommandGroup(), 5);
    }
}
