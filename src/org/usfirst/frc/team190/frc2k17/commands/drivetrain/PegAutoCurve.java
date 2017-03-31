package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.Robot.Peg;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class PegAutoCurve extends ConditionalCommand {

	public PegAutoCurve(double duration) {
		super(new PegAutoCurveActual(duration,Peg.LEFT), new PegAutoCurveActual(duration,Peg.RIGHT));
	}

	@Override
	protected boolean condition() {
		return Robot.getPeg() == Peg.LEFT;
	}

}
