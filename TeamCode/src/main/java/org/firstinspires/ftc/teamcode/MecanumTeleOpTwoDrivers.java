package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Mecanum TeleOp Two Drivers", group = "LinearOpMode")
public class MecanumTeleOpTwoDrivers extends LinearOpMode {
    Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        robot.setUp();
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            robot.getGamepadValues();
            robot.IMUSetUp();
            robot.botRotationStuff();
            robot.powerCalc();
            robot.runDriveBase();
        }

    }
}
