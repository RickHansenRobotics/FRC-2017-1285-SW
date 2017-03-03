package org.usfirst.frc.team1285.robot.subsystems;

import org.usfirst.frc.team1285.robot.NumberConstants;
import org.usfirst.frc.team1285.robot.RobotMap;
import org.usfirst.frc.team1285.robot.commands.TankDrive;
import org.usfirst.frc.team1285.robot.utilities.PIDController;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Saleen Shahriar
 * @author Neil Balaskandarajah
 */
public class Drivetrain extends Subsystem {

	CANTalon leftDriveFront;
	CANTalon leftDriveBack;

	CANTalon rightDriveFront;
	CANTalon rightDriveBack;

	DoubleSolenoid DriveGearboxShifter;
	
	ADXRS450_Gyro gyro;

	public PIDController drivePID;
	public PIDController drivePIDR;
	public PIDController gyroPID;

	private boolean DriveGearState;

	public double[] driveCurrentDraw = new double[4];

	public Drivetrain() {
		
		// left front
		leftDriveFront = new CANTalon(RobotMap.LEFT_DRIVE_FRONT);

		// left back
		leftDriveBack = new CANTalon(RobotMap.LEFT_DRIVE_BACK);
		leftDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftDriveBack.reverseSensor(true);
		leftDriveBack.configEncoderCodesPerRev(1024);
		
		// right front
		rightDriveFront = new CANTalon(RobotMap.RIGHT_DRIVE_FRONT);

		// right back
		rightDriveBack = new CANTalon(RobotMap.RIGHT_DRIVE_BACK);
		rightDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightDriveBack.reverseSensor(false);
		rightDriveBack.configEncoderCodesPerRev(1024);

		drivePID = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);
		drivePIDR = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);

		gyroPID = new PIDController(NumberConstants.pGyro, NumberConstants.iGyro, NumberConstants.dGyro);

		DriveGearboxShifter = new DoubleSolenoid(RobotMap.DRIVE_SOLENOID_A, RobotMap.DRIVE_SOLENOID_B);
		
		gyro = new ADXRS450_Gyro();
		@SuppressWarnings("unused")
		double driveCurrentDraw[] = new double[] { leftDriveBack.getOutputCurrent(),
				//leftDriveFront.getOutputCurrent(), rightDriveBack.getOutputCurrent(),
				rightDriveFront.getOutputCurrent() };
	}

	public void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
	}

	public void LockDrive() { // completely lock drive while scoring gear
		// set all Talons to brake
		leftDriveFront.enableBrakeMode(true);
		leftDriveBack.enableBrakeMode(true);
		rightDriveFront.enableBrakeMode(true);
		rightDriveBack.enableBrakeMode(true);
		// cut power
		leftDriveFront.set(0);
		leftDriveBack.set(0);
		 rightDriveFront.set(0);
		rightDriveBack.set(0);
	}

	public void CoastDrive() {
		leftDriveFront.enableBrakeMode(false);
		leftDriveBack.enableBrakeMode(false);
		rightDriveFront.enableBrakeMode(false);
		rightDriveBack.enableBrakeMode(false);
		
		leftDriveFront.set(0);
		leftDriveBack.set(0);
		 rightDriveFront.set(0);
		rightDriveBack.set(0);
	}
	
	public void runLeftDrive(double pwmVal) {
		leftDriveBack.set(pwmVal);
		leftDriveFront.set(pwmVal);
	}

	public void runRightDrive(double pwmVal) {
		rightDriveBack.set(pwmVal);
		rightDriveFront.set(pwmVal);
	}
	public void driveTurn(double setAngle, double speed) {
		double angle = gyroPID.calcPID(setAngle, getYaw(), 1);

		runLeftDrive ((-angle) * speed);
		runRightDrive ((-angle) * speed);
	}
	
	public void driveSetpoint(double setPoint, double speed, double setAngle) {
		double output = drivePID.calcPID(setPoint, getAverageDistance(), 1);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 1);

		runLeftDrive ((-output - angle) * speed);
		runRightDrive ((output - angle) * speed);
	}
	
	public void driveSetpointViper(double setPoint, double speed, double setAngle) {
		double output = drivePID.calcPID(setPoint, getLeftEncoderDist(), 0.01);
		double outputR = drivePIDR.calcPID(setPoint, getRightEncoderDist(), 0.01);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 0.01);

		runLeftDrive ((-output - angle) * speed);
		runRightDrive ((outputR - angle) * speed);
	}
	
	public void driveBlast() {
		runLeftDrive(1);
		runRightDrive(-1);
	}
	public boolean drivePIDDone() {
		return drivePID.isDone();
	}

	public void reset() {
		resetEncoders();
		resetGyro();
	}

	// SHIFT METHODS
	public void shiftLow() {
		DriveGearState = false;
		DriveGearboxShifter.set(DoubleSolenoid.Value.kForward);
	}

	public void shiftHigh() {
		DriveGearState = true;
		DriveGearboxShifter.set(DoubleSolenoid.Value.kReverse);
	}

	public boolean getDriveGearState() {
		return DriveGearState;
	}

	// ************************Encoder Functions************************
	public double getLeftEncoderDist() {
		return leftDriveBack.getPosition()*12.44;// * RobotMap.ROTATIONS_TO_INCHES;
	}

	public double getRightEncoderDist() {
		return rightDriveBack.getPosition()*12.44;// * RobotMap.ROTATIONS_TO_INCHES;
	}

	public double getAverageDistance() {
		return (getLeftEncoderDist() + getRightEncoderDist()) / 2;
	}

	public void resetEncoders() {
		leftDriveBack.setPosition(0);
		rightDriveBack.setPosition(0);
	}

	/************************ GYRO FUNCTIONS ************************/

	/**
	 * This function is used to check if the gyro is connected
	 * 
	 * @return Returns true or false depending on the state of the gyro
	 */
//	public boolean gyroConnected() {
//		return nav6.isConnected();
//	}

	/**
	 * This function is used to check if the gyro is calibrating
	 * 
	 * @return Returns true or false depending on the state of the gyro
	 */
//	public boolean gyroCalibrating() {
//		return nav6.isCalibrating();
//	}

	/**
	 * This function returns the YAW reading from the gyro
	 * 
	 * @return Returns YAW
	 */
	public double getYaw() {
		return gyro.getAngle();
	}

	public void resetGyro() {
		gyro.reset();
	}
	
	public void resetPID(){
		drivePID.resetPID();
		gyroPID.resetPID();
	}
}