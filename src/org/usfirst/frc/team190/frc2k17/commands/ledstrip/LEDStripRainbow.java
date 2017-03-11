package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripRainbow extends LEDStripCommand {
	
	public LEDStripRainbow(LEDStrip subsystem) {
        super(subsystem);
    }

    protected void initialize() {}

    protected void execute() {
    	strip.updateRainbow();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
