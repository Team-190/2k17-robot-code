package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.commands.AutoDriveBackAndForthCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveBoxCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperCurveCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperTurnCommand;
import org.usfirst.frc.team190.frc2k17.commands.boopers.BooperSetCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOff;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOn;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceHeadingCorrectionCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveToPegCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.PlaceGearCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnTowardPegCommand;
import org.usfirst.frc.team190.frc2k17.subsystems.Boopers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.XboxController;
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
	public static XboxController joystick2;

	
	private Button aButton;
	private Button bButton;
	private Button xButton;
	private Button yButton;
	
	private Button lbButton;
	private Button rbButton;
	
	private Button backButton;
	private Button startButton;
	
	
	public OI() {
		joystick0 = new FilteredJoystick(0);
		joystick0.setDeadband(0.05); // TODO: Put constant in robotmap
		
		joystick1 = new FilteredJoystick(1);

		joystick2 = new XboxController(2);
		
		aButton = new JoystickButton(joystick2, 1);
		bButton = new JoystickButton(joystick2, 2);
		xButton = new JoystickButton(joystick2, 3);
		yButton = new JoystickButton(joystick2, 4);
		
		lbButton = new JoystickButton(joystick2, 5);
		rbButton = new JoystickButton(joystick2, 6);
		
		backButton = new JoystickButton(joystick2, 7);
		startButton = new JoystickButton(joystick2, 8);
		
		
		aButton.whileHeld(new BooperSetCommand(Boopers.State.EXTENDED)); 
		
		
		
		
		//testButton = new JoystickButton(joystick0, 5);
		//testButton.whenPressed(new DriveStraightForDistanceCommand(5));
		//SmartDashboard.putData("Drive for Distance", new DriveStraightForDistanceCommand(name, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KP, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KI, RobotMap.Constants.DriveTrain.DRIVE_PID_DISTANCE_KD));
		SmartDashboard.putData("Drive 120 Inches", new DriveStraightForDistanceHeadingCorrectionCommand(120));
		SmartDashboard.putData("Turn 90 deg", new TurnToDegreesCommand(90));
		SmartDashboard.putData("Turn 20 deg", new TurnToDegreesCommand(20));
		SmartDashboard.putData("Drive 6ft Box", new AutoDriveBoxCommand());
		SmartDashboard.putData("Drive Back and Forth", new AutoDriveBackAndForthCommand());
		SmartDashboard.putData("Drive to Hopper (turn)", new AutoDriveToHopperTurnCommand());
		SmartDashboard.putData("Drive to Hopper (curve)", new AutoDriveToHopperCurveCommand());
		SmartDashboard.putData("Camera light on", new GearCameraLightOn());
		SmartDashboard.putData("Camera light off", new GearCameraLightOff());
		SmartDashboard.putData("Turn towards Peg", new TurnTowardPegCommand());
		SmartDashboard.putData("Place Gear Command", new PlaceGearCommand());
		SmartDashboard.putData("Drive to Peg command", new DriveToPegCommand());
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

