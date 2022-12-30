package com.team2073.robot;

import com.team2073.robot.commands.intake.AllOutakeCommand;
import com.team2073.robot.commands.intake.IntakeCommand;
import com.team2073.robot.commands.intake.OuttakeCommand;
import com.team2073.robot.commands.intake.PivotCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class OperatorInterface {
    private final ApplicationContext appCTX = ApplicationContext.getInstance();
    private final Joystick controller = appCTX.getController();


    private final JoystickButton a = new JoystickButton(controller, 1);
    private final JoystickButton b = new JoystickButton(controller, 2);
    private final JoystickButton x = new JoystickButton(controller, 3);
    private final JoystickButton y = new JoystickButton(controller, 4);
    private final JoystickButton lb = new JoystickButton(controller, 5);
    private final JoystickButton rb = new JoystickButton(controller, 6);
    private final JoystickButton windows = new JoystickButton(controller, 7);
    private final JoystickButton three = new JoystickButton(controller, 8);
    private final JoystickButton rightJoystickDown = new JoystickButton(controller, 10);
    private final JoystickButton leftJoystickDown = new JoystickButton(controller, 9);

    private final JoystickButton controllerBack = new JoystickButton(controller, 7);
    private final JoystickButton controllerStart = new JoystickButton(controller, 8);
    private final POVButton dPadUp = new POVButton(controller, 0);
    private final POVButton dPadRight = new POVButton(controller, 90);
    private final POVButton dPadDown = new POVButton(controller, 180);
    private final POVButton dPadLeft = new POVButton(controller, 270);

    public void init(){
        a.whileTrue(new IntakeCommand());
        b.whileTrue(new OuttakeCommand());
        lb.whileTrue(new PivotCommand());

        rightJoystickDown.whileTrue(new AllOutakeCommand());
//        dPadLeft.whenActive(new MediatorCommand(Mediator.RobotState.LIMELIGHT_SHOT));
//        dPadRight.whenActive(new MediatorCommand(Mediator.RobotState.PREP_SHOT_MOVING));
    }

}
