package com.team5115;

public class PID {
	
	double error;
	double last_error = 0;
	double error_accum = 0;
	double derror;
	double output;
	
	double kp, ki, kd;
	
	double active_range = 0;
	
	public PID(double p, double i, double d) {
		kp = p;
		ki = i;
		kd = d;
	}
	
	public void setActiveRange(double r) {
		active_range = r;
	}
	
	public double getPID(double setpoint, double actual) {
		output = 0;
		
		error = setpoint - actual;
		
		if (Math.abs(error) < active_range || active_range == 0) {
			error_accum += error * Constants.DELAY;
			
			if (last_error != 0)
				derror = (error - last_error) / Constants.DELAY;
			else
				derror = 0;
			
			last_error = error;
			
			output = kp * error + ki * error_accum + kd * derror;
		}
		
		return output;
	}

}
