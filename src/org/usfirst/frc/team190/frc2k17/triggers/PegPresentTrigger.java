package org.usfirst.frc.team190.frc2k17.triggers;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PegPresentTrigger extends Trigger {

    public boolean get() {
        return Robot.gearPlacer.getPegPresent();
    }
}
