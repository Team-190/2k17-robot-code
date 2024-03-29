package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Make the robot turn towards the peg using the two retro-reflective stripes on either
 * side of the peg.
 */
public class PegAssist extends Command {
	
	private boolean on;
	private Timer timer;
	private TimerTask leftTask, rightTask;
	private Instant leftLastFlashed, rightLastFlashed;
	private long rate;
	private Color allianceColor;
	private GearCameraLightOnCommand cameraLightOnCommand = new GearCameraLightOnCommand();
	private GearCameraLightOffCommand cameraLightOffCommand = new GearCameraLightOffCommand();
	
    public PegAssist() {
        requires(Robot.leftLEDs);
        requires(Robot.rightLEDs);
    }

    protected void initialize() {
    	Robot.leftLEDs.setColor(0);
    	Robot.rightLEDs.setColor(0);
		if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
			allianceColor = Color.RED;
		} else {
			allianceColor = Color.BLUE;
		}
    	on = false;
    	timer = new Timer();
    	resetTimer();
		cameraLightOnCommand.start();
		leftLastFlashed = Instant.now();
		rightLastFlashed = Instant.now();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				double degreesToTurn = Robot.gearCamera.getAngleToPeg();
				SmartDashboard.putNumber("Camera Theoretical Angle", degreesToTurn);
				resetTimer();
				long blinkRate = (long) (((Math.abs(degreesToTurn) - RobotMap.getInstance().PEGASSIST_TOLERANCE.get()) / (RobotMap.getInstance().CAMERA_HFOV.get() / 4d)) * 300d);
				if(degreesToTurn == 0) {
					Robot.leftLEDs.setColor(allianceColor);
					Robot.rightLEDs.setColor(allianceColor);
				} else if (Math.abs(degreesToTurn) < RobotMap.getInstance().PEGASSIST_TOLERANCE.get() || blinkRate == 0) {
					Robot.leftLEDs.setColor(Color.GREEN);
					Robot.rightLEDs.setColor(Color.GREEN);
				} else if (degreesToTurn > 0) {
					timer.schedule(leftTask, Math.max(0, blinkRate - Duration.between(leftLastFlashed, Instant.now()).toMillis()), blinkRate);
					Robot.rightLEDs.setColor(allianceColor);
				} else {
					timer.schedule(rightTask, Math.max(0, blinkRate - Duration.between(rightLastFlashed, Instant.now()).toMillis()), blinkRate);
					Robot.leftLEDs.setColor(allianceColor);
				}

			}
    	}, 0, RobotMap.getInstance().PEGASSIST_REFRESH_TIME.get());
    }

    protected void execute() {
    	
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
    	timer.cancel();
    	cameraLightOffCommand.start();
    }

    protected void interrupted() {
    	end();
    }
    
    private void resetTimer() {
    	if(leftTask != null) {
    		leftTask.cancel();
    	}
    	if(rightTask != null) {
    		rightTask.cancel();
    	}
    	leftTask = new TimerTask() {

			@Override
			public void run() {
				if(on) {
					Robot.leftLEDs.setColor(0);
					leftLastFlashed = Instant.now();
					on = false;
				} else {
					Robot.leftLEDs.setColor(Color.GREEN);
					leftLastFlashed = Instant.now();
					on = true;
				}
			}
    		
    	};
    	rightTask = new TimerTask() {

			@Override
			public void run() {
				if(on) {
					Robot.rightLEDs.setColor(0);
					rightLastFlashed = Instant.now();
					on = false;
				} else {
					Robot.rightLEDs.setColor(Color.GREEN);
					rightLastFlashed = Instant.now();
					on = true;
				}
			}
    		
    	};
    }
}
