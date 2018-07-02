package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.opencv.core.Size;

/**
 * Created by hp on 26/02/2016.
 */
public class RedAutonomous extends RobotComponents {
    /* Robot Specifications */
    final static int ENCODER_CPR = 560;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_DIAMETER = 5.08;
    /* ----------------------*/

    /* Actions Values */
    private final static int firstDrivingDistance = 108;     //The first driving distance in cm (approximately)
    private final static int secondDrivingDistance = 59;     //The second driving distance in cm (approximately)
    private final static int additionDrivingDistance = 12;   //The addition driving distance in cm (approximately)
    private final static int thirdDrivingDistance = 20;      //The third driving distance in cm (approximately)
    private final int requiredDistance = 9;                  //The required distance from wall in order to proceed to dropping the climbers into the alliance specific shelter
    private final double defaultButton = 0.5;                //The default position of the servo that pushes the button
    private final double rightButton = 0.6;                  //The position of the servo that pushes the button when the needed color is on the right side
    private final double leftButton = 0.4;                   //The position of the servo that pushes the button when the needed color is on the left side
    private final double climbersArmClose = 0;               //The position of the servo that drops the climbers into the alliance specific shelter in which it's closed
    private final double climbersArmHalf = 0.75;             //The position of the servo that drops the climbers into the alliance specific shelter in which it's half open
    private final double climbersArmOpen = 0.9;              //The position of the servo that drops the climbers into the alliance specific shelter in which it's fully open
    /* ----------------------*/

    /* Calculations */
    private final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    private final static double ROTATIONS = firstDrivingDistance / CIRCUMFERENCE;
    private final static double ROTATIONS2 = secondDrivingDistance / CIRCUMFERENCE;
    private final static double ROTATIONS3 = thirdDrivingDistance / CIRCUMFERENCE;
    private final static double ROTATIONS4 = additionDrivingDistance / CIRCUMFERENCE;
    private final static double finalFirstDrivingDistance = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    private final static double finalSecondDrivingDistance = ENCODER_CPR * ROTATIONS2 * GEAR_RATIO;
    private final static double finalAdditionDrivingDistance = -(ENCODER_CPR * ROTATIONS4 * GEAR_RATIO);
    private final static double finalThirdDrivingDistance = -(ENCODER_CPR * ROTATIONS3 * GEAR_RATIO);
    /* ----------------------*/

    private boolean init = true;
    private double time;
    private boolean i = true;
    private boolean firstAlign = false;
    private boolean wait1 = false;
    private boolean firstAction = false;
    private boolean wait2 = false;
    private boolean secondAction = false;
    private boolean wait3 = false;
    private double usValue = 255;
    private boolean thirdAction = false;
    private boolean wait4 = false;
    private boolean fourthAction = false;
    private boolean wait5 = false;
    private boolean fifthAction = false;
    private boolean wait6 = false;
    private boolean sixthAction = false;
    private boolean wait7 = false;
    private boolean seventhAction = false;
    private String actionMSG = "None";
    private int heading = 0;
    private boolean wait8 = false;
    private boolean eighthAction = false;
    private boolean ninthAction = false;
    private boolean tenthAction = false;
    private boolean addition = false;
    private boolean additionWait = false;

    @Override
    public void init() {
        super.init();
        this.setCamera(Cameras.SECONDARY);
        this.setFrameSize(new Size(900, 900));
        enableExtension(Extensions.BEACON);
        enableExtension(Extensions.ROTATION);
        rotation.setRotationInversion(false);
        beacon.setAnalysisMethod(Beacon.AnalysisMethod.COMPLEX);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        sensorGyro.calibrate();
    }

    @Override
    public void loop() {
        super.loop();

        if (init)
        {
            time = System.currentTimeMillis();
            button.setPosition(defaultButton);
            climbersArm.setPosition(climbersArmClose);
            debrisArmDoor.setPosition(1);
            init = false;
            firstAlign = true;
            debrisArmLeft.setPower(0);
            debrisArmRight.setPower(0);
        } /* End if (init) */

        if (firstAlign)
        {
            if ((System.currentTimeMillis() - time) < 1500) {
                rightMotor.setPower(-0.05);
                leftMotor.setPower(-0.05);
            } else {
                rightMotor.setPowerFloat();
                leftMotor.setPowerFloat();

                firstAlign = false;
                wait1 = true;
            }
        } /* End if (firstAlign) */

        if (wait1)
        {
            if (i) {
                time = System.currentTimeMillis();
                sensorGyro.resetZAxisIntegrator();
                rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                i = false;
            }
            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait1 = false;
                firstAction = true;
            }
        } /* End if (wait1) */

