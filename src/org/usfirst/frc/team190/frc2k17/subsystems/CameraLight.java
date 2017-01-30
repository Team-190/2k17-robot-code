package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.CameraLightOffCommand;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraLight extends Subsystem {

    private final Relay spike;
    
    public CameraLight() {
    	spike = new Relay(RobotMap.PWM.CAMERA_LIGHT);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CameraLightOffCommand());
    }
    
    public void turnOn() {
    	spike.set(Value.kOn);
    }
    
    public void turnOff() {
    	spike.set(Value.kOff);
    }
}

