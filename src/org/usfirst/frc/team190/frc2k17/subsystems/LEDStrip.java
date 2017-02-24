package org.usfirst.frc.team190.frc2k17.subsystems;

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
	 * @param g The PWM port for the G channel
	 * @param b The PWM port for the B channel
	 */
    public LEDStrip(int r, int g, int b) {
    	rPWM = new PWM(r);
    	gPWM = new PWM(g);
    	bPWM = new PWM(b);
	}
    
    /**
     * Sets the LED strip's color
     * @param r R part (0 - 255)
     * @param g G part (0 - 255)
     * @param b B part (0 - 255)
     */
    public void setColor(int r, int g, int b) {
    	rPWM.setRaw(r);
    	gPWM.setRaw(g);
    	bPWM.setRaw(b);
    }
    
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
            case 0:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (t * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 1:
                r = (int) (q * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 2:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (t * 255.0f + 0.5f);
                break;
            case 3:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (q * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 4:
                r = (int) (t * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 5:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (q * 255.0f + 0.5f);
                break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }
    
    /**
     * Returns the red component in the range 0-255 in the default sRGB
     * space.
     * @return the red component.
     */
    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     * @return the green component.
     */
    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     * @return the blue component.
     */
    public static int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LEDStripRainbow(this));
    }
}

