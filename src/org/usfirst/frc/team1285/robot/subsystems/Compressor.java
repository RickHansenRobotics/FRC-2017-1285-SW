package com.team1285.frc2017.subsystems;

import com.team1285.frc2017.RobotMap;
import com.team1285.frc2017.commands.Compress;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Compressor extends Subsystem {
	
	Relay compress;
    DigitalInput compressor; //change to spike
    
    
    public Compressor (){
    compressor = new DigitalInput(RobotMap.COMPRESSOR);
    compress = new Relay (RobotMap.compressorSpike);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new Compress());
    }
    
    public boolean pressureSwitch(){
    	return compressor.get();
    }
}

