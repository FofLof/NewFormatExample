package com.team2073.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

    @AutoLog
    class IntakeIOInputs {
        public double canCoderPosition;
        public double motorMasterCurrentDraw;
        public double pivotMotorCurrentDraw;
        public double motorMasterOutput;
        public double pivotMotorOutput;

        public boolean photoEyeFront;
        public boolean photoEyeBack;
    }

    default void updateInputs(IntakeIOInputs inputs) {}

    default void setIntakeOutput(double output) {}

    default void setPivotOutputs(double output) {}

}
