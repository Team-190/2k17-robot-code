package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearPlacerSetCommand extends Command {
	
	private final GearPlacer.State state;
	private final boolean wasExtended;

	private Instant startTime;
	
	public GearPlacerSetCommand(final GearPlacer.State state) {
		requires(Robot.gearPlacer);
		this.state = state;
		wasExtended = Robot.gearPlacer.getFullyExtended();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearPlacer.set(state);
		startTime = Instant.now();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(Duration.between(startTime, Instant.now()).toMillis() > RobotMap.Constants.GEAR_PLACER_SET_TIMEOUT) {
			Logger.defaultLogger.error("Gear placer failed to actuate within the timeout.");
			return true;
		}
		return Robot.gearPlacer.getFullyExtended() != wasExtended;
	}
}
