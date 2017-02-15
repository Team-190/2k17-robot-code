package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Make the robot turn towards the peg using the two retro-reflective stripes on either
 * side of the peg.
 */
public class TurnTowardPegCommand extends Command {
	
	private double degreesToTurn = 0;
	private boolean wasPegVisible;

    public TurnTowardPegCommand() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	degreesToTurn = Robot.gearCamera.getAngleToPeg();
    	wasPegVisible = Robot.gearCamera.isPegVisible();
    	Robot.drivetrain.enableTurningControl(degreesToTurn);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.controlTurning();
    }

    /**
     * This command will terminate if either:
     * - the peg is not visible (don't want to drive if we can't see where we're going)
     * - the robot has turned to the angle to the peg
     */
    protected boolean isFinished() {
    	return !wasPegVisible || Robot.drivetrain.isTurningControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableTurningControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
