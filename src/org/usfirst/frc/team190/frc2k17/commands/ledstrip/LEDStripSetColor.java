package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripSetColor extends LEDStripCommand {
	
	private final int r, g, b;
	public LEDStripSetColor(LEDStrip subsystem, int r, int g, int b) {
        super(subsystem);
        
        this.r = r;
        this.g = g;
        this.b = b;
    }

    protected void initialize() {
    	strip.setColor(r, g, b);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
