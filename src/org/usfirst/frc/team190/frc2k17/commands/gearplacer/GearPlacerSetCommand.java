package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearPlacerSetCommand extends Command {
	
	private final GearPlacer.State state;

	public GearPlacerSetCommand(final GearPlacer.State state) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearPlacer);

		this.state = state;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearPlacer.set(state);
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
