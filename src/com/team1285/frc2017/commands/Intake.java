package com.team1285.frc2017.commands;

import com.team1285.frc2017.NumberConstants;
import com.team1285.frc2017.Robot;

/**
 * Intake Command
 * 
 * This class controls the robots Intake. Allows the intake, pivot and gearclamp to 
 * be controlled with toolop joystick and button input
 * 
 * @author Jai Prajapati 
 * @author Eric Ly
 * @since 2017-01-21
 */

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {

	private Pivot scoreSetpoint;
	private Pivot restSetpoint;
	
	public Intake() {
		requires(Robot.geartool);
		scoreSetpoint = new Pivot(-0.45, 0.75, 5);
		restSetpoint = new Pivot(-0.067, 0.75, 5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		//Rollers
		if (Robot.oi.getToolRightBumper()) {
			Robot.geartool.intake(1);
		} else if (Robot.oi.getToolLeftBumper()) {
			Robot.geartool.intake(-1);
		} else {
			Robot.geartool.intake(0);
		}
		
		//Clamp
		if (Robot.oi.getToolXButton()) {
			Robot.geartool.openClamp();
		} else if (Robot.oi.getToolYButton()) {
			Robot.geartool.closeClamp();
		}
		//Arm
		if(Math.abs(Robot.oi.getToolRightY()) > 0.2){
			scoreSetpoint.cancel();
			restSetpoint.cancel();
			Robot.geartool.arm(-Robot.oi.getToolRightY()*0.6);
		}
		
		//Setpoints
		
		if (Robot.oi.getToolLeftY() < -0.9) {
			//Robot.geartool.rotateArm(-0.4, 0.1, 5);
			scoreSetpoint.cancel();
			restSetpoint.start();
		} else if (Robot.oi.getToolLeftY() > 0.9) {
			//Robot.geartool.rotateArm(0, 0.1, 5);
			restSetpoint.cancel();
			scoreSetpoint.start();
			
		}
		
		//Encoder
		if (Robot.oi.getToolStartButton()){
			Robot.geartool.resetEncoder();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}