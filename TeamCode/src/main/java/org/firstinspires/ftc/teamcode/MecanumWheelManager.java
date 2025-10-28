package org.firstinspires.ftc.teamcode;

//import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//import org.firstinspires.ftc.teamcode.Util.Util;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class MecanumWheelManager {

    private static CRServo ArmAxonCR;
    private static Servo ClawAxon;
    private static DcMotor frontRightMotor, frontLeftMotor, backLeftMotor, backRightMotor;

    public static void initialiseMotors(DcMotor FR, DcMotor FL, DcMotor BL, DcMotor BR) {
        frontLeftMotor = FL;
        frontRightMotor = FR;
        backLeftMotor = BL;
        backRightMotor = BR;

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public static void initialiseMotors(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.get(DcMotor.class,"2"); // FL;
        frontRightMotor = hardwareMap.get(DcMotor.class,"0"); // FR;
        backLeftMotor = hardwareMap.get(DcMotor.class,"1"); // BL;
        backRightMotor = hardwareMap.get(DcMotor.class,"3"); // BR;

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    static double bucketAngle = 135;

    public static void twoPlayerMovement(Gamepad gamepad1, Gamepad gamepad2) {
        double y = -(gamepad1.left_stick_y + gamepad2.left_stick_y / 3); // Y stick value is reversed
        double x = -(-gamepad1.left_stick_x + -gamepad2.left_stick_x / 3);
        double rx = 0;

        double botHeading = PinpointIMUManager.getZeroedIMU();
        double botHeadingDegrees = Math.toDegrees(PinpointIMUManager.getZeroedIMU());

        if (gamepad1.a) {

            // TODO: Fix the robot doing double rotations or being inneficient

            double adjusted360Heading = botHeadingDegrees%360;
            double difference = bucketAngle - adjusted360Heading;

            if (Math.abs(difference) > 5) {

                if (Math.abs(difference) < 20) {
                    if (difference < 0) {
                        rx=0.3;
                    } else if (difference > 0) {
                        rx=-0.3;
                    }
                } else {
                    if (difference < 10) {
                        rx=0.65;
                    } else if (difference > 0) {
                        rx=-0.65;
                    }
                }
//                if (difference <= 0) {
//
//                } else if (difference > 0) {
//
//                }
            } else {
                rx = 0;
            }

        } else {
            rx = -(-gamepad1.right_stick_x + -gamepad2.right_stick_x / 3);
        }


        double CPR = 8192;

        // Get the current position of the motor
        int position = backRightMotor.getCurrentPosition();
        double revolutions = position / CPR;

        if (gamepad1.back) {
            PinpointIMUManager.zeroIMU();
            //IMUManager.resetIMU();
        }

        //double botHeading = IMUManager.getTrueHeadingRadians();

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    public static void safeMovement(Gamepad gamepad1, double yaw) {
        double y = -(gamepad1.left_stick_y); // Y stick value is reversed
        double x = -(-gamepad1.left_stick_x);
        double rx = -(-gamepad1.right_stick_x);

        double CPR = 8192;

        // Get the current position of the motor
        int position = backRightMotor.getCurrentPosition();
        double revolutions = position / CPR;

        double angle = revolutions * 360;

        if (gamepad1.options) {
            IMUManager.resetIMU();
        }

        double botHeading = yaw;

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower*0.8);
        backLeftMotor.setPower(backLeftPower*0.8);
        frontRightMotor.setPower(frontRightPower*0.8);
        backRightMotor.setPower(backRightPower*0.8);
    }

    public static void runAllMotors(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public static void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public static void runMotors(List<Double> current, double goalDistance, double direction) { // in radians or degrees?

        double power = 0.5;

        double sin = Math.sin(direction-(Math.PI/4));
        double cos = Math.cos(direction-(Math.PI/4));
        double max = Math.max(Math.abs(sin),Math.abs(cos));

        double frontLeft = power * cos/max;
        double frontRight = power * sin/max;
        double backLeft = power * sin/max;
        double backRight = power * cos/max;

        PIDController pid = Util.DRIVE_PID;

        double currentX = current.get(0);
        double currentY = current.get(1);

        double distanceTravelled = Math.hypot(currentX,currentY);

        pid.setSetPoint(goalDistance);
        double output = pid.calculate(distanceTravelled);

        frontLeftMotor.setPower(frontLeft*output);
        backLeftMotor.setPower(backLeft*output);
        frontRightMotor.setPower(frontRight*output);
        backRightMotor.setPower(backRight*output);
    }

    public static void rot(double power) {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public static void move(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public static void strafe(double power) { // positive -> right
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
    }

    public static void resetEncoder() {

        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

}