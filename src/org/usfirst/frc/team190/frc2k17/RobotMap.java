package org.usfirst.frc.team190.frc2k17;

import com.ctre.CANTalon.FeedbackDevice;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * new Key<type>(practice-robot, real-robot)
 */
public class RobotMap {
	public final Key<Integer>
		PWM_CAMERA_LIGHT = new Key<Integer>(0),
		PCM_SHIFTER = new Key<Integer>(0),
		PCM_GEAR_PUSHER = new Key<Integer>(1),
		PCM_BOOPERS = new Key<Integer>(4),
		CAN_DRIVE_MOTOR_LEFT_FRONT = new Key<Integer>(6),
		CAN_DRIVE_MOTOR_LEFT_REAR = new Key<Integer>(3),
		CAN_DRIVE_MOTOR_RIGHT_FRONT = new Key<Integer>(2),
		CAN_DRIVE_MOTOR_RIGHT_REAR = new Key<Integer>(5),
		CAN_SHOOTER_MOTOR_FLYWHEEL1 = new Key<Integer>(4),
		CAN_SHOOTER_MOTOR_FLYWHEEL2 = new Key<Integer>(7),
		CAN_SHOOTER_MOTOR_FEED = new Key<Integer>(8),
		CAN_COLLECTOR_MOTOR = new Key<Integer>(9),
		CAN_CLIMBER_MOTOR = new Key<Integer>(10),
		CAN_PCM = new Key<Integer>(20),
		DIO_PEG_LIMIT_SWITCH = new Key<Integer>(0),
		DRIVE_TICKS_PER_REV = new Key<Integer>(4096, (int)(256.0 * (36.0 / 12.0) * (50.0 / 34.0))), // For Real bot, 256 quadrature ticks / rev Gear Ratio: 36:12 50:34
		CAMERA_RESOLUTION_X = new Key<Integer>(320),
		CAMERA_RESOLUTION_Y = new Key<Integer>(240),
		CAMERA_EXPOSURE = new Key<Integer>(0);

	public final Key<String>
		NETWORKTABLE_KANGAROO_TABLE_NAME = new Key<String>("kangaroo"),
		NETWORKTABLE_KANGAROO_VOICE_LOG = new Key<String>("voicelog");
	
	public final Key<Double>
		DRIVE_PID_TURN_KP = new Key<Double>(0.015),
		DRIVE_PID_TURN_KI = new Key<Double>(0.005),
		DRIVE_PID_TURN_KD = new Key<Double>(0.012),
		DRIVE_PID_TURN_I_ERROR_LIMIT = new Key<Double>(5.0),
		DRIVE_PID_TURN_TOLERANCE = new Key<Double>(0.75),
		
		DRIVE_PID_DISTANCE_KP = new Key<Double>(0.023, 0.05),
		DRIVE_PID_DISTANCE_KI = new Key<Double>(0.002, 0.0),
		DRIVE_PID_DISTANCE_KD = new Key<Double>(0.02, 0.0),
		DRIVE_PID_DISTANCE_MAX = new Key<Double>(1.0),
		DRIVE_PID_DISTANCE_I_ERROR_LIMIT = new Key<Double>(4.0),
		DRIVE_PID_DISTANCE_TOLERANCE = new Key<Double>(0.5),
		
		DRIVE_PID_SPEED_KP = new Key<Double>(0.3),
		DRIVE_PID_SPEED_KI = new Key<Double>(0.0), //0.002
		DRIVE_PID_SPEED_KD = new Key<Double>(0.8),
		DRIVE_PID_SPEED_KF = new Key<Double>(0.34),
		
		DRIVE_PID_INCHES_PER_TICK = new Key<Double>(0.003, 1.0), // For real bot, 4 * pi circumference / 3072 ticks per rev
		DRIVE_MAX_SPEED_LOW = new Key<Double>(450.0, 390.0),
		DRIVE_MAX_SPEED_HIGH = new Key<Double>(600.0),
		CAMERA_HFOV = new Key<Double>(54.8),
		DRIVE_TO_PEG_OUTPUT_TOLERANCE = new Key<Double>(0.1),
		DRIVE_TO_PEG_MAX_SPEED = new Key<Double>(0.5),
		TURN_TO_PEG_OUTPUT_TOLERANCE = new Key<Double>(3.0),
		GEAR_PLACER_SET_TIMEOUT = new Key<Double>(5.0),
		GEAR_PRESENT_DRIVE_BACK_TIME = new Key<Double>(0.3),
		GEAR_PRESENT_KICK_TIMEOUT = new Key<Double>(0.1),
		JOYSTICK_DEADBAND = new Key<Double>(0.05),
		
		AUTOSHIFT_SAMPLE_RATE = new Key<Double>(50.0), // hz
		AUTOSHIFT_RPM_FREQ_CUTOFF = new Key<Double>(10.0), //hz; Max is 25 hz because 50 hz sampling
		AUTOSHIFT_RATE_RPM_FREQ_CUTOFF = new Key<Double>(10.0), //hz; Max is 25 hz because 50 hz sampling
		AUTOSHIFT_COOLDOWN = new Key<Double>(200.0), // milliseconds
		AUTOSHIFT_MIDDLE_THRESHOLD = new Key<Double>(302.0), // RPM
		AUTOSHIFT_MIDDLE_THRESHOLD_RATE = new Key<Double>(250.0), // delta(RPM)/sec
		AUTOSHIFT_LOWER_THRESHOLD = new Key<Double>(250.0), // RPM
		AUTOSHIFT_LOWER_THRESHOLD_DELAY = new Key<Double>(500.0), // milliseconds
		AUTOSHIFT_UPPER_THRESHOLD = new Key<Double>(370.0), // RPM
		AUTOSHIFT_UPPER_THRESHOLD_DELAY = new Key<Double>(500.0); // milliseconds
		

	public final Key<FeedbackDevice> 
		DRIVE_FEEDBACK_DEV = new Key<FeedbackDevice>(FeedbackDevice.CtreMagEncoder_Relative, FeedbackDevice.QuadEncoder);

	public final Key<Boolean>
		DRIVE_LEFT_MOTOR_INVERTED = new Key<Boolean>(false,true),
		DRIVE_RIGHT_MOTOR_INVERTED = new Key<Boolean>(true,false),
		DRIVE_LEFT_ENC_INVERTED = new Key<Boolean>(true, false),
		DRIVE_RIGHT_ENC_INVERTED = new Key<Boolean>(false, false),
		OI_INVERT_DRIVER_JOSTICK_1 = new Key<Boolean>(true),
		OI_INVERT_DRIVER_JOSTICK_2 = new Key<Boolean>(true);
	
	public final Key<Long>
		DRIVE_PID_TURN_WAIT = new Key<Long>(100L); // milliseconds
			
	private static RobotMap instance = null;
	
	private RobotMap() {
	}
	
	public static synchronized RobotMap getInstance() {
		if (instance == null) {
			instance = new RobotMap();
		}
		return instance;
	}

	public static class Key<T> {
		private T practiceValue, realValue;
		
		public Key(T practiceRobot, T realRobot) {
			practiceValue = practiceRobot;
			realValue = realRobot;
		}
		
		public Key(T both) {
			practiceValue = realValue = both;
		}
		
		public T get() {
			if (Robot.isKitBot())
				return practiceValue;
			else
				return realValue;
		}
	}
}
