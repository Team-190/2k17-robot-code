package org.usfirst.frc.team190.frc2k17.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PovUpTrigger extends Trigger {

	private final GenericHID joystick;

	public PovUpTrigger(GenericHID joystick) {
		this.joystick = joystick;
	}

    public boolean get() {
    		int pov = joystick.getPOV(0);
        return pov != -1 && (pov < 90 || pov > 270);
    }
}
