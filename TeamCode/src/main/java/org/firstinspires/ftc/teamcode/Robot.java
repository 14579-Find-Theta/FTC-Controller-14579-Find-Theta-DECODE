package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Robot {
    Gamepad gamepad1;
    Gamepad gamepad2;

    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor motor;
    IMU imu;

    double y;
    double x;
    double rx;
    double botHeading;
    double rotX;
    double rotY;
    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    public Robot (HardwareMap map) {
        frontLeftMotor = map.dcMotor.get("frontLeftMotor");
        backLeftMotor = map.dcMotor.get("backLeftMotor");
        frontRightMotor = map.dcMotor.get("frontRightMotor");
        backRightMotor = map.dcMotor.get("backRightMotor");
        motor = map.dcMotor.get("frontRightMotor");

        imu = map.get(IMU.class, "imu");
    }

    public void setUp() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
    }
    public void getGamepadValues() {
        y = gamepad1.left_stick_y + gamepad2.left_stick_y / 2;
        x = -gamepad1.left_stick_x + -gamepad2.left_stick_x / 2;
        rx = -gamepad1.right_stick_x + -gamepad2.right_stick_x / 2;
    }
    public void IMUSetUp() {
        if (gamepad1.options) {
            imu.resetYaw();
        }

        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void botRotationStuff() {
        rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;
    }

    public void powerCalc() {
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        frontLeftPower = (rotY + rotX + rx) / denominator;
        backLeftPower = (rotY - rotX + rx) / denominator;
        frontRightPower = (rotY - rotX - rx) / denominator;
        backRightPower = (rotY + rotX - rx) / denominator;
    }

    public void runDriveBase() {
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
}
