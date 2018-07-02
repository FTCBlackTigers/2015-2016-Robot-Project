package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.DriveType;
import com.qualcomm.ftcrobotcontroller.opmodes.RobotComponents;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by hp on 27/02/2016.
 */
public class DriverControlledProgramTest extends RobotComponents {
    boolean init = true;
    boolean lastV = false;
    boolean blocked = false;
    boolean unBlockedi = true;
    boolean blockedi = true;
    int rightPos;
    int leftPos;

    @Override
    public void init() {
        super.init();
        rightMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        leftMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }
    @Override
    public void loop() {
        if (init) {
            button.setPosition(0.5);
            debrisArmDoor.setPosition(0);
            init = false;
        }
        tankDrive.drive(gamepad1, DriveType.Sensitivity.FIFTH);

        if (blocked) {
            if (blockedi) {
                debrisArmRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                debrisArmLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                blockedi = false;
                unBlockedi = true;
                rightPos = debrisArmRight.getCurrentPosition();
                leftPos = debrisArmLeft.getCurrentPosition();
            }
            debrisArmRight.setTargetPosition(rightPos);
            debrisArmLeft.setTargetPosition(leftPos);
            debrisArmRight.setPower(0.2);
            debrisArmLeft.setPower(0.2);
        } else {
            if (unBlockedi) {
                debrisArmRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                debrisArmLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                unBlockedi = false;
                blockedi = true;
            }
            if (gamepad2.left_stick_y > 0.05 && gamepad2.a) {
                debrisArmLeft.setPower(-0.6);
                debrisArmRight.setPower(-0.6);
            } else if (gamepad2.left_stick_y > 0.05) {
                debrisArmLeft.setPower(-0.07);
                debrisArmRight.setPower(-0.07);
            } else if (gamepad2.left_stick_y < -0.05) {
                debrisArmLeft.setPower(0.8);
                debrisArmRight.setPower(0.8);
            } else {
                debrisArmLeft.setPower(0);
                debrisArmRight.setPower(0);
            }
        }
        if (gamepad2.left_stick_button && !lastV) {
            blocked = !blocked;
        }

        if (gamepad2.right_stick_y > 0.05) {
            debrisArmDoor.setPosition(1);
        }
        if (gamepad2.right_stick_y < -0.05) {
            debrisArmDoor.setPosition(0);
        }
        if (gamepad2.left_bumper) {
            climbersArm.setPosition(0.8);
        } else {
            climbersArm.setPosition(0);
        }
        if (gamepad2.right_bumper) {
            button.setPosition(0.2);
        } else {
            button.setPosition(0.5);
        }

        lastV = gamepad2.left_stick_button;
    }
}
