package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripBlink extends LEDStripCommand {

	boolean on;
	Timer timer;
	
    public LEDStripBlink(LEDStrip subsystem) {
    	super(subsystem);
    	timer = new Timer();
    }
    
    protected void initialize() {
    	strip.setColor(0);
    	timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(on) {
					strip.setColor(0);
					on = false;
				} else {
					strip.setColor(Color.MAGENTA);
					on = true;
				}
			}
    		
    	}, 0, 500);
    }
    
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		timer.cancel();
	}
	
	protected void interrupted() {
		end();
	}
}
