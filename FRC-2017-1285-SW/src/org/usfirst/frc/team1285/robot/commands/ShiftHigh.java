package org.usfirst.frc.team1285.robot.commands;

import org.usfirst.frc.team1285.robot.Robot;
import org.usfirst.frc.team1285.utilities.WaitCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author Neil Balaskandarajah
 */

public class ShiftHigh extends Command {

	private boolean state; 
	private WaitCommand shiftWait;

    public ShiftHigh(boolean state) {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	
    	shiftWait = new WaitCommand(0.1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(state){
    	Robot.drive.CoastDrive();
    	shiftWait.start();
		Robot.drive.shiftHigh();// shift into low gear
		shiftWait.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true; // only needs to loop once, returns true
    }

    // Called once after isFinished returns true
    protected void end() {
    	//do nothing when returning true to return to TankDrive
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
