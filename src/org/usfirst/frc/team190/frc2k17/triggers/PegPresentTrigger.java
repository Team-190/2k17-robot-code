package org.usfirst.frc.team190.frc2k17.triggers;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PegPresentTrigger extends Trigger {
	
	private static boolean enabled = true;
	private static Instant lastTrigger = null; // Last time this trigger returned true or the gear placer was otherwise activated
	private static Instant pressedSince = null; // the instant since which the limit switch has been continuously pressed; null if the limit
												// switch is not currently pressed

	public static void setEnabled(boolean enabled) {
		PegPresentTrigger.enabled = enabled;
		if(enabled) {
			Logger.defaultLogger.info("Auto-kick enabled.");
		} else {
			Logger.defaultLogger.info("Auto-kick disabled.");
		}
	}
	
    public boolean get() {
    	
    	if(Robot.gearPlacer.getPegPresent()) {
    		if (pressedSince == null) {
    			pressedSince = Instant.now();
    		}
			if (enabled && Duration.between(pressedSince, Instant.now()).toMillis() > RobotMap.getInstance().PEG_PRESENT_TRIGGER_DELAY.get()
					&& (lastTrigger == null || Duration.between(lastTrigger, Instant.now())
							.toMillis() > RobotMap.getInstance().PEG_PRESENT_COOLDOWN.get())) {
    			startCooldown();
    			return true;
    		}
    	} else {
    		pressedSince = null;
    	}
    	
    	return false;
    	
    }
    
    public void startCooldown() {
    	lastTrigger = Instant.now();
    }
}
