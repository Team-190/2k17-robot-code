package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToPegCommand extends Command {
	
	private double forwardDist = 0;
	
    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	forwardDist = Robot.gearCamera.getDistanceToPeg();
    	Robot.drivetrain.enableDistanceControl(forwardDist - 12);

    	SmartDashboard.putNumber("Inches to drive", forwardDist);
    }

    protected void execute() {
    	double angle = Robot.gearCamera.getAngleToPeg();
    	Robot.drivetrain.controlDistance(angle * -0.01);
    }
    
    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    }

    protected void interrupted() {
    	end();
    }
}
