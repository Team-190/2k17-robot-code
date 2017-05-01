package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.FalconPathPlanner;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.Robot.Peg;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PegAutoCurveActual extends Command {
	
	private double distanceCorrectionP = 1;
	
	public final FalconPathPlanner path;
	private double runningSumLeft, runningSumRight, leftPreSum, rightPreSum;
	private Timer timer;
	private int step;
	private DecimalFormat df = new DecimalFormat("#.00"); 
	private boolean splineDone;
	private Peg peg;

    public PegAutoCurveActual(double duration, Alliance alliance, Peg peg) {
    	if(Robot.drivetrain != null) {
    		requires(Robot.drivetrain);
    	}
    	this.peg = peg;
    	double[][] waypoints;
		if (alliance == Alliance.Red) {
			if (peg == Peg.LEFT) {
				waypoints = new double[][] { { 0.0, 0.0 }, { 1, 13.6833 }, { 2, 19.8516 }, { 3, 24.5596 },
						{ 4, 28.5039 }, { 5, 31.9562 }, { 7.25222, 38.5663 }, { 14.5044, 50 }, { 21.7567, 50 },
						{ 29.0089, 50 }, { 36.2611, 50 }, { 43.5133, 50 }, { 50.7656, 50 }, { 58.0178, 50 },
						{ 65.27, 50 } };
			} else {
				waypoints = new double[][] { { 0.0, 0.0 }, { -1, 13.6833 }, { -2, 19.8516 }, { -3, 24.5596 },
						{ -4, 28.5039 }, { -5, 31.9562 }, { -7.25222, 38.5663 }, { -14.5044, 50 }, { -21.7567, 60 },
						{ -29.0089, 70 }, { -36.2611, 80 }, { -43.5133, 80 }, { -50.7656, 80 }, { -58.0178, 80 },
						{ -65.27, 80 } };
			}
		} else {
			if (peg == Peg.LEFT) {
				waypoints = new double[][] { { 0.0, 0.0 }, { 1, 13.6833 }, { 2, 19.8516 }, { 3, 24.5596 },
						{ 4, 28.5039 }, { 5, 31.9562 }, { 7.25222, 38.5663 }, { 14.5044, 50 }, { 21.7567, 60 },
						{ 29.0089, 70 }, { 36.2611, 80 }, { 43.5133, 80 }, { 50.7656, 80 }, { 58.0178, 80 },
						{ 65.27, 80 } };
			} else {
				waypoints = new double[][] { { 0.0, 0.0 }, { -1, 13.6833 }, { -2, 19.8516 }, { -3, 24.5596 },
						{ -4, 28.5039 }, { -5, 31.9562 }, { -7.25222, 38.5663 }, { -14.5044, 50 }, { -21.7567, 50 },
						{ -29.0089, 50 }, { -36.2611, 50 }, { -43.5133, 50 }, { -50.7656, 50 }, { -58.0178, 50 },
						{ -65.27, 50 } };
			}
    	}
    	path = new FalconPathPlanner(waypoints);
    	path.setVelocityAlpha(0.001);
    	path.setVelocityBeta(0.9);
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
    	if(peg == Peg.LEFT) {
    		Robot.changeGearKickAfterwardsCommand(new LeftPegAutoDriveAcrossField());
    	} else {
    		Robot.changeGearKickAfterwardsCommand(new RightPegAutoDriveAcrossField());
    	}
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
