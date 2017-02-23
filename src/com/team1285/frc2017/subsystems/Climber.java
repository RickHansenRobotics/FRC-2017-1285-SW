package com.team1285.frc2017.subsystems;

import com.ctre.CANTalon;
import com.team1285.frc2017.RobotMap;
import com.team1285.frc2017.commands.ClimbCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// make object for talons for the winch
	CANTalon rightWinch;
	CANTalon leftWinch;
	{

		rightWinch = new CANTalon(RobotMap.LEFT_WINCH);
		leftWinch = new CANTalon(RobotMap.RIGHT_WINCH);
	}

	/**
	 * 
	 * @param pwmVal
	 *            The power that you want to send to the motor(s) -1.0 to 1.0
	 */
	public void WinchIn() {
		leftWinch.set(1);
		rightWinch.set(-1);
	}

	public void WinchOut() {
		leftWinch.set(0.3);
		rightWinch.set(-0.3);
	}

	public void StopWinch() {
		leftWinch.set(0.0);
		rightWinch.set(0.0);
	}

	public double getLeftWinchCurrent() {
		return leftWinch.getOutputCurrent();
	}
	
	public double getRightWinchCurrent() {
		return rightWinch.getOutputCurrent();
	}
	
	@Override
	protected void initDefaultCommand() {
	setDefaultCommand(new ClimbCommand());
	}
}
