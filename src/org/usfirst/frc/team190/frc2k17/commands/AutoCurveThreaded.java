package org.usfirst.frc.team190.frc2k17.commands;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Timer;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCurveThreaded extends Command {
	
	private ADXRS450_Gyro gyro;
	private double gyrofudge = 1.125;
	private double headingCorrectionP = 0.0095;
	
	public final FalconPathPlanner path;
	private double duration;
	private Timer timer;
	private int step;
	private DecimalFormat df = new DecimalFormat("#.00"); 
	private boolean timerDone;

    public AutoCurveThreaded(double duration) {
    	if(Robot.drivetrain != null) {
    		requires(Robot.drivetrain);
    		gyro = new ADXRS450_Gyro();
    		
    	}
    	this.duration = duration;
    	double[][] waypoints = new double[][]{
    		/* theoretical values
        	{0,0},
        	{0,12},
        	{42.2,150},
    		{42.2,192.1}*/
    		{0,0},
        	{0,12},
        	{42.2 + 32, 150},
    		{42.2 + 32, 192.1 - 9}
        }; 
    	path = new FalconPathPlanner(waypoints);
    	path.calculate(duration, RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get(),
				RobotMap.getInstance().DRIVE_CURVE_TRACK_WIDTH.get());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.reset();
    	timerDone = false;
    	Logger.defaultLogger.debug(this.getClass().getSimpleName() + " initializing.");
    	step = 0;
		double sum = 0;
		for(int i = 0; i < path.smoothLeftVelocity.length; i++) {
			sum += path.smoothLeftVelocity[i][1] * RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();
		}
		Logger.defaultLogger.debug("The left side of the drivetrain is going to travel a total of " + df.format(sum) + " inches.");
		sum = 0;
		for(int i = 0; i < path.smoothRightVelocity.length; i++) {
			sum += path.smoothRightVelocity[i][1] * RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();
		}
		Logger.defaultLogger.debug("The right side of the drivetrain is going to travel a total of " + df.format(sum) + " inches.");
    	
    	timer = new Timer();   	
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				if (step >= path.smoothLeftVelocity.length || step >= path.smoothRightVelocity.length) {
					timer.cancel();
					timerDone = true;
					return;
				}
				double angleError = gyro.getAngle()*gyrofudge - path.heading[step][1];
				
				double leftVel = path.smoothLeftVelocity[step][1] + angleError*headingCorrectionP;
				double rightVel = path.smoothRightVelocity[step][1] - angleError*headingCorrectionP;
				
				//double leftRPM = path.smoothLeftVelocity[step][1] / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
		    	//double rightRPM = path.smoothRightVelocity[step][1] / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
		    	double leftRPM = leftVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
		    	double rightRPM = rightVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
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

    protected void execute() {
    }

    protected boolean isFinished() {
        return timerDone;
    }

    protected void end() {
    	Robot.drivetrain.tankDrive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
