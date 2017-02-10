package org.usfirst.frc.team190.frc2k17;

import edu.wpi.first.wpilibj.Joystick;

public class FilteredJoystick extends Joystick {
	
	double deadband;
	
	public FilteredJoystick(int port) {
		super(port);
	}
	
	void setDeadband(double deadband) {
		this.deadband = deadband;
	}
	
	@Override
	public double getMagnitude() {
		double mag = super.getMagnitude();
		mag -= deadband;
		if (mag < 0) mag = 0;
		return mag / (1 - deadband);
	}
	
	@Override
	public double getAxis(AxisType axis) {
		double axisValue = 0;
		
		switch (axis) {
		case kX: {
			double mag = this.getMagnitude();
			axisValue = mag * Math.sin(this.getDirectionRadians());
			break;
		}
			
		case kY: {
			double mag = this.getMagnitude();
			axisValue = -mag * Math.cos(this.getDirectionRadians());
			break;
		}
			
		default:
			return super.getAxis(axis);
		}
		
		return axisValue;
	}
	
}
