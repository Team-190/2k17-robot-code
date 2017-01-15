package org.usfirst.frc.team190.frc2k17.commands;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToPegCommand extends Command {

    public DriveToPegCommand() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
    	NetworkTable grip = NetworkTable.getTable("/GRIP/frontCameraReport");	
		
		double[] heights = {0, 0};
		heights = grip.getNumberArray("height", heights);
		
		double targetHeight = 93.0;
		
		if (heights.length >= 2) {
			System.out.println("h1: " + heights[0]);
			System.out.println("h2: " + heights[1]);
			System.out.println("");
			
			double error = (targetHeight - heights[0]);
			double kP = SmartDashboard.getNumber("kP", 0.01);
			
			double output = (error * kP);
			
			NetworkTable table = NetworkTable.getTable("/kangaroo");
			String[] currentMessagesArr = table.getStringArray("voice", new String[0]);
			List<String> currentMessages = Arrays.asList(currentMessagesArr);
			currentMessages.add("message");
			table.putStringArray("voice", currentMessages.toArray(new String[currentMessages.size()]));
			
			
		} else {
			
		}
    }
    
    // TODO: Implement finished method when the robot arrives at peg
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
