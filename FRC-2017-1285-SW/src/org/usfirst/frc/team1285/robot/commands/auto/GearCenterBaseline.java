package org.usfirst.frc.team1285.robot.commands.auto;

import org.usfirst.frc.team1285.robot.commands.GearClamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearCenterBaseline extends CommandGroup {

    public GearCenterBaseline() {
    	
    	addSequential(new DriveDistance(83.5, 0.8, 0, 2.5));
    	addSequential (new GearClamp ());
    	addSequential (new DriveDistance(-11, 0.8, 0, 2));
    	addSequential (new DriveTurn (20,-20,0.8,0,2));
    	addSequential (new DriveDistance (50,1,0,2));
    	addSequential (new DriveTurn (20,20,0.8,0,2));
    	addSequential (new DriveDistance (30,1,0,2));
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