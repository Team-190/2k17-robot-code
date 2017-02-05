package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightForDistanceCommand extends Command {
	
	double distance = 0.0;

    public DriveStraightForDistanceCommand(double distance) {
        requires(Robot.drivetrain);
        
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.drivetrain.enableTurnControl(0);
    	Robot.drivetrain.enableSpeedControl();
    	Robot.drivetrain.enableDistanceControl(distance);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Robot.drivetrain.getDistanceControlLoopOutput();
    	Robot.drivetrain.tankDriveAtSpeed(speed, speed);
    	SmartDashboard.putNumber("Robot speed", speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isDistanceControlOnTarget(); //Robot.drivetrain.isTurnControlOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disableSpeedControl();
    	Robot.drivetrain.disableDistanceControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disableSpeedControl();
    	Robot.drivetrain.disableDistanceControl();
    }
}
