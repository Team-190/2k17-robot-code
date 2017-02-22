package org.usfirst.frc.team190.frc2k17.subsystems.drivetrain;

public interface DriveController {
	public boolean isOnTarget();
	public double getLoopOutput();
	public void enable(double input);
	public void disable();
}
