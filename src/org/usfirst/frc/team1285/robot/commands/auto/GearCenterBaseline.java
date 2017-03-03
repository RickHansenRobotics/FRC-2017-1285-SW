package org.usfirst.frc.team1285.robot.commands.auto;

import org.usfirst.frc.team1285.robot.commands.GearClamp;
import org.usfirst.frc.team1285.robot.utilities.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearCenterBaseline extends CommandGroup {

    public GearCenterBaseline() {
    	//DSAT
    	addSequential(new DriveDistance(83, 0.6, 0, 3)); //DSAT
    	addSequential (new GearClamp ());
    	addSequential(new WaitCommand(0.5));
    	addSequential(new DriveDistance(-20, 0.8, 0, 1));
    	addSequential(new DriveTurn(-90, 0.8, 1.5));
    	addSequential (new DriveDistance (60,1,-90,2));		 //clear airship
    	addSequential(new DriveTurn(0, 0.8, 1.5));	 //turn 90 to light
    	addSequential (new DriveDistance (60,1,0,2));		 //cross base line
    }
}
