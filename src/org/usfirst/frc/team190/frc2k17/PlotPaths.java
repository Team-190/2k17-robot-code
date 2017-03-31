package org.usfirst.frc.team190.frc2k17;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import org.usfirst.frc.team190.frc2k17.Robot.Peg;
import org.usfirst.frc.team190.frc2k17.commands.AutoCurveThreaded;
import org.usfirst.frc.team190.frc2k17.commands.drivetrain.PegAutoCurveActual;

public class PlotPaths {
	
	private static AutoCurveThreaded hopperCommand = new AutoCurveThreaded(5);
	private static PegAutoCurveActual leftPegCommand = new PegAutoCurveActual(3.0, Peg.LEFT);

	public static void main(String[] args) {
		if(!GraphicsEnvironment.isHeadless())
		{
			plotHopper();
			plotLeftPeg();
		}

	}
	
	private static void plotHopper() {
		FalconLinePlot plot0 = new FalconLinePlot(hopperCommand.path.smoothCenterVelocity,null,Color.blue);
		plot0.yGridOn();
		plot0.xGridOn();
		plot0.setYLabel("Velocity (in/sec)");
		plot0.setXLabel("time (seconds)");
		plot0.setTitle("Velocity Profile for Left and Right Wheels \n Left = Cyan, Right = Magenta");
		plot0.addData(hopperCommand.path.smoothRightVelocity, Color.magenta);
		plot0.addData(hopperCommand.path.smoothLeftVelocity, Color.cyan);

		FalconLinePlot plot1 = new FalconLinePlot(hopperCommand.path.nodeOnlyPath,Color.blue,Color.green);
		plot1.yGridOn();
		plot1.xGridOn();
		plot1.setYLabel("Y (in)");
		plot1.setXLabel("X (in)");
		plot1.setTitle("Top Down View of FRC Field (24ft x 27ft) \n shows global position of robot path, along with left and right wheel trajectories");

		//force graph to show 1/2 field dimensions of 24ft x 27 feet
		plot1.setXTic(0, 27*12, 24);
		plot1.setYTic(0, 24*12, 24);
		plot1.addData(hopperCommand.path.smoothPath, Color.red, Color.blue);

		plot1.addData(hopperCommand.path.leftPath, Color.magenta);
		plot1.addData(hopperCommand.path.rightPath, Color.magenta);
	}
	
	private static void plotLeftPeg() {
		FalconLinePlot plot0 = new FalconLinePlot(leftPegCommand.path.smoothCenterVelocity,null,Color.blue);
		plot0.yGridOn();
		plot0.xGridOn();
		plot0.setYLabel("Velocity (in/sec)");
		plot0.setXLabel("time (seconds)");
		plot0.setTitle("Velocity Profile for Left and Right Wheels \n Left = Cyan, Right = Magenta");
		plot0.addData(leftPegCommand.path.smoothRightVelocity, Color.magenta);
		plot0.addData(leftPegCommand.path.smoothLeftVelocity, Color.cyan);

		FalconLinePlot plot1 = new FalconLinePlot(leftPegCommand.path.nodeOnlyPath,Color.blue,Color.green);
		plot1.yGridOn();
		plot1.xGridOn();
		plot1.setYLabel("Y (in)");
		plot1.setXLabel("X (in)");
		plot1.setTitle("Top Down View of FRC Field (24ft x 27ft) \n shows global position of robot path, along with left and right wheel trajectories");

		plot1.setXTic(0, 65.27, 12);
		plot1.setYTic(0, 101.98, 12);
		plot1.addData(leftPegCommand.path.smoothPath, Color.red, Color.blue);

		plot1.addData(leftPegCommand.path.leftPath, Color.magenta);
		plot1.addData(leftPegCommand.path.rightPath, Color.magenta);
	}

}
