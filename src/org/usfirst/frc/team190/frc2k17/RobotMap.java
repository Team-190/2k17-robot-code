package org.usfirst.frc.team190.frc2k17;

import com.ctre.CANTalon.FeedbackDevice;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public abstract class RobotMap {
	
	public final Key<Integer>
	PWM_CAMERA_LIGHT,
	PCM_SHIFTER,
	PCM_GEAR_PUSHER,
	PCM_BOOPERS,
	CAN_DRIVE_MOTOR_LEFT_FRONT,
	CAN_DRIVE_MOTOR_LEFT_REAR,
	CAN_DRIVE_MOTOR_RIGHT_FRONT,
	CAN_DRIVE_MOTOR_RIGHT_REAR,
	CAN_SHOOTER_MOTOR_FLYWHEEL1,
	CAN_SHOOTER_MOTOR_FLYWHEEL2,
	CAN_SHOOTER_MOTOR_FEED,
	CAN_COLLECTOR_MOTOR,
	CAN_CLIMBER_MOTOR,
	CAN_PCM,
	DIO_PEG_LIMIT_SWITCH,
	DRIVE_TICKS_PER_REV,
	CAMERA_RESOLUTION_X,
	CAMERA_RESOLUTION_Y,
	CAMERA_EXPOSURE;
	
	public final Key<Double>
	DRIVE_PID_TURN_KP,
	DRIVE_PID_TURN_KI,
	DRIVE_PID_TURN_KD,
	DRIVE_PID_TURN_I_ERROR_LIMIT,
	DRIVE_PID_TURN_TOLERANCE,
	DRIVE_PID_DISTANCE_KP,
	DRIVE_PID_DISTANCE_KI,
	DRIVE_PID_DISTANCE_KD,
	DRIVE_PID_DISTANCE_MAX,
	DRIVE_PID_DISTANCE_I_ERROR_LIMIT,
	DRIVE_PID_DISTANCE_TOLERANCE,
	DRIVE_PID_SPEED_KP,
	DRIVE_PID_SPEED_KI,
	DRIVE_PID_SPEED_KD,
	DRIVE_PID_SPEED_KF,
	DRIVE_PID_INCHES_PER_TICK,
	DRIVE_MAX_SPEED_LOW,
	DRIVE_MAX_SPEED_HIGH,
	CAMERA_HFOV,
	DRIVE_TO_PEG_OUTPUT_TOLERANCE,
	DRIVE_TO_PEG_MAX_SPEED,
	TURN_TO_PEG_OUTPUT_TOLERANCE,
	GEAR_PLACER_SET_TIMEOUT,
	GEAR_PRESENT_DRIVE_BACK_TIME,
	GEAR_PRESENT_KICK_TIMEOUT,
	JOYSTICK_DEADBAND;
	
	public final Key<Long>
	DRIVE_PID_TURN_WAIT;
	
	public final Key<Boolean>
	DRIVE_LEFT_MOTOR_INVERTED,
	DRIVE_RIGHT_MOTOR_INVERTED,
	DRIVE_LEFT_ENC_INVERTED,
	DRIVE_RIGHT_ENC_INVERTED,
	OI_INVERT_DRIVER_JOSTICK_1,
	OI_INVERT_DRIVER_JOSTICK_2;
	
	public final Key<String>
	NETWORKTABLE_KANGAROO_TABLE_NAME,
	NETWORKTABLE_KANGAROO_VOICE_LOG;
	
	public final Key<FeedbackDevice> 
	DRIVE_FEEDBACK_DEV;
	
	private static RobotMap instance;
	
	public RobotMap() {
		PWM_CAMERA_LIGHT = new Key<Integer>();
		PCM_SHIFTER = new Key<Integer>();
		PCM_GEAR_PUSHER = new Key<Integer>();
		PCM_BOOPERS = new Key<Integer>();
		CAN_DRIVE_MOTOR_LEFT_FRONT = new Key<Integer>();
		CAN_DRIVE_MOTOR_LEFT_REAR = new Key<Integer>();
		CAN_DRIVE_MOTOR_RIGHT_FRONT = new Key<Integer>();
		CAN_DRIVE_MOTOR_RIGHT_REAR = new Key<Integer>();
		CAN_SHOOTER_MOTOR_FLYWHEEL1 = new Key<Integer>();
		CAN_SHOOTER_MOTOR_FLYWHEEL2 = new Key<Integer>();
		CAN_SHOOTER_MOTOR_FEED = new Key<Integer>();
		CAN_COLLECTOR_MOTOR = new Key<Integer>();
		CAN_CLIMBER_MOTOR = new Key<Integer>();
		CAN_PCM = new Key<Integer>();
		DIO_PEG_LIMIT_SWITCH = new Key<Integer>();
		NETWORKTABLE_KANGAROO_TABLE_NAME = new Key<String>();
		NETWORKTABLE_KANGAROO_VOICE_LOG = new Key<String>();
		DRIVE_PID_TURN_KP = new Key<Double>();
		DRIVE_PID_TURN_KI = new Key<Double>();
		DRIVE_PID_TURN_KD = new Key<Double>();
		DRIVE_PID_TURN_I_ERROR_LIMIT = new Key<Double>();
		DRIVE_PID_TURN_TOLERANCE = new Key<Double>();
		DRIVE_PID_DISTANCE_KP = new Key<Double>();
		DRIVE_PID_DISTANCE_KI = new Key<Double>();
		DRIVE_PID_DISTANCE_KD = new Key<Double>();
		DRIVE_PID_DISTANCE_MAX = new Key<Double>();
		DRIVE_PID_DISTANCE_I_ERROR_LIMIT = new Key<Double>();
		DRIVE_PID_DISTANCE_TOLERANCE = new Key<Double>();
		DRIVE_PID_SPEED_KP = new Key<Double>();
		DRIVE_PID_SPEED_KI = new Key<Double>();
		DRIVE_PID_SPEED_KD = new Key<Double>();
		DRIVE_PID_SPEED_KF = new Key<Double>();
		DRIVE_PID_INCHES_PER_TICK = new Key<Double>();
		DRIVE_PID_TURN_WAIT = new Key<Long>();
		DRIVE_MAX_SPEED_LOW = new Key<Double>();
		DRIVE_MAX_SPEED_HIGH = new Key<Double>();
		DRIVE_LEFT_MOTOR_INVERTED = new Key<Boolean>();
		DRIVE_RIGHT_MOTOR_INVERTED = new Key<Boolean>();
		DRIVE_LEFT_ENC_INVERTED = new Key<Boolean>();
		DRIVE_RIGHT_ENC_INVERTED = new Key<Boolean>();
		DRIVE_TICKS_PER_REV = new Key<Integer>();
		DRIVE_FEEDBACK_DEV = new Key<FeedbackDevice>();
		OI_INVERT_DRIVER_JOSTICK_1 = new Key<Boolean>();
		OI_INVERT_DRIVER_JOSTICK_2 = new Key<Boolean>();
		CAMERA_RESOLUTION_X = new Key<Integer>();
		CAMERA_RESOLUTION_Y = new Key<Integer>();
		CAMERA_EXPOSURE = new Key<Integer>();
		CAMERA_HFOV = new Key<Double>();
		DRIVE_TO_PEG_OUTPUT_TOLERANCE = new Key<Double>();
		DRIVE_TO_PEG_MAX_SPEED = new Key<Double>();
		TURN_TO_PEG_OUTPUT_TOLERANCE = new Key<Double>();
		GEAR_PLACER_SET_TIMEOUT = new Key<Double>();
		GEAR_PRESENT_DRIVE_BACK_TIME = new Key<Double>();
		GEAR_PRESENT_KICK_TIMEOUT = new Key<Double>();
		JOYSTICK_DEADBAND = new Key<Double>();
	}

	public static void init(Class<? extends RobotMap> type) {
		try {
			instance = type.newInstance();
		} catch (InstantiationException e) {
			Logger.defaultLogger.critical("Failed to instantiate RobotMap", e.getCause());
			assert false;
		} catch (IllegalAccessException e) {
			Logger.defaultLogger.critical("Failed to instantiate RobotMap", e);
			assert false;
		}
	}
	
	public static RobotMap getInstance() {
		if(instance == null) {
			Logger.defaultLogger.critical("Attempted to access RobotMap before initializing it.");
			assert false;
		}
		return instance;
	}
	
	public class Key<T> {
		
		private T value;
		
		private Key() {
			// private constructor so this class can only be instantiated in RobotMap
		}
		
		public T get() {
			if(value == null) {
				Logger.defaultLogger.critical("Retrieving value of uninitialized RobotMap constant.");
			}
			return value;
		}
		
		protected void set(T value) {
			this.value = value;
		}
		
	}
}
