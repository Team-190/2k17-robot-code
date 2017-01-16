package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class TurnTowardPegCommand extends Command {

    public TurnTowardPegCommand() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Get the network table
    	NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");	
		
    	double[] centerXArray = grip.getNumberArray("centerX", new double[0]);
    	
    	if (centerXArray.length >= 2) {
    		double centerXActual = (centerXArray[0] + centerXArray[1]) / 2;
    		
    		
    	} else {
        	Robot.drivetrain.arcadeDrive(0, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
