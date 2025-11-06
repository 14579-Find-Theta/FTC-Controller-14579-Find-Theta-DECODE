package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.CharArrayWriter;

@TeleOp (name = "Mecanum TeleOp Two Drivers", group = "LinearOpMode")
public class MecanumTeleOpTwoDrivers extends LinearOpMode {

    //private CRServo ArmAxonCR;
    //private Servo ClawAxon;

    private DcMotor frontRightMotor;


    @Override
    public void runOpMode() throws InterruptedException {
        // Declare motors
        int DesiredAngle1;
        int DesiredAngle2;
        int DesiredAngle3;
        double DesiredAngle4;
        int DeadBand;
        // int Arm_Pos;
        double servoPower = 0;
        int WhichAngle = 0;
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
      //  ArmAxonCR = hardwareMap.get(CRServo.class, "ArmAxonCR");
        //ClawAxon = hardwareMap.get(Servo.class, "ClawAxon");
        // Find a motor in the hardware map named "frontRightMotor"
        DcMotor motor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor outtake = hardwareMap.dcMotor.get("outtake");

        //Shooter shooter = new Shooter(hardwareMap);
        //Intake intake = new Intake(hardwareMap);

        DesiredAngle1 = -250;
        DesiredAngle2 = -180;
        DesiredAngle3 = -75;
        DesiredAngle4 = -0.1;
        DeadBand = 1;
        //ArmAxonCR.setDirection(CRServo.Direction.REVERSE);

//Reset the motor encoder so that it reads zero ticks
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Reverse the right side motors. This may be wrong.
        // If robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Create an object to receive the IMU angles
       /* YawPitchRollAngles robotOrientation;
        robotOrientation = imu.getRobotYawPitchRollAngles();

        // Create angular velocity array variable
        AngularVelocity myRobotAngularVelocity;
*/
        // Retrieve the IMU from the hardware map
       // GoBildaPinpointDriver imu = hardwareMap.get(GoBildaPinpointDriver.class, "imu");

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y + -gamepad2.left_stick_y / 2; // Y stick value is reversed
            double x = -gamepad1.left_stick_x + -gamepad2.left_stick_x / 2;
            double rx = gamepad1.right_stick_x + gamepad2.right_stick_x / 2;

            double CPR = 8192;

            // Get the current position of the motor
            int position = motor.getCurrentPosition();
            double revolutions = position / CPR;

            double angle = revolutions * 360;

            double Arm_Pos = -angle;

            double LTrigger = gamepad2.left_trigger;
            //intake.runIntake(LTrigger);
            double RTrigger = gamepad2.right_trigger;
            //shooter.outtakeShoot(RTrigger);

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on some controllers.
            if (gamepad1.start) {
           //     imu.recalibrateIMU();
            }


            outtake.setPower(0);


            if (gamepad1.b) {
                outtake.setPower(1);
            }

           else if (gamepad1.a) {
                outtake.setPower(-1);
            }
//
//            else {
//                outtake.setPower(0);
//            }



         //   double botHeading = imu.getHeading(AngleUnit.RADIANS);
           // double botHeadingMeasure = imu.getHeading(AngleUnit.DEGREES);
           // telemetry.addLine("Field orientation is: " + botHeadingMeasure);

            // Rotate the movement direction counter to the bot's rotation
            //double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            //double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            /*rotX = rotX * 1.1;  // Counteract imperfect strafing

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

            telemetry.update();
            imu.update();

*/

        }

    }
}