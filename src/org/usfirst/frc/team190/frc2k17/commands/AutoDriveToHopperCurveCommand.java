package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive from the driver station wall to the hopper.
 */
public class AutoDriveToHopperCurveCommand extends Command {
	
	int step = 0;
	double duration;
	FalconPathPlanner path;
	
	/**
	 * Drive from the driver station wall to the hopper.
	 * @param duration the amount of seconds to take
	 */
    public AutoDriveToHopperCurveCommand(double duration) {
    	requires(Robot.drivetrain);
    	this.duration = duration;
        double[][] waypoints = new double[][]{
        	{0, 0},
        	{10, 0},
        	{192.1, 42.2}
        };
        path = new FalconPathPlanner(waypoints);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Logger.defaultLogger.debug(this.getClass().getName() + " initializing.");
		path.calculate(duration, RobotMap.Constants.Drivetrain.Curve.TIME_STEP,
				RobotMap.Constants.Drivetrain.Curve.TRACK_WIDTH);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	assert step < path.smoothLeftVelocity.length && step < path.smoothRightVelocity.length : "Ran off the end of the curve velocities array";
    	double leftRPM = path.smoothLeftVelocity[step][1] / RobotMap.Constants.Drivetrain.Curve.WHEEL_CIRCUMFERENCE;
    	double rightRPM = path.smoothRightVelocity[step][1] / RobotMap.Constants.Drivetrain.Curve.WHEEL_CIRCUMFERENCE;
    	double leftRatio = leftRPM / RobotMap.Constants.Drivetrain.MAX_SPEED_LOW;
    	double rightRatio = rightRPM / RobotMap.Constants.Drivetrain.MAX_SPEED_LOW;
		Logger.defaultLogger.trace("Falcon output: left " + path.smoothLeftVelocity[step][1] + " in/sec, " + leftRPM
				+ " RPM, " + (leftRatio * 100) + " percent; right " + path.smoothRightVelocity[step][1] + " in/sec, "
				+ rightRPM + " RPM, " + (rightRatio * 100) + " percent.");
    	Robot.drivetrain.tankDrive(leftRatio, rightRatio);
    	step++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(!(step < path.smoothLeftVelocity.length && step < path.smoothRightVelocity.length)) {
        	Logger.defaultLogger.debug(this.getClass().getName() + " finished.");
        	return true;
        }
        return false;
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
