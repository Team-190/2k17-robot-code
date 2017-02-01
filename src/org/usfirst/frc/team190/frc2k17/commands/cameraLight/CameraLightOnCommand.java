package org.usfirst.frc.team190.frc2k17.commands.cameraLight;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraLightOnCommand extends Command {

    public CameraLightOnCommand() {
        requires(Robot.cameraLight);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.cameraLight.turnOn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

}