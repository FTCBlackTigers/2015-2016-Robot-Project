package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by hp on 26/02/2016.
 */
public class ODSTesting extends RobotComponents {
    double reflectance;
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        reflectance = ODS.getLightDetected();
        telemetry.addData("Light", reflectance);

    }
}
