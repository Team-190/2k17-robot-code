package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.Color;
import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LEDStripsQuickBlink extends CommandGroup {

    public LEDStripsQuickBlink(Color color) {
        addParallel(new LEDStripQuickBlink(Robot.leftLEDs, color));
        addParallel(new LEDStripQuickBlink(Robot.rightLEDs, color));
    }
}
