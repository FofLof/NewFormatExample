package com.team2073.robot.commands.intake;

import com.team2073.common.command.AbstractLoggingCommand;
import com.team2073.robot.ApplicationContext;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;
import com.team2073.robot.subsystems.intermediate.IntermediateSubsystem;

public class AllOutakeCommand extends AbstractLoggingCommand {
    private ApplicationContext appCTX = ApplicationContext.getInstance();
    IntakeSubsystem intakeSubsystem = appCTX.getIntakeSubsystem();
    IntermediateSubsystem intermediateSubsystem = appCTX.getIntermediateSubsystem();

    @Override
    protected void executeDelegate() {
        intakeSubsystem.setCurrentRollerState(IntakeSubsystem.IntakeRollerState.FULL_OUTAKE);
        intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.FULL_OUTAKE);
    }

    @Override
    protected void endDelegate(boolean interruptible) {
        super.endDelegate(interruptible);
        intakeSubsystem.setCurrentRollerState(IntakeSubsystem.IntakeRollerState.STOP);
        intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.STOP);
        intakeSubsystem.setFirstBallFalse();
    }



    @Override
    protected boolean isFinishedDelegate() {
        return false;
    }
}
