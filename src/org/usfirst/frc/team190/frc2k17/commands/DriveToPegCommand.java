package org.usfirst.frc.team190.frc2k17.commands;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToPegCommand extends Command {
	
	private boolean _finished = false;
	private static final double OUTPUT_TOLERANCE = 0.1;

    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
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
			// Compute the error between current and desired height
			double error = (targetHeight - heights[0]);
			double kP = SmartDashboard.getNumber("kP", 0.01);
			
			double output = (error * kP);
			
			if (output < OUTPUT_TOLERANCE) {
				_finished = true;
			}
			
			// Drive robot
			Robot.drivetrain.arcadeDrive(output, 0);
		} else {
			// Stop robot if GRIP is not reporting values
			Robot.drivetrain.arcadeDrive(0, 0);
		}
    }
    
    // TODO: Implement finished method when the robot arrives at peg
    protected boolean isFinished() {
        return _finished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
