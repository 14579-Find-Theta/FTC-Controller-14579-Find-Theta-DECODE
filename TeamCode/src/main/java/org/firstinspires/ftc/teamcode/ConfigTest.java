package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp (name = "Mecanum TeleOp Config Test", group = "LinearOpMode")
public class ConfigTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motor1 = hardwareMap.dcMotor.get("0");
        DcMotor motor2 = hardwareMap.dcMotor.get("1");
        DcMotor motor3 = hardwareMap.dcMotor.get("2");
        DcMotor motor4 = hardwareMap.dcMotor.get("3");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.a) {motor1.setPower(1);};
            if (gamepad1.b) {motor2.setPower(1);};
            if (gamepad1.x) {motor3.setPower(1);};
            if (gamepad1.y) {motor4.setPower(1);};
        }

    }
}
