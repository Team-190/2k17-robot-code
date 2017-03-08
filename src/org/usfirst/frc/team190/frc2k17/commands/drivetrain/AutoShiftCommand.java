package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.DSPFilter;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShiftCommand extends Command {
	
	private DSPFilter rpmSig;
	private DSPFilter rateRPM; //derivative of RPM
	private DSPFilter rpmSetpoint; 
	private Instant lastShifted = Instant.now(), pastThresholdSince;
	private double lastRPM = 0;
		
    public AutoShiftCommand() {
    	lastRPM = getAvgRPM();
    	rpmSig = new DSPFilter(DSPFilter.FilterType.LOW_PASS,RobotMap.getInstance().AUTOSHIFT_RPM_FREQ_CUTOFF.get(),lastRPM,RobotMap.getInstance().AUTOSHIFT_SAMPLE_RATE.get());
    	rateRPM = new DSPFilter(DSPFilter.FilterType.LOW_PASS,RobotMap.getInstance().AUTOSHIFT_RATE_RPM_FREQ_CUTOFF.get(),0,RobotMap.getInstance().AUTOSHIFT_SAMPLE_RATE.get());
    	//TODO: implement rpmSetpoint
    }
    
    private double getAvgRPM() {
    	return (Math.abs(Robot.drivetrain.getLeftRPM())+Math.abs(Robot.drivetrain.getLeftRPM()))/2.0;
    }
    
    protected void initialize() {
    	Logger.defaultLogger.info("Auto-shifting activated.");
    }
    
    protected void execute() {
    	double currentRPM = rpmSig.processNextPoint(getAvgRPM());
    	double newRateChange = (currentRPM - lastRPM)*RobotMap.getInstance().AUTOSHIFT_SAMPLE_RATE.get(); 
    	double rateChange = rateRPM.processNextPoint(newRateChange);
    	if(isBetween(RobotMap.getInstance().AUTOSHIFT_MIDDLE_THRESHOLD.get(),lastRPM,currentRPM))
    	{
    		//over transition point
    		if(Math.abs(Robot.drivetrain.getAverageSetpoint()) > RobotMap.getInstance().AUTOSHIFT_MIDDLE_THRESHOLD.get())
    		{
    			//check to shift high
    			if(Math.abs(rateChange) > RobotMap.getInstance().AUTOSHIFT_MIDDLE_THRESHOLD_RATE.get() && Duration.between(lastShifted, Instant.now()).toMillis() > RobotMap.getInstance().AUTOSHIFT_COOLDOWN.get())
    			{
    				(new ShiftersShiftCommand(Shifters.Gear.HIGH)).start();
    				lastShifted = Instant.now();
    				Logger.defaultLogger.trace("Shifting to high gear due to passing the middle threshold. Current RPM: " + currentRPM);
    			}
    		}
    	}
    	else if(Math.abs(currentRPM) > RobotMap.getInstance().AUTOSHIFT_UPPER_THRESHOLD.get() && Robot.shifters.getGear() == Shifters.Gear.LOW && Duration.between(lastShifted, Instant.now()).toMillis() > RobotMap.getInstance().AUTOSHIFT_COOLDOWN.get())
    	{
    		if(pastThresholdSince == null) {
    			pastThresholdSince = Instant.now();
    		} else if(Duration.between(pastThresholdSince, Instant.now()).toMillis() > RobotMap.getInstance().AUTOSHIFT_UPPER_THRESHOLD_DELAY.get()) {
    			(new ShiftersShiftCommand(Shifters.Gear.HIGH)).start();
        		lastShifted = Instant.now();
        		Logger.defaultLogger.trace("Shifting to high gear due to passing the upper threshold of " + RobotMap.getInstance().AUTOSHIFT_UPPER_THRESHOLD.get() + " RPM. Current RPM: " + currentRPM);
    		}
    	}
    	else if(Math.abs(currentRPM) < RobotMap.getInstance().AUTOSHIFT_LOWER_THRESHOLD.get() && Robot.shifters.getGear() == Shifters.Gear.HIGH && Duration.between(lastShifted, Instant.now()).toMillis() > RobotMap.getInstance().AUTOSHIFT_COOLDOWN.get())
    	{
    		if(pastThresholdSince == null) {
    			pastThresholdSince = Instant.now();
    		} else if(Duration.between(pastThresholdSince, Instant.now()).toMillis() > RobotMap.getInstance().AUTOSHIFT_LOWER_THRESHOLD_DELAY.get()) {
    			(new ShiftersShiftCommand(Shifters.Gear.LOW)).start();
				lastShifted = Instant.now();
				Logger.defaultLogger.trace("Shifting to low gear due to passing the lower threshold of " + RobotMap.getInstance().AUTOSHIFT_LOWER_THRESHOLD.get() + " RPM. Current RPM: " + currentRPM);
    		}
    	}
    	else
    	{
    		pastThresholdSince = null;
    	}
    	lastRPM = currentRPM;
    }
    
    private boolean isBetween(double setpoint, double pointOne, double pointTwo)
    {
    	return ((pointTwo>=setpoint) && (pointOne<=setpoint)) || ((pointTwo<=setpoint) && (pointOne>=setpoint));
    }
    
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Logger.defaultLogger.info("Auto-shifting deactivated.");
    	if(!DriverStation.getInstance().isDisabled()) {
    		Logger.voice.info("manual");
    	}
    }

    protected void interrupted() {
    	end(); //Might want to add more elegant interrupt handling later
    }
}
