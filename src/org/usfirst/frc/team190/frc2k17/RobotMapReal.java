package org.usfirst.frc.team190.frc2k17;
import java.util.HashMap;

import com.ctre.CANTalon.FeedbackDevice;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMapReal extends RobotMap{
	
	public RobotMapReal() {
		PWM_CAMERA_LIGHT.set(0);
		PCM_SHIFTER.set(0);
		PCM_GEAR_PUSHER.set(2);
		PCM_BOOPERS.set(4);
		CAN_DRIVE_MOTOR_LEFT_FRONT.set(6);
		CAN_DRIVE_MOTOR_LEFT_REAR.set(3);
		CAN_DRIVE_MOTOR_RIGHT_FRONT.set(2);
		CAN_DRIVE_MOTOR_RIGHT_REAR.set(5);
		CAN_SHOOTER_MOTOR_FLYWHEEL1.set(4);
		CAN_SHOOTER_MOTOR_FLYWHEEL2.set(7);
		CAN_SHOOTER_MOTOR_FEED.set(8);
		CAN_COLLECTOR_MOTOR.set(9);
		CAN_CLIMBER_MOTOR.set(10);
		CAN_PCM.set(20);
		DIO_PEG_LIMIT_SWITCH.set(0);
		NETWORKTABLE_KANGAROO_TABLE_NAME.set("kangaroo");
		NETWORKTABLE_KANGAROO_VOICE_LOG.set("voicelog");
		DRIVE_PID_TURN_KP.set(0.015);
		DRIVE_PID_TURN_KI.set(0.002);
		DRIVE_PID_TURN_KD.set(0.04);
		DRIVE_PID_TURN_I_ERROR_LIMIT.set(5.0);
		DRIVE_PID_TURN_TOLERANCE.set(0.5);
		DRIVE_PID_DISTANCE_KP.set(0.023);
		DRIVE_PID_DISTANCE_KI.set(0.002);
		DRIVE_PID_DISTANCE_KD.set(0.02);
		DRIVE_PID_DISTANCE_MAX.set(1.0);
		DRIVE_PID_DISTANCE_I_ERROR_LIMIT.set(4.0);
		DRIVE_PID_DISTANCE_TOLERANCE.set(0.5);
		DRIVE_PID_SPEED_KP.set(0.111);
		DRIVE_PID_SPEED_KI.set(0.0);
		DRIVE_PID_SPEED_KD.set(0.5);
		DRIVE_PID_SPEED_KF.set(0.3188372672);
		DRIVE_PID_INCHES_PER_TICK.set(0.003);
		DRIVE_PID_TURN_WAIT.set(100L); // milliseconds
		DRIVE_MAX_SPEED_LOW.set(450.0);
		DRIVE_MAX_SPEED_HIGH.set(600.0);
		DRIVE_LEFT_MOTOR_INVERTED.set(true);
		DRIVE_RIGHT_MOTOR_INVERTED.set(false);
		DRIVE_LEFT_ENC_INVERTED.set(true);
		DRIVE_RIGHT_ENC_INVERTED.set(false);
		DRIVE_TICKS_PER_REV.set(4096);
		DRIVE_FEEDBACK_DEV.set(FeedbackDevice.CtreMagEncoder_Relative);
		OI_INVERT_DRIVER_JOSTICK_1.set(true);
		OI_INVERT_DRIVER_JOSTICK_2.set(true);
		CAMERA_RESOLUTION_X.set(320);
		CAMERA_RESOLUTION_Y.set(240);
		CAMERA_EXPOSURE.set(0);
		CAMERA_HFOV.set(54.8);
		DRIVE_TO_PEG_OUTPUT_TOLERANCE.set(0.1);
		DRIVE_TO_PEG_MAX_SPEED.set(0.5);
		TURN_TO_PEG_OUTPUT_TOLERANCE.set(3.0);
		GEAR_PLACER_SET_TIMEOUT.set(5.0);
		GEAR_PRESENT_DRIVE_BACK_TIME.set(0.3);
		GEAR_PRESENT_KICK_TIMEOUT.set(0.1);
	}
	
}
