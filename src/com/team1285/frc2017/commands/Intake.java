package com.team1285.frc2017.commands;

import com.team1285.frc2017.Robot;
import com.team1285.frc2017.utilities.ToggleBoolean;

import edu.wpi.first.wpilibj.Timer;

/**
 * Intake Command
 * 
 * This class controls the robots Intake. Allows the intake, pivot and gearclamp to 
 * be controlled with toolop, joystick, autooptic and button input
 * 
 * @author Jai Prajapati 
 * @author Eric Ly
 * @since 2017-01-21
 */

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {
	
	ToggleBoolean toggle;
	Timer timer;
	private boolean auto = true;	
	boolean started = false;
	
	public Intake() {
		toggle = new ToggleBoolean();
		timer = new Timer();
		requires(Robot.geartool);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {	
		
		if (!Robot.geartool.getOptic() && started == false) {
			timer.start();
			started = true;
		}	
		if (!Robot.geartool.getOptic() && auto && !Robot.geartool.getContains() && timer.get() > 0.5) {
			timer.reset();
			timer.stop();
			Robot.geartool.setContains(true);
			new GearClamp().start();
		}
		if (Robot.geartool.getContains()) {
			started = false;
		}
		
		if (Robot.geartool.getOptic() && started == true) {
			timer.stop();
			started = false;
		}	
			if (Robot.oi.getToolYButton()) {
				toggle.set(true);
				Robot.geartool.openClamp();
				auto = false;
			} else {
				auto = true;
			}
			
			if (Robot.oi.getToolXButton()) {
				toggle.set(true);
				Robot.geartool.closeClamp();
				auto = false;
			} else {
				auto = true;
			}		
		Robot.geartool.arm(Robot.oi.getToolRightY());
		
		if (Robot.oi.getToolLeftY() > -0.9) {
			Robot.geartool.rotateArm(100, 1, 5);
		} 
		
		else if (Robot.oi.getToolLeftY() < 0.9) {
			Robot.geartool.rotateArm(0, 1, 5);
		}
		if (Robot.oi.getToolRightBumper()) {
			Robot.geartool.intake(0.5);
		}

		else if (Robot.oi.getToolLeftBumper()) {
			Robot.geartool.intake(-0.5);
		}

		else {
			Robot.geartool.intake(0);}
		}
		/*if (Robot.oi.getToolXButton()) {
			Robot.geartool.openClamp();
		}

		else if (Robot.oi.getToolYButton()) {
			Robot.geartool.closeClamp();
		}*/


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
