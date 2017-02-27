
package com.team5115.robot;

import com.team5115.Constants;
import com.team5115.statemachines.DriveTrainManager;
import com.team5115.systems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	public static DriveTrain drivetrain;
	
	public static DriveTrainManager dtm;

	public void robotInit() {
		drivetrain = new DriveTrain();
		
		dtm = new DriveTrainManager();
	}

	public void autonomousInit() {
		dtm.setState(DriveTrainManager.DRIVING);
	}

	public void autonomousPeriodic() {
		dtm.update();
		
		Timer.delay(Constants.DELAY);
	}

	public void teleopInit() {
		dtm.setState(DriveTrainManager.DRIVING);
	}

	public void teleopPeriodic() {
		dtm.update();
		
		Timer.delay(Constants.DELAY);
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		Timer.delay(Constants.DELAY);
	}

}
