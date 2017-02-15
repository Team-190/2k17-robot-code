package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToPegCommand extends Command {
	
	private double forwardDist = 0;
	private boolean wasPegVisible;
	
    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	forwardDist = Robot.gearCamera.getDistanceToPeg();
    	wasPegVisible = Robot.gearCamera.isPegVisible();
    	Robot.drivetrain.enableDistanceControl(forwardDist);
    }

    protected void execute() {
    	Robot.drivetrain.controlDistance();
    }
    
    protected boolean isFinished() {
        return !wasPegVisible || Robot.drivetrain.isDistanceControlOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.disableDistanceControl();
    }

    protected void interrupted() {
    	end();
    }
}
