package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Handle the front camera to see the peg.
 */
public class GearCamera extends Subsystem {

	private final DigitalOutput spike;
	private double degreesToTurn;
	private double inchesToDrive;
	private boolean pegIsVisible;

	public GearCamera() {
		spike = new DigitalOutput(RobotMap.getInstance().DIO_CAMERA_LIGHT.get());
		spike.disablePWM();
		LiveWindow.addActuator("gear and boopers", "camera light", spike);
	}

	/**
	 * turn on the front ring light
	 */
	public void lightOn() {
		spike.set(true);
	}

	/**
	 * turn off the front ring light
	 */
	public void lightOff() {
		spike.set(false);
	}

	/**
	 * Toggle the status of the ring light.
	 */
	public void lightToggle() {
		if (spike.get() == false) {
			spike.set(true);
		} else {
			spike.set(false);
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
			double cameraHalfWidth = (double) RobotMap.getInstance().CAMERA_RESOLUTION_X.get() / 2;
			double centerXAvg = (centerXArray[0] + centerXArray[1]) / 2.0;
			//double distanceFromCenter = centerXAvg - cameraHalfWidth;

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

		} else {
			degreesToTurn = 0;
			inchesToDrive = 0;
		}
		
		SmartDashboard.putNumber("Degrees to turn", degreesToTurn);
		SmartDashboard.putNumber("Inches to drive", inchesToDrive);
	}

	public boolean isPegVisible() {
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
	}
}
