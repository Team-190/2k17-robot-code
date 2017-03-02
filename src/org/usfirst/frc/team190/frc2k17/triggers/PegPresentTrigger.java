package org.usfirst.frc.team190.frc2k17.triggers;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PegPresentTrigger extends Trigger {
	
	private static boolean enabled = true;

	public static void setEnabled(boolean enabled) {
		PegPresentTrigger.enabled = enabled;
	}
	
    public boolean get() {
        return enabled && Robot.gearPlacer.getPegPresent();
    }
}
