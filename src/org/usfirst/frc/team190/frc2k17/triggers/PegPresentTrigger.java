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
	private Instant lastTrigger = null; // Last time limit switch was triggered

	public static void setEnabled(boolean enabled) {
		PegPresentTrigger.enabled = enabled;
		if(enabled) {
			Logger.defaultLogger.info("Auto-kick enabled.");
		} else {
			Logger.defaultLogger.info("Auto-kick disabled.");
		}
	}
	
    public boolean get() {
    	
    	boolean pegPresent = Robot.gearPlacer.getPegPresent(); // Status of limit switch (true if pressed)
    	
    	
    	if(pegPresent && enabled) {
    		
    		long pegCooldown = RobotMap.getInstance().PEG_PRESENT_COOLDOWN.get();
    		
    		if(lastTrigger == null || Duration.between(lastTrigger, Instant.now()).compareTo(Duration.ofMillis(pegCooldown)) > 0) {
    			lastTrigger = Instant.now();
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
}
