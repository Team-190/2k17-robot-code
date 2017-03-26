package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SearchForPegCommand extends Command {

	private boolean wasPegVisible;
	
    public SearchForPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	wasPegVisible = Robot.gearCamera.isPegVisible();
    }

    protected void execute() {
    	if(!wasPegVisible) {
    		Robot.drivetrain.arcadeDrive(0, 0.25);
    	}
    }

    protected boolean isFinished() {
        return wasPegVisible || Robot.gearCamera.isPegVisible();
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
