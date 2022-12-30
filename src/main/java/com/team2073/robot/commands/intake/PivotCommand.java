package com.team2073.robot.commands.intake;
import com.revrobotics.CANSparkMax;
import com.team2073.common.command.AbstractLoggingCommand;
import com.team2073.robot.ApplicationContext;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;

public class PivotCommand extends AbstractLoggingCommand {
    private ApplicationContext appCTX = new ApplicationContext().getInstance();
    private IntakeSubsystem intakeSubsystem = appCTX.getIntakeSubsystem();
    private CANSparkMax pivot = appCTX.getIntakeMotorPivot();

    @Override
    protected void executeDelegate() {
        intakeSubsystem.setCurrentPositionState(IntakeSubsystem.IntakePositionState.EXTEND);
    }

    @Override
    protected void endDelegate(boolean initialize) {
        super.endDelegate(initialize);
        intakeSubsystem.setCurrentPositionState(IntakeSubsystem.IntakePositionState.RETRACT);
    }

    @Override
    protected boolean isFinishedDelegate() {
        return false;
    }
}