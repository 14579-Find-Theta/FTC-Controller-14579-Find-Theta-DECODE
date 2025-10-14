package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    DcMotor intakeMotor;
    final boolean INTAKEREVERSE = false;

    public Intake(HardwareMap map) {
        intakeMotor = map.get(DcMotor.class, "motor");
        intakeMotor.setDirection(INTAKEREVERSE ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
    }

    public void runIntake(double LTrigger) {
        if (Math.abs(LTrigger) > 0.1) {
            intakeMotor.setPower(1);
        } else {
            intakeMotor.setPower(0);
        }
    }
}