        if (firstAction) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setTargetPosition((int) finalFirstDrivingDistance);
                rightMotor.setPower(0.3);

                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setTargetPosition((int) finalFirstDrivingDistance);
                leftMotor.setPower(0.3);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 200) {
                if ((!rightMotor.isBusy()) && (!leftMotor.isBusy())) {
                    i = true;
                    firstAction = false;
                    wait2 = true;
                }
            }
        } /* End if (firstAction) */

        if (wait2)
        {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
                leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            }

            if ((System.currentTimeMillis() - time) > 1000) {
                i = true;
                wait2 = false;
                secondAction = true;
            }
        } /* End if (wait2) */

        if (secondAction)
        {
            leftMotor.setPower(0);
            if (heading < 80) {
                rightMotor.setPower(0.3);
            } else if (heading < 90) {
                rightMotor.setPower(0.05);
            } else {
                rightMotor.setPower(0);
                secondAction = false;
                wait3 = true;
            }
        } /* End if (secondAction) */

        if (wait3)
        {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait3 = false;
                thirdAction = true;
            }
        } /* End if (wait3) */

        if (thirdAction) {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
            }
            if (System.currentTimeMillis() - time < 1000) {
                rightMotor.setPower(0.3);
                leftMotor.setPower(0.3);
            } else if (usValue > 45) {
                rightMotor.setPower(0.3);
                leftMotor.setPower(0.3);
            } else {
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                thirdAction = false;
                wait4 = true;
                i = true;
            }
        }/* End if (thirdAction) */

        if (wait4)
        {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait4 = false;
                fourthAction = true;
            }
        } /* End if (wait4) */

        if(fourthAction) {
            rightMotor.setPower(0);
            if (heading > 10) {
                leftMotor.setPower(0.3);
            } else if (heading > -4) {
                leftMotor.setPower(0.05);
            } else {
                leftMotor.setPower(0);
                fourthAction = false;
                wait5 = true;
            }
        }/* End if (fourthAction) */

        if (wait5)
        {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait5 = false;
                fifthAction = true;
            }
        } /* End if (wait5) */

        if (fifthAction) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setTargetPosition((int) finalSecondDrivingDistance);
                rightMotor.setPower(0.3);

                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setTargetPosition((int) finalSecondDrivingDistance);
                leftMotor.setPower(0.3);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 200) {
                if ((!rightMotor.isBusy()) && (!leftMotor.isBusy())) {
                    i = true;
                    fifthAction = false;
                    wait6 = true;
                }
            }
        }/* End if (fifthAction) */

        if (wait6)
        {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait6 = false;
                addition = true;
            }
        } /* End if (wait6) */

        if (addition) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setTargetPosition((int) finalAdditionDrivingDistance);
                rightMotor.setPower(0.3);

                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setTargetPosition((int) finalAdditionDrivingDistance);
                leftMotor.setPower(0.3);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 200) {
                if ((!rightMotor.isBusy()) && (!leftMotor.isBusy())) {
                    i = true;
                    addition = false;
                    additionWait = true;
                }
            }
        }/* End if (addition) */

        if (additionWait)
        {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                additionWait = false;
                sixthAction = true;
            }
        } /* End if (additionWait) */

        if (sixthAction) {
            rightMotor.setPower(0);
            if (heading < 80) {
                leftMotor.setPower(-0.3);
            } else if (heading < 86) {
                leftMotor.setPower(-0.05);
            } else {
                leftMotor.setPower(0);
                sixthAction = false;
                wait7 = true;
            }
        }/* End if (sixthAction) */

        if (wait7) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait7 = false;
                seventhAction = true;
            }
        }/* End if (wait7) */

        if (seventhAction) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                rightMotor.setTargetPosition((int) finalThirdDrivingDistance);
                rightMotor.setPower(0.3);

                leftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                leftMotor.setTargetPosition((int) finalThirdDrivingDistance);
                leftMotor.setPower(0.3);
                i = false;
            }
            if ((System.currentTimeMillis() - time) > 200) {
                if ((!rightMotor.isBusy()) && (!leftMotor.isBusy())) {
                    i = true;
                    seventhAction = false;
                    wait8 = true;
                }
            }
        } /* End if (seventhAction) */

        if (wait8) {
            if (i) {
                time = System.currentTimeMillis();
                rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                i = false;
            }

            if ((System.currentTimeMillis() - time) > 500) {
                i = true;
                wait8 = false;
                eighthAction = true;
            }
        } /* End if (wait8) */

        if (eighthAction) {
            if (usValue > (requiredDistance + 10)) {
                if (beacon.getAnalysis().isLeftRed()) {
                    button.setPosition(leftButton);
                }

                if (beacon.getAnalysis().isRightRed()) {
                    button.setPosition(rightButton);
                }
            }
            if (usValue > requiredDistance) {
                rightMotor.setPower(0.35);
                leftMotor.setPower(0.35);
            } else {
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                eighthAction = false;
                ninthAction = true;
            }
        } /* End if (eighthAction) */

        if (ninthAction)
        {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
            }
            if ((System.currentTimeMillis() - time) < 400) {
                rightMotor.setPower(0.2);
                leftMotor.setPower(0.2);
            } else if ((System.currentTimeMillis() - time) < 900) {
                rightMotor.setPower(0);
                leftMotor.setPower(0);
            } else if ((System.currentTimeMillis() - time) < 1600) {
                rightMotor.setPower(-0.1);
                leftMotor.setPower(-0.1);
            }
            else if ((System.currentTimeMillis() - time) < 2100) {
                button.setPosition(0.15);
            } else if ((System.currentTimeMillis() - time) < 2700) {
                rightMotor.setPower(0.2);
                leftMotor.setPower(0.2);
            } else {
                i = true;
                tenthAction = true;
                ninthAction = false;
            }
        } /* End if (ninthAction) */

        if (tenthAction)
        {
            if (i) {
                time = System.currentTimeMillis();
                i = false;
            }
            if ((System.currentTimeMillis() - time) < 300) {
                climbersArm.setPosition(climbersArmOpen);
            }
            else if ((System.currentTimeMillis() - time) < 600) {
                rightMotor.setPower(0);
                leftMotor.setPower(0);
            }
            else if ((System.currentTimeMillis() - time) < 1000) {
                climbersArm.setPosition(climbersArmHalf);
            }
            else if ((System.currentTimeMillis() - time) < 1500) {
                climbersArm.setPosition(climbersArmOpen);
            }
            else if ((System.currentTimeMillis() - time) < 2000) {
                climbersArm.setPosition(climbersArmClose);
            }
            else {
                tenthAction = false;
                i = true;
            }
        } /* End if (tenthAction) */

       /* Telemetry Data */
        telemetry.addData("Right ticks", rightMotor.getCurrentPosition());
        telemetry.addData("Left ticks", leftMotor.getCurrentPosition());

        if (firstAlign) {
            actionMSG = "Aligning on wall";
        }

        if (wait1) {
            actionMSG = "Waiting for 10 seconds to pass";
        }

        if (firstAction) {
            actionMSG = "Driving forward";
        }

        if (secondAction) {
            actionMSG = "Turning in place";
        }

        if (thirdAction) {
            actionMSG = "Driving towards the wall";
        }

        if (fourthAction) {
            actionMSG = "Turning towards the beacon";
        }

        if (fifthAction) {
            actionMSG = "Driving towards the beacon";
        }

        if (sixthAction) {
            actionMSG = "Turning to face the beacon";
        }

        if (seventhAction) {
            actionMSG = "Driving backwards";
        }

        if (eighthAction) {
            actionMSG = "Driving towards the beacon";
        }

        if (ninthAction) {
            actionMSG = "Making sure button was pressed and setting position for the next action";
        }

        if (tenthAction) {
            actionMSG = "Dropping the climbers to their basket";
        }

        if (rightMotor.isBusy()) {
            telemetry.addData("Is right motor busy:", "Yes");
        }
        else {
            telemetry.addData("Is right motor busy:", "No");
        }

        if (leftMotor.isBusy()) {
            telemetry.addData("Is left motor busy:", "Yes");
        }
        else {
            telemetry.addData("Is left motor busy:", "No");
        }
        telemetry.addData("Real US Value", ultraSonic.getUltrasonicLevel());
        telemetry.addData("Calculated US Value", usValue);
        telemetry.addData("Beacon Color", beacon.getAnalysis().getColorString());
        telemetry.addData("Action:", actionMSG);
        telemetry.addData("Heading:", heading);
       /* ----------------------*/

        if ((!(ultraSonic.getUltrasonicLevel() == 255)) && (!(ultraSonic.getUltrasonicLevel() == 0))) {
            usValue = ultraSonic.getUltrasonicLevel();
        }
        heading = sensorGyro.getHeading();
        if (heading > 180) {
            heading = -(heading - 360);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
