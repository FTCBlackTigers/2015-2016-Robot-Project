/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class DriverControlledProgram extends RobotComponents {
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
            debrisArmDoor.setPosition(1);
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
            if (gamepad2.left_stick_y > 0.05) {
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
