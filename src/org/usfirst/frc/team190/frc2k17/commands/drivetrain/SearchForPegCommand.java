package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SearchForPegCommand extends Command {

    public SearchForPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.drivetrain.arcadeDrive(0, 0.25);
    }

    protected boolean isFinished() {
        return Robot.gearCamera.isPegVisible();
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
