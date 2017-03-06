package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive to the Peg using the camera to calculate the distance. Constantly ensure
 * that the robot is driving straight by getting the angle value from the camera
 * while driving.
 */
public class DriveToPegCommand extends Command {
	
	private DriveStraightForDistanceHeadingCorrectionCommand driveCommand;
	
    /**
     * get the distance to the peg and drive for that distance
     */
    protected void initialize() {
    	double dist = Robot.gearCamera.getDistanceToPeg();
    	Logger.defaultLogger.debug("Distance to peg: " + dist + " inches.");
    	driveCommand = new DriveStraightForDistanceHeadingCorrectionCommand(dist - 3);
    	driveCommand.start();
    }

    protected boolean isFinished() {
        return driveCommand.isFinished();
    }

}
