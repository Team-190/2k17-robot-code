package org.usfirst.frc.team190.frc2k17;

public class Color {
	
	private int r, g, b;

	/**
     * The color white.  In the default sRGB space.
     */
    public final static Color WHITE = new Color(255, 255, 255);

    /**
     * The color light gray.  In the default sRGB space.
     */
    public final static Color LIGHT_GRAY = new Color(192, 192, 192);

    /**
     * The color gray.  In the default sRGB space.
     */
    public final static Color GRAY = new Color(128, 128, 128);

    /**
     * The color dark gray.  In the default sRGB space.
     */
    public final static Color DARK_GRAY = new Color(64, 64, 64);

    /**
     * The color black.  In the default sRGB space.
     */
    public final static Color BLACK = new Color(0, 0, 0);

    /**
     * The color red.  In the default sRGB space.
     */
    public final static Color RED = new Color(255, 0, 0);

    /**
     * The color pink.  In the default sRGB space.
     */
    public final static Color PINK = new Color(255, 175, 175);

    /**
     * The color orange.  In the default sRGB space.
     */
    public final static Color YELLOW = new Color(255, 200, 0);

    /**
     * The color green.  In the default sRGB space.
     */
    public final static Color GREEN = new Color(0, 255, 0);

    /**
     * The color magenta, fixed to look better on the LED strips.
     */
    public final static Color MAGENTA = new Color(255, 0, 100);

    /**
     * The color cyan.  In the default sRGB space.
     */
    public final static Color CYAN = new Color(0, 255, 255);

    /**
     * The color blue.  In the default sRGB space.
     */
    public final static Color BLUE = new Color(0, 0, 255);

	public Color(int r, int g, int b) {
		this.r = Math.min(255, r);
	    this.g = Math.min(255, g);
	    this.b = Math.min(255, b);
	}
	
	public int getR() {
		return r;
	}
	
	public int getG() {
		return g;
	}
	
	public int getB() {
		return b;
	}

}
