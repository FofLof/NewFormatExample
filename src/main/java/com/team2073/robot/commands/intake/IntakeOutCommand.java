package com.team2073.robot.commands.intake;

import com.team2073.common.command.AbstractLoggingCommand;
import com.team2073.robot.ApplicationContext;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;

public class IntakeOutCommand extends AbstractLoggingCommand {
    private ApplicationContext appCTX = ApplicationContext.getInstance();
    private IntakeSubsystem intakeSubsystem = appCTX.getIntakeSubsystem();

    @Override
    protected void initializeDelegate() {
        intakeSubsystem.setCurrentPositionState(IntakeSubsystem.IntakePositionState.EXTEND);
    }

    @Override
    protected boolean isFinishedDelegate() {
        return true;
    }
}
