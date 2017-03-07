package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightForDistanceHeadingCorrectionCommand extends Command {
	
	private boolean useNavx;
	private double inches;
	private double speedLimit;
	
	/**
	 * @param inches Distance to drive in inches
	 */
    public DriveStraightForDistanceHeadingCorrectionCommand(double inches) {
    	this.inches = inches;
    	speedLimit = 1;
    	requires(Robot.drivetrain);
    }
    
    /**
	 * @param inches Distance to drive in inches
	 * @param speedLimit the maximum speed to drive
	 */
    public DriveStraightForDistanceHeadingCorrectionCommand(double inches, double speedLimit) {
    	requires(Robot.drivetrain);
    	this.inches = inches;
    	this.speedLimit = speedLimit;
    }

    protected void initialize() {
    	useNavx = Robot.drivetrain.isNavxPresent();
    	Robot.drivetrain.enableDistanceControl(inches);
    	Robot.drivetrain.enableEncoderDiffControl(0);
    	if(useNavx) {
    		Robot.drivetrain.enableTurningControl(0);
    	}
    }

    protected void execute() {
    	if(useNavx) {
    		Robot.drivetrain.controlTurningAndEncoderDiffAndDistance(speedLimit);
    	} else {
    		Robot.drivetrain.controlEncoderDiffAndDistance(speedLimit);
    	}
    }

    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    	Robot.drivetrain.disableEncoderDiffControl();
    	if(useNavx) {
    		Robot.drivetrain.disableTurningControl();
    	}
    }

    protected void interrupted() {
    	end();
    }
}
