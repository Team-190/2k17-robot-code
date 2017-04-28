package org.usfirst.frc.team190.frc2k17.commands.drivetrain;

import org.usfirst.frc.team190.frc2k17.Robot;
import org.usfirst.frc.team190.frc2k17.Robot.Peg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class PegAutoCurve extends ConditionalCommand {

	public PegAutoCurve(double duration) {
		super(new InternalCommand(duration, Alliance.Red), new InternalCommand(duration, Alliance.Blue));
	}

	@Override
	protected boolean condition() {
		return DriverStation.getInstance().getAlliance() == Alliance.Red;
	}
	
	private static class InternalCommand extends ConditionalCommand {
		
		public InternalCommand(double duration, Alliance alliance) {
			super(new PegAutoCurveActual(duration, alliance, Peg.LEFT), new PegAutoCurveActual(duration, alliance, Peg.RIGHT));
		}
		
		@Override
		protected boolean condition() {
			return Robot.getPeg() == Peg.LEFT;
		}
	}

}
