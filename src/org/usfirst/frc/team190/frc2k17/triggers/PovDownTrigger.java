package org.usfirst.frc.team190.frc2k17.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PovDownTrigger extends Trigger {

	private final GenericHID joystick;

	public PovDownTrigger(GenericHID joystick) {
		this.joystick = joystick;
	}

	public boolean get() {
		int pov = joystick.getPOV(0);
		return pov != -1 && (pov > 90 && pov < 270);
	}
}
