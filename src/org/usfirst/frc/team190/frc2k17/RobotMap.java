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
	
	public static final class CAN {
		public static final int
		DRIVE_MOTOR_LEFT_FRONT = 0,
		DRIVE_MOTOR_LEFT_REAR = 1, 
		DRIVE_MOTOR_RIGHT_FRONT = 2,
		DRIVE_MOTOR_RIGHT_REAR = 3;
	}
	
	public static final class DIO {
		public static final int
		DRIVE_ENCODER_LEFT_A = 0,
		DRIVE_ENCODER_LEFT_B = 1,
		DRIVE_ENCODER_RIGHT_A = 2,
		DRIVE_ENCODER_RIGHT_B = 3;
	}
	
	public static final class NetworkTable {
		public static final class Kangaroo {
			public static final String
			TABLE_NAME = "kangaroo",
			VOICE_LOG = "voicelog";
		}
	}
	
	public static final class Constants {
		public static final int CAMERA_RESOLUTION_X = 320;
		public static final int CAMERA_RESOLUTION_Y = 240;
		public static final int CAMERA_EXPOSURE = 0;
		public static final double DRIVE_TO_PEG_OUTPUT_TOLERANCE = 0.1;
		public static final double TURN_TO_PEG_OUTPUT_TOLERANCE = 3;
	}
}
