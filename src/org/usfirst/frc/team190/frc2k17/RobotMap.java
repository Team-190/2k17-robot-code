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
		
	}
	
	public static final class PCM {
		public static final int
		SHIFTERS_SHIFT_HIGH = 0,
		SHIFTERS_SHIFT_LOW = 1,
		
		GEAR_PUSH_OUT = 2,
		GEAR_PUSH_IN = 3,
		
		BOOPER_PUSH_OUT = 4,
		BOOPER_PULL_IN = 5;
		
		
	}
	
	public static final class CAN {
		public static final int
		DRIVE_MOTOR_LEFT_FRONT = 0,
		DRIVE_MOTOR_LEFT_REAR = 1, 
		DRIVE_MOTOR_RIGHT_FRONT = 2,
		DRIVE_MOTOR_RIGHT_REAR = 3,
		
		SHOOTER_MOTOR_FLYWHEEL1 = 4,
		SHOOTER_MOTOR_FLYWHEEL2 = 5,
		SHOOTER_MOTOR_FEED = 6,
		
		CLIMBER_MOTOR = 7;
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
		public static final int
		CAMERA_RESOLUTION_X = 320,
		CAMERA_RESOLUTION_Y = 240,
		CAMERA_EXPOSURE = 0;
		
		public static final double
		CAMERA_HFOV = 63.0,
		DRIVE_TO_PEG_OUTPUT_TOLERANCE = 0.1,
		DRIVE_TO_PEG_MAX_SPEED = 0.5,
		TURN_TO_PEG_OUTPUT_TOLERANCE = 3;
	}
}
