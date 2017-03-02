
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
	private static Compressor compressor;
	
	public static OI oi;
	
    private Command autonomousCommand;
    //SendableChooser chooser;
    
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
    	// prefs MUST be initialized before drivetrain. Do not change order.
    	prefs = Preferences.getInstance();
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
		leftLEDs = new LEDStrip(RobotMap.getInstance().PWM_LEDS_LEFT_R.get(),
				RobotMap.getInstance().PWM_LEDS_LEFT_G.get(), RobotMap.getInstance().PWM_LEDS_LEFT_B.get());
		rightLEDs = new LEDStrip(RobotMap.getInstance().PWM_LEDS_RIGHT_R.get(),
				RobotMap.getInstance().PWM_LEDS_RIGHT_G.get(), RobotMap.getInstance().PWM_LEDS_RIGHT_B.get());
		compressor = new Compressor();
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
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	Logger.lowLogger.trace("disabledInit at " + Utility.getFPGATime());
    	Logger.defaultLogger.info("Robot Disabled.");
    	Logger.kangarooVoice.info("disabled");
    	
    	compressor.stop();
    }
	
	public void disabledPeriodic() {
		Logger.lowLogger.trace("disabledPeriodic at " + Utility.getFPGATime());
		Scheduler.getInstance().run();
		
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
    	Logger.lowLogger.trace("autonomousInit at " + Utility.getFPGATime());
    	Logger.kangarooVoice.info("auto");
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
    	Logger.lowLogger.trace("autonomousPeriodic at " + Utility.getFPGATime());
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	Logger.lowLogger.trace("teleopInit at " + Utility.getFPGATime());
    	Logger.kangarooVoice.info("teleop");
    	Logger.defaultLogger.info("Teleop mode started.");

    	compressor.start();
    	
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	Logger.lowLogger.trace("teleopPeriodic at " + Utility.getFPGATime());
		drivetrain.outputEncoderValues();
        Scheduler.getInstance().run();        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	Logger.lowLogger.trace("testPeriodic at " + Utility.getFPGATime());
        LiveWindow.run();
    }
    
    /**
     * Called once when the robot realizes that the driver station has become disconnected.
     * Think about safety, do not actuate anything here!
     */
    public void disconnectedInit() {
    	Logger.lowLogger.trace("disconnectedInit at " + Utility.getFPGATime());
    	Logger.defaultLogger.warn("Driver station disconnected.");
    	leftLEDs.override(Color.YELLOW);
    	rightLEDs.override(Color.YELLOW);
    }
    
    /**
     * Called periodically when the driver station has lost connection.
     * Think about safety, do not actuate anything here!
     */
    public void disconnectedPeriodic() {
    	Logger.lowLogger.trace("disconnectedPeriodic at " + Utility.getFPGATime());
    	
    }
    
    /**
     * Called once when the driver station becomes connected.
     */
    public void connected() {
    	(new LEDStripsQuickBlink(Color.MAGENTA)).start();
    }
    
	/**
	 * Overrides main robot loop from IterativeRobot
	 */
	public void startCompetition() {
		boolean disabledInitialized = false;
		boolean autonomousInitialized = false;
		boolean teleopInitialized = false;
		boolean testInitialized = false;
		boolean disconnectedInitialized = false;
		double sleepTime = 1d / RobotMap.getInstance().ROBOT_MAIN_LOOP_RATE.get();
		long lastReceivedDSPacket = 0L;
		long timeoutMicros = RobotMap.getInstance().ROBOT_COMMS_TIMEOUT.get() * 1000L;
		
		Logger.defaultLogger.trace("Robot comms timeout configured to " + timeoutMicros + " microseconds.");
		Logger.defaultLogger.trace("Robot main loop sleep time configured to " + sleepTime + " seconds.");
		
		HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);

		robotInit();

		// Tell the DS that the robot is ready to be enabled
		HAL.observeUserProgramStarting();

		// loop forever, calling the appropriate mode-dependent function
		LiveWindow.setEnabled(false);
		long currentTime;
		while (true) {
			currentTime = Utility.getFPGATime();
			// sleep for the appropriate amount of time so that the loop runs
			// at the frequency specified in RobotMap
			sleep(sleepTime);
			if (m_ds.isNewControlData()) {
				Logger.lowLogger.trace("packet received sometime in the last loop, at " + Utility.getFPGATime());
				leftLEDs.disableOverride();
				rightLEDs.disableOverride();
				disconnectedInitialized = false;
				lastReceivedDSPacket = currentTime;
			}
			if (currentTime < lastReceivedDSPacket + timeoutMicros) {
				Logger.lowLogger.trace("iteration of main loop at " + Utility.getFPGATime());
				// Call the appropriate function depending upon the current robot
				// mode
				if (isDisabled()) {
					// call DisabledInit() if we are now just entering disabled mode
					// from
					// either a different mode or from power-on
					if (!disabledInitialized) {
						LiveWindow.setEnabled(false);
						disabledInit();
						disabledInitialized = true;
						// reset the initialization flags for the other modes
						autonomousInitialized = false;
						teleopInitialized = false;
						testInitialized = false;
					}
					HAL.observeUserProgramDisabled();
					disabledPeriodic();
				} else if (isTest()) {
					// call TestInit() if we are now just entering test mode from
					// either
					// a different mode or from power-on
					if (!testInitialized) {
						LiveWindow.setEnabled(true);
						testInit();
						testInitialized = true;
						autonomousInitialized = false;
						teleopInitialized = false;
						disabledInitialized = false;
					}
					HAL.observeUserProgramTest();
					testPeriodic();
				} else if (isAutonomous()) {
					// call Autonomous_Init() if this is the first time
					// we've entered autonomous_mode
					if (!autonomousInitialized) {
						LiveWindow.setEnabled(false);
						// KBS NOTE: old code reset all PWMs and relays to "safe
						// values"
						// whenever entering autonomous mode, before calling
						// "Autonomous_Init()"
						autonomousInit();
						autonomousInitialized = true;
						testInitialized = false;
						teleopInitialized = false;
						disabledInitialized = false;
					}
					HAL.observeUserProgramAutonomous();
					autonomousPeriodic();
				} else {
					// call Teleop_Init() if this is the first time
					// we've entered teleop_mode
					if (!teleopInitialized) {
						LiveWindow.setEnabled(false);
						teleopInit();
						teleopInitialized = true;
						testInitialized = false;
						autonomousInitialized = false;
						disabledInitialized = false;
					}
					HAL.observeUserProgramTeleop();
					teleopPeriodic();
				}
				robotPeriodic();
			} else {
				Logger.lowLogger.trace("iteration of disconnected loop at " + Utility.getFPGATime());
				if (!disconnectedInitialized) {
					disconnectedInit();
					disconnectedInitialized = true;
				}
				disconnectedPeriodic();
			}
		}
	}
    
    /**
     * Sleep for a certain number of seconds
     * @param timeout the number of seconds to sleep
     * 
     */
    private void sleep(double timeout) {
    	long startTime = Utility.getFPGATime();
        long timeoutMicros = (long)(timeout * 1000000);
        if(!(timeout > 0)) {
        	return;
        }
        while (true) {
        	long now = Utility.getFPGATime();
        	if (now < startTime + timeoutMicros) {
        		// We still have time to wait
        		long microsLeft = startTime + timeoutMicros - now;
        		try {
        			Thread.sleep(microsLeft / 1000L, (int) ((microsLeft % 1000L) * 1000L));
        		} catch (InterruptedException ex) {
        			// do nothing
        		}
        	} else {
        		// Time has elapsed.
        		return;
        	}
        }
    }
    
    /**
     * @return whether the robot is the kit bot
     */
    public static boolean isKitBot() {
    	return true;
    }
    
    /**
     * Call the diagnose functions on all of the subsystems.
     */
    public void diagnose() {
    	if(isKitBot()) {
    		Logger.defaultLogger.info("This is the kit bot.");
    	} else {
    		Logger.defaultLogger.info("This is the real (non-kit) robot.");
    	}
    	if(drivetrain.isNavxPresent()) {
    		Logger.defaultLogger.info("NavX is present.");
    	} else {
    		Logger.defaultLogger.warn("NavX is not connected.");
    	}
    	drivetrain.diagnose();
    	shooter.diagnose();
    	shooterFeeder.diagnose();
    	agitator.diagnose();
    	climber.diagnose();
    	gearPlacer.diagnose();
    }
    
    private void interceptOutputStream() {
    	System.setOut(new CustomStream(System.out));
    }
    
    private static class CustomStream extends PrintStream {

    	public CustomStream(OutputStream out) {
			super(out);
		}
    	
    	@Override
    	public void println(String s) {
    		if (!s.contains("navX-MXP SPI Read:  CRC error")){
    			super.println(s);
    		}
    	}

    }
}
