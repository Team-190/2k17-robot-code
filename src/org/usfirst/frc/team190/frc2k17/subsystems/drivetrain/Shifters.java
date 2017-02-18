package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

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
		shifter = new Solenoid(RobotMap.CAN.PCM, RobotMap.PCM.SHIFTER);
	}
    
	public void initDefaultCommand() {
		shift(Gear.LOW);
    }
    
	/**
	 * Shift the transmission into the specified gear.
	 * @param gear the gear to shift into
	 */
	public void shift(Gear gear){
		if(gear == Gear.HIGH)
		{
			shifter.set(true);
		}
		else if(gear == Gear.LOW)
		{
			shifter.set(false);
		}
	}
	
	public boolean isInHighGear() {
		if (shifter.get()) {
			return true;
		} else {
			return false;
		}
	}
    
}

