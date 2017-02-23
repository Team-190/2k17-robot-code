package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.awt.Color;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripSetColor extends LEDStripCommand {
	
	private final Color color;
	
	public LEDStripSetColor(LEDStrip subsystem, Color c) {
        super(subsystem);
        
        this.color = c;
    }

    protected void initialize() {
    	strip.setColor(color);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
