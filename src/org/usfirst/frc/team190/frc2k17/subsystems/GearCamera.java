package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Handle the front camera to see the peg.
 * 
 * @author bradmiller This class can get the distance and angle to the peg
 */
public class GearCamera extends Subsystem {

	private final Relay spike;
	private NetworkTable grip;
	private double degreesToTurn;
	private double inchesToDrive;
	private boolean pegIsVisible;

	public GearCamera() {
		spike = new Relay(RobotMap.getInstance().PWM_CAMERA_LIGHT.get());
		grip = NetworkTable.getTable("/GRIP/frontCameraReport");
	}

	/**
	 * turn on the front ring light
	 */
	public void lightOn() {
		spike.set(Value.kForward);
	}

	/**
	 * turn off the front ring light
	 */
	public void lightOff() {
		spike.set(Value.kOff);
	}

	/**
	 * Toggle the status of the ring light.
	 */
	public void lightToggle() {
		if (spike.get() == Value.kOff) {
			spike.set(Value.kForward);
		} else {
			spike.set(Value.kOff);
		}
	}

	/**
	 * Get data from the camera that is used to compute the distance and angle
	 * to the peg.
	 */
	private void getCameraData() {
		pegIsVisible = false;
		NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");

		double[] centerXArray = grip.getNumberArray("centerX", new double[0]);
		double[] heightArray = grip.getNumberArray("height", new double[0]);

		if (centerXArray.length >= 2) {
			double cameraHalfWidth = (RobotMap.getInstance().CAMERA_RESOLUTION_X.get() / 2);
			double centerXAvg = (centerXArray[0] + centerXArray[1]) / 2.0;
			double distanceFromCenter = centerXAvg - cameraHalfWidth;

			// Math.toDegrees(Math.atan((distanceFromCenter / cameraHalfWidth) *
			// Math.tan(Math.toRadians(RobotMap.Constants.CAMERA_HFOV / 2))));
			degreesToTurn = (centerXAvg / RobotMap.getInstance().CAMERA_RESOLUTION_X.get()
					* RobotMap.getInstance().CAMERA_HFOV.get()) - (RobotMap.getInstance().CAMERA_HFOV.get() / 2);

			if (heightArray.length >= 2) {
				double h = heightArray[0];
				inchesToDrive = 12.0
						* (-0.0001599959 * h * h * h + 0.0225971104 * h * h - 1.116489553 * h + 22.7077288336) - 12;
				pegIsVisible = true;
			} else {
				pegIsVisible = false;
			}

			Logger.defaultLogger.info("Peg found, degreesToTurn set to " + degreesToTurn);
			Logger.kangarooVoice.info(String.format("%1$.3f", degreesToTurn) + " degrees");
		} else {
			degreesToTurn = 0;
			inchesToDrive = 0;
			Logger.defaultLogger.info("Peg not seen, degreesToTurn not set.");
		}
	}

	public boolean isPegVisible() {
		// TODO: we really don't want to call this 3 times, but this will work
		// for a test
		getCameraData();
		return pegIsVisible;
	}

	public double getDistanceToPeg() {
		getCameraData();
		return inchesToDrive;
	}

	public double getAngleToPeg() {
		getCameraData();
		return degreesToTurn;
	}

	@Override
	protected void initDefaultCommand() {
		// setDefaultCommand(new GearCameraLightOff());
	}
}
