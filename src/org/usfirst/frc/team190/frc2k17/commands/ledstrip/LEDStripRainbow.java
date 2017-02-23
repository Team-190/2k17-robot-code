package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.awt.Color;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripRainbow extends LEDStripCommand {
	
	private float currentHue = 0;

    public LEDStripRainbow(LEDStrip subsystem) {
        super(subsystem);
    }

    protected void initialize() {}

    protected void execute() {
    	Color c = Color.getHSBColor(currentHue, 1.0f, 1.0f);
    	
    	strip.setColor(c);
    	
    	currentHue += 0.01f;
    	if (currentHue > 1.0) {
    		currentHue = 0.0f;
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
