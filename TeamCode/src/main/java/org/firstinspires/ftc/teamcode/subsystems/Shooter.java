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

    public Shooter (HardwareMap map) {
        shooterMotor = map.get(DcMotor.class, "motor");
        shooterMotor.setDirection(REVERSEMOTOR ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        shooterServo1 = map.get(CRServo.class, "spin");
        shooterServo2 = map.get(CRServo.class, "spin1");

    }

    public void outtakeShoot (double RTrigger) {
        if (Math.abs(RTrigger) > 0.1) {
            shooterMotor.setPower(1);
            shooterServo1.setPower(REVERSESERVO ? -1 : 1);
            shooterServo2.setPower(REVERSESERVO ? 1 : -1);
        } else {
            shooterMotor.setPower(0);
            shooterServo1.setPower(0);
            shooterServo2.setPower(0);
        }
    }

}
