package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearPlacer extends Subsystem {
    
    private final DoubleSolenoid gearPusher;
    private final DigitalInput gearPusherSensor;
    // TODO: implement GearPlacer methods and commands
    public GearPlacer(){
    	
    	gearPusher = new DoubleSolenoid(RobotMap.PCM.GEAR_PUSH_IN, RobotMap.PCM.GEAR_PUSH_OUT);
    	gearPusherSensor = new DigitalInput(RobotMap.DIO.GEAR_PUSHER_SENSOR);
    	
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void PushGearOut(){
    	gearPusher.set(Value.kForward);
    }
    
    public void RetractGearPiston(){			// Retract piston once reed switch "sees" change in state
    	if(gearPusherSensor.get()){
    		gearPusher.set(Value.kReverse);
    	}
    }
   
    
    // TODO: implement diagnose for GearPlacer
}

