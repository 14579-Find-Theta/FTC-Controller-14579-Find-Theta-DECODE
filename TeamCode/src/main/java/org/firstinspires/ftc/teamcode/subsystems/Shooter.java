package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    DcMotor shooterMotor;

    CRServo shooterServo1;
    CRServo shooterServo2;

    final boolean REVERSEMOTOR = false;
    final boolean REVERSESERVO = false;

    Shooter (HardwareMap map) {
        shooterMotor = map.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(REVERSEMOTOR ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        shooterServo1 = map.get(CRServo.class, "shooterServo1");
        shooterServo2 = map.get(CRServo.class, "shooterServo2");

    }

    void outtakeShoot (double RTrigger) {
        if (Math.abs(RTrigger) > 0.1) {
            shooterMotor.setPower(LTrigger-RTrigger);
        } else {
            intakeServo.setPower(0);
        }
    }

}
