package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
    
    private final DoubleSolenoid gearPusher;
    private final DigitalInput gearPusherSensor;
    
    public Gear(){
    	
    	gearPusher = new DoubleSolenoid(RobotMap.PCM.GEAR_PUSH_IN, RobotMap.PCM.GEAR_PUSH_OUT);
    	
    	gearPusherSensor = new DigitalInput(RobotMap.DIO.GEAR_PUSHER_SENSOR);
    	
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

