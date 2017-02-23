package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.awt.Color;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDStripRainbow extends Command {
	
	private final LEDStrip strip;
	private float currentHue = 0;

    public LEDStripRainbow(LEDStrip subsystem) {
        requires(subsystem);
        
        this.strip = subsystem;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Color c = Color.getHSBColor(currentHue, 1.0f, 1.0f);
    	
    	strip.setColor(c);
    	
    	currentHue += 0.01f;
    	if (currentHue > 1.0) {
    		currentHue = 0.0f;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
