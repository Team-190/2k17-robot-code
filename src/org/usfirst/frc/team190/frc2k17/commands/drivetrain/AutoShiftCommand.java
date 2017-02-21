package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.time.Duration;
import java.time.Instant;

import org.usfirst.frc.team190.frc2k17.DSPFilter;
import org.usfirst.frc.team190.frc2k17.Logger;
import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShiftCommand extends Command {
	
	private DSPFilter rpmSig;
	private DSPFilter rateRPM; //derivative of RPM
	private DSPFilter rpmSetpoint; 
	private Instant lastShifted = Instant.now(), pastThresholdSince;
	private final double RPM_FREQ_CUTOFF = 10; //hz; Max is 25 hz because 50 hz sampling
	private final double RATE_RPM_FREQ_CUTOFF = 10;
	private final double SAMPLE_RATE = 50; //hz
	private double lastRPM = 0;
	private final double COOLDOWN = 200, DELAY = 500, RPM_TRANSITION = 300, LOWER_THRESHOLD = 250, UPPER_THRESHOLD = 370, RATE_THRESHOLD = 250; //delta(RPM)/sec 
	
    public AutoShiftCommand() {
    	lastRPM = getAvgRPM();
    	rpmSig = new DSPFilter(DSPFilter.FilterType.LOW_PASS,RPM_FREQ_CUTOFF,lastRPM,SAMPLE_RATE);
    	rateRPM = new DSPFilter(DSPFilter.FilterType.LOW_PASS,RATE_RPM_FREQ_CUTOFF,0,SAMPLE_RATE);
    	//TODO: implement rpmSetpoint
    }
    
    private double getAvgRPM() {
    	return (Math.abs(Robot.drivetrain.getLeftRPM())+Math.abs(Robot.drivetrain.getLeftRPM()))/2.0;
    }
    
    protected void initialize() {	
    }
    
    protected void execute() {
    	double currentRPM = rpmSig.processNextPoint(getAvgRPM());
    	double newRateChange = (currentRPM - lastRPM)*SAMPLE_RATE; 
    	double rateChange = rateRPM.processNextPoint(newRateChange);
    	if(isBetween(RPM_TRANSITION,lastRPM,currentRPM))
    	{
    		//over transition point
    		if(Math.abs(Robot.drivetrain.getAverageSetpoint()) > RPM_TRANSITION)
    		{
    			//check to shift high
    			if(Math.abs(rateChange) > RATE_THRESHOLD && Duration.between(lastShifted, Instant.now()).toMillis() > COOLDOWN)
    			{
    				(new ShiftersShiftCommand(Shifters.Gear.HIGH)).start();
    				lastShifted = Instant.now();
    				Logger.defaultLogger.trace("Shifting to high gear due to passing the middle threshold. Current RPM: " + currentRPM);
    			}
    		}
    	}
    	else if(Math.abs(currentRPM) > UPPER_THRESHOLD && Robot.shifters.getGear() == Shifters.Gear.LOW && Duration.between(lastShifted, Instant.now()).toMillis() > COOLDOWN)
    	{
    		if(pastThresholdSince == null) {
    			pastThresholdSince = Instant.now();
    		} else if(Duration.between(pastThresholdSince, Instant.now()).toMillis() > DELAY) {
    			(new ShiftersShiftCommand(Shifters.Gear.HIGH)).start();
        		lastShifted = Instant.now();
        		Logger.defaultLogger.trace("Shifting to high gear due to passing the upper threshold of " + UPPER_THRESHOLD + " RPM. Current RPM: " + currentRPM);
    		}
    	}
    	else if(Math.abs(currentRPM) < LOWER_THRESHOLD && Robot.shifters.getGear() == Shifters.Gear.HIGH && Duration.between(lastShifted, Instant.now()).toMillis() > COOLDOWN)
    	{
    		if(pastThresholdSince == null) {
    			pastThresholdSince = Instant.now();
    		} else if(Duration.between(pastThresholdSince, Instant.now()).toMillis() > DELAY) {
    			(new ShiftersShiftCommand(Shifters.Gear.LOW)).start();
				lastShifted = Instant.now();
				Logger.defaultLogger.trace("Shifting to low gear due to passing the lower threshold of " + LOWER_THRESHOLD + " RPM. Current RPM: " + currentRPM);
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
    	Robot.shifters.shift(Shifters.Gear.LOW);
    }

    protected void interrupted() {
    	end(); //Might want to add more elegant interrupt handeling later
    }
}
