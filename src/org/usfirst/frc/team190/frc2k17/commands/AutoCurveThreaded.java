package org.usfirst.frc.team190.frc2k17.commands;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Timer;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCurveThreaded extends Command {
	
	private FalconPathPlanner path;
	private double duration;
	private Timer timer;
	private int step;
	private DecimalFormat df = new DecimalFormat("#.00"); 
	private boolean timerDone;

    public AutoCurveThreaded(double duration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timerDone = false;
    	step = 0;

        double[][] waypoints = new double[][]{
        	{0,0},
    		{-48,0}
			/*
			{60, 12},
			{108, 12},
			{144, 108},
			{180, 72}*/
	}; 
    	
    	path = new FalconPathPlanner(waypoints);
    	path.calculate(duration, RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get(),
				RobotMap.getInstance().DRIVE_CURVE_TRACK_WIDTH.get());
    	
    	timer = new Timer();   	
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				if (step >= path.smoothLeftVelocity.length || step >= path.smoothRightVelocity.length) {
					timer.cancel();
					timerDone = true;
					return;
				}
				
				double leftRPM = path.smoothLeftVelocity[step][1] / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
		    	double rightRPM = path.smoothRightVelocity[step][1] / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
		    	double leftRatio = leftRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
		    	double rightRatio = rightRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				Logger.defaultLogger.trace("Falcon output: left " + df.format(path.smoothLeftVelocity[step][1]) + " in/sec, " + df.format(leftRPM)
						+ " RPM, " + df.format(leftRatio * 100) + " percent; right " + df.format(path.smoothRightVelocity[step][1]) + " in/sec, "
						+ df.format(rightRPM) + " RPM, " + df.format(rightRatio * 100) + " percent.");
				if(Math.abs(leftRatio) > 1 || Math.abs(rightRatio) > 1) {
					Logger.defaultLogger.error("Falcon output has exceeded robot capability.");
				}
		    	Robot.drivetrain.tankDrive(leftRatio, rightRatio);
		    	step++;
		    	
				
			}
		}, 0, 20);
    	  	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timerDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
