
package org.usfirst.frc.team190.frc2k17;

import org.usfirst.frc.team190.frc2k17.subsystems.Boopers;
import org.usfirst.frc.team190.frc2k17.subsystems.GearCamera;
import org.usfirst.frc.team190.frc2k17.subsystems.GearPlacer;
import org.usfirst.frc.team190.frc2k17.subsystems.Climber;
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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	public static OI oi;
	
	private static Compressor compressor;
	
    Command autonomousCommand;
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
		oi = new OI();
        //chooser = new SendableChooser();
        //chooser.addObject("My Auto", new MyAutoCommand());
        //SmartDashboard.putData("Auto mode", chooser);
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(RobotMap.getInstance().CAMERA_RESOLUTION_X.get(),
							 RobotMap.getInstance().CAMERA_RESOLUTION_Y.get());
		camera.setExposureManual(RobotMap.getInstance().CAMERA_EXPOSURE.get());
		
		compressor = new Compressor();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	Logger.defaultLogger.info("Robot Disabled.");
    	Logger.kangarooVoice.info("disabled");
    	
    	compressor.stop();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	Logger.kangarooVoice.info("teleop");
    	Logger.defaultLogger.info("Teleop mode started.");
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	
    	compressor.start();
    	
        if (autonomousCommand != null) autonomousCommand.cancel();
        //if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		drivetrain.outputEncoderValues();
        Scheduler.getInstance().run();        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    /**
     * @return whether the robot is the kit bot
     */
    public static boolean isKitBot() {
    	return false;
    }
}
