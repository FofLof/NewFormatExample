package com.team2073.robot.commands.intake;

import com.team2073.common.command.AbstractLoggingCommand;
import com.team2073.robot.ApplicationContext;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;

public class AutoStopIntakeCommand extends AbstractLoggingCommand {
    private ApplicationContext appCTX = ApplicationContext.getInstance();
    private IntakeSubsystem intakeSubsystem = appCTX.getIntakeSubsystem();

    @Override
    protected void initializeDelegate() {
        intakeSubsystem.setCurrentRollerState(IntakeSubsystem.IntakeRollerState.STOP);
    }

    @Override
    protected boolean isFinishedDelegate() {
        return false;
    }
}
