package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Operate gear placer
 * This command will continue running until the gear placer moves to the correct state
 * or if the command times out.
 */
public class GearPlacerSetCommand extends Command {
	
	private final GearPlacer.State state;

	public GearPlacerSetCommand(final GearPlacer.State state) {
		requires(Robot.gearPlacer);
		this.state = state;
	}

	protected void initialize() {
		Robot.gearPlacer.set(state);
		setTimeout(RobotMap.getInstance().GEAR_PLACER_SET_TIMEOUT.get());
	}

	protected boolean isFinished() {
		return isTimedOut();
	}
}
