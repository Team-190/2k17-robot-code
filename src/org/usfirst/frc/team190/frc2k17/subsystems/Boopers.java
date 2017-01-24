package org.usfirst.frc.team190.frc2k17.subsystems;


import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.commands.boopers.BooperOffCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Boopers extends Subsystem {

	private final DoubleSolenoid boopersDoubleSolenoid;
	
	public Boopers() {
		boopersDoubleSolenoid = new DoubleSolenoid(RobotMap.PCM.BOOPERS_PULL_IN, RobotMap.PCM.BOOPERS_PUSH_OUT);
	}


	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new BooperOffCommand());
    }
    
    public void forward() {
    	boopersDoubleSolenoid.set(DoubleSolenoid.Value.kForward);    	
    }
    
    public void reverse() {
    	boopersDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void off() {
    	boopersDoubleSolenoid.set(DoubleSolenoid.Value.kOff);
    }
}

