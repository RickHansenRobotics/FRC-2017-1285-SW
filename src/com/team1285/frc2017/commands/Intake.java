package com.team1285.frc2017.commands;

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

	public Intake() {
		requires(Robot.geartool);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	
		Robot.geartool.arm(Robot.oi.getToolRightY());

		if (Robot.oi.getToolRightBumper()) {
			Robot.geartool.intake(0.5);
		}

		else if (Robot.oi.getToolLeftBumper()) {
			Robot.geartool.intake(-0.5);
		}

		else {
			Robot.geartool.intake(0);
		}

		if (Robot.oi.getToolXButton()) {
			Robot.geartool.openClamp();
		}

		else if (Robot.oi.getToolYButton()) {
			Robot.geartool.closeClamp();
		}

		if (Robot.oi.getToolLeftY() > -0.9) {
			Robot.geartool.rotateArm(60, 0.5, 5);
		} else if (Robot.oi.getToolLeftY() < 0.9) {
			Robot.geartool.rotateArm(180, 0.5, 5);
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
