package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "RampDrive", group = "Mechanisms")
public class RampDrive extends LinearOpMode {

    Hardware robot = new Hardware();

    private float FORWARDNESS_MULTIPLIER = 0.7f;
    private float STRAFENESS_MULTIPLIER = 1f;
    private float TURNYNESS_MULTIPLIER = 0.7f;

    double BL, BR, FL, FR = 0;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            rampDrive();

            telemetry.update();
            sleep(25);
        }
    }

    public void rampDrive() {

        /* Three components of robot movement: Forwards/Backwards, Left/Right, and Turning.
         */

        double FORWARDNESS = gamepad1.left_stick_y * FORWARDNESS_MULTIPLIER;
        double STRAFENESS = gamepad1.left_stick_x * STRAFENESS_MULTIPLIER;
        double TURNYNESS = gamepad1.right_stick_x * TURNYNESS_MULTIPLIER;

        /* All three components of robot movement are combined into smooth acceleration in the
        motors. The player controls increment of the motor power, instead of the motor power
        directly.
         */

        BL = BL + (-FORWARDNESS - STRAFENESS - TURNYNESS) * 25 / 1000;
        BR = BR + (-FORWARDNESS + STRAFENESS + TURNYNESS) * 25 / 1000;
        FL = FL + (-FORWARDNESS + STRAFENESS - TURNYNESS) * 25 / 1000;
        FR = FR + (-FORWARDNESS - STRAFENESS + TURNYNESS) * 25 / 1000;

        robot.backLeftDrive.setPower(Math.min(1, BL));
        robot.backRightDrive.setPower(Math.min(1, BR));
        robot.frontLeftDrive.setPower(Math.min(1, FL));
        robot.frontRightDrive.setPower(Math.min(1, FR));

        /* Telemetry for reference. (Mostly debugging.) */

        telemetry.addData("Forwardness%3A", FORWARDNESS);
        telemetry.addData("Strafeness%3A", STRAFENESS);
        telemetry.addData("Turnyness%3A", TURNYNESS);
        telemetry.addData("LEFT REAR", robot.backLeftDrive.getPower());
        telemetry.addData("RIGHT REAR", robot.backRightDrive.getPower());
        telemetry.addData("LEFT FRONT", robot.frontLeftDrive.getPower());
        telemetry.addData("RIGHT FRONT", robot.frontRightDrive.getPower());

    }
}
