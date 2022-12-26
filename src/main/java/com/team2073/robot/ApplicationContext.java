package com.team2073.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.*;
import com.team2073.robot.subsystems.intake.IntakeIOReal;
import com.team2073.robot.subsystems.intake.IntakeIOSim;
import com.team2073.robot.subsystems.intake.IntakeSubsystem;
import com.team2073.robot.subsystems.intermediate.IntermediateIOReal;
import com.team2073.robot.subsystems.intermediate.IntermediateIOSim;
import com.team2073.robot.subsystems.intermediate.IntermediateSubsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.Getter;

@Getter
public class ApplicationContext {

    private static ApplicationContext instance;

    //IntermediateSubsystem
    private CANSparkMax intermediateTopMasterMotor;
    private CANSparkMax intermediateTopSlaveMotor;
    private CANSparkMax intermediateBottomMotor;
    private IntermediateSubsystem intermediateSubsystem;
    private DigitalInput photoEye1;
    private DigitalInput photoEye2;
    private DigitalInput firstInput;
    private DigitalInput secondInput;

    // IntakeSubsystem
    private IntakeSubsystem intakeSubsystem;
    private CANSparkMax intakeMaster;
    private CANSparkMax intakeSlave;
    private CANSparkMax intakeMotorPivot;
    private CANCoder intakeCANCoder;


    public void initializeObjects() {
        switch (Constants.getRobot()) {
            case ROBOT_TEST:
                intermediateTopMasterMotor = new CANSparkMax(Constants.IntermediateConstants.INTERMEDIATE_TOP_MASTER_MOTOR_PORT, MotorType.kBrushless);
                intermediateTopSlaveMotor = new CANSparkMax(Constants.IntermediateConstants.INTERMEDIATE_TOP_SLAVE_MOTOR_PORT, MotorType.kBrushless);
                intermediateBottomMotor = new CANSparkMax(Constants.IntermediateConstants.INTERMEDIATE_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

                photoEye1 = new DigitalInput(Constants.IntermediateConstants.PHOTO_EYE_PORT_1);
                photoEye2 = new DigitalInput(Constants.IntermediateConstants.PHOTO_EYE_PORT_2);
                firstInput = new DigitalInput(Constants.IntermediateConstants.FIRST_INPUT_PORT);
                secondInput = new DigitalInput(Constants.IntermediateConstants.SECOND_INPUT_PORT);

                intakeMotorPivot = new CANSparkMax(Constants.IntakeConstants.INTAKE_PIVOT, MotorType.kBrushless);
                intakeMaster = new CANSparkMax(Constants.IntakeConstants.INTAKE_MASTER, MotorType.kBrushless);
                intakeSlave = new CANSparkMax(Constants.IntakeConstants.INTAKE_SLAVE, MotorType.kBrushless);
                intakeCANCoder = new CANCoder(Constants.IntakeConstants.INTAKE_CANCODER);
                break;
            case ROBOT_PRACTICE:
                //Reintialize constants according to a theoretical practice bot here
                break;
            default:
                throw new RuntimeException("Error initialzing robot");
        }

        initializeSubsystems();
    }

    public void initializeSubsystems() {
        if (RobotBase.isSimulation()) {
            intakeSubsystem = new IntakeSubsystem(new IntakeIOSim());
            intermediateSubsystem = new IntermediateSubsystem(new IntermediateIOSim());
        } else {
            intermediateSubsystem = new IntermediateSubsystem(new IntermediateIOReal());
            intakeSubsystem = new IntakeSubsystem(new IntakeIOReal());
        }

    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }
}
