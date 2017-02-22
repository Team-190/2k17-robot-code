package org.usfirst.frc.team190.frc2k17.commands;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveAndTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToHopperCurveCommand extends CommandGroup {

    public AutoDriveToHopperCurveCommand() {
        requires(Robot.drivetrain);
        addSequential(new DriveAndTurnCommand(106, -30));
    }
}
