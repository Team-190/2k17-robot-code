package org.usfirst.frc.team190.frc2k17.subsystems;

import java.awt.Color;

import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripRainbow;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDStrip extends Subsystem {
	
	private final PWM rPWM;
	private final PWM gPWM;
	private final PWM bPWM;

	/**
	 * Constructor
	 * @param r The PWM port for the R channel
	 * @param g The PWM port for the R channel
	 * @param b The PWM port for the R channel
	 */
    public LEDStrip(int r, int g, int b) {
    	rPWM = new PWM(r);
    	gPWM = new PWM(g);
    	bPWM = new PWM(b);
	}
    
    /**
     * Set the color of the LED strip
     * @param color the color
     */
    public void setColor(Color color) {
    	rPWM.setRaw(color.getRed());
    	gPWM.setRaw(color.getBlue());
    	bPWM.setRaw(color.getGreen());
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LEDStripRainbow(this));
    }
}

