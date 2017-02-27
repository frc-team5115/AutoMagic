package com.team5115.statemachines;

import com.team5115.PID;
import com.team5115.robot.Robot;

/**
 * Manages the speed of each side of the drivetrain with two PID loops.<br>
 * Classes wanting to move the drivetrain must go through here. Do not call Robot.drivetrain.drive() directly.
 * @author Brian
 *
 */
public class DriveTrainManager extends StateMachineBase {
	
	public static final int DRIVING = 1;
	
	double leftSpeed = 0;
	double rightSpeed = 0;
	double v_left;
	double v_right;
	
	PID pidLeft;
	PID pidRight;
	
	double kf = 0.1;
	double kp = 0.04;
	double ki = 1.5;
	double kd = 0;
	
	public DriveTrainManager() {
		pidLeft = new PID(kp, ki, kd);
		pidRight = new PID(kp, ki, kd);
	}
	
	/**
	 * Sets the left and right speed of the drivetrain, in ft/s.
	 * @param left
	 * @param right
	 */
	public void set(double left, double right) {
		leftSpeed = left;
		rightSpeed = right;
	}
	
	public void update() {
		switch(state) {
		case DRIVING:
			
			v_left = kf * leftSpeed + pidLeft.getPID(leftSpeed, Robot.drivetrain.leftSpeed());
			v_right = kf * rightSpeed + pidRight.getPID(rightSpeed, Robot.drivetrain.rightSpeed());
			
			Robot.drivetrain.drive(v_left, v_right);
			
			break;
			
		}
	}

}
