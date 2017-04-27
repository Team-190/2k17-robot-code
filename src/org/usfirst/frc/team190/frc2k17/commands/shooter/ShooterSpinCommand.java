package org.usfirst.frc.team190.frc2k17.commands.shooter;

import org.usfirst.frc.team190.frc2k17.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSpinCommand extends Command {

	private Double leftFlywheel, rightFlywheel;
	
    public ShooterSpinCommand() {
    	requires(Robot.shooter);
    	leftFlywheel = null;
    	rightFlywheel = null;
    }
    
    public ShooterSpinCommand(double leftFlywheel, double rightFlywheel) {
    	requires(Robot.shooter);
    	this.leftFlywheel = leftFlywheel;
    	this.rightFlywheel = rightFlywheel;
    }

    protected void initialize() {
    	if(leftFlywheel == null || rightFlywheel == null) {
    		Robot.shooter.shooterOn();
    	} else {
    		Robot.shooter.shooterOn(leftFlywheel, rightFlywheel);
    	}
    }
    
    protected void execute() {
    	Robot.shooter.outputEncoderValues();
    }

    protected boolean isFinished() {
    	return false;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.shooterOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
