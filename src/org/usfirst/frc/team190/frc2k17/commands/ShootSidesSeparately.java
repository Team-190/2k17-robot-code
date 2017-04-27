package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.FeederFeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinLeftCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinRightCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterStopCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ShootSidesSeparately extends CommandGroup {

    public ShootSidesSeparately() {
    	addSequential(new SetAutoKickEnabledCommand(false));
		addParallel(new ShooterSpinCommand(1,0));
		addSequential(new TimedCommand(0.5));
		addParallel(new FeederFeedCommand(2));
		addSequential(new TimedCommand(1.9));
		addParallel(new ShooterSpinCommand(1,-1));
		addSequential(new TimedCommand(0.2));
		addParallel(new ShooterSpinCommand(1,1));
		addSequential(new TimedCommand(0.5));
		addSequential(new FeederFeedCommand(2));
		addSequential(new ShooterStopCommand());
    }
}
