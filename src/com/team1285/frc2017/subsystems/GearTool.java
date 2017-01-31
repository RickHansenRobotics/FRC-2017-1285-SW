package com.team1285.frc2017.subsystems;

import com.team1285.frc2017.RobotMap;
import com.team1285.frc2017.commands.Intake;
import com.team1285.frc2017.NumberConstants;
import com.team1285.frc2017.utilities.PIDController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * GearTool Class
 * 
 * This class controls the robots GearTool. Allows the the intake motor to be
 * run with buttons, and the GearTool arms with buttons and a joystick.
 * 
 * @author Jai Prajapati
 * @author Eric Ly
 * @since 2017-01-21
 */

public class GearTool extends Subsystem {

	CANTalon rightArm;
	CANTalon leftArm;

	CANTalon rightIntake;
	CANTalon leftIntake;

	DigitalInput optical;

	private boolean contains = false;

	private boolean gearState = false;

	DoubleSolenoid clamp;

	public PIDController armPID;

	public boolean encoderConnected = false;

	public GearTool() {
		rightArm = new CANTalon(RobotMap.RIGHT_ARM);
		rightArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightArm.configEncoderCodesPerRev(1024);
		rightArm.reverseSensor(false);

		FeedbackDeviceStatus status = rightArm.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative);

		rightArm.setProfile(0);
		rightArm.setPID(0.3, 0, 0);
		rightArm.changeControlMode(CANTalon.TalonControlMode.Position);

		switch (status) {
		case FeedbackStatusPresent:
			System.out.println("ENCODER FOUND");
			encoderConnected = true;
			break;
		case FeedbackStatusNotPresent:
			System.out.println("ENCODER NOT FOUND");
			break;
		case FeedbackStatusUnknown:
			System.out.println("ENCODER NOT FOUND");
			break;
		}

		resetEncoder();

		leftArm = new CANTalon(RobotMap.LEFT_ARM);
		rightIntake = new CANTalon(RobotMap.RIGHT_INTAKE);
		leftIntake = new CANTalon(RobotMap.LEFT_INTAKE);
		
		optical = new DigitalInput(RobotMap.GEARTOOL_OPTICS);
		
		clamp = new DoubleSolenoid(RobotMap.SOLENOID_A, RobotMap.SOLENOID_B);
		armPID = new PIDController(NumberConstants.pArm, NumberConstants.iArm, NumberConstants.dArm);
	}

	public double armEncoderGet() {
		return rightArm.getPosition() * 360 / 1.4;
	}

	public void resetEncoder() {
		rightArm.setPosition(0);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new Intake());
	}

	public void intake(double Val) {
		rightIntake.set(Val);
		leftIntake.set(-Val);
	}

	public void arm(double Val) {
		rightArm.set(Val);
		leftArm.set(-Val);
	}

	public void armPreset(double input) {
		rightArm.set(input);
		leftArm.set(-input);
	}

	public void openClamp() {
		clamp.set(DoubleSolenoid.Value.kForward);
		gearState = false;
	}

	public void closeClamp() {
		clamp.set(DoubleSolenoid.Value.kReverse);
		gearState = true;
	}
	
	public boolean getOptic() {
		return optical.get();
	}
	
	/**
	 * @return Retains the state of the holder
	 */
	public boolean getContains() {
		return contains;
	}

	/**
	 * @return the hold state
	 */
	public boolean getGearState() {
		return gearState;
	}


	public void rotateArm(double angle, double speed, double timeout) {
		double output = armPID.calcPID(angle, armEncoderGet(), 1);
		armPreset(output * speed);
	}

	public void setTarget(double angle) {
		angle = angle * 1.4 / 360;
		rightArm.set(angle);
	}

	public void disableArm() {
		rightArm.disableControl();
	}

	public double getSetpoint() {
		return rightArm.getSetpoint() * 360 / 1.4;
	}

	public boolean isArmPIDDone() {
		return armPID.isDone();
	}
}
