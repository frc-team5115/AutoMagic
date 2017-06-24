package com.team5115.systems;

import com.team5115.Constants;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Drivetrain of the robot.<br>
 * Provides getters for encoder data and a drive method only to be used by DriveTrainManager.
 * @author Brian
 *
 */
public class DriveTrain {

	public boolean inuse;

	CANTalon frontleft;
	CANTalon frontright;
	CANTalon backleft;
	CANTalon backright;

	double kv = 0.1;

	public DriveTrain() {
		frontleft = new CANTalon(Constants.FRONT_LEFT_MOTOR_ID);
		frontright = new CANTalon(Constants.FRONT_RIGHT_MOTOR_ID);
		backleft = new CANTalon(Constants.BACK_LEFT_MOTOR_ID);
		backright = new CANTalon(Constants.BACK_RIGHT_MOTOR_ID);

		backleft.changeControlMode(CANTalon.TalonControlMode.Follower);
		backright.changeControlMode(CANTalon.TalonControlMode.Follower);
		backleft.set(frontleft.getDeviceID());
		backright.set(frontright.getDeviceID());

		frontright.setInverted(true);
	}

	/**
	 * Sets the values sent to the motor controllers on each side. Values should range [-1, 1].<br>
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void driveRaw(double leftPower, double rightPower) {
		frontleft.set(leftSpeed);
		frontright.set(rightSpeed);
	}

	public void drive(double forward, double turn) {
		double leftSpeed = forward + turn * Constants.ROBOT_RADIUS;
		double rightSpeed = forward - turn * Constants.ROBOT_RADIUS;

		driveRaw(leftSpeed * kv, rightSpeed * kv);
	}

	/**
	 * Speed of left wheels in ft/s.
	 * @return speed
	 */
	public double leftSpeed() {
		double rawSpeed = frontleft.getSpeed();
		return (rawSpeed * Constants.WHEEL_DIAMETER * Math.PI * 10) / (1440 * 12);
	}

	/**
	 * Speed of right wheels in ft/s.
	 * @return speed
	 */
	public double rightSpeed() {
		double rawSpeed = frontright.getSpeed();
		return (rawSpeed * Constants.WHEEL_DIAMETER * Math.PI * 10) / (1440 * 12);
	}

}
