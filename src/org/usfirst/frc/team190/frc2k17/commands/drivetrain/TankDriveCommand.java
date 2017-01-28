package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team190.frc2k17.OI;
import org.usfirst.frc.team190.frc2k17.Robot;

/**
 *
 */
public class TankDriveCommand extends Command {

    public TankDriveCommand() {
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.tankDrive(OI.joystick0.getY(), OI.joystick1.getY());
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