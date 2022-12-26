package com.team2073.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

    @AutoLog
    public static class IntakeIOInputs {
        public double canCoderPosition;
        public double motorMasterCurrentDraw;
        public double pivotMotorCurrentDraw;
        public double motorMasterOutput;
        public double pivotMotorOutput;

        public boolean photoEyeFront;
        public boolean photoEyeBack;
    }

    public default void updateInputs(IntakeIOInputs inputs) {}

    public default void setIntakeOutput(double output) {}

    public default void setPivotOutputs(double output) {}

    public default void configurePID(double p, double i, double d) {}
}
