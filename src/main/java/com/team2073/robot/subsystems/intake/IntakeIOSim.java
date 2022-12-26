package com.team2073.robot.subsystems.intake;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.team2073.robot.ApplicationContext;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeIOSim implements IntakeIO{
    ApplicationContext appCTX = ApplicationContext.getInstance();
    CANCoder intakeCancoder = appCTX.getIntakeCANCoder();
    CANSparkMax intakeMaster = appCTX.getIntakeMaster();
    CANSparkMax intakeSlave = appCTX.getIntakeSlave();
    CANSparkMax intakePivot = appCTX.getIntakeMotorPivot();

    DigitalInput photoEyeFront = appCTX.getPhotoEye1();
    DigitalInput photoEyeBack = appCTX.getPhotoEye2();

    public IntakeIOSim() {
        intakeMaster.setSmartCurrentLimit(25);
        intakeSlave.setSmartCurrentLimit(25);
        intakePivot.setSmartCurrentLimit(45);
        intakeSlave.follow(intakeMaster, true);
        intakeCancoder.setPositionToAbsolute();
        intakeMaster.setInverted(true);
        intakeSlave.setInverted(false);
        intakePivot.setInverted(true);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.canCoderPosition = intakeCancoder.getPosition();
        inputs.motorMasterCurrentDraw = intakeMaster.getOutputCurrent();
        inputs.pivotMotorCurrentDraw = intakePivot.getOutputCurrent();
        inputs.motorMasterOutput = intakeMaster.getAppliedOutput();
        inputs.pivotMotorOutput = intakePivot.getAppliedOutput();

        inputs.photoEyeFront = photoEyeFront.get();
        inputs.photoEyeBack = photoEyeBack.get();
    }

    @Override
    public void setIntakeOutput(double output) {
        intakeMaster.setVoltage(output * 12);
    }

    @Override
    public void setPivotOutputs(double output) {
        intakePivot.setVoltage(output * 12);
    }
}
