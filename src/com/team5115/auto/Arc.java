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
		// Calculate forward distance and linear distance of the turn
		double distForward = radius * Math.toRadians(angle);
		double distTurn = Constants.ROBOT_RADIUS * Math.toRadians(angle);

		// Check if turn or forward motion is longer. Use longer one to set the time of the other
		if (distForward > distTurn) {
			mpForward = new MotionProfile(v_start, v_max, v_end, accel, distForward);
			mpTurn = new MotionProfile(accel, distTurn, mpForward.totalTime());
		} else {
			mpTurn = new MotionProfile(0, v_max, 0, accel, distTurn);
			mpForward = new MotionProfile(accel, distForward, mpTurn.totalTime());
		}
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
			Robot.drivetrain.drive(mpForward.getVelocity(t), mpTurn.getVelocity(t));

			if (t == finishTime)
				setState(0);

			break;

		}
	}

	public boolean isFinished() {
		return t >= mpForward.totalTime();
	}

}
