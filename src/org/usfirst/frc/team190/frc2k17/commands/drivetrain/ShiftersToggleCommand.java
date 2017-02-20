package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftersToggleCommand extends Command {
	
    public ShiftersToggleCommand() {
        requires(Robot.shifters);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.shifters.getGear() == Shifters.Gear.HIGH) {
        	Robot.shifters.shift(Shifters.Gear.LOW);
    	} else {
        	Robot.shifters.shift(Shifters.Gear.HIGH);
    	}
    }

    protected boolean isFinished() {
        return true;
    }

}
