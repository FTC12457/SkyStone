
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
--------------------------------------------PID Loop-------------------------------------------
This class is a PID (Proportional, Integral, Derivative) Loop which gives better control of the
robot in both the autonomous and teleoperational op modes. It can be used with any motor inside
a loop with time increments (such as the teleop) but must be calibrated for three coefficients:
kP, kI, and kD. The parameter updateTime specifies the time taken for the loop to repeat.
-----------------------------------------------------------------------------------------------
*/

class PIDLoop {

    // Initializes variables
    private LinearOpMode opMode;
    private Telemetry telemetryInstance;

    DcMotor motor;
    private double kP;
    private double kD;
    private double kI;

    private double err;
    private double errP = 0;
    private double errS = 0;
    private double P;
    private double D;
    private double I;
    private double correction;

    PIDLoop(double kP, double kD, double kI, LinearOpMode linearOpMode, DcMotor dcmotor, Telemetry telemetry) {
        opMode = linearOpMode;
        motor = dcmotor;
        telemetryInstance = telemetry;

        this.kP = kP;
        this.kD = kD;
        this.kI = kI;
    }

    void pidRun(double target, double value, double updateMilliseconds, double initPower) {

        if (opMode.opModeIsActive()) {

            motor.setPower(initPower);

            err = target - value;
            errS = errS + err;

            P = kP * err;
            D = kD * (err - errP) / updateMilliseconds;
            I = kI * errS * updateMilliseconds;
            correction = P + D + I;
            motor.setPower(motor.getPower() + correction);

            errP = target - value;

            telemetryInstance.addData("Error:", err);
        }
    }
}