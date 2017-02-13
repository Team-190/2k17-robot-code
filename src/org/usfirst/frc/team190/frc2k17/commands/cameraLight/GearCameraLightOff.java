package org.usfirst.frc.team190.frc2k17.commands.cameraLight;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn off the gear camera light
 */
public class GearCameraLightOff extends Command {

    public GearCameraLightOff() {
        requires(Robot.gearCamera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearCamera.lightOff();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}
