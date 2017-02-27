package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LEDStripsBlink extends CommandGroup {

    public LEDStripsBlink() {
        addParallel(new LEDStripBlink(Robot.leftLEDs));
        addParallel(new LEDStripBlink(Robot.rightLEDs));
    }
}
