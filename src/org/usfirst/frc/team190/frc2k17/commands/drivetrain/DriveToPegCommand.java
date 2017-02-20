package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive to the Peg using the camera to calculate the distance. Constantly ensure
 * that the robot is driving straight by getting the angle value from the camera
 * while driving.
 */
public class DriveToPegCommand extends Command {
	
	private double forwardDist = 0;
	
    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    /**
     * get the distance to the peg and drive for that distance
     * TODO: we are arbitrarily subtracting 12" from the computed distance for testing
     */
    protected void initialize() {
    	forwardDist = Robot.gearCamera.getDistanceToPeg();
    	Robot.drivetrain.enableDistanceControl(forwardDist - 12);

    	SmartDashboard.putNumber("Inches to drive", forwardDist);
    }

    protected void execute() {
    	double angle = Robot.gearCamera.getAngleToPeg();
    	//TODO: arbitrary proportional constant below for steering while driving
    	//Robot.drivetrain.controlDistance(angle * -0.01);
    	Robot.drivetrain.controlDistance();
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
