package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

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
    	int rgb = LEDStrip.HSBtoRGB(currentHue, 1, 1);
    	int r = LEDStrip.getRed(rgb);
    	int g = LEDStrip.getGreen(rgb);
    	int b = LEDStrip.getBlue(rgb);
    	
    	strip.setColor(r, g, b);
    	
    	currentHue += 0.001f;
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
