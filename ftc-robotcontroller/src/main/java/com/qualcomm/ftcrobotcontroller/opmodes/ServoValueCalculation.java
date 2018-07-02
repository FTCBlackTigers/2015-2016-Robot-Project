package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by hp on 09/02/2016.
 */
public class ServoValueCalculation extends RobotComponents {
    boolean lastValueUp = false;
    boolean lastValueDown = false;
    boolean iUp = true;
    boolean iDown = true;

    @Override
    public void init() {
        super.init();
        button.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && !lastValueUp) {
            iUp = true;
        }
        if (gamepad1.dpad_down && !lastValueDown) {
            iDown = true;
        }

        if (iUp) {
            if ((debrisArmDoor.getPosition() + 0.05) < 1) {
                debrisArmDoor.setPosition(debrisArmDoor.getPosition() + 0.05 );
                iUp = false;
            } else {
                iUp = false;
            }
        }
        if (iDown) {
            if ((debrisArmDoor.getPosition() - 0.05 ) > 0) {
                debrisArmDoor.setPosition(debrisArmDoor.getPosition() - 0.05 );
                iDown = false;
            }
            iDown = false;
        }
        telemetry.addData("Current position is:", debrisArmDoor.getPosition());
        lastValueUp = gamepad1.dpad_up;
        lastValueDown = gamepad1.dpad_down;
    }
}
