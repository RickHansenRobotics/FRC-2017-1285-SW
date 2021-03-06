package org.usfirst.frc.team1285.robot.commands;

import org.usfirst.frc.team1285.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * GearClamp Command
 * 
 * This class controls the robots GearClamp. Allows the gear clamp to be actuated or
 * closed based on boolean statements
 * 
 * @author Jai Prajapati 
 * @author Eric Ly
 * @since 2017-01-21
 */

public class GearClamp extends Command {

	//private boolean clamp;

	//public GearClamp(boolean clamp) {
		//clamp = Robot.geartool.getOptic();
	//}
	/*
	public GearState(boolean gearState){
		gearState = Robot.geartool.getGearState();
	}
	*/
	/*public GearClamp() {
		this(!Robot.geartool.getGearState());
	}*/

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		/*if (true)  {
			Robot.geartool.closeClamp();
		} else {
			Robot.geartool.openClamp();
		}*/
		Robot.geartool.openClamp();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

