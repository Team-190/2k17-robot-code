package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.RobotMap;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftersShiftCommand extends Command {

	private final Shifters.Gear gear;
	private boolean finished = false;
	private Timer timer;
	private TimerTask task;
	
    public ShiftersShiftCommand(Shifters.Gear gear) {
        requires(Robot.shifters);
        requires(Robot.drivetrain);
        this.gear = gear;
        timer = new Timer();
        task = new TimerTask() {

			@Override
			public void run() {
				finished = true;
			}
        	
        };
        timer.schedule(task, RobotMap.getInstance().SHIFT_PAUSE.get());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.tankDrive(0, 0);
    	Robot.shifters.shift(gear);
    }

    protected boolean isFinished() {
        return finished;
    }

}
