package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by hp on 26/01/2016.
 */
public class TankDrive extends DriveType {
    TankDrive(DcMotor rightMotor, DcMotor leftMotor) {
        super(rightMotor, leftMotor);
    }

    void drive(Gamepad gamepad, Sensitivity sens) {
        double rightVal = changeSensitivity(-gamepad.left_stick_y, sens);
        double leftVal = changeSensitivity(-gamepad.right_stick_y, sens);

        leftMotor.setPower(-leftVal);
        rightMotor.setPower(-rightVal);
    }
}
