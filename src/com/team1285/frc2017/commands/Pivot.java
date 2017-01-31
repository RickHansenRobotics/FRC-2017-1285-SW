package com.team1285.frc2017.commands;

import com.team1285.frc2017.Robot;

/**
 * Pivot Command
 * 
 * This class controls the robots Pivot. Allows the pivot to be moved to certain angles
 * with parameters angle, speed and timeout with a button and joystick
 * 
 * @author Jai Prajapati 
 * @author Eric Ly
 * @since 2017-01-21
 */

import edu.wpi.first.wpilibj.command.Command;

public class Pivot extends Command {
	
	private double angle;
	private double speed;
	private double timeout;
	
    public Pivot(double angle, double speed, double timeout) {
        this.angle = angle;
        this.speed = speed;
        this.timeout = timeout; 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearTool.rotateArm(angle, speed, timeout);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.gearTool.isArmPIDDone() || isTimedOut();
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearTool.armPreset(0);
    	Robot.gearTool.armPID.resetPID();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gearTool.armPreset(0);
    	Robot.gearTool.armPID.resetPID();
    }
}

