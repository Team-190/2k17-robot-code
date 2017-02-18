package org.usfirst.frc.team190.frc2k17;
import com.ctre.CANTalon.FeedbackDevice;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public final class RobotMap {
	
	private static boolean IS_KIT_BOT;
	
	{
		// TODO calculate IS_KIT_BOT based on something
		IS_KIT_BOT = true;
	}
	
	private RobotMap(){
		// private constructor to prevent this class from being instantiated
	}
	
	public static final class PWM {
		public static final int
		CAMERA_LIGHT = IS_KIT_BOT ? RobotMapKitBot.PWM.CAMERA_LIGHT : RobotMapReal.PWM.CAMERA_LIGHT;
	}
	
	public static final class PCM {
		public static final int
		SHIFTER = IS_KIT_BOT ? RobotMapKitBot.PCM.SHIFTER : RobotMapReal.PCM.SHIFTER,
		
		GEAR_PUSHER = IS_KIT_BOT ? RobotMapKitBot.PCM.GEAR_PUSHER : RobotMapReal.PCM.GEAR_PUSHER,
		
		BOOPERS = IS_KIT_BOT ? RobotMapKitBot.PCM.BOOPERS : RobotMapReal.PCM.BOOPERS;
	}
	
	public static final class CAN {
		public static final int
		DRIVE_MOTOR_LEFT_FRONT = IS_KIT_BOT ? RobotMapKitBot.CAN.DRIVE_MOTOR_LEFT_FRONT : RobotMapReal.CAN.DRIVE_MOTOR_LEFT_FRONT,
		DRIVE_MOTOR_LEFT_REAR = IS_KIT_BOT ? RobotMapKitBot.CAN.DRIVE_MOTOR_LEFT_REAR : RobotMapReal.CAN.DRIVE_MOTOR_LEFT_REAR,
		DRIVE_MOTOR_RIGHT_FRONT = IS_KIT_BOT ? RobotMapKitBot.CAN.DRIVE_MOTOR_RIGHT_FRONT : RobotMapReal.CAN.DRIVE_MOTOR_RIGHT_FRONT,
		DRIVE_MOTOR_RIGHT_REAR = IS_KIT_BOT ? RobotMapKitBot.CAN.DRIVE_MOTOR_RIGHT_REAR : RobotMapReal.CAN.DRIVE_MOTOR_RIGHT_REAR,
		
		SHOOTER_MOTOR_FLYWHEEL1 = IS_KIT_BOT ? RobotMapKitBot.CAN.SHOOTER_MOTOR_FLYWHEEL1 : RobotMapReal.CAN.SHOOTER_MOTOR_FLYWHEEL1,
		SHOOTER_MOTOR_FLYWHEEL2 = IS_KIT_BOT ? RobotMapKitBot.CAN.SHOOTER_MOTOR_FLYWHEEL2 : RobotMapReal.CAN.SHOOTER_MOTOR_FLYWHEEL2,
		SHOOTER_MOTOR_FEED = IS_KIT_BOT ? RobotMapKitBot.CAN.SHOOTER_MOTOR_FEED : RobotMapReal.CAN.SHOOTER_MOTOR_FEED,
		
		COLLECTOR_MOTOR = IS_KIT_BOT ? RobotMapKitBot.CAN.COLLECTOR_MOTOR : RobotMapReal.CAN.COLLECTOR_MOTOR,
		
		CLIMBER_MOTOR = IS_KIT_BOT ? RobotMapKitBot.CAN.CLIMBER_MOTOR : RobotMapReal.CAN.CLIMBER_MOTOR,
		
		PCM = IS_KIT_BOT ? RobotMapKitBot.CAN.PCM : RobotMapReal.CAN.PCM;
	}
	
	public static final class DIO {
		public static final int
		PEG_LIMIT_SWITCH = IS_KIT_BOT ? RobotMapKitBot.DIO.PEG_LIMIT_SWITCH : RobotMapReal.DIO.PEG_LIMIT_SWITCH,
		GEAR_PLACER_FULLY_EXTENDED = IS_KIT_BOT ? RobotMapKitBot.DIO.GEAR_PLACER_FULLY_EXTENDED : RobotMapReal.DIO.GEAR_PLACER_FULLY_EXTENDED;
		
	}
	
	public static final class NetworkTable {
		public static final class Kangaroo {
			public static final String
			TABLE_NAME = IS_KIT_BOT ? RobotMapKitBot.NetworkTable.Kangaroo.TABLE_NAME : RobotMapReal.NetworkTable.Kangaroo.TABLE_NAME,
			VOICE_LOG = IS_KIT_BOT ? RobotMapKitBot.NetworkTable.Kangaroo.VOICE_LOG : RobotMapReal.NetworkTable.Kangaroo.VOICE_LOG;
		}
	}
	
	public static final class Constants {
		public static final class Drivetrain {
			public static final class PID {

				public static final double
				TURN_KP = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.TURN_KP : RobotMapReal.Constants.Drivetrain.PID.TURN_KP,
				TURN_KI = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.TURN_KI : RobotMapReal.Constants.Drivetrain.PID.TURN_KI,
				TURN_KD = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.TURN_KD : RobotMapReal.Constants.Drivetrain.PID.TURN_KD,
				TURN_I_ERROR_LIMIT = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.TURN_I_ERROR_LIMIT : RobotMapReal.Constants.Drivetrain.PID.TURN_I_ERROR_LIMIT,
				TURN_TOLERANCE = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.TURN_TOLERANCE : RobotMapReal.Constants.Drivetrain.PID.TURN_TOLERANCE,
				
				DISTANCE_KP = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_KP : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_KP,
				DISTANCE_KI = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_KI : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_KI,
				DISTANCE_KD = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_KD : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_KD,
				DISTANCE_MAX = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_MAX : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_MAX,
				DISTANCE_I_ERROR_LIMIT = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_I_ERROR_LIMIT : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_I_ERROR_LIMIT,
				DISTANCE_TOLERANCE = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DISTANCE_TOLERANCE : RobotMapReal.Constants.Drivetrain.PID.DISTANCE_TOLERANCE,
				
				SPEED_KP = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.SPEED_KP : RobotMapReal.Constants.Drivetrain.PID.SPEED_KP,
				SPEED_KI = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.SPEED_KI : RobotMapReal.Constants.Drivetrain.PID.SPEED_KI,
				SPEED_KD = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.SPEED_KD : RobotMapReal.Constants.Drivetrain.PID.SPEED_KD,
				SPEED_KF = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.SPEED_KF : RobotMapReal.Constants.Drivetrain.PID.SPEED_KF,
				
				INCHES_PER_TICK = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.INCHES_PER_TICK : RobotMapReal.Constants.Drivetrain.PID.INCHES_PER_TICK;
				
				public static final long
				DRIVE_PID_TURN_WAIT = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.PID.DRIVE_PID_TURN_WAIT : RobotMapReal.Constants.Drivetrain.PID.DRIVE_PID_TURN_WAIT;
				
			}
			
			public static final double
			MAX_SPEED_LOW = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.MAX_SPEED_LOW : RobotMapReal.Constants.Drivetrain.MAX_SPEED_LOW,
			MAX_SPEED_HIGH = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.MAX_SPEED_HIGH : RobotMapReal.Constants.Drivetrain.MAX_SPEED_HIGH;
			
			public static final boolean
			DRIVE_LEFT_INVERTED = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.DRIVE_LEFT_INVERTED : RobotMapReal.Constants.Drivetrain.DRIVE_LEFT_INVERTED,
			DRIVE_RIGHT_INVERTED = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.DRIVE_RIGHT_INVERTED : RobotMapReal.Constants.Drivetrain.DRIVE_RIGHT_INVERTED,
			INVERT_LEFT_ENC = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.INVERT_LEFT_ENC : RobotMapReal.Constants.Drivetrain.INVERT_LEFT_ENC,
			INVERT_RIGHT_ENC = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.INVERT_RIGHT_ENC : RobotMapReal.Constants.Drivetrain.INVERT_RIGHT_ENC;
			
			public static int TICKS_PER_REV = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.TICKS_PER_REV : RobotMapReal.Constants.Drivetrain.TICKS_PER_REV;
			public static FeedbackDevice DRIVE_FEEDBACK_DEV = IS_KIT_BOT ? RobotMapKitBot.Constants.Drivetrain.DRIVE_FEEDBACK_DEV : RobotMapReal.Constants.Drivetrain.DRIVE_FEEDBACK_DEV;
		}
		
		public static final class OI {
			public static final boolean
			INVERT_DRIVER_JOSTICK_1 = IS_KIT_BOT ? RobotMapKitBot.Constants.OI.INVERT_DRIVER_JOSTICK_1 : RobotMapReal.Constants.OI.INVERT_DRIVER_JOSTICK_1,
			INVERT_DRIVER_JOSTICK_2 = IS_KIT_BOT ? RobotMapKitBot.Constants.OI.INVERT_DRIVER_JOSTICK_2 : RobotMapReal.Constants.OI.INVERT_DRIVER_JOSTICK_2;
		}
		
		public static final int
		CAMERA_RESOLUTION_X = IS_KIT_BOT ? RobotMapKitBot.Constants.CAMERA_RESOLUTION_X : RobotMapReal.Constants.CAMERA_RESOLUTION_X,
		CAMERA_RESOLUTION_Y = IS_KIT_BOT ? RobotMapKitBot.Constants.CAMERA_RESOLUTION_Y : RobotMapReal.Constants.CAMERA_RESOLUTION_Y,
		CAMERA_EXPOSURE = IS_KIT_BOT ? RobotMapKitBot.Constants.CAMERA_EXPOSURE : RobotMapReal.Constants.CAMERA_EXPOSURE,
		GEAR_PLACER_SET_TIMEOUT = IS_KIT_BOT ? RobotMapKitBot.Constants.GEAR_PLACER_SET_TIMEOUT : RobotMapReal.Constants.GEAR_PLACER_SET_TIMEOUT;
		
		public static final double
		CAMERA_HFOV = IS_KIT_BOT ? RobotMapKitBot.Constants.CAMERA_HFOV : RobotMapReal.Constants.CAMERA_HFOV,
		DRIVE_TO_PEG_OUTPUT_TOLERANCE = IS_KIT_BOT ? RobotMapKitBot.Constants.DRIVE_TO_PEG_OUTPUT_TOLERANCE : RobotMapReal.Constants.DRIVE_TO_PEG_OUTPUT_TOLERANCE,
		DRIVE_TO_PEG_MAX_SPEED = IS_KIT_BOT ? RobotMapKitBot.Constants.DRIVE_TO_PEG_MAX_SPEED : RobotMapReal.Constants.DRIVE_TO_PEG_MAX_SPEED,
		TURN_TO_PEG_OUTPUT_TOLERANCE = IS_KIT_BOT ? RobotMapKitBot.Constants.TURN_TO_PEG_OUTPUT_TOLERANCE : RobotMapReal.Constants.TURN_TO_PEG_OUTPUT_TOLERANCE;
	}
}
