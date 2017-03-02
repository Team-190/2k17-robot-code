package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LEDStripsBlink extends CommandGroup {

    public LEDStripsBlink(Color color) {
        addParallel(new LEDStripBlink(Robot.leftLEDs, color));
        addParallel(new LEDStripBlink(Robot.rightLEDs, color));
    }
}
