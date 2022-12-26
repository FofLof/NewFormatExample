package com.team2073.robot.subsystems.intermediate;

import org.littletonrobotics.junction.AutoLog;

public interface IntermediateIO {

    @AutoLog
     class IntermediateIOInputs {
        public double bottomMotorOutput;
        public double bottomMotorCurrent;

        public double topMotorCurrent;
        public double topMotorOutput;
        public double topSlaveCurrent;

        public boolean firstInput;
        public boolean secondInput;

    }

    default void updateInputs(IntermediateIOInputs inputs) {}

    default void setOutput(double bottomPercent, double topPercent) {}


}
