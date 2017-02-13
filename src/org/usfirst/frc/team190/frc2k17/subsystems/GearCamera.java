package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOff;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Handle the front camera to see the peg.
 * @author bradmiller
 * This class can get the distance and angle to the peg
 */
public class GearCamera extends Subsystem {

    private final Relay spike;
    private NetworkTable grip;
    
    public GearCamera() {
    	spike = new Relay(RobotMap.PWM.CAMERA_LIGHT);
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
     * Get data from the camera that is used to compute the distance and angle
     * to the peg.
     */
    private void getCameraData() {
    	double[] heights = {0, 0};
    	heights = grip.getNumberArray("height", heights);
    }
    
    public double getDistanceToPeg() {
    	getCameraData();
    	return 0.0;
    }
    
    public double getAngleToPeg() {
    	getCameraData();
    	return 0.0;
    }

	@Override
	protected void initDefaultCommand() {
        setDefaultCommand(new GearCameraLightOff());
	}
}
