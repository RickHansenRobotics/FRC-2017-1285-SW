package com.team1285.frc2017.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;



/**
 *
 */
public class BaseLineCross extends CommandGroup{

	public BaseLineCross() {
    	addSequential(new DriveDistance(240, 0.8, 0, 4));
    }
}
