package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.commands.AutoDriveBackAndForthCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveBoxCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperCurveCommand;
import org.usfirst.frc.team190.frc2k17.commands.AutoDriveToHopperTurnCommand;
import org.usfirst.frc.team190.frc2k17.commands.ClearStickyFaultsCommand;
import org.usfirst.frc.team190.frc2k17.commands.boopers.BooperSetCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOffCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightOnCommand;
import org.usfirst.frc.team190.frc2k17.commands.cameraLight.GearCameraLightToggleCommand;
import org.usfirst.frc.team190.frc2k17.commands.climber.ClimberClimbCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.AutoShiftCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveStraightForDistanceHeadingCorrectionCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.DriveToPegCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.PlaceGearCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.ShiftersShiftCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.ShiftersToggleCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnToDegreesCommand;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.TurnTowardPegCommand;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.GearPlacerSetCommand;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.GearPlacerToggleCommand;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.GearPresentCommandGroup;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.KickGearCommand;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripRandom;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripsBlink;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.PegAssist;
import org.usfirst.frc.team190.frc2k17.commands.gearplacer.SetAutoKickEnabledCommand;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripBlink;
import org.usfirst.frc.team190.frc2k17.commands.shooter.ShooterFeedCommand;
import org.usfirst.frc.team190.frc2k17.commands.shooter.StartShooterCommand;
import org.usfirst.frc.team190.frc2k17.subsystems.Boopers;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer.State;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;
import org.usfirst.frc.team190.frc2k17.triggers.PegPresentTrigger;
import org.usfirst.frc.team190.frc2k17.triggers.PovDownTrigger;
import org.usfirst.frc.team190.frc2k17.triggers.PovUpTrigger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
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
	
	private FilteredJoystick joystick0;
	private FilteredJoystick joystick1;
	private GenericHID joystick2;

	private Button highShiftButton, lowShiftButton, gearKickButton, driveToPegButton;
	private Button aButton, bButton, xButton, yButton, lbButton, rbButton, backButton, startButton;
	private Button boopButton, climbButton, shiftHighButton, shiftLowButton, shooterSpinButton, shooterFeedButton,
			shooterStopButton, gearOutButton, gearInButton, pegAssistOnButton, pegAssistOffButton, blinkLEDsButton;
	private Trigger povUpTrigger, povDownTrigger, pegPresentTrigger;
	
	public OI() {
		joystick0 = new FilteredJoystick(0);
		joystick0.setDeadband(RobotMap.getInstance().JOYSTICK_DEADBAND.get());
		joystick1 = new FilteredJoystick(1);
		if(Robot.usingXboxController()) {
			joystick2 = new XboxController(2);
			
			aButton = new JoystickButton(joystick2, 1);
			bButton = new JoystickButton(joystick2, 2);
			xButton = new JoystickButton(joystick2, 3);
			yButton = new JoystickButton(joystick2, 4);
			lbButton = new JoystickButton(joystick2, 5);
			rbButton = new JoystickButton(joystick2, 6);
			backButton = new JoystickButton(joystick2, 7);
			startButton = new JoystickButton(joystick2, 8);
			
			lbButton.whenPressed(new BooperSetCommand(Boopers.State.EXTENDED));
			lbButton.whenReleased(new BooperSetCommand(Boopers.State.RETRACTED));
			backButton.whenPressed(new GearCameraLightToggleCommand());
			povUpTrigger.whileActive(new ClimberClimbCommand());
			yButton.toggleWhenPressed(new StartShooterCommand());
			aButton.whileHeld(new ShooterFeedCommand());
			xButton.whenPressed(new KickGearCommand());
			startButton.whenPressed(new GearPlacerToggleCommand());
			bButton.whenPressed(new ShiftersShiftCommand(Shifters.Gear.HIGH));
			bButton.whenReleased(new ShiftersShiftCommand(Shifters.Gear.LOW));
		} else {
			joystick2 = new FilteredJoystick(2);
			
			boopButton = new JoystickButton(joystick2, 1);
			climbButton = new JoystickButton(joystick2, 2);
			shiftHighButton = new JoystickButton(joystick2, 7);
			shiftLowButton = new JoystickButton(joystick2, 8);
			shooterSpinButton = new JoystickButton(joystick2, 11);
			shooterStopButton = new JoystickButton(joystick2, 12);
			shooterFeedButton = new JoystickButton(joystick2, 5);
			gearOutButton = new JoystickButton(joystick2, 6);
			gearInButton = new JoystickButton(joystick2, 4);
			pegAssistOnButton = new JoystickButton(joystick2, 9);
			pegAssistOffButton = new JoystickButton(joystick2, 10);
			blinkLEDsButton = new JoystickButton(joystick2, 3);
			
			boopButton.whenPressed(new BooperSetCommand(Boopers.State.EXTENDED));
			boopButton.whenReleased(new BooperSetCommand(Boopers.State.RETRACTED));
			climbButton.whileActive(new ClimberClimbCommand());
			shiftHighButton.whenPressed(new ShiftersShiftCommand(Shifters.Gear.HIGH));
			shiftLowButton.whenReleased(new ShiftersShiftCommand(Shifters.Gear.LOW));
			Command shooterSpinCommand = new StartShooterCommand();
			shooterSpinButton.whenPressed(shooterSpinCommand);
			shooterStopButton.cancelWhenPressed(shooterSpinCommand);
			shooterFeedButton.whileHeld(new ShooterFeedCommand());
			gearOutButton.whenPressed(new GearPlacerSetCommand(State.EXTENDED));
			gearInButton.whenPressed(new GearPlacerSetCommand(State.RETRACTED));
			Command pegAssistCommand = new PegAssist();
			pegAssistOnButton.whenPressed(pegAssistCommand);
			pegAssistOffButton.cancelWhenPressed(pegAssistCommand);
			blinkLEDsButton.whileHeld(new LEDStripsBlink(Color.MAGENTA));
		}
		
		pegPresentTrigger = new PegPresentTrigger();
		pegPresentTrigger.whenActive(new GearPresentCommandGroup());
		
		highShiftButton = new JoystickButton(joystick0, 3);
		lowShiftButton = new JoystickButton(joystick0, 2);
		//driveToPegButton = new JoystickButton(joystick0, 3);
		//gearKickButton = new JoystickButton(joystick0, 2);
		
		highShiftButton.whenPressed(new ShiftersShiftCommand(Shifters.Gear.HIGH));
		lowShiftButton.whenPressed(new ShiftersToggleCommand());
		//driveToPegButton.whenPressed(new PlaceGearCommand());
		//gearKickButton.whenPressed(new KickGearCommand());
		
		povUpTrigger = new PovUpTrigger(joystick2);
		povDownTrigger = new PovDownTrigger(joystick2);
		
		SmartDashboard.putData("Auto Shift", new AutoShiftCommand());
		SmartDashboard.putData("Drive 120 Inches", new DriveStraightForDistanceHeadingCorrectionCommand(120,1));
		SmartDashboard.putData("Turn 90 deg", new TurnToDegreesCommand(90));
		SmartDashboard.putData("Turn 20 deg", new TurnToDegreesCommand(20));
		SmartDashboard.putData("Drive 6ft Box", new AutoDriveBoxCommand());
		SmartDashboard.putData("Drive Back and Forth", new AutoDriveBackAndForthCommand());
		SmartDashboard.putData("Drive to Hopper (turn)", new AutoDriveToHopperTurnCommand());
		SmartDashboard.putData("Drive to Hopper (curve)", new AutoDriveToHopperCurveCommand());
		SmartDashboard.putData("Camera light on", new GearCameraLightOnCommand());
		SmartDashboard.putData("Camera light off", new GearCameraLightOffCommand());
		SmartDashboard.putData("Turn towards Peg", new TurnTowardPegCommand());
		SmartDashboard.putData("Place Gear Command", new PlaceGearCommand());
		SmartDashboard.putData("Drive to Peg command", new DriveToPegCommand());
		SmartDashboard.putData("Climb", new ClimberClimbCommand());
		SmartDashboard.putData("Blink LEDs", new LEDStripsBlink(Color.MAGENTA));
		SmartDashboard.putData("Peg Assist", new PegAssist());
		SmartDashboard.putData("Random LEDs", new LEDStripRandom(Robot.leftLEDs));
		SmartDashboard.putData("Disable automatic gear placing", new SetAutoKickEnabledCommand(false));
		SmartDashboard.putData("Start Shooter", new StartShooterCommand());
		SmartDashboard.putData("Clear Sticky Faults", new ClearStickyFaultsCommand());
	}
	
	public double getDriverJoystick1X() {
		return (RobotMap.getInstance().OI_INVERT_DRIVER_JOSTICK_1.get()) ? -joystick0.getAxis(AxisType.kX) : joystick0.getAxis(AxisType.kX);
	}
	
	public double getDriverJoystick1Y() {
		//return joystick0.getAxis(AxisType.kThrottle);
		return (RobotMap.getInstance().OI_INVERT_DRIVER_JOSTICK_1.get()) ? -joystick0.getAxis(AxisType.kY) : joystick0.getAxis(AxisType.kY);
	}
	
	public double getDriverJoystick2X() {
		return (RobotMap.getInstance().OI_INVERT_DRIVER_JOSTICK_2.get()) ? -joystick1.getAxis(AxisType.kX) : joystick1.getAxis(AxisType.kX);
	}
	
	public double getDriverJoystick2Y() {
		return (RobotMap.getInstance().OI_INVERT_DRIVER_JOSTICK_2.get()) ? -joystick1.getAxis(AxisType.kY) : joystick1.getAxis(AxisType.kY);
	}

	public double getDriverJoystick1Throttle() {
		return joystick1.getThrottle();
	}
}

