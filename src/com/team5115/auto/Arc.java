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

	public Arc(double v_start, double v_max, double v_max_angular, double v_end, double accel, double angle, double radius) {
		double dist_forward = radius * Math.toRadians(angle);

		// Calculate the turn values like normal
		double dist_turn = Constants.ROBOT_RADIUS * Math.toRadians(angle);
		double v_max_turn = Constants.ROBOT_RADIUS * v_max_angular;

		mpForward = new MotionProfile(v_start, v_max, v_end, accel, dist_forward);
		mpTurn = new MotionProfile(0, v_max_turn, 0, accel, dist_turn);
	}

	public void setState(int s) {
		switch (s) {
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
			Robot.drivetrain.drive(mpForward.getVelocity(t), mpTurn.getVelocity(t));

			if (t == finishTime)
				setState(0);

			break;

		}
	}

	public boolean isFinished() {
		return t >= finishTime;
	}

}
