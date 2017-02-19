package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTowardPegCommandLegacy extends Command {
	
	boolean _finished = false;

    public TurnTowardPegCommandLegacy() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("kP Turning", SmartDashboard.getNumber("kP Turning", 0.01));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Get the network table
    	NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");	
		
    	double[] centerXArray = grip.getNumberArray("centerX", new double[0]);
    	
    	if (centerXArray.length >= 2) {
    		double centerXActual = (centerXArray[0] + centerXArray[1]) / 2.0;
    		
    		double error = (RobotMap.getInstance().CAMERA_RESOLUTION_X.get() / 2.0) - centerXActual;
			double kP = SmartDashboard.getNumber("kP Turning", 0.01);
    		
    		double output = (error * kP);
    		
    		if (output < RobotMap.getInstance().TURN_TO_PEG_OUTPUT_TOLERANCE.get()) {
    			_finished = true;
    		}
    		
    		Robot.drivetrain.tankDrive(-output, output);
    	} else {
        	Robot.drivetrain.arcadeDrive(0, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//_finished;
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
