package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Make the robot turn towards the peg using the two retro-reflective stripes on either
 * side of the peg.
 */
public class TurnTowardPegCommand extends Command {
	
	private boolean wasPegVisible;

    public TurnTowardPegCommand() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
    	wasPegVisible = Robot.gearCamera.isPegVisible();
    	Robot.drivetrain.enableTurningControl(Robot.gearCamera.getAngleToPeg());
    }

    protected void execute() {
    	SmartDashboard.putNumber("Camera Theoretical Angle", Robot.gearCamera.getAngleToPeg());
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

    protected void end() {
    	Robot.drivetrain.disableTurningControl();
    }

    protected void interrupted() {
    	end();
    }
}
