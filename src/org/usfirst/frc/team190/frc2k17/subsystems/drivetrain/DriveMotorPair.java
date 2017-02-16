package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveMotorPair {
	
	private CANTalon master;
	private CANTalon slave;
	private String name;
	private boolean inverted;

	public DriveMotorPair(String name, int masterID, int slaveID, boolean inverted) {
		master = new CANTalon(masterID);
		master.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		master.configNominalOutputVoltage(+0.0f, -0.0f);
		master.configPeakOutputVoltage(+12.0f, -12.0f);
		master.setInverted(inverted);
		//TODO: set the reverseSensor() on the encoders. I believe it's opposite the inverted flag.
		//TODO: after setting reverseSensor(), delete the corrections in the position functions
		master.setProfile(0);
		master.setP(RobotMap.Constants.Drivetrain.PID.SPEED_KP);
		master.setI(RobotMap.Constants.Drivetrain.PID.SPEED_KI);
		master.setD(RobotMap.Constants.Drivetrain.PID.SPEED_KD);
		master.setF(RobotMap.Constants.Drivetrain.PID.SPEED_KF);
		master.changeControlMode(TalonControlMode.Speed);
		LiveWindow.addActuator("Drive Train", name + " motor", master);
		
		slave = new CANTalon(slaveID);
		slave.changeControlMode(CANTalon.TalonControlMode.Follower);
		slave.set(master.getDeviceID());
		this.name = name;
		this.inverted = inverted;
	}

	public void set(double speed) {
		master.set(speed);
	}
	
	public double getSpeed() {
		return master.getSpeed();
	}
	
	public double getClosedLoopError() {
		return master.getClosedLoopError();
	}
	
	/**
	 * Get the encoder position in inches
	 * @return encoder position in inches
	 */
	public double getEncoderPosition() {
		double pos = ticksToInches(master.getEncPosition());
		return inverted ? pos : -pos;
	}
	
	public void setEncoderPosition(int position) {
		master.setEncPosition(position);
	}
	
	private double inchesToTicks(double inches) {
		return inches / RobotMap.Constants.Drivetrain.PID.INCHES_PER_TICK;
	}
	
	private double ticksToInches(double ticks) {
		return ticks * RobotMap.Constants.Drivetrain.PID.INCHES_PER_TICK;
	}
	
	public void diagnose() {
		if (master.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn(name + "front drivetrain motor has over-temperature sticky bit set.");
		}
		if (master.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn(name + " front drivetrain motor has under-voltage sticky bit set.");
		}
		if (slave.getStickyFaultOverTemp() != 0) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor has over-temperature sticky bit set.");
		}
		if (slave.getStickyFaultUnderVoltage() != 0) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor has under-voltage sticky bit set.");
		}
		if (!master.isAlive()) {
			Logger.defaultLogger.warn(name + " front drivetrain motor is stopped by motor safety.");
		}
		if (!slave.isAlive()) {
			Logger.defaultLogger.warn(name + " rear drivetrain motor is stopped by motor safety.");
		}
		if (master.isSensorPresent(FeedbackDevice.QuadEncoder) != FeedbackDeviceStatus.FeedbackStatusPresent) {
			Logger.defaultLogger.warn(name + " drivetrain encoder not present.");
		} else {
			Logger.defaultLogger.debug(name + " drivetrain encoder is present.");
		}
	}	
}
