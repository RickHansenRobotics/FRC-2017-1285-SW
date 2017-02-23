package org.usfirst.frc.team1285.robot.subsystems;

import org.usfirst.frc.team1285.robot.NumberConstants;
import org.usfirst.frc.team1285.robot.RobotMap;
import org.usfirst.frc.team1285.robot.commands.Intake;
import org.usfirst.frc.team1285.utilities.PIDController;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
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

	//Initializes all talons, sensors and controllers
	
	CANTalon rightPivot;
	CANTalon leftPivotSlave;
	
	Encoder intake;

	CANTalon rightIntake;
	CANTalon leftIntake;

	DigitalInput optical;

	private boolean contains = false;

	private boolean gearState = false;

	public int armPulseWidthPos;

	DoubleSolenoid clamp;

	public PIDController armPID;

	public boolean encoderConnected = false;

	public GearTool() {
		rightPivot = new CANTalon(RobotMap.RIGHT_ARM);
		rightPivot.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//rightPivot.configEncoderCodesPerRev(RobotMap.ticksPerRev);
		rightPivot.reverseSensor(false);

		FeedbackDeviceStatus status = rightPivot.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative);

		
		  rightPivot.setProfile(0); rightPivot.setPID(0.3, 0, 0);
		 
		rightPivot.changeControlMode(CANTalon.TalonControlMode.Position);
		

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

		// resetEncoder();

		leftPivotSlave = new CANTalon(RobotMap.LEFT_ARM);
//		leftPivotSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
//		leftPivotSlave.set(rightPivot.getDeviceID());
//		leftPivotSlave.reverseOutput(false);

		//rightPivot = new CANTalon(RobotMap.RIGHT_ARM);

		rightIntake = new CANTalon(RobotMap.RIGHT_INTAKE);
		leftIntake = new CANTalon(RobotMap.LEFT_INTAKE);

		optical = new DigitalInput(RobotMap.GEARTOOL_OPTICS);

		clamp = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_A, RobotMap.INTAKE_SOLENOID_B);

		armPID = new PIDController(NumberConstants.pArm, NumberConstants.iArm, NumberConstants.dArm);
		
		intake = new Encoder 		(RobotMap.ARM_PIVOT_ENCODER_A,
									RobotMap.ARM_PIVOT_ENCODER_B, RobotMap.ArmEncoderReverse,
									Encoder.EncodingType.k4X);
		
		//resetEncoder();
	}
	
	public void resetArmEncoder(){
		intake.reset();
	}
	public double getArmEncoder(){
		return intake.getDistance();
	}
	//method used to reset encoders to 0 (initial position)
	public void resetEncoder() {
		rightPivot.setPosition(0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new Intake());
	}
	//method for setting intake motors to set val
	public void intake(double Val) {
		rightIntake.set(Val);
		leftIntake.set(-Val);
	}
	//method for setting geartool arm to set val
	public void arm(double Val) {
		rightPivot.set(Val);
		leftPivotSlave.set(-Val);
	}
	//metod used to get current position of the geartool
	public double armEncoderGet() {
		return rightPivot.getPosition() * 360/1.4;
	}
	//method used to set geartool to position input from pid Controller 
	public void armPreset(double input) {
		rightPivot.set(input);
		leftPivotSlave.set(-input);
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
		double output = armPID.calcPID(angle, getArmEncoder(), 0.05);
		armPreset(output * speed);
	}

	public void setTarget(double angle) {
		angle = angle * 1.4 / 360;
		rightPivot.set(angle);
	}

	public void disableArm() {
		rightPivot.disableControl();
	}

	public double getSetpoint() {
		return rightPivot.getSetpoint() * 360 / 1.4;//degrees divided by external ratio
	}

	public boolean isArmPIDDone() {
		return armPID.isDone();
	}

	// CURRENT
	public double getLeftPivotCurrent() {
		return leftPivotSlave.getOutputCurrent();
	}
	
	public double getRightPivotCurrent() {
		return rightPivot.getOutputCurrent();
	}
	// SMART DASHBOARD

	public double armPosGet() {
		return rightPivot.getPosition();
	}
}