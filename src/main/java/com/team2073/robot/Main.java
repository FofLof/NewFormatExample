package com.team2073.robot;

import com.team2073.common.robot.RobotApplication;
import edu.wpi.first.wpilibj.RobotBase;

public class Main {

    public static void main(String...args) throws NoSuchFieldException, IllegalAccessException{
        RobotApplication.start(() -> new RobotDelegate(.02));
//        RobotBase.startRobot(RobotDelegate::new);
    }

}
