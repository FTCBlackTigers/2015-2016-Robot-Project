package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.lasarobotics.vision.opmode.VisionOpMode;

/**
 * Created by hp on 26/01/2016.
 */
public abstract class RobotComponents extends VisionOpMode {
    DcMotor rightMotor;
    DcMotor leftMotor;
    TankDrive tankDrive;
    UltrasonicSensor ultraSonic;
    Servo button;
    Servo climbersArm;
    Servo debrisArmDoor;
    GyroSensor sensorGyro;
    DcMotor debrisArmRight;
    DcMotor debrisArmLeft;
    Servo debrisArmRotate;
    OpticalDistanceSensor ODS;



    @Override
    public void init() {
        super.init();

        rightMotor = hardwareMap.dcMotor.get("right_motor");
        //rightMotor.setDirection(DcMotor.Direction.REVERSE);

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        button = hardwareMap.servo.get("button");

        sensorGyro = hardwareMap.gyroSensor.get("gyro");

        ultraSonic = hardwareMap.ultrasonicSensor.get("ultra_sonic");

        climbersArm = hardwareMap.servo.get("climbers_arm");

        debrisArmDoor = hardwareMap.servo.get("debris_arm_door");

        debrisArmRight = hardwareMap.dcMotor.get("debris_arm_right");

        debrisArmLeft = hardwareMap.dcMotor.get("debris_arm_left");
        debrisArmLeft.setDirection(DcMotor.Direction.REVERSE);

        ODS = hardwareMap.opticalDistanceSensor.get("ods");

        tankDrive = new TankDrive(rightMotor, leftMotor);
    }
}
