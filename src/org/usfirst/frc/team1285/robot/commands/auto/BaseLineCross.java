package org.usfirst.frc.team1285.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;



/**
 *
 */
public class BaseLineCross extends CommandGroup{

	public BaseLineCross() {
    	addSequential(new DriveDistance(144, 0.8, 0, 4));
    }
}
