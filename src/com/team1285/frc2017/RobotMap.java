package com.team1285.frc2017;
/**
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//**************************************************************************
	//*****************************DRIVE MOTORS*********************************
	//**************************************************************************        
	
	public static final int RIGHT_DRIVE_FRONT                               = 2; 
	public static final int RIGHT_DRIVE_BACK                                = 3;
	
	public static final int LEFT_DRIVE_FRONT                                = 1;
	public static final int LEFT_DRIVE_BACK                                 = 4;
	
	public static final int compressorSpike									= 1;
	    
	//**************************************************************************
	//*****************************INTAKE MOTORS********************************
	//**************************************************************************        
		
	public static final int RIGHT_ARM                                       = 5; 
	public static final int LEFT_ARM                                        = 6;
		
	public static final int RIGHT_INTAKE                                    = 7;
	public static final int LEFT_INTAKE                                     = 8;
	
	//**************************************************************************
	//******************************* CLIMBER **********************************
	//**************************************************************************
	
	public static final int LEFT_WINCH 										= 9;
	public static final int RIGHT_WINCH										= 10;
	
	//**************************************************************************
	//************************** GEARTOOL SENSORS ******************************
	//**************************************************************************
		
	public static final int GEARTOOL_OPTICS									= 6;
	public static final int ARM_PIVOT_ENCODER_A								= 0;
	public static final int ARM_PIVOT_ENCODER_B								= 1;


	//**************************************************************************
	//*************************** PNEUMATICS ***********************************
	//**************************************************************************
	 
	public static final int INTAKE_SOLENOID_A								= 0;
	public static final int INTAKE_SOLENOID_B							 	= 1;
	public static final int DRIVE_SOLENOID_A							 	= 2;
	public static final int DRIVE_SOLENOID_B							 	= 3;
	public static final int COMPRESSOR										= 12;
	 
	//**************************************************************************
    //********************* DRIVE ENCODER CONSTANTS ****************************
	//**************************************************************************
	public static final int driveWheelRadius = 2;//wheel radius in inches
	public static final double driveGearRatio = 1/1; //ratio between wheel and encoder
	public static final double ROTATIONS_TO_INCHES 	= (2*Math.PI*driveWheelRadius*driveGearRatio);
	
	//**************************************************************************
    //********************* INTAKE ENCODER CONSTANTS ***************************
	//**************************************************************************
	
	public static final boolean ArmEncoderReverse = false;
	public static final int pulsesPerRev = 128; //pulses per revolution of arm
	public static final double armGearRatio = 1.4; //ratio between wheel and encoder
	//public static final double ROTATIONS_TO_DEGREES = 1; //degrees per tick
}