package com.team1285.frc2017.subsystems;

import com.team1285.frc2017.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.can.CANJNI;
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

		rightWinch = new CANTalon(RobotMap.Winch_In);
		leftWinch = new CANTalon(RobotMap.Winch_Out);
	}

	/**
	 * 
	 * @param pwmVal
	 *            The power that you want to send to the motor(s) -1.0 to 1.0
	 */
	public void WinchIn() {
		leftWinch.set(1.0);
		rightWinch.set(-1.0);
	}

	public void WinchOut() {
		leftWinch.set(-1.0);
		rightWinch.set(1.0);
	}

	public void StopWinch() {
		leftWinch.set(0.0);
		rightWinch.set(0.0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
