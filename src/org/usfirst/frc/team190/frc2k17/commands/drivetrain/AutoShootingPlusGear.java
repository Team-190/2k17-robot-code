package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.FeederFeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterSpinToSpeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterStopCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoShootingPlusGear extends ConditionalCommand {
	
	public AutoShootingPlusGear() {
		super(new InternalCommandGroup(Alliance.Red), new InternalCommandGroup(Alliance.Blue));
	}

	@Override
	protected boolean condition() {
		return DriverStation.getInstance().getAlliance() == Alliance.Red;
	}
	
	private static class InternalCommandGroup extends CommandGroup {

		public InternalCommandGroup(Alliance alliance) {
			addSequential(new SetAutoKickEnabledCommand(false));
			addParallel(new ShooterSpinCommand());
			addSequential(new TimedCommand(0.5));
			addSequential(new FeederFeedCommand(2));
			addSequential(new ShooterStopCommand());
			if (alliance == Alliance.Blue) {
				addSequential(new TankDriveValueCommand(-1, 0, 1.5));
				addSequential(new LeftPegAuto(true));
			} else {
				addSequential(new TankDriveValueCommand(0, -1, 1.5));
				addSequential(new RightPegAuto(true));
			}

		}

	}

}
