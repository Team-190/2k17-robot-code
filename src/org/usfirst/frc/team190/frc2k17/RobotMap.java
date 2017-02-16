package org.usfirst.frc.team190.frc2k17;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public final class RobotMap {
	
	private RobotMap(){
		// private constructor to prevent this class from being instantiated
	}
	
	public static final class PWM {
		public static final int
		CAMERA_LIGHT = 0;
	}
	
	public static final class PCM {
		public static final int
		SHIFTERS_SHIFT_HIGH = 0,
		SHIFTERS_SHIFT_LOW = 1,
		
		GEAR_PUSH_OUT = 2,
		GEAR_PUSH_IN = 3,
		
		BOOPERS_PUSH_OUT = 4,
		BOOPERS_PULL_IN = 5;
	}
	
	public static final class CAN {
		public static final int
		DRIVE_MOTOR_LEFT_FRONT = 6,
		DRIVE_MOTOR_LEFT_REAR = 3, 
		DRIVE_MOTOR_RIGHT_FRONT = 2,
		DRIVE_MOTOR_RIGHT_REAR = 5,
		
		SHOOTER_MOTOR_FLYWHEEL1 = 4,
		SHOOTER_MOTOR_FLYWHEEL2 = 7,
		SHOOTER_MOTOR_FEED = 8,
		
		COLLECTOR_MOTOR = 9,
		
		CLIMBER_MOTOR = 10;
	}
	
	public static final class DIO {
		public static final int
		GEAR_PUSHER_SENSOR = 0;
		
	}
	
	public static final class NetworkTable {
		public static final class Kangaroo {
			public static final String
			TABLE_NAME = "kangaroo",
			VOICE_LOG = "voicelog";
		}
	}
	
	public static final class Constants {
		public static final class Drivetrain {
			public static final class PID {

				public static final double
				TURN_KP = 0.015,
				TURN_KI = 0.002,
				TURN_KD = 0.04,
				TURN_I_ERROR_LIMIT = 5,
				TURN_TOLERANCE = 0.5,
				
				DISTANCE_KP = 0.023,
				DISTANCE_KI = 0.002,
				DISTANCE_KD = 0.02,
				DISTANCE_MAX = 1.0,
				DISTANCE_I_ERROR_LIMIT = 4.0,
				DISTANCE_TOLERANCE = 0.5,
				
				SPEED_KP = 0.1,
				SPEED_KI = 0.0,
				SPEED_KD = 1.25,
				SPEED_KF = 0.37463378906249994,
				
				INCHES_PER_TICK = 0.003;
				
				public static final long
				DRIVE_PID_TURN_WAIT = 100; // milliseconds
				
			}
			
			public static final double
			MAX_SPEED_LOW = 337,
			MAX_SPEED_HIGH = 600;
			
			public static final boolean
			DRIVE_LEFT_INVERTED = false,
			DRIVE_RIGHT_INVERTED = true;

			public static final boolean
			INVERT_LEFT_ENC = true,
			INVERT_RIGHT_ENC = false;
		}
		
		public static final class OI {
			public static final boolean
			INVERT_DRIVER_JOSTICK_1 = true,
			INVERT_DRIVER_JOSTICK_2 = true;
		}
		
		public static final int
		CAMERA_RESOLUTION_X = 320,
		CAMERA_RESOLUTION_Y = 240,
		CAMERA_EXPOSURE = 0;
		
		public static final double
		CAMERA_HFOV = 54.8,
		DRIVE_TO_PEG_OUTPUT_TOLERANCE = 0.1,
		DRIVE_TO_PEG_MAX_SPEED = 0.5,
		TURN_TO_PEG_OUTPUT_TOLERANCE = 3;
	}
}
