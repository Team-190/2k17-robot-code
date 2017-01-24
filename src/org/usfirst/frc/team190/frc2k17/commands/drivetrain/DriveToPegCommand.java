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
public class DriveToPegCommand extends Command {
	
	private boolean _finished = false;
	private boolean alreadySpoke = false;

    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	SmartDashboard.putNumber("kP Driving", SmartDashboard.getNumber("kP Driving", 0.01));
    }

    protected void execute() {
    	// Get the network table
    	NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");	
		
    	// Get heights reported by GRIP
		double[] heights = {0, 0};
		heights = grip.getNumberArray("height", heights);
		
		double targetHeight = 93.0;
		
		// Check if GRIP is reporting values
		if (heights.length >= 2) {
			if(!alreadySpoke) {
				Logger.kangarooVoice.info("peg");
				alreadySpoke = true;
			}
			// Compute the error between current and desired height
			double error = (targetHeight - heights[0]);
			double kP = SmartDashboard.getNumber("kP Driving", 0.01);
			
			double output = (error * kP);
			
			if (output < RobotMap.Constants.DRIVE_TO_PEG_OUTPUT_TOLERANCE) {
				_finished = true;
			}
			
			// Drive robot
			Robot.drivetrain.arcadeDrive(output < RobotMap.Constants.DRIVE_TO_PEG_MAX_SPEED ? output : RobotMap.Constants.DRIVE_TO_PEG_MAX_SPEED, 0);
		} else {
			// Stop robot if GRIP is not reporting values
			Robot.drivetrain.arcadeDrive(0, 0);
		}
    }
    
    protected boolean isFinished() {
        return _finished;
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }
}
