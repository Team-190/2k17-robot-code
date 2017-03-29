package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Timer;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.ChangeGearKickAfterwardsCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LeftPegAutoCurve extends Command {
	
	private double distanceCorrectionP = 1;
	
	public final FalconPathPlanner path;
	private double runningSumLeft, runningSumRight, leftPreSum, rightPreSum;
	private Timer timer;
	private int step;
	private DecimalFormat df = new DecimalFormat("#.00"); 
	private boolean splineDone;

    public LeftPegAutoCurve(double duration) {
    	if(Robot.drivetrain != null) {
    		requires(Robot.drivetrain);
    	}
    	// generated 50 points using Mathematica
		double[][] waypoints = new double[][] { { 0.0, 0.0 }, { 1.33204, 15.9816 }, { 2.66408, 23.0836 },
				{ 3.99612, 28.4897 }, { 5.32816, 33.0076 }, { 6.6602, 36.952 }, { 7.99224, 40.4851 },
				{ 9.32429, 43.7036 }, { 10.6563, 46.6706 }, { 11.9884, 49.4301 }, { 13.3204, 52.0143 },
				{ 14.6524, 54.4475 }, { 15.9845, 56.7486 }, { 17.3165, 58.9328 }, { 18.6486, 61.0123 },
				{ 19.9806, 62.9972 }, { 21.3127, 64.896 }, { 22.6447, 66.716 }, { 23.9767, 68.4632 },
				{ 25.3088, 70.143 }, { 26.6408, 71.7601 }, { 27.9729, 73.3185 }, { 29.3049, 74.8219 },
				{ 30.6369, 76.2733 }, { 31.969, 77.6757 }, { 33.301, 79.0316 }, { 34.6331, 80.3434 },
				{ 35.9651, 81.6131 }, { 37.2971, 82.8427 }, { 38.6292, 84.0338 }, { 39.9612, 85.188 },
				{ 41.2933, 86.3069 }, { 42.6253, 87.3917 }, { 43.9573, 88.4437 }, { 45.2894, 89.464 },
				{ 46.6214, 90.4537 }, { 47.9535, 91.4137 }, { 49.2855, 92.345 }, { 50.6176, 93.2484 },
				{ 51.9496, 94.1247 }, { 53.2816, 94.9746 }, { 54.6137, 95.7989 }, { 55.9457, 96.5981 },
				{ 57.2778, 97.3729 }, { 58.6098, 98.1239 }, { 59.9418, 98.8516 }, { 61.2739, 99.5564 },
				{ 62.6059, 100.239 }, { 63.938, 100.9 }, { 65.27, 101.539 } }; 
    	path = new FalconPathPlanner(waypoints);
    	path.calculate(duration, RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get(),
				RobotMap.getInstance().DRIVE_CURVE_TRACK_WIDTH.get());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
					Logger.defaultLogger.debug("Falcon has finished. Now driving forward.");
					timer.cancel();
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

				double leftVel = path.smoothLeftVelocity[step][1] - (leftError * distanceCorrectionP);
				double rightVel = path.smoothRightVelocity[step][1] - (rightError * distanceCorrectionP);

				double leftRPM = leftVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
				double rightRPM = rightVel / RobotMap.getInstance().DRIVE_CURVE_WHEEL_CIRCUMFERENCE.get() * 60;
				double leftRatio = leftRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				double rightRatio = rightRPM / RobotMap.getInstance().DRIVE_MAX_SPEED_LOW.get();
				Logger.defaultLogger.trace("Falcon output: left " + df.format(path.smoothLeftVelocity[step][1])
						+ " in/sec, " + df.format(leftRPM) + " RPM, " + df.format(leftRatio * 100) + " percent; right "
						+ df.format(path.smoothRightVelocity[step][1]) + " in/sec, " + df.format(rightRPM) + " RPM, "
						+ df.format(rightRatio * 100) + " percent. Left error: " + df.format(leftError)
						+ " Right error: " + df.format(rightError));
				if (Math.abs(leftRatio) > 1 || Math.abs(rightRatio) > 1) {
					Logger.defaultLogger.error("Falcon output has exceeded robot capability.");
				}
				Robot.drivetrain.tankDrive(leftRatio, rightRatio);
				step++;

			}
		}, 0, (long) (RobotMap.getInstance().DRIVE_CURVE_TIME_STEP.get() * 1000));
    	// drive across the field after the gear is placed
    	Robot.changeGearKickAfterwardsCommand(new LeftPegAutoDriveAcrossField());
    }

    protected void execute() {
    	if(splineDone) {
    		Robot.drivetrain.tankDrive(0.25, 0.25);
    	}
    }

    protected boolean isFinished() {
        return false;
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
