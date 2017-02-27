package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.MotionProfile;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;

public class Arc extends StateMachineBase {
	
	public static final int DRIVING = 1;
	
	MotionProfile mp;
	
	double rightLeftRatio;
	
	double t;
	double startTime;
	double finishTime;
	double leftSpeed;
	double rightSpeed;
	
	public Arc(double v_start, double v_max, double v_end, double accel, double angle, double radius) {
		double leftDist = (radius + Constants.ROBOT_RADIUS) * Math.toRadians(angle);
		double rightDist = (radius - Constants.ROBOT_RADIUS) * Math.toRadians(angle);
		
		rightLeftRatio = rightDist / leftDist;
		
		mp = new MotionProfile(v_start, v_max, v_end, accel, leftDist);
	}
	
	public void setState(int s) {
		switch (state) {
		case DRIVING:
			
			startTime = Timer.getFPGATimestamp();
			finishTime = mp.totalTime();
			
		}
		
		super.setState(s);
	}
	
	public void update() {
		switch (state) {
		case DRIVING:
			
			t = Timer.getFPGATimestamp() - startTime;
			leftSpeed = mp.getVelocity(t);
			rightSpeed = leftSpeed * rightLeftRatio;
			Robot.drivetrain.drive(leftSpeed, rightSpeed);
			
			if (t == finishTime)
				setState(0);
			
			break;
			
		}
	}
	
	public boolean isFinished() {
		return t >= finishTime;
	}

}
