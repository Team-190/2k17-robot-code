package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTowardPegCommand extends Command {
	
	boolean _finished = false;
	double degreesToTurn = 0;

    public TurnTowardPegCommand() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("kP Turning", SmartDashboard.getNumber("kP Turning", 0.01));
    	
    	// Get the network table
    	NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");	
		
    	double[] centerXArray = grip.getNumberArray("centerX", new double[0]);
    	
    	if (centerXArray.length >= 2) {
    		double cameraHalfWidth = (RobotMap.Constants.CAMERA_RESOLUTION_X / 2);
    		double centerXAvg = (centerXArray[0] + centerXArray[1]) / 2.0;
    		double distanceFromCenter = centerXAvg - cameraHalfWidth;
    		
    		degreesToTurn = Math.toDegrees(Math.atan((distanceFromCenter / cameraHalfWidth) * Math.tan(Math.toRadians(RobotMap.Constants.CAMERA_HFOV / 2))));

    		Logger.defaultLogger.info("Peg found, degreesToTurn set to " + degreesToTurn);
    		Logger.kangarooVoice.info(String.format("%1$.3f", degreesToTurn) + " degrees");
    	} else {
    		Logger.defaultLogger.info("Peg not seen, degreesToTurn not set.");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _finished || (degreesToTurn == 0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }
}
