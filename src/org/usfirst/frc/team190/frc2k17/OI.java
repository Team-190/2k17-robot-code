package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveToPegCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	public static Joystick joystick0;
	public static Joystick joystick1;
	
	public static Joystick joystick2;		// Assuming the Operator is using a Joystick
	
	private Button testButton;
	private Button testButton2;
	
	public OI() {
		joystick0 = new Joystick(0);
		joystick1 = new Joystick(1);

		joystick2 = new Joystick(2);

		testButton = new JoystickButton(joystick0, 1);
		testButton.whenPressed(new TurnToDegreesCommand(90));

		testButton = new JoystickButton(joystick0, 5);
		testButton.whenPressed(new DriveStraightForDistanceCommand(5));
	}
}

