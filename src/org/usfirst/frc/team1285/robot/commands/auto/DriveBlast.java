package com.team1285.frc2017.commands.auto;

import com.team1285.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBlast extends Command {

   public double d;
   public double t;

	public DriveBlast(double d, double t) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.d = d;
    	this.t = t;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetEncoders();
    	setTimeout(t);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (d < Robot.drive.getAverageDistance()) {
    	Robot.drive.runLeftDrive(-1);
    	Robot.drive.runRightDrive(1);
    	}
//    	if (d > Robot.drive.getAverageDistance()) {
//    	Robot.drive.LockDrive();
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.LockDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.LockDrive();
    	//Robot.geartool.intake(1);
    }
}
