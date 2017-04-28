package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterPegAuto extends CommandGroup {

    public CenterPegAuto() {
    	addSequential(new DriveStraightForTimeCommand(0.5, 1, false));
    	addSequential(new DriveStraightForTimeCommand(3, 0.25));
    }
}
