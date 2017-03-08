package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder.State;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Climber extends Subsystem {
	private final CANTalon motor;
	
	public enum State {
		CLIMB(1), STOP(0);
		
		private final double value;
		
		private State(double value) {
			assert value >= 0 : "Don't drive the climber in reverse!";
			if(value < 0) {
				Logger.defaultLogger.error("Don't drive the climber in reverse!");
				this.value = 0;
			} else {
				this.value = value;
			}
		}
		
		private double getPercentVbusMode() {
			return value;
		}
		
		private double getCurrentMode() {
			return value * RobotMap.getInstance().CLIMBER_MAX_CURRENT.get();
		}
	}
	
	public Climber(){
		motor = new CANTalon(RobotMap.getInstance().CAN_CLIMBER_MOTOR.get());
		motor.ConfigRevLimitSwitchNormallyOpen(false);
		LiveWindow.addActuator("climber", "climber", motor);
	}
	
    public void set(State state) {
    	if(motor.getControlMode() == TalonControlMode.PercentVbus) {
    		Logger.defaultLogger.debug("Setting climber to " + state.getPercentVbusMode() + "%.");
    		motor.set(state.getPercentVbusMode());
    	} else if(motor.getControlMode() == TalonControlMode.Current) {
    		Logger.defaultLogger.debug("Setting climber to " + state.getCurrentMode() + "A.");
    		motor.set(state.getCurrentMode());
    	} else {
    		assert false;
    	}
	}
    
    public void enableCurrentPid() {
    	motor.changeControlMode(TalonControlMode.Current);
    	motor.configPeakOutputVoltage(+12.0f, -12.0f);
    	motor.setProfile(0);
    	motor.setP(RobotMap.getInstance().CLIMBER_PID_KP.get());
    	motor.setI(RobotMap.getInstance().CLIMBER_PID_KI.get());
    	motor.setD(RobotMap.getInstance().CLIMBER_PID_KD.get());
    	motor.setF(RobotMap.getInstance().CLIMBER_PID_KF.get());
    	Logger.defaultLogger.debug("Climber current closed-loop PID enabled.");
    }
    
    public void disableCurrentPid() {
    	motor.set(0.0);
    	motor.changeControlMode(TalonControlMode.PercentVbus);
    	Logger.defaultLogger.debug("Climber current closed-loop PID disabled.");
    }
    
    public double getOutputCurrent() {
    	return motor.getOutputCurrent();
    }
    
    public double getOutputVoltage() {
    	return motor.getOutputVoltage();
    }
    
    public boolean isLimitSwitchPressed() {
    	return motor.isFwdLimitSwitchClosed();
    }
    
    public void diagnose() {
		if (motor.getBusVoltage() != 4.0) {
			if (motor.getStickyFaultOverTemp() != 0) {
				Logger.defaultLogger.warn("Climber motor has over-temperature sticky bit set.");
			}
			if (motor.getStickyFaultUnderVoltage() != 0) {
				Logger.defaultLogger.warn("Climber motor has under-voltage sticky bit set.");
			}
		} else {
			Logger.defaultLogger.warn("Climber motor controller not reachable over CAN.");
			Logger.voice.warn("check climber");
		}
		if (!motor.isAlive()) {
			Logger.defaultLogger.warn("Climber motor is stopped by motor safety.");
		}
    }
    
    public void clearStickyFaults() {
    	motor.clearStickyFaults();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

