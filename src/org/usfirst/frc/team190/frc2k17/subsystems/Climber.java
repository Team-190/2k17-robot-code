package org.usfirst.frc.team190.frc2k17.subsystems;

import org.usfirst.frc.team190.frc2k17.Logger;
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
	private final CANTalon climberMotor;
	
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
		climberMotor = new CANTalon(RobotMap.getInstance().CAN_CLIMBER_MOTOR.get());
		climberMotor.ConfigRevLimitSwitchNormallyOpen(false);
		LiveWindow.addActuator("climber", "climber", climberMotor);
		diagnose();
	}
	
    public void set(State state) {
    	if(climberMotor.getControlMode() == TalonControlMode.PercentVbus) {
    		Logger.defaultLogger.debug("Setting climber to " + state.getPercentVbusMode() + "%.");
    		climberMotor.set(state.getPercentVbusMode());
    	} else if(climberMotor.getControlMode() == TalonControlMode.Current) {
    		Logger.defaultLogger.debug("Setting climber to " + state.getCurrentMode() + "A.");
    		climberMotor.set(state.getCurrentMode());
    	} else {
    		assert false;
    	}
	}
    
    public void enableCurrentPid() {
    	climberMotor.changeControlMode(TalonControlMode.Current);
    	climberMotor.configPeakOutputVoltage(+12.0f, -12.0f);
    	climberMotor.setProfile(0);
    	climberMotor.setP(RobotMap.getInstance().CLIMBER_PID_KP.get());
    	climberMotor.setI(RobotMap.getInstance().CLIMBER_PID_KI.get());
    	climberMotor.setD(RobotMap.getInstance().CLIMBER_PID_KD.get());
    	climberMotor.setF(RobotMap.getInstance().CLIMBER_PID_KF.get());
    	Logger.defaultLogger.debug("Climber current closed-loop PID enabled.");
    }
    
    public void disableCurrentPid() {
    	climberMotor.set(0.0);
    	climberMotor.changeControlMode(TalonControlMode.PercentVbus);
    	Logger.defaultLogger.debug("Climber current closed-loop PID disabled.");
    }
    
    public double getOutputCurrent() {
    	return climberMotor.getOutputCurrent();
    }
    
    public double getOutputVoltage() {
    	return climberMotor.getOutputVoltage();
    }
    
    public boolean isLimitSwitchPressed() {
    	return climberMotor.isFwdLimitSwitchClosed();
    }
    
    public void diagnose() {
		if (climberMotor.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn("Collector motor has over-temperature sticky bit set.");
		}
		if (climberMotor.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn("Collector motor has under-voltage sticky bit set.");
		}
		if (!climberMotor.isAlive()) {
			Logger.defaultLogger.warn("Collector motor is stopped by motor safety.");
		}
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

