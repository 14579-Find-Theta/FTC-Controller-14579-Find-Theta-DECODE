/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.filter.SlewRateLimiter;
//import edu.wpi.first.wpilibj.TimedRobot;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import java.util.List;

/*
 * This OpMode illustrates how to use the Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */
public class  LimeLightOrientation {

    private Limelight3A limelight;


    public LimeLightOrientation(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)


        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0); // Switch to pipeline number 0

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();


        LLStatus status = limelight.getStatus();
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());


    }


    public void LimeLightAim(HardwareMap hardwareMap, Telemetry telemetry) {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            // Access general information
            Pose3D botpose = result.getBotpose();
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            telemetry.addData("LL Latency", captureLatency + targetingLatency);
            telemetry.addData("Parse Latency", parseLatency);
            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)
            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);

            if (result.isValid()) {
                telemetry.addData("tx", result.getTx());
                telemetry.addData("txnc", result.getTxNC());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("tync", result.getTyNC());

                telemetry.addData("Botpose", botpose.toString());

             /*   List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fiducial : fiducials) {
                    int id = fiducial.getFiducialId(); // The ID number of the fiducial
                    double x = fiducial.getTargetXDegrees();
                    double y = fiducial.getTargetYDegrees(); // Where it is (up-down)
                    double StrafeDistance_3D = fiducial.getRobotPoseTargetSpace().getPosition().y;
                    telemetry.addData("Fiducial " + id, "is " + StrafeDistance_3D + " meters away");
                    if (id == 16) {
                        if (x < 0.1) {
                            MecanumWheelManager.rot(-1);
                        } else if (x > 0.1) {
                            MecanumWheelManager.rot(1);
                        } else {
                            MecanumWheelManager.rot(0);
                        }
                    }

                }*/
                // Access barcode results
                List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
                for (LLResultTypes.BarcodeResult br : barcodeResults) {
                    telemetry.addData("Barcode", "Data: %s", br.getData());
                }

                // Access classifier results
                List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
                for (LLResultTypes.ClassifierResult cr : classifierResults) {
                    telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
                }

                // Access detector results
                List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
                for (LLResultTypes.DetectorResult dr : detectorResults) {
                    telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                }

                // Access fiducial results

                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                }


                // Access color results

                List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                for (LLResultTypes.ColorResult cr : colorResults) {
                    telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
                }

            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();

            limelight.stop();
        }

    }
}



//    // simple proportional turning control with Limelight.
//    // "proportional control" is a control algorithm in which the output is proportional to the error.
//    // in this case, we are going to return an angular velocity that is proportional to the
//    // "tx" value from the Limelight.
//    double limelight_aim_proportional()    {
//        // kP (constant of proportionality)
//        // this is a hand-tuned number that determines the aggressiveness of our proportional control loop
//        // if it is too high, the robot will oscillate.
//        // if it is too low, the robot will never reach its target
//        // if the robot never turns in the correct direction, kP should be inverted.
//        double kP = .035;
//
//        // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of
//        // your limelight 3 feed, tx should return roughly 31 degrees.
//        double targetingAngularVelocity = LimelightHelpers.getx("limelight") * kP;
//
//        // convert to radians per second for our drive method
//        targetingAngularVelocity *= Drivetrain.kMaxAngularSpeed;
//
//        //invert since tx is positive when the target is to the right of the crosshair
//        targetingAngularVelocity *= -1.0;
//
//        return targetingAngularVelocity;
//    }
//
//    // simple proportional ranging control with Limelight's "ty" value
//    // this works best if your Limelight's mount height and target mount height are different.
//    // if your limelight and target are mounted at the same or similar heights, use "ta" (area) for target ranging rather than "ty"
//    double limelight_range_proportional()
//    {
//        double kP = .1;
//        double targetingForwardSpeed = LimelightHelpers.getTY("limelight") * kP;
//        targetingForwardSpeed *= Drivetrain.kMaxSpeed;
//        targetingForwardSpeed *= -1.0;
//        return targetingForwardSpeed;
//    }
//
//
//
//    }
//
//    private void drive(boolean fieldRelative) {
//
//         final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
//         final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
//         final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);
//        // Get the x speed. We are inverting this because Playstation controllers return
//        // negative values when we push forward.
//        float xSpeed =
//                -m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getLeftY(), 0.02))
//                        * Drivetrain.kMaxSpeed;
//
//        // Get the y speed or sideways/strafe speed. We are inverting this because
//        // we want a positive value when we pull to the left. Playstation controllers
//        // return positive values when you pull to the right by default.
//        float ySpeed =
//                -m_yspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getLeftX(), 0.02))
//                        * Drivetrain.kMaxSpeed;
//
//        // Get the rate of angular rotation. We are inverting this because we want a
//        // positive value when we pull to the left (remember, CCW is positive in
//        // mathematics). Playstation controllers return positive values when you pull to
//        // the right by default.
//        float rot =
//                -m_rotLimiter.calculate(MathUtil.applyDeadband(m_controller.getRightX(), 0.02))
//                        * Drivetrain.kMaxAngularSpeed;
//
//
//
//
//
//    public void limelightRangeAim(HardwareMap hardwareMap, Telemetry telemetry) {
//
//        if(INSERTGAMEPADBUTTON())
//        {
//            final var rot_limelight = limelight_aim_proportional();
//            rot = rot_limelight;
//
//            final var forward_limelight = limelight_range_proportional();
//            xSpeed = forward_limelight;
//
//            //while using Limelight, turn off field-relative driving.
//            fieldRelative = false;
//        }
//
//
//
//







/*float KpDistance = -0.1f;

std::shared_ptr<NetworkTable> table = NetworkTable::GetTable("limelight");
float distance_error = table->GetNumber("ty");

if (joystick->GetRawButton(9))
        {
driving_adjust = KpDistance * distance_error;

left_command += distance_adjust;
right_command += distance_adjust;
}*/