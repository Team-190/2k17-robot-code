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
	}

	protected void execute() {
		Robot.gearPlacer.toggle();
	}

	protected boolean isFinished() {
		// TODO: implement isFinished for PushOut
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
