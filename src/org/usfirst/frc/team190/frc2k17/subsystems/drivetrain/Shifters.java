package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shifters extends Subsystem {

    /**
	 * The gears that the transmission may be shifted into.
	 */
	public enum Gear {
		HIGH, LOW
	}

	private Solenoid shifter;
	
	public Shifters() {
		shifter = new Solenoid(RobotMap.getInstance().CAN_PCM.get(), RobotMap.getInstance().PCM_SHIFTER.get());
		shift(Gear.LOW);
		LiveWindow.addActuator("drivetrain", "shifters", shifter);
	}
    
	public void initDefaultCommand() {
    }
    
	/**
	 * Shift the transmission into the specified gear.
	 * @param gear the gear to shift into
	 */
	public void shift(Gear gear){
		if(gear == Gear.HIGH) {
			shifter.set(true);
		} else if(gear == Gear.LOW) {
			shifter.set(false);
		}
	}
	
    public Gear getGear() {
    	if(!Robot.isKitBot() && shifter.get()) {
    		return Gear.HIGH;
    	} else {
    		return Gear.LOW;
    	}
    }
}

