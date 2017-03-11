package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 *
 */
public class LEDStripAllianceColor extends LEDStripCommand {

	private Color color;
	private boolean blinkTimer1Started, blinkTimer2Started, blinkOn;
	private Timer blinkTimer;
	
    public LEDStripAllianceColor(LEDStrip subsystem) {
        super(subsystem);
    }

    protected void initialize() {
    	blinkTimer = new Timer();
    	blinkTimer1Started = false;
    	blinkTimer2Started = false;
    	boolean blinkOn = true;
    	if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
    		color = Color.RED;
    	} else {
    		color = Color.BLUE;
    	}
    	strip.setColor(color);
    }
    
    protected void execute() {
		if (blinkTimer1Started && !(DriverStation.getInstance().isAutonomous()
				|| (DriverStation.getInstance().getMatchTime() <= RobotMap.getInstance().LED_CLIMBING_SIGNAL_TIME.get()
						&& DriverStation.getInstance().getMatchTime() >= 0))) {
			blinkTimer.cancel();
			blinkTimer = new Timer();
			blinkTimer1Started = false;
			strip.setColor(color);
		}
		if (blinkTimer2Started && !(Robot.gearPlacer.get() == GearPlacer.State.EXTENDED)) {
			blinkTimer.cancel();
			blinkTimer = new Timer();
			blinkTimer2Started = false;
			strip.setColor(color);
		}
		if (!blinkTimer1Started && (DriverStation.getInstance().isAutonomous()
				|| (DriverStation.getInstance().getMatchTime() <= RobotMap.getInstance().LED_CLIMBING_SIGNAL_TIME.get()
						&& DriverStation.getInstance().getMatchTime() >= 0))) {
			blinkTimer.schedule(new TimerTask() {

    			@Override
    			public void run() {
    				if(blinkOn) {
    					strip.setColor(0);
    					blinkOn = false;
    				} else {
    					strip.setColor(color);
    					blinkOn = true;
    				}
    			}
        		
        	}, 0, 1000);
    		blinkTimer1Started = true;
    	}
		if (!blinkTimer2Started && Robot.gearPlacer.get() == GearPlacer.State.EXTENDED) {
			blinkTimer.schedule(new TimerTask() {

    			@Override
    			public void run() {
    				if(blinkOn) {
    					strip.setColor(0);
    					blinkOn = false;
    				} else {
    					strip.setColor(color);
    					blinkOn = true;
    				}
    			}
        		
        	}, 0, 250);
    		blinkTimer2Started = true;
    	}
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	blinkTimer.cancel();
    }
    
    protected void interrupted() {
    	end();
    }
}
