package org.usfirst.frc.team190.frc2k17.commands.gearplacer;

import org.usfirst.frc.team190.frc2k17.triggers.PegPresentTrigger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetAutoKickEnabledCommand extends Command {
	
	private boolean enabled;

    public SetAutoKickEnabledCommand(boolean enabled) {
    	this.enabled = enabled;
    }

    protected void initialize() {
    	PegPresentTrigger.setEnabled(enabled);
    }

    protected boolean isFinished() {
        return true;
    }

}