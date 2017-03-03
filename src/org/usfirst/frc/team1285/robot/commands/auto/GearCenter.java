package org.usfirst.frc.team1285.robot.commands.auto;

import org.usfirst.frc.team1285.robot.commands.GearClamp;
import org.usfirst.frc.team1285.robot.utilities.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearCenter extends CommandGroup {

    public GearCenter() {
    	addSequential(new DriveDistance(83, 0.6, 0, 3)); //DSAT
    	addSequential (new GearClamp ());
    	addSequential(new WaitCommand(0.5));
    	addSequential(new DriveDistance(10, 0.4, 0, 3));
    	addSequential (new GearClamp ());
    	addSequential(new WaitCommand(0.5));
    	addSequential(new DriveDistance(-15, 0.8, 0, 3));
    	
    	//addSequential new 
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
