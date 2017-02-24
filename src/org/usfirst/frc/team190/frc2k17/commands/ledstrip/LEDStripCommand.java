package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDStripCommand extends Command {

	protected final LEDStrip strip;
	
    public LEDStripCommand(LEDStrip subsystem) {
        requires(subsystem);
        
        this.strip = subsystem;
    }

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
