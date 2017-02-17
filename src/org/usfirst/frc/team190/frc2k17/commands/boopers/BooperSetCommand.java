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
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.boopers);

		this.state = state;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.boopers.set(state);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// TODO: implement isFinished for PushOut
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
