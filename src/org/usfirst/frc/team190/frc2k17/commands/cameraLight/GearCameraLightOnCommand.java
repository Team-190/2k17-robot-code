package org.usfirst.frc.team190.frc2k17.commands.cameraLight;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn on the gear camera light
 */
public class GearCameraLightOnCommand extends Command {

    public GearCameraLightOnCommand() {
        requires(Robot.gearCamera);
        setTimeout(0.4);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearCamera.lightOn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }
}
