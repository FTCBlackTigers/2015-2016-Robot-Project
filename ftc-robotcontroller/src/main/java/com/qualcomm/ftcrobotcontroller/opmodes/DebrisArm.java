package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

public class DebrisArm extends RobotComponents {
    /* Specifications */
    private final static double LOW_LOCATION = 10;         //The distance between the robot and the arm in when it's near the low shelter in cm
    private final static double MID_LOCATION = 30;         //The distance between the robot and the arm in when it's near the mid shelter in cm
    private final static int ENCODER_CPCM = 1440;          //Encoder counts per 1 cm
    private final double OPERATION_SPEED = 0.5;            //The speed of the arm operation
    /* ----------------------*/

    /* Calculations */
    private final static double LOW_CALCULATION = LOW_LOCATION * ENCODER_CPCM;
    private final static double MID_CALCULATION = MID_LOCATION * ENCODER_CPCM;
    /* ----------------------*/

    private boolean currentlyRunning = false;
    private double time;
    private boolean lastV = false;
    private int state = 0;
    private int previousState = 0;

    @Override
    public void init() {
        super.init();
        debrisArmLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        debrisArmRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper && !lastV) {
            if (state == 2) {
                state = 0;
            } else {
                state++;
            }
        }

        if (gamepad1.a && !currentlyRunning) {
            if (state == 0 && previousState != 0) {
                debrisArmLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmLeft.setTargetPosition(0);
                debrisArmRight.setTargetPosition(0);
                debrisArmLeft.setPower(OPERATION_SPEED);
                debrisArmRight.setPower(OPERATION_SPEED);

                currentlyRunning = true;
                previousState = 0;
                time = System.currentTimeMillis();
            }

            if (state == 1 && previousState != 1) {
                debrisArmLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmLeft.setTargetPosition((int) LOW_CALCULATION);
                debrisArmRight.setTargetPosition((int) LOW_CALCULATION);
                debrisArmLeft.setPower(OPERATION_SPEED);
                debrisArmRight.setPower(OPERATION_SPEED);

                currentlyRunning = true;
                previousState = 1;
                time = System.currentTimeMillis();
            }

            if (state == 2 && previousState != 2) {
                debrisArmLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmLeft.setTargetPosition((int) MID_CALCULATION);
                debrisArmRight.setTargetPosition((int) MID_CALCULATION);
                debrisArmLeft.setPower(OPERATION_SPEED);
                debrisArmRight.setPower(OPERATION_SPEED);

                currentlyRunning = true;
                previousState = 2;
                time = System.currentTimeMillis();
            }
        }
        if (currentlyRunning && ((System.currentTimeMillis() - time) > 200 )) {
            if (!debrisArmLeft.isBusy() && !debrisArmRight.isBusy()) {
                currentlyRunning = false;
            }
        }

        /* Telemetry Data */
        if (state == 0){
            telemetry.addData("Chosen state:", "Closed");
        }
        if(state == 1){
            telemetry.addData("Chosen state:", "Low shelter");
        }
        if (state == 2){
            telemetry.addData("Chosen state:", "Mid shelter");
        }
        if (previousState == 0){
            telemetry.addData("Current state:", "Closed");
        }
        if(previousState == 1){
            telemetry.addData("Current state:", "Low shelter");
        }
        if (previousState == 2){
            telemetry.addData("Current state:", "Mid shelter");
        }
        /* ----------------------*/
        lastV = gamepad1.left_bumper;
    }
}