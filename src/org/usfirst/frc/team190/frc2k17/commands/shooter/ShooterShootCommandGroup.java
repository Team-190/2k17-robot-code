package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ShooterShootCommandGroup extends CommandGroup {

    public ShooterShootCommandGroup() {
    	addSequential(new ShooterSpinToSpeedCommand());
    	addSequential(new OpenFeederCommand());
    }
}
