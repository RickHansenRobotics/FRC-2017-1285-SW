package com.team1285.frc2017.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.team1285.frc2017.robot.NumberConstants;
import com.team1285.frc2017.robot.RobotMap;
import com.team1285.frc2017.robot.commands.TankDrive;
import com.team1285.frc2017.utilities.Nav6;
import com.team1285.frc2017.utilities.PIDController;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	public PIDController drivePID;
	public PIDController drivePIDR;
	public PIDController gyroPID;

	private SerialPort serial_port;
	private Nav6 nav6;

	private boolean DriveGearState;

	public double[] driveCurrentDraw = new double[4];

	public Drivetrain() {
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kOnboard);

			// You can add a second parameter to modify the
			// update rate (in hz) from 4 to 100. The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.

			// You can also use the IMUAdvanced class for advanced
			// features.

			byte update_rate_hz = 50;
			nav6 = new Nav6(serial_port, update_rate_hz);
			SmartDashboard.putString("IN TRYs", "OH GAWD IN TRY");
			if (!nav6.isCalibrating()) {
				Timer.delay(0.3);
				nav6.zeroYaw();
				SmartDashboard.putString("IN TRYs", "ZEROED");
			}
		} catch (Exception e) {
			SmartDashboard.putString("IN TRYs", "Shits still not working");
			nav6 = null;
		}
		// left front
		leftDriveFront = new CANTalon(RobotMap.LEFT_DRIVE_FRONT);
//		leftDriveFrontSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
//		leftDriveFrontSlave.set(leftDriveBack.getDeviceID());
//		leftDriveFrontSlave.reverseOutput(false);
		
		// left back
		leftDriveBack = new CANTalon(RobotMap.LEFT_DRIVE_BACK);
		leftDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftDriveBack.reverseSensor(true);
		leftDriveBack.configEncoderCodesPerRev(1024);
		
		// right front
		rightDriveFront = new CANTalon(RobotMap.RIGHT_DRIVE_FRONT);
//		rightDriveFrontSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
//		rightDriveFrontSlave.set(rightDriveBack.getDeviceID());
//		rightDriveFrontSlave.reverseOutput(true);
		
		// right back
		rightDriveBack = new CANTalon(RobotMap.RIGHT_DRIVE_BACK);
		rightDriveBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightDriveBack.reverseSensor(false);
		rightDriveBack.configEncoderCodesPerRev(1024);

		drivePID = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);
		drivePIDR = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);

		gyroPID = new PIDController(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);

		DriveGearboxShifter = new DoubleSolenoid(RobotMap.DRIVE_SOLENOID_A, RobotMap.DRIVE_SOLENOID_B);

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
	public void driveTurn(double setPointR,double setPointL, double speed, double setAngle) {
		double output = drivePID.calcPID(setPointL, getLeftEncoderDist(), 0.01);
		double outputR = drivePIDR.calcPID(setPointR, getRightEncoderDist(), 0.01);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 0.01);

		runLeftDrive ((-output + angle) * speed);
		runRightDrive ((outputR + angle) * speed);
	}
	
	public void driveSetpoint(double setPoint, double speed, double setAngle) {
		double output = drivePID.calcPID(setPoint, getAverageDistance(), 0.01);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 0.01);

		runLeftDrive ((-output + angle) * speed);
		runRightDrive ((output + angle) * speed);
	}
	
	public void driveSetpointViper(double setPoint, double speed, double setAngle) {
		double output = drivePID.calcPID(setPoint, getLeftEncoderDist(), 0.01);
		double outputR = drivePIDR.calcPID(setPoint, getRightEncoderDist(), 0.01);
		double angle = gyroPID.calcPID(setAngle, getYaw(), 0.01);

		runLeftDrive ((-output + angle) * speed);
		runRightDrive ((outputR + angle) * speed);
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
	public boolean gyroConnected() {
		return nav6.isConnected();
	}

	/**
	 * This function is used to check if the gyro is calibrating
	 * 
	 * @return Returns true or false depending on the state of the gyro
	 */
	public boolean gyroCalibrating() {
		return nav6.isCalibrating();
	}

	/**
	 * This function returns the YAW reading from the gyro
	 * 
	 * @return Returns YAW
	 */
	public double getYaw() {
		return nav6.getYaw();
	}

	/**
	 * This function returns the PITCH reading from the gyro
	 * 
	 * @return Returns PITCH
	 */
	public double getPitch() {
		return nav6.getPitch();
	}

	/**
	 * This function returns the ROLL reading from the gyro
	 * 
	 * @return Returns ROLL
	 */
	public double getRoll() {
		return nav6.getRoll();
	}

	public void resetGyro() {
		nav6.zeroYaw();
	}
	
	public void resetPID(){
		drivePID.resetPID();
		gyroPID.resetPID();
	}
}