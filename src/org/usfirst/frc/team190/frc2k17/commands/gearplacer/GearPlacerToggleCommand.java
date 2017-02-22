package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearPlacerToggleCommand extends Command {
	
	public GearPlacerToggleCommand() {
		requires(Robot.gearPlacer);
	}

	protected void initialize() {
		Robot.gearPlacer.toggle();
	}

	protected boolean isFinished() {
		return true;
	}

}
