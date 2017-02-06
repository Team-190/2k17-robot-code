package org.usfirst.frc.team190.frc2k17;

import edu.wpi.first.wpilibj.PIDOutput;

public class SimplePIDOutput implements PIDOutput {
	
	private double pidOutput;
	
	public double getPidOutput() {
		return pidOutput;
	}

	@Override
	public void pidWrite(double output) {
		pidOutput = output;
	}
}