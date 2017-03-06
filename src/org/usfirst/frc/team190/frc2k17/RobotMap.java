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
		RELAY_CAMERA_LIGHT = new Key<Integer>(0),
		DIO_LEDS_LEFT_R = new Key<Integer>(1),
		DIO_LEDS_LEFT_G = new Key<Integer>(2),
		DIO_LEDS_LEFT_B = new Key<Integer>(3),
		DIO_LEDS_RIGHT_R = new Key<Integer>(4),
		DIO_LEDS_RIGHT_G = new Key<Integer>(5),
		DIO_LEDS_RIGHT_B = new Key<Integer>(6),
		PCM_SHIFTER = new Key<Integer>(0),
		PCM_GEAR_PUSHER = new Key<Integer>(1),
		PCM_BOOPERS = new Key<Integer>(4),
		CAN_DRIVE_MOTOR_LEFT_FRONT = new Key<Integer>(3, 6),
		CAN_DRIVE_MOTOR_LEFT_REAR = new Key<Integer>(6, 3),
		CAN_DRIVE_MOTOR_RIGHT_FRONT = new Key<Integer>(2),
		CAN_DRIVE_MOTOR_RIGHT_REAR = new Key<Integer>(5),
		CAN_DRIVE_MOTOR_LEFT_MASTER = new Key<Integer>(CAN_DRIVE_MOTOR_LEFT_REAR.get(), CAN_DRIVE_MOTOR_LEFT_FRONT.get()),
		CAN_DRIVE_MOTOR_LEFT_SLAVE = new Key<Integer>(CAN_DRIVE_MOTOR_LEFT_FRONT.get(), CAN_DRIVE_MOTOR_LEFT_REAR.get()),
		CAN_DRIVE_MOTOR_RIGHT_MASTER = new Key<Integer>(CAN_DRIVE_MOTOR_RIGHT_FRONT.get(), CAN_DRIVE_MOTOR_RIGHT_FRONT.get()),
		CAN_DRIVE_MOTOR_RIGHT_SLAVE = new Key<Integer>(CAN_DRIVE_MOTOR_RIGHT_REAR.get(), CAN_DRIVE_MOTOR_RIGHT_REAR.get()),
		CAN_SHOOTER_MOTOR_FLYWHEEL1 = new Key<Integer>(4),
		CAN_SHOOTER_MOTOR_FLYWHEEL2 = new Key<Integer>(7),
		CAN_SHOOTER_MOTOR_FEED = new Key<Integer>(8),
		CAN_AGITATOR_MOTOR = new Key<Integer>(9),
		CAN_CLIMBER_MOTOR = new Key<Integer>(10),
		CAN_PCM = new Key<Integer>(20),
		CAN_PDP = new Key<Integer>(1),
		DIO_PEG_LIMIT_SWITCH = new Key<Integer>(0),
		DRIVE_TICKS_PER_REV = new Key<Integer>(4096, (int)(256.0 * (36.0 / 12.0) * (50.0 / 34.0))), // For Real bot, 256 quadrature ticks / rev Gear Ratio: 36:12 50:34
		CAMERA_RESOLUTION_X = new Key<Integer>(320),
		CAMERA_RESOLUTION_Y = new Key<Integer>(240),
		CAMERA_EXPOSURE = new Key<Integer>(0),
		SHIFT_PAUSE = new Key<Integer>(20), // milliseconds
		
		SHOOTER_RPM_TOLERANCE = new Key<Integer>(10);

	public final Key<String>
		NETWORKTABLE_KANGAROO_TABLE_NAME = new Key<String>("kangaroo"),
		NETWORKTABLE_KANGAROO_VOICE_LOG = new Key<String>("voicelog");
	
	public final Key<Double>
	
		ROBOT_MAIN_LOOP_RATE = new  Key<Double>(50.0), // hz
	
		DRIVE_PID_TURN_KP = new Key<Double>(0.015, 0.017),
		DRIVE_PID_TURN_KI = new Key<Double>(0.005, 0.002),
		DRIVE_PID_TURN_KD = new Key<Double>(0.012, 0.005),
		DRIVE_PID_TURN_I_ERROR_LIMIT = new Key<Double>(5.0),
		DRIVE_PID_TURN_TOLERANCE = new Key<Double>(0.75),
		
		DRIVE_PID_DISTANCE_KP = new Key<Double>(0.023, 0.05),
		DRIVE_PID_DISTANCE_KI = new Key<Double>(0.002, 0.0),
		DRIVE_PID_DISTANCE_KD = new Key<Double>(0.02, 0.0),
		DRIVE_PID_DISTANCE_MAX = new Key<Double>(1.0),
		DRIVE_PID_DISTANCE_I_ERROR_LIMIT = new Key<Double>(4.0),
		DRIVE_PID_DISTANCE_TOLERANCE = new Key<Double>(0.5),
		
		DRIVE_PID_SPEED_KP = new Key<Double>(0.111, 0.3),
		DRIVE_PID_SPEED_KI = new Key<Double>(0.0, 0.0), //0.002
		DRIVE_PID_SPEED_KD = new Key<Double>(0.5, 0.8),
		DRIVE_PID_SPEED_KF = new Key<Double>(0.3188372672, 0.34),
		
		DRIVE_WHEEL_DIAMETER_INCHES = new Key<Double>(4.0),
		
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
		
		CLIMBER_KILL_CURRENT = new Key<Double>(40.0),
		CLIMBER_SAMPLE_RATE = new Key<Double>(50.0), // hz
		CLIMBER_FREQ_CUTOFF = new Key<Double>(15.0), // hz
		CLIMBER_PID_KP = new Key<Double>(0.15),
		CLIMBER_PID_KI = new Key<Double>(0.0),
		CLIMBER_PID_KD = new Key<Double>(0.0),
		CLIMBER_PID_KF = new Key<Double>(0.0),
		CLIMBER_MAX_CURRENT = new Key<Double>(45.0),
		
		AUTOSHIFT_SAMPLE_RATE = ROBOT_MAIN_LOOP_RATE, // hz
		AUTOSHIFT_RPM_FREQ_CUTOFF = new Key<Double>(10.0), //hz; Max is 25 hz because 50 hz sampling
		AUTOSHIFT_RATE_RPM_FREQ_CUTOFF = new Key<Double>(10.0), //hz; Max is 25 hz because 50 hz sampling
		AUTOSHIFT_COOLDOWN = new Key<Double>(200.0), // milliseconds
		AUTOSHIFT_MIDDLE_THRESHOLD = new Key<Double>(302.0), // RPM
		AUTOSHIFT_MIDDLE_THRESHOLD_RATE = new Key<Double>(250.0), // delta(RPM)/sec
		AUTOSHIFT_LOWER_THRESHOLD = new Key<Double>(250.0), // RPM
		AUTOSHIFT_LOWER_THRESHOLD_DELAY = new Key<Double>(500.0), // milliseconds
		AUTOSHIFT_UPPER_THRESHOLD = new Key<Double>(370.0), // RPM
		AUTOSHIFT_UPPER_THRESHOLD_DELAY = new Key<Double>(500.0), // milliseconds
		
		AUTO_TIME_TO_STOP = new Key<Double>(14.5),
		LED_RAINBOW_TIME = new Key<Double>(5.0),
		LED_RAINBOW_REFRESH_RATE = ROBOT_MAIN_LOOP_RATE, // hz
		LED_CLIMBING_SIGNAL_TIME = new Key<Double>(10.0),
		PEGASSIST_TOLERANCE = new Key<Double>(2.0), // degrees
		
		SHOOTER_PID_KF = new Key<Double>(0.10455),
		SHOOTER_PID_KP = new Key<Double>(0.0),
		SHOOTER_PID_KI = new Key<Double>(0.0),
		SHOOTER_PID_KD = new Key<Double>(0.0);
		

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
		ROBOT_COMMS_TIMEOUT = new Key<Long>(250L), // milliseconds
		DRIVE_PID_TURN_WAIT = new Key<Long>(100L), // milliseconds
		PEGASSIST_REFRESH_TIME = new Key<Long>(200L); // milliseconds
			
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
