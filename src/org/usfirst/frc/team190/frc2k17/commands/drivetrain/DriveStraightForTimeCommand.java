package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveStraightForTimeCommand extends TimedCommand {
	
	private double speed;
	private boolean stop = true;
	
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
    
    /**
	 * @param seconds time to drive in seconds
	 * @param speed the speed to drive
	 * @param stop whether to stop at the end of the command
	 */
    public DriveStraightForTimeCommand(double seconds, double speed, boolean stop) {
    	super(seconds);
    	this.stop = stop;
    	requires(Robot.drivetrain);
    	this.speed = speed;
    }

    protected void initialize() {
    	Robot.drivetrain.enableCoast(false);
    }

    protected void execute() {
    	Robot.drivetrain.tankDrive(0, 0);
    }

    protected void end() {
		if (stop) {
			Robot.drivetrain.arcadeDrive(0, 0);
		}
    }

    protected void interrupted() {
    	end();
    }
    
}
