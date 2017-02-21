package org.usfirst.frc.team190.frc2k17;

public class DSPFilter { 
	// This class is designed to do first order filtering
	
	public enum FilterType {
		LOW_PASS,
		HIGH_PASS
	}
	
	private FilterType type;
	private double lastOutput;
	private double lastSample;
	private double alpha;
	
	public DSPFilter(FilterType filter,double cutoff, double initValue, double sampleFrequency) {
		type = filter;
		double samplePeriod = 1/sampleFrequency;
		lastOutput = initValue;
		lastSample = initValue;
		if(filter == FilterType.LOW_PASS)
		{
			alpha = (2*Math.PI*samplePeriod*cutoff)/((2*Math.PI*samplePeriod*cutoff)+1);
		}
		else
		{
			//high pass
			alpha = 1/((2*Math.PI*samplePeriod*cutoff)+1);
		}
	}
	
	public double processNextPoint(double newSample) {
		double newOutput = 0;
		if(type == FilterType.LOW_PASS)
		{
			newOutput = (alpha*newSample) + ((1-alpha)*lastOutput);
		}
		else
		{
			//high pass
			newOutput = (alpha*lastOutput) + (alpha*(newSample-lastSample));
		}
		lastSample = newSample;
		lastOutput = newOutput;
		return newOutput;
	}

}
