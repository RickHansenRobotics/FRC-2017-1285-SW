package org.usfirst.frc.team1285.robot.commands.auto;

import org.usfirst.frc.team1285.robot.commands.GearClamp;
import org.usfirst.frc.team1285.robot.utilities.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearCenterWaitBaseline extends CommandGroup {

    public GearCenterWaitBaseline() {
    	addSequential(new DriveDistance(71, 0.7, 0, 2)); //DSAT
    	addSequential(new WaitCommand(2));
    	addSequential(new DriveDistance(10, 0.6, 0, 1)); //DSAT
    	addSequential (new GearClamp ());
    	addSequential(new WaitCommand(0.5));
    	addSequential(new DriveDistance(-20, 0.8, 0, 1));
    	addSequential(new DriveTurn(-90, 0.8, 1.5));
    	addSequential (new DriveDistance (60,1,-90,2));		 //clear airship
    	addSequential(new DriveTurn(0, 0.8, 1.5));	 //turn 90 to light
    	addSequential (new DriveDistance (60,1,0,2));		 //cross base line
    }
}
