package org.usfirst.frc.team190.frc2k17.subsystems;


import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Boopers extends Subsystem {

	private final Solenoid boopersSingleSolenoid;
	
	public Boopers() {
		boopersSingleSolenoid = new Solenoid(RobotMap.CAN.PCM, RobotMap.PCM.BOOPERS_PUSH_OUT);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void on() {
    	boopersSingleSolenoid.set(true);
    }
    
    public void off() {
    	boopersSingleSolenoid.set(false);
    }
    
    // TODO: implement diagnose for Boopers
}

