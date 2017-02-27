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
	 * <b>Do not call from anywhere except DriveTrainManager!</b><br>
	 * Seriously, it'll screw up the PID loop.
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void drive(double leftSpeed, double rightSpeed) {
		frontleft.set(leftSpeed);
		frontright.set(rightSpeed);
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
