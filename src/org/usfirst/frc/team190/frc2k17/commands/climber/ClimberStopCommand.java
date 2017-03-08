package org.usfirst.frc.team190.frc2k17.commands.climber;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberStopCommand extends Command {

    public ClimberStopCommand() {
        requires(Robot.climber);
    }

    protected void initialize() {
    	Robot.climber.set(Climber.State.STOP);
    }

    protected boolean isFinished() {
        return true;
    }

}
