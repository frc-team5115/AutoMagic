package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.MotionProfile;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;

public class Pivot extends StateMachineBase {
	
	public static final int DRIVING = 1;
	
	MotionProfile mp;
	
	double t;
	double startTime;
	double finishTime;
	double leftSpeed;
	double rightSpeed;
	
	public Pivot(double v_start, double v_max, double v_end, double accel, double angle) {
		double dist_turn = Constants.ROBOT_RADIUS * Math.toRadians(angle);
		mp = new MotionProfile(v_start, v_max, v_end, accel, dist_turn);
	}
	
	public void setState(int s) {
		switch (s) {
		case DRIVING:
			
			startTime = Timer.getFPGATimestamp();
			
		}
		
		super.setState(s);
	}
	
	public void update() {
		switch (state) {
		case DRIVING:
			
			t = Timer.getFPGATimestamp() - startTime;
			Robot.drivetrain.drive(0, mp.getVelocity(t));
			
			if (isFinished())
				setState(STOP);
			
			break;
			
		}
	}
	
	public boolean isFinished() {
		return t >= mp.totalTime;
	}

}
