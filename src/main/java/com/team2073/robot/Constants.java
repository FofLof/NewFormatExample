package com.team2073.robot;

import edu.wpi.first.wpilibj.RobotBase;

import java.awt.*;
import java.util.Map;

public class Constants {
    public static final boolean tuningMode = false;
    private static final boolean isPracticeBot = false;

    //Field Constants (ft)
    public final double UPPER_HUB_RADIUS = 2;
    public final double DISTANCE_OFFSET = 2.417;


    //Controllers
    public final int WHEEL_PORT = 0;
    public final int JOYSTICK_PORT = 1;
    public static final int CONTROLLER_PORT = 2;


    private static final RobotType robot = RobotType.ROBOT_REAL;
    public static final double loopPeriodSecs = 0.02;

//    private static final Alert invalidRobotAlert =
//            new Alert("Invalid robot selected, using competition robot as default.",
//                    AlertType.ERROR);

    public static RobotType getRobot() {
        if (RobotBase.isSimulation()) return RobotType.ROBOT_SIMBOT;
        return isPracticeBot ? RobotType.ROBOT_PRACTICE : RobotType.ROBOT_REAL;
    }

    public static Mode getMode() {
        switch (getRobot()) {
            case ROBOT_REAL:
                return RobotBase.isReal() ? Mode.REAL : Mode.SIM;

            case ROBOT_SIMBOT:
                return Mode.SIM;

            default:
                return Mode.REAL;
        }
    }

    public class IntakeConstants {
        public static final int INTAKE_MASTER = 8; // TODO: get the right port
        public static final int INTAKE_SLAVE = 9;

        public static final int INTAKE_CANCODER = 19;
        public static final int INTAKE_PIVOT = 7;
    }

    public class IntermediateConstants {
        public static final int INTERMEDIATE_TOP_MASTER_MOTOR_PORT = 11;
        public static final int INTERMEDIATE_TOP_SLAVE_MOTOR_PORT = 12;
        public static final int INTERMEDIATE_BOTTOM_MOTOR_PORT = 10;
        public static final int PHOTO_EYE_PORT_1 = 5;
        public static final int PHOTO_EYE_PORT_2 = 6;
        public static final int FIRST_INPUT_PORT = 8;
        public static final int SECOND_INPUT_PORT = 9;
    }
    public static final Map<RobotType, String> logFolders =
            Map.of(RobotType.ROBOT_REAL, "/media/sda2");

    public static enum RobotType {
        ROBOT_REAL, ROBOT_SIMBOT, ROBOT_PRACTICE;
    }

    public static enum Mode {
        REAL, REPLAY, SIM, PRACTICE
    }
}
