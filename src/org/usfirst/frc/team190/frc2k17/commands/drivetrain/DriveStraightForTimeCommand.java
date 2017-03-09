package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveStraightForTimeCommand extends TimedCommand {
	
	private double speed;
	
	/**
	 * @param seconds time to drive in seconds
	 */
    public DriveStraightForTimeCommand(double seconds) {
    	super(seconds);
    	requires(Robot.drivetrain);
    	speed = 1;
    }
    
    /**
	 * @param seconds time to drive in seconds
	 * @param speed the speed to drive
	 */
    public DriveStraightForTimeCommand(double seconds, double speed) {
    	super(seconds);
    	requires(Robot.drivetrain);
    	this.speed = speed;
    }

    protected void initialize() {
    	Robot.drivetrain.enableCoast(false);
    }

    protected void execute() {
    	Robot.drivetrain.arcadeDrive(speed, 0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
