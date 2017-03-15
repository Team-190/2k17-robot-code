package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 *
 */
public class LEDStripAllianceColor extends LEDStripCommand {

	private Color color;
	private boolean blinkTimerStarted, blinkOn;
	private Timer blinkTimer;
	
    public LEDStripAllianceColor(LEDStrip subsystem) {
        super(subsystem);
    }

    protected void initialize() {
    	blinkTimer = new Timer();
    	blinkTimerStarted = false;
    	boolean blinkOn = true;
    	if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
    		color = Color.RED;
    	} else {
    		color = Color.BLUE;
    	}
    	strip.setColor(color);
    }
    
    protected void execute() {
		if (blinkTimerStarted && !(DriverStation.getInstance().isAutonomous()
				|| (DriverStation.getInstance().getMatchTime() <= RobotMap.getInstance().LED_CLIMBING_SIGNAL_TIME.get()
						&& DriverStation.getInstance().getMatchTime() >= 0))) {
			blinkTimer.cancel();
			blinkTimer = new Timer();
			blinkTimerStarted = false;
			strip.setColor(color);
		}
		if (!blinkTimerStarted && (DriverStation.getInstance().isAutonomous()
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
    		blinkTimerStarted = true;
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
