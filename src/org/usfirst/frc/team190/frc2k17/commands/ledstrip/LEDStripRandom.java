package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripRandom extends LEDStripCommand {

    public LEDStripRandom(LEDStrip subsystem) {
        super(subsystem);
    }
    
    protected void execute() {
    	strip.setRandomColor();
    }

    protected boolean isFinished() {
        return false;
    }
}
