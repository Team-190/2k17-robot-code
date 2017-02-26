package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripAllianceColor;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripRainbow;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripSetColor;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LEDStrip extends Subsystem {
	
	private final DigitalOutput rChannel;
	private final DigitalOutput gChannel;
	private final DigitalOutput bChannel;
	private float currentHue = 0;

	/**
	 * Constructor
	 * @param r The DIO port for the R channel
	 * @param g The DIO port for the G channel
	 * @param b The DIO port for the B channel
	 */
    public LEDStrip(int r, int g, int b) {
    	rChannel = new DigitalOutput(r);
    	gChannel = new DigitalOutput(g);
    	bChannel = new DigitalOutput(b);
    	
    	// Set PWM rate in Hz (for all channels)
    	rChannel.setPWMRate(100);
    	
    	// Enable PWM channels
    	rChannel.enablePWM(1.0);
    	gChannel.enablePWM(0.0);
    	bChannel.enablePWM(1.0);
	}
    
    /**
     * Sets the LED strip's color
     * @param r R part (0 - 255)
     * @param g G part (0 - 255)
     * @param b B part (0 - 255)
     */
    public void setColor(int r, int g, int b) {
    	rChannel.updateDutyCycle(r / 255.0);
    	gChannel.updateDutyCycle(g / 255.0);
    	bChannel.updateDutyCycle(b / 255.0);
    }
    
    public void setColor(int rgb) {
    	int r = LEDStrip.getRed(rgb);
    	int g = LEDStrip.getGreen(rgb);
    	int b = LEDStrip.getBlue(rgb);
    	
    	setColor(r, g, b);
    }
    
    /**
     * Updates the LED strip to iterate over the hue range
     */
    public void updateRainbow() {
    	int rgb = HSBtoRGB(currentHue, 1, 1);
    	setColor(rgb);
    	
    	currentHue += 0.001f;
    	if (currentHue > 1.0) {
    		currentHue = 0.0f;
    	}
    }
    
    /**
     * Generates a random color and sets it
     */
    public void setRandomColor() {
    	int r = (int)(Math.random() * 255);
    	int g = (int)(Math.random() * 255);
    	int b = (int)(Math.random() * 255);
    	
    	setColor(r, g, b);
    }
    
    /**
     * Convert a color in HSB space to RGB
     * @param hue Hue part (0 .. 1)
     * @param saturation Saturation (0 .. 1)
     * @param brightness Brightness (0 .. 1)
     * @return An encoded RGB color (use getRed, getGreen, getBlue to decode)
     */
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
        return correctGamma((rgb >> 16) & 0xFF);
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     * @return the green component.
     */
    public static int getGreen(int rgb) {
        return correctGamma((rgb >> 8) & 0xFF);
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     * @return the blue component.
     */
    public static int getBlue(int rgb) {
        return correctGamma((rgb >> 0) & 0xFF);
    }
    
    /**
     * Correct for nonlinearity in human eyes' perception of color
     * @param color Color value (0 - 255)
     * @return Gamma corrected color value (0 - 255)
     * @see https://learn.adafruit.com/led-tricks-gamma-correction/the-issue
     */
    public static int correctGamma(int color) {
    	int gamma8[] = {
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1,  1,
    		    1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  2,  2,  2,  2,  2,  2,
    		    2,  3,  3,  3,  3,  3,  3,  3,  4,  4,  4,  4,  4,  5,  5,  5,
    		    5,  6,  6,  6,  6,  7,  7,  7,  7,  8,  8,  8,  9,  9,  9, 10,
    		   10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 15, 15, 16, 16,
    		   17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 24, 24, 25,
    		   25, 26, 27, 27, 28, 29, 29, 30, 31, 32, 32, 33, 34, 35, 35, 36,
    		   37, 38, 39, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 50,
    		   51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 66, 67, 68,
    		   69, 70, 72, 73, 74, 75, 77, 78, 79, 81, 82, 83, 85, 86, 87, 89,
    		   90, 92, 93, 95, 96, 98, 99,101,102,104,105,107,109,110,112,114,
    		  115,117,119,120,122,124,126,127,129,131,133,135,137,138,140,142,
    		  144,146,148,150,152,154,156,158,160,162,164,167,169,171,173,175,
    		  177,180,182,184,186,189,191,193,196,198,200,203,205,208,210,213,
    		  215,218,220,223,225,228,231,233,236,239,241,244,247,249,252,255 };
    	
    	return gamma8[color];
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LEDStripAllianceColor(this));
    }
}

