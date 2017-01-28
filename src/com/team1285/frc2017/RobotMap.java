package com.team1285.frc2017;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//**************************************************************************
	//*****************************INTAKE MOTORS********************************
	//**************************************************************************        
	
	public static final int RIGHT_ARM                                       = 1; 
	public static final int LEFT_ARM                                        = 2;
	
	public static final int RIGHT_INTAKE                                    = 3;
	public static final int LEFT_INTAKE                                     = 4;
	
	//**************************************************************************
	//************************** GEARTOOL ENCODERS *****************************
	//**************************************************************************
	
	public static final int GEARTOOL_OPTICS									= 1;
	
	//**************************************************************************
	//*************************** GearTool Pneumatics ***********************************
	//**************************************************************************
	 
	public static final int SOLENOID_A				                     	= 1;
	public static final int SOLENOID_B				                       	= 2;
}
