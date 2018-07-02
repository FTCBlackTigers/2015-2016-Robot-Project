package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by hp on 26/01/2016.
 */
public class DriveType {
    DcMotor rightMotor, leftMotor;
    enum Sensitivity{NORMAL, SQUARED, CUBED, QUADRO, FIFTH}

    protected float changeSensitivity(double value, Sensitivity sens) {
        switch (sens) {
            case SQUARED:
                return (float) (Math.pow(value, 2) * Math.signum(value));
            case CUBED:
                return (float) Math.pow(value, 3);
            case QUADRO:
                return (float) (Math.pow(value, 4) * Math.signum(value));
            case FIFTH:
                return (float) Math.pow(value, 5);
            default:
                return (float) value;
        }
    }

    DriveType (DcMotor rightMotor, DcMotor leftMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    void drive(Gamepad gamepad, Sensitivity sens) {

    }
}
