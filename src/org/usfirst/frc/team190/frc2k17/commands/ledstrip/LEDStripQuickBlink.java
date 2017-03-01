package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

/**
 *
 */
public class LEDStripQuickBlink extends LEDStripCommand {

	int count;
	boolean on;
	Timer timer;
	Color color;
	
    public LEDStripQuickBlink(LEDStrip subsystem, Color color) {
    	super(subsystem);
    	this.color = color;
    }
    
    protected void initialize() {
    	count = 3;
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
					count--;
				}
			}
    		
    	}, 0, 50);
    }
    
	protected boolean isFinished() {
		return count <= 0;
	}
	
	protected void end() {
		timer.cancel();
	}
	
	protected void interrupted() {
		end();
	}
}
