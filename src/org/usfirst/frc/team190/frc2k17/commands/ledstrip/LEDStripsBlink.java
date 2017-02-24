package org.usfirst.frc.team190.frc2k17.commands.ledstrip;

import org.usfirst.frc.team190.frc2k17.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LEDStripsBlink extends CommandGroup {

    public LEDStripsBlink() {    	
        addSequential(new LEDStripSetColor(Robot.leftLEDs, 255, 255, 0));
        //addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.ORANGE));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, 0, 0, 0));
        //addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.BLACK));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, 255, 255, 0));
        //addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.ORANGE));
        addSequential(new WaitCommand(0.25));
        addSequential(new LEDStripSetColor(Robot.leftLEDs, 0, 0, 0));
        //addSequential(new LEDStripSetColor(Robot.rightLEDs, Color.BLACK));
        addSequential(new WaitCommand(0.25));
    }
}
