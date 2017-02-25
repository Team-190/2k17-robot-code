package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDStripAllianceColor extends LEDStripCommand {

    public LEDStripAllianceColor(LEDStrip subsystem) {
        super(subsystem);
    }

    protected void initialize() {
    	if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
    		strip.setColor(255, 0, 0);
    	} else {
    		strip.setColor(0, 0, 255);
    	}
    }
    
    protected boolean isFinished() {
        return false;
    }
}
