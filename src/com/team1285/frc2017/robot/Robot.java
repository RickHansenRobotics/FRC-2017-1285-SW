
package com.team1285.frc2017.robot;

import com.team1285.frc2017.robot.commands.auto.BaseLineCross;
import com.team1285.frc2017.robot.commands.auto.DriveBlast;
import com.team1285.frc2017.robot.commands.auto.DriveTurn;
import com.team1285.frc2017.robot.commands.auto.GearCenter;
import com.team1285.frc2017.robot.commands.auto.GearCenterBaseline;
import com.team1285.frc2017.robot.commands.auto.NoAuto;
import com.team1285.frc2017.robot.subsystems.Climber;
import com.team1285.frc2017.robot.subsystems.Drivetrain;
import com.team1285.frc2017.robot.subsystems.GearTool;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static PowerDistributionPanel pdp;
	
	Preferences prefs;
	private Command autonomousCommand;
	public static Drivetrain drive;// = new Drivetrain();
	public static GearTool geartool = new GearTool();
	public static Climber climber = new Climber();
	public static OI oi;

	public SendableChooser autoChooser;
	private int autoNumber;
	
	/*CameraServer server;
	
	 {server = CameraServer.getInstance();
		 server.setQuality(25);
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		
		server.startAutomaticCapture("cam0", 0);
	    }*/

	// Command autonomousCommand;
	// private int autoNumber;
	// private Object autoNumber;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();
		drive = new Drivetrain();

		prefs = Preferences.getInstance();

		autoChooser = new SendableChooser<Command>();

		autoChooser.addDefault("No Auton", new NoAuto());
		autoChooser.addObject("CROSS BASE", new BaseLineCross());
		autoChooser.addObject("Blast", new DriveBlast(50, 10));
		autoChooser.addObject("GearCenter",new GearCenter());
		autoChooser.addObject("GearCenterBaseline", new GearCenterBaseline());
		autoChooser.addObject("Turn Drive", new DriveTurn(20,-20,0.8,0,5));

		SmartDashboard.putData("AUTONOMOUs", autoChooser);
		
		CameraServer.getInstance().startAutomaticCapture();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();

		/*
		 * switch(autoNumber) { case 0: autonomousCommand = (Command) new
		 * NoAuto(); break; case 1: autonomousCommand = (Command) new
		 * BaseLineCross(); break; case 2: autonomousCommand = (Command) new
		 * DriveBlast(100, 5); break; }
		 */


	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		autonomousCommand = (Command) autoChooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
	}

	public void teleopInit() {
		// Robot.drive.shiftLow();
		if (autonomousCommand != null)
			((Command) autonomousCommand).cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();

		// PREFERENCES
		NumberConstants.speedScale = prefs.getDouble("DRIVE SPEED SCALE", 1); // default
																				// scalar
																				// for
																				// drive
																				// speed
		NumberConstants.slowSpeedScale = prefs.getDouble("DRIVE SLOW SPEED SCALE", 0.5);
		NumberConstants.armScale = prefs.getDouble("ARM SPEED SCALE", 1); // default
																			// scalar
																			// for
																			// arm
																			// speed
		NumberConstants.climbScale = prefs.getDouble("CLIMB SPEED SCALE", 1.0);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void updateSmartDashboard() {
		//gyro pitch value 
		SmartDashboard.putNumber("GYRO YAW",drive.getYaw());
		SmartDashboard.putNumber("GYRO ROLL",drive.getRoll());
		// WINCH CURRENT DRAW
		SmartDashboard.putNumber("LEFT WINCH CURRENT DRAW", climber.getLeftWinchCurrent());
		SmartDashboard.putNumber("RIGHT WINCH CURRENT DRAW", climber.getRightWinchCurrent());

		// PIVOT CURRENT DRAW
		SmartDashboard.putNumber("LEFT PIVOT CURRENT DRAW", geartool.getLeftPivotCurrent());
		SmartDashboard.putNumber("RIGHT PIVOT CURRENT DRAW", geartool.getRightPivotCurrent());

		// DRIVE CURRENT DRAW
		SmartDashboard.putNumber("LEFT BACK DRIVE CURRENT DRAW", drive.driveCurrentDraw[0]);
		SmartDashboard.putNumber("LEFT FRONT DRIVE CURRENT DRAW", drive.driveCurrentDraw[1]);
		SmartDashboard.putNumber("RIGHT BACK DRIVE CURRENT DRAW", drive.driveCurrentDraw[2]);
		SmartDashboard.putNumber("RIGHT FRONT DRIVE CURRENT DRAW", drive.driveCurrentDraw[3]);

		// DRIVE GEAR
		SmartDashboard.putBoolean("DRIVE GEAR", drive.getDriveGearState());

		// GET ARM POSITION
		SmartDashboard.putNumber("ARM DEGREES", geartool.armPosGet());

		// DRIVE ENCODERS
		SmartDashboard.putNumber("LEFT DRIVE ENCODER", drive.getLeftEncoderDist());
		SmartDashboard.putNumber("RIGHT DRIVE ENCODER", drive.getRightEncoderDist());
		SmartDashboard.putNumber("AVERAGE DRIVE ENCODER", drive.getAverageDistance());
		SmartDashboard.putBoolean("gyro", drive.gyroConnected());
		
		SmartDashboard.putNumber("p", NumberConstants.pDrive);
		SmartDashboard.putNumber("i", NumberConstants.iDrive);
		SmartDashboard.putNumber("d", NumberConstants.dDrive);
		// AUTO
		// SmartDashboard.putNumber(key, value)
	}
}