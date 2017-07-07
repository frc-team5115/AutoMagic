package com.team5115;

public class MotionProfile {

	double v_start;
	double v_max;
	double v_end;
	double accel;
	double t_up;
	double t_hold;
	double t_down;

	// Profile based on velocities and distance
	public MotionProfile(double vstart, double vmax, double vend, double a, double dist) {
		v_start = vstart;
		v_max = vmax;
		v_end = vend;
		accel = a;

		t_up = (v_max - v_start) / accel;
		t_down = (v_max - v_end) / accel;

		double dist_up = (v_start + v_max) * t_up / 2;
		double dist_down = (v_max + v_end) * t_down / 2;

		if (Math.abs(dist) > dist_up + dist_down) {
			t_hold = (Math.abs(dist) - (dist_up + dist_down)) / v_max;
		} else {
			v_max = Math.sqrt((2 * accel * Math.abs(dist) + Math.pow(v_start, 2) + Math.pow(v_end, 2)) / 2);

			if (v_max < v_start) {
				v_max = v_start;
				System.out.println("Distance is too short for given v_start, v_end, and accel. Using slower v_start.");
			}
			if (v_max < v_end) {
				v_max = v_end;
				System.out.println("Distance is too short for given v_start, v_end, and accel. Using slower v_end.");
			}

			t_up = (v_max - v_start) / accel;
			t_down = (v_max - v_end) / accel;
			t_hold = 0;
		}

		if (dist < 0) {
			v_start = -v_start;
			v_max = -v_max;
			v_end = -v_end;
			accel = -accel;
		}
	}

	// Profile based on time and distance (start and end velocity is 0, used for turning in arcs)
	public MotionProfile(double a, double dist, double t) {
		// calculate v_max with magic
		this(0, (t - Math.sqrt(Math.pow(t, 2) - 4 * dist / a)) * a / 2, 0, a, dist);
	}

	public double getVelocity(double t) {
		if (t < t_up) {
			return v_start + t * accel;
		} else if (t - t_up < t_hold) {
			return v_max;
		} else if (t - t_up - t_hold < t_down) {
			return v_end + v_max - (t - t_up - t_hold) * accel;
		}

		return 0;
	}

	public double totalTime() {
		return t_up + t_hold + t_down;
	}

}
