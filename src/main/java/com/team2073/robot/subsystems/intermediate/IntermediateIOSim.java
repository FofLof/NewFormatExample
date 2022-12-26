package com.team2073.robot.subsystems.intermediate;

import com.revrobotics.CANSparkMax;
import com.team2073.robot.ApplicationContext;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntermediateIOSim implements IntermediateIO{
    private ApplicationContext appCTX = ApplicationContext.getInstance();

    private CANSparkMax bottomMotor = appCTX.getIntermediateBottomMotor();
    private CANSparkMax topMasterMotor = appCTX.getIntermediateTopMasterMotor();
    private CANSparkMax topSlaveMotor = appCTX.getIntermediateTopSlaveMotor();

    private DigitalInput firstInput = appCTX.getFirstInput();
    private DigitalInput secondInput = appCTX.getSecondInput();
    public IntermediateIOSim() {
        topMasterMotor.setSmartCurrentLimit(25);
        bottomMotor.setSmartCurrentLimit(25);
        topSlaveMotor.setSmartCurrentLimit(25);
        bottomMotor.setInverted(true);
        topMasterMotor.setInverted(false);
        topSlaveMotor.follow(topMasterMotor, true);
    }

    @Override
    public void updateInputs(IntermediateIOInputs inputs) {
        inputs.bottomMotorCurrent = bottomMotor.getOutputCurrent();
        inputs.topMotorCurrent = topMasterMotor.getOutputCurrent();
        inputs.topSlaveCurrent = topSlaveMotor.getOutputCurrent();

        inputs.bottomMotorOutput = bottomMotor.getAppliedOutput();
        inputs.topMotorOutput = topMasterMotor.getAppliedOutput();

        inputs.firstInput = firstInput.get();
        inputs.secondInput = secondInput.get();
    }

    @Override
    public void setOutput(double bottomPercent, double topPercent) {
        bottomMotor.setVoltage(bottomPercent * 12);
        topMasterMotor.setVoltage(topPercent * 12);
    }
}
