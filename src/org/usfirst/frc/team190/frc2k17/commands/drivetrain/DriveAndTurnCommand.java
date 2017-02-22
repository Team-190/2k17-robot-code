package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive to distance and turn to degrees simultaneously.
 */
public class DriveAndTurnCommand extends Command {

	private double distance = 0.0, degrees = 0.0;
	
    public DriveAndTurnCommand(double distance, double degrees) {
        requires(Robot.drivetrain);
        this.distance = distance;
        this.degrees = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.enableTurningControl(degrees);
    	Robot.drivetrain.enableDistanceControl(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.controlTurningAndDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Robot.drivetrain.isDistanceControlOnTarget() && Robot.drivetrain.isTurningControlOnTarget());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    	Robot.drivetrain.disableTurningControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
