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
	Color color;
	
    public LEDStripBlink(LEDStrip subsystem, Color color) {
    	super(subsystem);
    	this.color = color;
    }
    
    protected void initialize() {
    	strip.setColor(0);
    	on = false;
    	timer = new Timer();
    	timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(on) {
					strip.setColor(0);
					on = false;
				} else {
					strip.setColor(color);
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
