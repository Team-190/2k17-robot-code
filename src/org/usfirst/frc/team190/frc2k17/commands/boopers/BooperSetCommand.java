package org.usfirst.frc.team190.frc2k17.commands.boopers;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.Boopers;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BooperSetCommand extends Command {
	
	private final Boopers.State state;

	public BooperSetCommand(final Boopers.State state) {
		requires(Robot.boopers);
		this.state = state;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.boopers.set(state);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

}
