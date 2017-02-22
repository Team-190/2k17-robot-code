package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class ShiftersShiftCommand extends TimedCommand {

	private final Shifters.Gear gear;
	
    public ShiftersShiftCommand(Shifters.Gear gear) {
    	super(RobotMap.getInstance().SHIFT_PAUSE.get());
        requires(Robot.shifters);
        requires(Robot.drivetrain);
        this.gear = gear;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.tankDrive(0, 0);
    	Robot.shifters.shift(gear);
    }

}
