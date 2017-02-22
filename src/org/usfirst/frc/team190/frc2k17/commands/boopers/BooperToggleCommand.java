package org.usfirst.frc.team190.frc2k17.commands.boopers;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BooperToggleCommand extends Command {

	public BooperToggleCommand() {
		requires(Robot.boopers);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.boopers.toggle();
	}

	protected boolean isFinished() {
		return true;
	}

}
