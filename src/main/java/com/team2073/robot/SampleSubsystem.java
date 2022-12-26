package com.team2073.robot;

import com.team2073.common.periodic.AsyncPeriodicRunnable;
import org.littletonrobotics.junction.Logger;

public class SampleSubsystem implements AsyncPeriodicRunnable {

    public long testVar1 = 0;

    public SampleSubsystem() {
        autoRegisterWithPeriodicRunner(10);
    }

    @Override
    public void onPeriodicAsync() {
        testVar1++;
        Logger.getInstance().recordOutput("Testinuygufuty", testVar1);
    }
}
