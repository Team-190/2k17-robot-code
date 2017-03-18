package org.usfirst.frc.team190.frc2k17;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import org.usfirst.frc.team190.frc2k17.commands.AutoCurveThreaded;

public class PlotPaths {
	
	private static AutoCurveThreaded hopperCommand = new AutoCurveThreaded(5);

	public static void main(String[] args) {
		if(!GraphicsEnvironment.isHeadless())
		{

			FalconLinePlot hopperPlot0 = new FalconLinePlot(hopperCommand.path.smoothCenterVelocity,null,Color.blue);
			hopperPlot0.yGridOn();
			hopperPlot0.xGridOn();
			hopperPlot0.setYLabel("Velocity (in/sec)");
			hopperPlot0.setXLabel("time (seconds)");
			hopperPlot0.setTitle("Velocity Profile for Left and Right Wheels \n Left = Cyan, Right = Magenta");
			hopperPlot0.addData(hopperCommand.path.smoothRightVelocity, Color.magenta);
			hopperPlot0.addData(hopperCommand.path.smoothLeftVelocity, Color.cyan);

			FalconLinePlot hopperPlot1 = new FalconLinePlot(hopperCommand.path.nodeOnlyPath,Color.blue,Color.green);
			hopperPlot1.yGridOn();
			hopperPlot1.xGridOn();
			hopperPlot1.setYLabel("Y (feet)");
			hopperPlot1.setXLabel("X (feet)");
			hopperPlot1.setTitle("Top Down View of FRC Field (24ft x 27ft) \n shows global position of robot path, along with left and right wheel trajectories");

			//force graph to show 1/2 field dimensions of 24ft x 27 feet
			hopperPlot1.setXTic(0, 27*12, 24);
			hopperPlot1.setYTic(0, 24*12, 24);
			hopperPlot1.addData(hopperCommand.path.smoothPath, Color.red, Color.blue);


			hopperPlot1.addData(hopperCommand.path.leftPath, Color.magenta);
			hopperPlot1.addData(hopperCommand.path.rightPath, Color.magenta);

		}

	}

}
