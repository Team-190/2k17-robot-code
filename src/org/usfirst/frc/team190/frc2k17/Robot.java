
package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.subsystems.Boopers;
import org.usfirst.frc.team190.frc2k17.subsystems.GearCamera;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;
import org.usfirst.frc.team190.frc2k17.subsystems.LEDStrip;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;
import org.usfirst.frc.team190.frc2k17.commands.ledstrip.LEDStripsQuickBlink;

import java.io.OutputStream;
import java.io.PrintStream;
import org.usfirst.frc.team190.frc2k17.subsystems.Agitator;
import org.usfirst.frc.team190.frc2k17.subsystems.Shooter;
import org.usfirst.frc.team190.frc2k17.subsystems.ShooterFeeder;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Drivetrain;
import org.usfirst.frc.team190.frc2k17.subsystems.drivetrain.Shifters;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Preferences prefs;
	public static Drivetrain drivetrain;
	public static GearCamera gearCamera;
	public static Shooter shooter;
	public static ShooterFeeder shooterFeeder;
	public static Agitator agitator;
	public static Climber climber;
	public static Boopers boopers;
	public static GearPlacer gearPlacer;
	public static Shifters shifters;
	public static LEDStrip leftLEDs;
	public static LEDStrip rightLEDs;
	public static Compressor compressor;
	public static PowerDistributionPanel pdp;
	
	public static OI oi;
	
    private Command autonomousCommand;
    //SendableChooser chooser;
    
    private static Boolean wasKitBot = null;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	Logger.defaultLogger.info("Robot initializing.");
		// RobotMap must be initialized before almost anything else.
    	Logger.init();
    	Logger.resetTimestamp();
    	interceptOutputStream();
    	// prefs must not be initialized statically. Do not move from robotInit().
    	// prefs MUST be initialized before everything else. Do not change order.
    	prefs = Preferences.getInstance();
    	leftLEDs = new LEDStrip(RobotMap.getInstance().DIO_LEDS_LEFT_R.get(),
				RobotMap.getInstance().DIO_LEDS_LEFT_G.get(), RobotMap.getInstance().DIO_LEDS_LEFT_B.get());
		rightLEDs = new LEDStrip(RobotMap.getInstance().DIO_LEDS_RIGHT_R.get(),
				RobotMap.getInstance().DIO_LEDS_RIGHT_G.get(), RobotMap.getInstance().DIO_LEDS_RIGHT_B.get());
    	drivetrain = new Drivetrain();
    	// gearCamera must not be initialized statically. Do not move from robotInit().
    	gearCamera = new GearCamera();
    	shooter = new Shooter();
    	shooterFeeder = new ShooterFeeder();
    	agitator = new Agitator();
    	climber = new Climber();
    	boopers = new Boopers();
    	gearPlacer = new GearPlacer();
    	shifters = new Shifters();
		compressor = new Compressor(RobotMap.getInstance().CAN_PCM.get());
		pdp = new PowerDistributionPanel(RobotMap.getInstance().CAN_PDP.get());
		// OI must be initialized last
		oi = new OI();
		
        //chooser = new SendableChooser();
        //chooser.addObject("My Auto", new MyAutoCommand());
        //SmartDashboard.putData("Auto mode", chooser);
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(RobotMap.getInstance().CAMERA_RESOLUTION_X.get(),
							 RobotMap.getInstance().CAMERA_RESOLUTION_Y.get());
		camera.setExposureManual(RobotMap.getInstance().CAMERA_EXPOSURE.get());
		
		diagnose();
		Diagnostics.start();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	Logger.defaultLogger.info("Robot Disabled.");
    	
    	compressor.stop();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		Diagnostics.resetDisconnected();
		
		leftLEDs.updateRainbow();
    	rightLEDs.updateRainbow();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	Logger.defaultLogger.info("Autonomous mode started.");
    	
        //autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
    //    if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        Diagnostics.resetDisconnected();
    }

    public void teleopInit() {
    	Logger.defaultLogger.info("Teleop mode started.");

    	compressor.start();
    	
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();   
        Diagnostics.resetDisconnected();
        
        drivetrain.outputEncoderValues();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        Diagnostics.resetDisconnected();
    }
    
	/**
     * @return whether the robot is the kit bot
     */
    public static boolean isKitBot() {
    	boolean isKitBot  = Robot.prefs.getBoolean("Is kit bot", false);
    	if(wasKitBot == null) {
    		wasKitBot = isKitBot;
    		if(isKitBot) {
    			Logger.voice.info("kit bot");
    		}
    	} else if(wasKitBot != isKitBot) {
    		// The "Is kit bot" setting on the smart dashboard has changed.
			// In order to make sure that all of the new settings get applied,
			// the robot code must restart. It is not safe to just start
			// returning the new value from this method; we would get a
			// frankenstein robot using constants from both modes.
			Logger.defaultLogger.critical("The \"Is kit bot\" setting on the smart dashboard has changed value. "
					+ "In order to apply the new setting, the robot code must restart.");
			Logger.defaultLogger.info("ROBOT CODE IS NOW INTENTIONALLY RESTARTING");
			System.exit(0);
    	}
    	return wasKitBot;
    }
    
    /**
     * @return whether to enable debug mode
     */
    public static boolean debug() {
    	return true;
    }
    
    /**
     * @return whether we are using an xbox controller for the operator
     */
    public static boolean usingXboxController() {
    	return false;
    }
    
    /**
     * Call the diagnose functions on all of the subsystems.
     */
    public static void diagnose() {
    	Logger.defaultLogger.info("\n====================\nRunning diagnostics...");
    	if(isKitBot()) {
    		Logger.defaultLogger.info("This is the kit bot.");
    	} else {
    		Logger.defaultLogger.info("This is the real (non-kit) robot.");
    	}
    	double vbat = pdp.getVoltage();
    	Logger.defaultLogger.info("Battery voltage is " + vbat + " volts.");
    	if(vbat < 11.7) {
    		Logger.voice.warn("battery");
    	}
    	drivetrain.diagnose();
    	shooter.diagnose();
    	shooterFeeder.diagnose();
    	agitator.diagnose();
    	climber.diagnose();
    	gearPlacer.diagnose();
    	if(compressor.getCompressorCurrentTooHighStickyFault()) {
    		Logger.defaultLogger.warn("PCM has compressor-current-too-high sticky bit set.");
    		Logger.voice.warn("check compressor");
    	}
    	if(compressor.getCompressorNotConnectedStickyFault()) {
    		Logger.defaultLogger.warn("PCM has compressor-not-connected sticky bit set. Is the compressor connected properly?");
    		Logger.voice.warn("check compressor");
    	}
    	if(compressor.getCompressorShortedStickyFault()) {
    		Logger.defaultLogger.warn("PCM has compressor-shorted sticky bit set. Is the compressor output shorted?");
    		Logger.voice.warn("check compressor");
    	}
    	Logger.defaultLogger.info("Diagnostics complete.\n====================\n");
    }
    
    public static void clearStickyFaults() {
		Robot.compressor.clearAllPCMStickyFaults();
		Robot.pdp.clearStickyFaults();
		drivetrain.clearStickyFaults();
    	shooter.clearStickyFaults();
    	shooterFeeder.clearStickyFaults();
    	agitator.clearStickyFaults();
    	climber.clearStickyFaults();
	}
    
    public static int getNavxErrorCount() {
    	return CustomStream.getNavxErrorCount();
    }
    
    public static void resetNavxErrorCount() {
    	CustomStream.resetNavxErrorCount();
    }
    
    private void interceptOutputStream() {
    	System.setOut(new CustomStream(System.out));
    	System.setErr(new CustomStream(System.err));
    }
    
    private static class CustomStream extends PrintStream {

    	private static int navxErrorCount = 0;
    	
    	public static int getNavxErrorCount() {
    		return navxErrorCount;
    	}
    	
    	public static void resetNavxErrorCount() {
    		navxErrorCount = 0;
    	}
    	
    	public CustomStream(OutputStream out) {
			super(out);
		}
    	
    	@Override
    	public void println(String s) {
    		if (s.contains("navX-MXP SPI Read:  CRC error")){
    			navxErrorCount++;
    		} else {
    			super.println(s);
    		}
    	}
    	
    }
}
