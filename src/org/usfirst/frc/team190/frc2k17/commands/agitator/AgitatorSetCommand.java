package org.usfirst.frc.team190.frc2k17.commands.agitator;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.Agitator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AgitatorSetCommand extends Command {
	
	private Agitator.State state;

    public AgitatorSetCommand(Agitator.State state) {
    	requires(Robot.agitator);
    	this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.agitator.set(state);
    }

    protected boolean isFinished() {
        return true;
    }

}
