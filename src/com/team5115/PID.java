package com.team5115;

public class PID {

	double error;
	double last_error = 0;
	double error_accum = 0;
	double derror;
	double output;

	double kp, ki, kd;

	double maxOutput;

	public PID(double p, double i, double d, double max) {
		kp = p;
		ki = i;
		kd = d;
		maxOutput = max;
	}

	public PID(double p, double i, double d) {
		this(p, i, d, 1);
	}

	public double getPID(double setpoint, double actual) {
		error = setpoint - actual;

		// calculate derivative
		if (last_error != 0)
			derror = (error - last_error) / Constants.DELAY;
		else
			derror = 0;

		last_error = error;

		// calculate output
		output = kp * error + ki * error_accum + kd * derror;

		// make sure output does not exceed max, and don't integrate if it does
		// not integrating helps prevent overshooting
		if (output > maxOutput) {
			output = maxOutput;
		} else if (output < -maxOutput) {
			output = -maxOutput;
		} else {
			error_accum += error * Constants.DELAY;
		}

		return output;
	}

}
