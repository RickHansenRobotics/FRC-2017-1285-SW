package org.usfirst.frc.team1285.robot.commands.auto;

import org.usfirst.frc.team1285.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class DriveDistance extends Command {
	private double distance;
	private double speed;
	private double angle;
	private double timeOut;
	//private double tolerance;

	public DriveDistance(double setPoint, double speed, double angle, double timeOut) {
		this(setPoint, speed, angle, timeOut, 1);
	}

    public DriveDistance(double setPoint, double speed, double angle, double timeOut, double tolerance) {
    	this.distance = setPoint;
    	this.speed = speed;
    	this.angle = angle;
    	this.timeOut = timeOut;
    	//this.tolerance = tolerance;
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetEncoders();
    	setTimeout(timeOut);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.driveSetpointViper(distance, speed, angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.runLeftDrive(0);
    	Robot.drive.runRightDrive(0);
    	Robot.drive.resetPID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.runLeftDrive(0);
		Robot.drive.runRightDrive(0);
		Robot.drive.resetPID();
    //	Robot.geartool.intake(1);
    	
    }
}