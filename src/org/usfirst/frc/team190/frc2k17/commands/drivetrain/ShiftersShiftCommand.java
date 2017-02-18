package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftersShiftCommand extends Command {

	private final Shifters.Gear gear;
	
    public ShiftersShiftCommand(Shifters.Gear gear) {
        requires(Robot.shifters);
        this.gear = gear;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shifters.shift(gear);
    }

    protected boolean isFinished() {
        return true;
    }

}
