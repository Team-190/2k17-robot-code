package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive to the Peg using the camera to calculate the distance. Constantly ensure
 * that the robot is driving straight by getting the angle value from the camera
 * while driving.
 */
public class DriveHalfwayToPegCommand extends Command {
	
	private DriveStraightForDistanceHeadingCorrectionCommand driveCommand;
	private double speedLimit;
	private double dist;
	private boolean wasPegVisible;
	private boolean useNavx;
	
	public DriveHalfwayToPegCommand() {
		requires(Robot.drivetrain);
    	speedLimit = 1;
	}
	
	public DriveHalfwayToPegCommand(double speedLimit) {
		requires(Robot.drivetrain);
    	this.speedLimit = speedLimit;
	}
	
    /**
     * get the distance to the peg and drive for that distance
     */
    protected void initialize() {
    	wasPegVisible = Robot.gearCamera.isPegVisible();
    	if(wasPegVisible) {
	    	Logger.defaultLogger.info("Drive halfway to peg.");
	    	dist = Robot.gearCamera.getDistanceToPeg();
	    	Logger.defaultLogger.debug("Distance to peg: " + dist + " inches.");
	    	dist /= 2;
	    	Robot.drivetrain.enableCoast(false);
	    	useNavx = Robot.drivetrain.isNavxPresent();
	    	Robot.drivetrain.enableDistanceControl(dist);
	    	Robot.drivetrain.enableEncoderDiffControl(0);
	    	if(useNavx) {
	    		Robot.drivetrain.enableTurningControl(0);
	    	}
    	} else {
    		Logger.defaultLogger.warn("Peg not visible.");
    	}
    }
    
    protected void execute() {
    	if(wasPegVisible) {
	    	if(useNavx) {
	    		Robot.drivetrain.controlTurningAndEncoderDiffAndDistance(speedLimit);
	    	} else {
	    		Robot.drivetrain.controlEncoderDiffAndDistance(speedLimit);
	    	}
    	}
    }

    protected boolean isFinished() {
        return !wasPegVisible || Robot.drivetrain.isDistanceControlOnTarget();
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
