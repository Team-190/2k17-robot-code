package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LEDStripBlink extends CommandGroup {

    public LEDStripBlink(LEDStrip subsystem, Color color) {
        addSequential(new LEDStripBlinkRate(subsystem, color, 500));
    }
}
