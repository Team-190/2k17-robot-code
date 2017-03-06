package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForDistanceHeadingCorrectionCommand extends Command {
	private double inches;
	/**
	 * 
	 * @param inches Distance to drive in inches
	 */
    public DriveStraightForDistanceHeadingCorrectionCommand(double inches) {
    	this.inches = inches;
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	Robot.drivetrain.enableDistanceControl(inches);
    	Robot.drivetrain.enableTurningControl(0);
    }

    protected void execute() {
    	Robot.drivetrain.controlTurningAndDistance();
    }

    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    	Robot.drivetrain.disableTurningControl();
    }

    protected void interrupted() {
    	end();
    }
}
