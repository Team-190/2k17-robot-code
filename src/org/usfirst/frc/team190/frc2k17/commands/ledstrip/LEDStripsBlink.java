package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import java.awt.Color;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LEDStripsBlink extends CommandGroup {

    public LEDStripsBlink() {    	
        addSequential(new LEDStripSetColor(Robot.leftLEDs, Color.ORANGE));
        addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.ORANGE));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, Color.BLACK));
        addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.BLACK));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, Color.ORANGE));
        addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.ORANGE));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, Color.BLACK));
        addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.BLACK));
        addSequential(new WaitCommand(0.25));
    }
}
