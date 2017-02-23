package com.team1285.frc2017.robot.commands;

import com.team1285.frc2017.robot.NumberConstants;
import com.team1285.frc2017.robot.Robot;
import com.team1285.frc2017.robot.commands.auto.DriveDistance;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Neil Balaskandarajah
 */
	

public class TankDrive extends Command {
	private DriveDistance drivePoint;
	
	public TankDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
		drivePoint = new DriveDistance(60, 1, 0, 5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
//		if (Robot.oi.getDriveAButton()){
//			drivePoint.start();	
//		}
			
		//}
		
		if (Robot.oi.getDriveLeftTrigger()) { //Shift into low gear
			//new ShiftLow(true); //shift command
			Robot.drive.shiftLow();
		} else if (Robot.oi.getDriveRightTrigger()) { //Shift into high gear
			//new ShiftHigh(true); //shift command
			Robot.drive.shiftHigh();
		} else if (Robot.oi.getDriveRightBumper()) { //slow drive
			Robot.drive.runLeftDrive(Robot.oi.getDriveLeftY()*NumberConstants.slowSpeedScale);
			Robot.drive.runRightDrive(-Robot.oi.getDriveRightY()*NumberConstants.slowSpeedScale);
		} else if(Robot.oi.getDriveLeftBumper()) { //lock drive
			Robot.drive.LockDrive();
		} else if(Robot.oi.getDriveXButton()){
			Robot.drive.resetEncoders();
		} else if(Robot.oi.getDriveYButton()){
			Robot.drive.CoastDrive();
		} else { //regular drive
			Robot.drive.runLeftDrive(Robot.oi.getDriveLeftY()*NumberConstants.speedScale);
			Robot.drive.runRightDrive(-Robot.oi.getDriveRightY()*NumberConstants.speedScale);
		}
}

	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.runLeftDrive(0);
		Robot.drive.runRightDrive(0);
	}
}