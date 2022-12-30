package com.team2073.robot.commands.intake;

import com.team2073.common.command.AbstractLoggingCommand;
import com.team2073.robot.ApplicationContext;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;
import com.team2073.robot.subsystems.intermediate.IntermediateSubsystem;

public class IntakeCommand extends AbstractLoggingCommand {
    ApplicationContext appCTX = ApplicationContext.getInstance();
    IntakeSubsystem intakeSubsystem = appCTX.getIntakeSubsystem();
    IntermediateSubsystem intermediateSubsystem = appCTX.getIntermediateSubsystem();

    @Override
    protected void initializeDelegate() {
        intakeSubsystem.setCurrentRollerState(IntakeSubsystem.IntakeRollerState.INTAKE);
    }

    @Override
    protected void endDelegate(boolean initialize) {
        super.endDelegate(initialize);
        intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.STOP);
        intakeSubsystem.setCurrentRollerState(IntakeSubsystem.IntakeRollerState.STOP);
    }

    @Override
    protected boolean isFinishedDelegate() {
        return false;
    }
}
