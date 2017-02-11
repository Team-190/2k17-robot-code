package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.commands.AutoDriveBoxCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperCurveCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperTurnCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	public static FilteredJoystick joystick0;
	public static FilteredJoystick joystick1;
	
	public static Joystick joystick2;		// Assuming the Operator is using a Joystick
	
	private Button testButton;
	
	public OI() {
		joystick0 = new FilteredJoystick(0);
		joystick0.setDeadband(0.05); // TODO: Put constant in robotmap
		
		joystick1 = new FilteredJoystick(1);

		joystick2 = new Joystick(2);

		testButton = new JoystickButton(joystick0, 1);
		
		//testButton = new JoystickButton(joystick0, 5);
		//testButton.whenPressed(new DriveStraightForDistanceCommand(5));
		//SmartDashboard.putData("Drive for Distance", new DriveStraightForDistanceCommand(name, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD));
		SmartDashboard.putData("Drive 120 Inches", new DriveStraightForDistanceCommand(120));
		SmartDashboard.putData("Turn 90 deg", new TurnToDegreesCommand(90));
		SmartDashboard.putData("Drive 6ft Box", new AutoDriveBoxCommand());
		SmartDashboard.putData("Drive to Hopper (turn)", new AutoDriveToHopperTurnCommand());
		SmartDashboard.putData("Drive to Hopper (curve)", new AutoDriveToHopperCurveCommand());
	}
	
	public double getDriverJoystick1X() {
		return (RobotMap.Constants.OI.INVERT_DRIVER_JOSTICK_1) ? -joystick0.getAxis(AxisType.kX) : joystick0.getAxis(AxisType.kX);
	}
	
	public double getDriverJoystick1Y() {
		return (RobotMap.Constants.OI.INVERT_DRIVER_JOSTICK_1) ? -joystick0.getAxis(AxisType.kY) : joystick0.getAxis(AxisType.kY);
	}
	
	public double getDriverJoystick2X() {
		return (RobotMap.Constants.OI.INVERT_DRIVER_JOSTICK_2) ? -joystick1.getAxis(AxisType.kX) : joystick1.getAxis(AxisType.kX);
	}
	
	public double getDriverJoystick2Y() {
		return (RobotMap.Constants.OI.INVERT_DRIVER_JOSTICK_2) ? -joystick1.getAxis(AxisType.kY) : joystick1.getAxis(AxisType.kY);
	}

	public double getDriverJoystick1Throttle() {
		return joystick1.getThrottle();
	}
}

