package org.usfirst.frc.team190.frc2k17.triggers;

import org.usfirst.frc.team190.frc2k17.OI;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PovUpTrigger extends Trigger {

    public boolean get() {
    	int pov = OI.joystick2.getPOV(0);
        return pov != -1 && (pov < 90 || pov > 270);
    }
}
