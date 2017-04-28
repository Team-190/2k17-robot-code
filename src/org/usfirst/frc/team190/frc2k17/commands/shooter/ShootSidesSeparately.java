package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;

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
		addSequential(new TimedCommand(1.8));
		addParallel(new ShooterSpinCommand(1,-1));
		addSequential(new TimedCommand(0.4));
		addParallel(new ShooterSpinCommand(1,1));
		addSequential(new TimedCommand(0.5));
		addSequential(new FeederFeedCommand(2));
		addSequential(new ShooterStopCommand());
    }
}
