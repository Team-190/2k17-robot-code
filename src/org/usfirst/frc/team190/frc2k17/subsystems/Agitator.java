package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Agitator extends Subsystem {

	private final CANTalon motor;
	
	public enum State {
		ON(1), OFF(0);
		
		private final double value;
		
		private State(double value) {
			this.value = value;
		}
		
		private double get() {
			return value;
		}
	}
	
	public Agitator() {
		motor = new CANTalon(RobotMap.getInstance().CAN_AGITATOR_MOTOR.get());
		LiveWindow.addActuator("shooting", "agitator", motor);
		diagnose();
	}
	
	// TODO: implement speed control for collector
	
	public void set(State state) {
		motor.set(state.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void diagnose() {
		if (motor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Agitator motor has over-temperature sticky bit set.");
		}
		if (motor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Agitator motor has under-voltage sticky bit set.");
		}
		if (!motor.isAlive()) {
			Logger.defaultLogger.warn("Agitator motor is stopped by motor safety.");
		}
    }
}

