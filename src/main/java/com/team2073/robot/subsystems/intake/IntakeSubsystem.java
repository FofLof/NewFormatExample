package com.team2073.robot.subsystems.intake;

import com.team2073.common.controlloop.PidfControlLoop;
import com.team2073.common.periodic.AsyncPeriodicRunnable;
import com.team2073.common.util.LoggedTunableNumber;
import com.team2073.robot.ApplicationContext;

import com.team2073.robot.Constants;
import com.team2073.robot.subsystems.intermediate.IntermediateSubsystem;
import edu.wpi.first.wpilibj.Timer;
import lombok.Setter;
import org.littletonrobotics.junction.LogTable;

@Setter
public class IntakeSubsystem implements AsyncPeriodicRunnable {
    private final ApplicationContext appCTX = ApplicationContext.getInstance();
    private IntermediateSubsystem intermediateSubsystem = appCTX.getIntermediateSubsystem();
    private static final double MAX_POSITION = 89.121;
    private static final double RETRACTED_POSITION = 134.033;

    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private LoggedTunableNumber kP = new LoggedTunableNumber("Intake/kP");
    private LoggedTunableNumber kI = new LoggedTunableNumber("Intake/kI");
    private LoggedTunableNumber kD = new LoggedTunableNumber("Intake/kD");

    private PidfControlLoop intakePid;

    private boolean firstBall = false;
    private boolean sensorOff = false;

    private IntakeRollerState currentRollerState = IntakeRollerState.STOP;
    private IntakePositionState currentPositionState = IntakePositionState.STOP;

    private double output = 0;
    private double pivotOutput = 0;

    IntakeIO io;
    public IntakeSubsystem(IntakeIO io) {
        this.io = io;
        autoRegisterWithPeriodicRunner(10);
        switch (Constants.getRobot()) {
            case ROBOT_REAL:
                intakePid = new PidfControlLoop(0.02, 0, .001, 0, .3);
                kP.initDefault(0.02);
                kI.initDefault(0);
                kD.initDefault(0.001);
                break;
            case ROBOT_PRACTICE:
                //If we had a practice bot that wasn't murdered
                intakePid = new PidfControlLoop(0, 0, 0, 0, 0);
                kP.initDefault(0);
                kI.initDefault(0);
                kD.initDefault(0);
                break;
            case ROBOT_SIMBOT:
                //For tuning simulation PIDs
                intakePid = new PidfControlLoop(0, 0, 0, 0, 0);
                kP.initDefault(0);
                kI.initDefault(0);
                kD.initDefault(0);
                break;
            default:
                throw new RuntimeException("Error initializing Intake Subsystem");
        }
        intakePid.setPositionSupplier(() -> inputs.canCoderPosition);
        enableTuningMode(Constants.tuningMode);
    }
    @Override
    public void onPeriodicAsync() {
        if (kP.hasChanged() || kI.hasChanged() || kD.hasChanged()) {
            intakePid.setPID(kP.get(), kI.get(), kD.get());
        }
        io.updateInputs(inputs);
        switch (currentRollerState) {
            case STOP:
                output = 0;
                break;
            case INTAKE:
                output = .5;
                if (!firstBall) {
                    intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.INTAKE);
                }
                if (inputs.photoEyeFront && !firstBall) {
                    intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.BEAM_BROKEN);
                    firstBall = true;
                }
                if (inputs.photoEyeBack) {
                    if (sensorOff) {
                        intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.CORRECT_BALL);
                    }else {
                        if (!intermediateSubsystem.readColor().equals("No Ball")){
                            if (intermediateSubsystem.readColor().equals(intermediateSubsystem.getAlliance())) {
                                intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.CORRECT_BALL);
                            } else if (intermediateSubsystem.readColor().equals(intermediateSubsystem.getEnemy()) && !intermediateSubsystem.isOverride()) {
//                                new WrongBall().start();
                            }
                        }else {
                            intermediateSubsystem.setState(IntermediateSubsystem.IntermediateState.STOP);
                        }
                    }
                }

                break;
            case OUTTAKE:
                firstBall = false;
                output = -.6;
                break;
            case INITIAL:
                output = .05;
                break;
            case FULL_OUTAKE:
                output = -.4;
                break;
        }

        switch(currentPositionState) {
            case EXTEND:
                    pivot(currentPositionState.getDegree());
                break;
            case RETRACT:
                pivot(currentPositionState.getDegree());
                break;
            case STOP:
                break;
        }
        io.setIntakeOutput(output);
        io.setPivotOutputs(pivotOutput);
    }

    public void pivot(double position) {
        intakePid.updateSetPoint(position);
        intakePid.updatePID();
        pivotOutput = intakePid.getOutput();
    }

    public void enableTuningMode(boolean tuningMode) {
        kP.setTuningMode(tuningMode);
        kI.setTuningMode(tuningMode);
        kD.setTuningMode(tuningMode);
    }

    public enum IntakeRollerState {
        STOP,
        INTAKE,
        INITIAL,
        OUTTAKE,
        FULL_OUTAKE;
    }


    public void setFirstBallFalse() {
        firstBall = false;
    }

    public void turnSensorOff() {
        sensorOff = true;
    }

    public enum IntakePositionState {
        EXTEND(MAX_POSITION + 2.3),
        RETRACT(RETRACTED_POSITION),
        STOP(0d);

        private double degree;

        IntakePositionState(double degree) {
            this.degree = degree;
        }

        public double getDegree() {
            return degree;
        }
    }
}