package com.team2073.robot.subsystems.intermediate;

import com.team2073.common.periodic.AsyncPeriodicRunnable;
import com.team2073.robot.ApplicationContext;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntermediateSubsystem implements AsyncPeriodicRunnable {
    private ApplicationContext appCtx = ApplicationContext.getInstance();
    private IntermediateState state = IntermediateState.STOP;
    private DigitalInput firstInput = appCtx.getFirstInput();
    private DigitalInput secondInput = appCtx.getSecondInput();

    private IntermediateIOInputsAutoLogged inputs = new IntermediateIOInputsAutoLogged();

    public boolean override = false;
    public static String colorString;
    private String alliance;
    private String enemy;

    IntermediateIO io;


    /**
     * What will happen when intermediate subsystem gets put into use.
     */
    public IntermediateSubsystem(IntermediateIO io) {
        this.io = io;
        autoRegisterWithPeriodicRunner(20);
    }

    @Override
    public void onPeriodicAsync() {
        io.updateInputs(inputs);
        io.setOutput(state.getBottomPercent(), state.getTopPercent());
    }


    public String readColor() {
        if (inputs.firstInput && !inputs.secondInput)
            colorString = "Blue";
        else if (!inputs.firstInput && inputs.secondInput)
            colorString = "Red";
        else if (!inputs.firstInput && !inputs.secondInput)
            colorString  =  "No Ball";
        else
            colorString = "Error";
        return colorString;
    }

    public void setAlliance() {
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            alliance = "Red";
            enemy = "Blue";
        } else if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            alliance = "Blue";
            enemy = "Red";
        }
    }

    /**
     * Intake case will set the rpm in order to rise the ball up
     * Outtake case will se the rpm to negative and push the ball out.
     * Stop will stop the motor.
     */
    @Getter
    public enum IntermediateState {
        INTAKE(.75, 0.75),
        OUTAKE(-1,-1),
        FULL_OUTAKE(-.4, -.4),
        SHOOT(0.35, .7),
        STOP(0,0),
        BEAM_BROKEN(.35, 0.35),
        CORRECT_BALL(0, 0),
        WRONG_BALL_OUTAKE(-.75, 0),
        WRONG_BALL(-.75, .5);

        private double topPercent;
        private double bottomPercent;

        IntermediateState(double bottomPercent, double topPercent) {
            this.bottomPercent = bottomPercent;
            this.topPercent = topPercent;
        }

    }
}