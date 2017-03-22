package org.usfirst.frc.team190.frc2k17.commands;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Timer;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.LeftPegAuto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCurveThreaded extends Command {
	
	private ADXRS450_Gyro gyro;
	private double gyrofudge = 1.125;
	private double headingCorrectionP = 0.0095;
	private double distanceCorrectionP = 1;
	
	public final FalconPathPlanner path;
	private double duration, runningSumLeft, runningSumRight, leftPreSum, rightPreSum;
	private Timer timer;
	private int step;
	private DecimalFormat df = new DecimalFormat("#.00"); 
	private boolean splineDone;

    public AutoCurveThreaded(double duration) {
    	if(Robot.drivetrain != null) {
    		requires(Robot.drivetrain);
    		gyro = new ADXRS450_Gyro();
    	}
    	this.duration = duration;
    	double[][] waypoints = new double[][]{
        	{0,0},
        	{0,12},
        	{42.2,125},
    		{42.2 + 6,192.1}
        }; 
    	path = new FalconPathPlanner(waypoints);
    	path.calculate(duration, RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get(),
				RobotMap.getInstance().DRIVE_CURVE_TRACK_WIDTH.get());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.reset();
    	splineDone = false;
    	Robot.drivetrain.zeroEncoderPositions();
    	runningSumLeft = 0;
    	runningSumRight = 0;
    	leftPreSum = 0;
    	rightPreSum = 0;
    	Logger.defaultLogger.debug(this.getClass().getSimpleName() + " initializing.");
    	step = 0;
		for(int i = 0; i < path.smoothLeftVelocity.length; i++) {
			leftPreSum += path.smoothLeftVelocity[i][1] * RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();
		}
		Logger.defaultLogger.debug("The left side of the drivetrain is going to travel a total of " + df.format(leftPreSum) + " inches.");
		for(int i = 0; i < path.smoothRightVelocity.length; i++) {
			rightPreSum += path.smoothRightVelocity[i][1] * RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();
		}
		Logger.defaultLogger.debug("The right side of the drivetrain is going to travel a total of " + df.format(rightPreSum) + " inches.");
    	
		assert Robot.drivetrain.getLeftEncoderPosition() == 0;
		assert Robot.drivetrain.getRightEncoderPosition() == 0;
		
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				if (step >= path.smoothLeftVelocity.length || step >= path.smoothRightVelocity.length) {
					Logger.defaultLogger.debug("Left side error with respect to precalculated distance: "
							+ df.format(Robot.drivetrain.getLeftEncoderPosition() - leftPreSum));
					Logger.defaultLogger.debug("Right side error with respect to precalculated distance: "
							+ df.format(Robot.drivetrain.getRightEncoderPosition() - rightPreSum));
					Logger.defaultLogger.debug("Falcon has finished. Now running distance PID.");
					Robot.drivetrain.enableLeftDistanceControl(leftPreSum - Robot.drivetrain.getLeftEncoderPosition());
					Robot.drivetrain.enableRightDistanceControl(rightPreSum - Robot.drivetrain.getRightEncoderPosition());
					Robot.drivetrain.zeroEncoderPositions();
					timer.cancel();
					timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Robot.drivetrain.controlLeftRightDistance();
						}
						
					}, 1000, (long) (RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get() * 1000));
					splineDone = true;
					return;
				}

				assert runningSumLeft >= 0;
				assert runningSumRight >= 0;

				if (step == 0) {
					assert runningSumLeft == 0;
					assert runningSumRight == 0;
				}

				double leftError = Robot.drivetrain.getLeftEncoderPosition() - runningSumLeft;
				double rightError = Robot.drivetrain.getRightEncoderPosition() - runningSumRight;

				if (step == 0) {
					assert leftError == 0;
					assert rightError == 0;
				}

				runningSumLeft += path.smoothLeftVelocity[step][1] * RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();
				runningSumRight += path.smoothRightVelocity[step][1]
						* RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get();

				double angleError = gyro.getAngle() * gyrofudge - path.heading[step][1];

				double leftVel = path.smoothLeftVelocity[step][1] - (leftError * distanceCorrectionP);// +
																										// (angleError
																										// *
																										// headingCorrectionP);
				double rightVel = path.smoothRightVelocity[step][1] - (rightError * distanceCorrectionP);// -
																											// (angleError
																											// *
																											// headingCorrectionP);

				// double leftRPM = path.smoothLeftVelocity[step][1] /
				// RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get()
				// * 60;
				// double rightRPM = path.smoothRightVelocity[step][1] /
				// RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get()
				// * 60;
				double leftRPM = leftVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
				double rightRPM = rightVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
				double leftRatio = leftRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				double rightRatio = rightRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				Logger.defaultLogger.trace("Falcon output: left " + df.format(path.smoothLeftVelocity[step][1])
						+ " in/sec, " + df.format(leftRPM) + " RPM, " + df.format(leftRatio * 100) + " percent; right "
						+ df.format(path.smoothRightVelocity[step][1]) + " in/sec, " + df.format(rightRPM) + " RPM, "
						+ df.format(rightRatio * 100) + " percent. Left error: " + df.format(leftError)
						+ " Right error: " + df.format(rightError) + " Angle error: " + df.format(angleError));
				if (Math.abs(leftRatio) > 1 || Math.abs(rightRatio) > 1) {
					Logger.defaultLogger.error("Falcon output has exceeded robot capability.");
				}
				Robot.drivetrain.tankDrive(leftRatio, rightRatio);
				step++;

			}
		}, 0, (long) (RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get() * 1000));
    	  	
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return splineDone && Robot.drivetrain.isLeftDistanceControlOnTarget() && Robot.drivetrain.isRightDistanceControlOnTarget();
    }

    protected void end() {
    	timer.cancel();
    	Robot.drivetrain.tankDrive(0, 0);
		Logger.defaultLogger.debug(this.getClass().getSimpleName() + " terminating.");
    }

    protected void interrupted() {
    	end();
    }
}
