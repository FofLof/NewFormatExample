package com.team2073.robot;

import com.revrobotics.REVPhysicsSim;
import com.team2073.common.periodic.AsyncPeriodicRunnable;
import com.team2073.common.robot.AbstractRobotDelegate;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.PowerDistribution;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedPowerDistribution;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class RobotDelegate extends AbstractRobotDelegate {
    private SampleSubsystem sampleSubsystem = new SampleSubsystem();
    ApplicationContext appCTX;
    private Logger logger;
    public RobotDelegate(double period) {
        super(period);
    }

    @Override
    public void robotInit() {
        appCTX = ApplicationContext.getInstance();
        appCTX.initializeObjects();
        logger = Logger.getInstance();

        switch (Constants.getMode()) {
            case REAL:
                String folder = Constants.logFolders.get(Constants.getRobot());
                if (folder != null) {
                    logger.addDataReceiver(new WPILOGWriter(folder));
                } else {
//                    Constants.logNoFileAlert.set(true);
                }
                logger.addDataReceiver(new NT4Publisher());
                LoggedPowerDistribution.getInstance();
                break;

            case SIM:
                logger.addDataReceiver(new NT4Publisher());
                break;

            case REPLAY:
//                String path = LogFileUtil.findReplayLog();
//                logger.setReplaySource(new WPILOGReader(path));
//                logger.addDataReceiver(
//                        new WPILOGWriter(LogFileUtil.addPathSuffix(path, "_sim")));
                break;
        }
        logger.start();
    }

    @Override
    public void autonomousInit() {
        appCTX.getIntermediateSubsystem().setAlliance();
    }

    @Override
    public void simulationInit() {
        REVPhysicsSim.getInstance().addSparkMax(appCTX.getIntakeMaster(), DCMotor.getNeo550(1));
        REVPhysicsSim.getInstance().addSparkMax(appCTX.getIntakeSlave(), DCMotor.getNeo550(1));
        REVPhysicsSim.getInstance().addSparkMax(appCTX.getIntakeMotorPivot(), DCMotor.getNEO(1));
    }

    @Override
    public void simulationPeriodic() {
        REVPhysicsSim.getInstance().run();
    }
}
